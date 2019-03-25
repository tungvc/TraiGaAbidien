using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PhoneNumber.Models
{
    public struct PhoneInfo
    {
        public static class Const
        {
            public static string DATE_FORMAT = "yyyy-MM-dd";
        }

        public ulong phoneNumber;
        public int activationDate;
        public int deactivationDate;

        /// <summary>
        /// Order by phone number ACS, if same phone number then order by activation date ACS
        /// </summary>
        public class PhoneInfoComparer : IComparer<PhoneInfo>
        {
            public int Compare(PhoneInfo x, PhoneInfo y)
            {
                if (x.phoneNumber > y.phoneNumber)
                    return 1;
                else if (x.phoneNumber < y.phoneNumber)
                    return -1;
                else if (x.activationDate > y.activationDate)
                    return 1;
                else if (x.activationDate < y.activationDate)
                    return -1;
                else return 0;
            }
        }

        public PhoneInfo(ulong _phoneNumber, int _activationDate, int _deactivationDate)
        {
            phoneNumber = _phoneNumber;
            activationDate = _activationDate;
            deactivationDate = _deactivationDate;
        }

        public static bool ParsePhoneInfo(string _serializedPhoneInfo, out PhoneInfo _phoneInfo)
        {
            bool result = false;
            _phoneInfo = new PhoneInfo(0, 0, 0);

            if (_serializedPhoneInfo != null && _serializedPhoneInfo.Length > 0)
            {
                String[] phoneInfoParts = _serializedPhoneInfo.Split(',');

                if (phoneInfoParts.Length > 1)
                {
                    try
                    {
                        ulong phoneNumber = ConvertPhoneNumberToLong(phoneInfoParts[0]);
                        int activationDate = ConvertDateStringToInt(phoneInfoParts[1]);
                        int deactivationDate = 0;
                        if (phoneInfoParts.Length > 2 && phoneInfoParts[2] != null && phoneInfoParts[2].Length > 0)
                        {
                            deactivationDate = ConvertDateStringToInt(phoneInfoParts[2]);
                        }

                        _phoneInfo = new PhoneInfo(phoneNumber, activationDate, deactivationDate);
                        result = true;
                    }
                    catch (Exception ex)
                    {
                        //Console.WriteLine(ex);
                    }
                }
            }

            return result;
        }

        /// <summary>
        /// Convert date string (yyyy-MM-dd) to integer. E.g: 2019-07-22 => 20190722
        /// </summary>
        /// <param name="_dateString"></param>
        /// <returns></returns>
        private static int ConvertDateStringToInt(String _dateString)
        {
            int result = 0;
            int iBase = 1;

            if (_dateString != null)
            {
                for (int i = _dateString.Length - 1; i >= 0; i--)
                {
                    if (char.IsDigit(_dateString[i]))
                    {
                        result += (_dateString[i] - '0') * iBase;
                        iBase *= 10;
                    }
                }
            }

            return result;
        }

        public string GetActivationDateAsString()
        {
            int day = activationDate % 100;
            int month = activationDate / 100 % 100;
            int year = activationDate / 10000;

            return new DateTime(year, month, day).ToString(Const.DATE_FORMAT);
        }

        /// <summary>
        /// Convert phone number to long. Support upto 3 leading zero phone number
        /// </summary>
        /// <param name="_phoneNumber"></param>
        /// <returns></returns>
        private static ulong ConvertPhoneNumberToLong(String _phoneNumber)
        {
            ulong leadingZeroCount = (ulong)_phoneNumber.TakeWhile(c => c == '0').Count();

            // support upto 3 leading zero
            if (leadingZeroCount > 3)
            {
                leadingZeroCount = 3;
            }

            // Using first 2 bit to store number of leading zero
            return ulong.Parse(_phoneNumber) | (leadingZeroCount << 62);
        }

        public string GetPhoneNumberString()
        {
            // Get first 2 bits to calculate number of leading zero
            int leadingZeroCount = (int)(phoneNumber >> 62);

            // Reset first 2 bits then convert to string
            ulong realPhoneNumber = phoneNumber & ~(3 << 62);

            return new String('0', leadingZeroCount) + realPhoneNumber;
        }

        #region TDD

#if (DEBUG)

        [TestFixture]
        [Category("PhoneInfo")]
        public new sealed class Tests
        {
            #region ParsePhoneInfo

            [Test]
            public void ParsePhoneInfo_AsExpected()
            {
                PhoneInfo phoneInfo;
                Assert.IsTrue(ParsePhoneInfo("0910000001,2016-01-01,2016-02-02", out phoneInfo));

                Assert.AreEqual("0910000001", phoneInfo.GetPhoneNumberString());
                Assert.AreEqual(20160101, phoneInfo.activationDate);
                Assert.AreEqual(20160202, phoneInfo.deactivationDate);
            }

            [Test]
            public void ParsePhoneInfo_EmptyDeactivationDate()
            {
                PhoneInfo phoneInfo;
                Assert.IsTrue(ParsePhoneInfo("0910000001,2016-01-01,", out phoneInfo));

                Assert.AreEqual("0910000001", phoneInfo.GetPhoneNumberString());
                Assert.AreEqual(20160101, phoneInfo.activationDate);
                Assert.AreEqual(0, phoneInfo.deactivationDate);


                Assert.IsTrue(ParsePhoneInfo("0910000001,2016-01-01", out phoneInfo));

                Assert.AreEqual("0910000001", phoneInfo.GetPhoneNumberString());
                Assert.AreEqual(20160101, phoneInfo.activationDate);
                Assert.AreEqual(0, phoneInfo.deactivationDate);
            }

            [Test]
            public void ParsePhoneInfo_InvalidFormat()
            {
                PhoneInfo phoneInfo;
                Assert.IsFalse(ParsePhoneInfo("Invalid format", out phoneInfo));
                Assert.IsFalse(ParsePhoneInfo("0910000001;2016-01-01;2016-01-01", out phoneInfo));
                Assert.IsFalse(ParsePhoneInfo("0910000001", out phoneInfo));
            }

            #endregion

            #region ConvertDateStringToInt

            [Test]
            public void ConvertDateStringToInt_AsExpected()
            {
                Assert.AreEqual(20160322, ConvertDateStringToInt("2016-03-22"));
                Assert.AreEqual(19911201, ConvertDateStringToInt("1991-12-01"));
            }

            public void ConvertDateStringToInt_Null()
            {
                Assert.AreEqual(0, ConvertDateStringToInt(null));
            }

            #endregion

            #region GetActivationDateAsString

            [Test]
            public void GetActivationDateAsString_AsExpected()
            {
                PhoneInfo phoneInfo = new PhoneInfo();
                phoneInfo.activationDate = 20160322;
                Assert.AreEqual("2016-03-22", phoneInfo.GetActivationDateAsString());

                phoneInfo.activationDate = 19911201;
                Assert.AreEqual("1991-12-01", phoneInfo.GetActivationDateAsString());
            }

            #endregion

            #region ConvertPhoneNumberToLong

            [Test]
            public void ConvertPhoneNumberToLong_AsExpected()
            {
                Assert.AreEqual(4611686019337387905, ConvertPhoneNumberToLong("0910000001"));

                Assert.AreEqual(9223372037764775809, ConvertPhoneNumberToLong("00910000001"));

                Assert.AreEqual(13835058056192163713, ConvertPhoneNumberToLong("000910000001"));
            }

            #endregion

            #region GetPhoneNumberString

            [Test]
            public void GetPhoneNumberString_AsExpected()
            {
                PhoneInfo phoneInfo = new PhoneInfo();
                phoneInfo.phoneNumber = 4611686019337387905;
                Assert.AreEqual("0910000001", phoneInfo.GetPhoneNumberString());

                phoneInfo.phoneNumber = 9223372037764775809;
                Assert.AreEqual("00910000001", phoneInfo.GetPhoneNumberString());

                phoneInfo.phoneNumber = 13835058056192163713;
                Assert.AreEqual("000910000001", phoneInfo.GetPhoneNumberString());
            }

            #endregion

            #region PhoneInfoComparer

            [Test]
            public void PhoneInfoComparer_Larger()
            {
                PhoneInfoComparer comparer = new PhoneInfoComparer();
                Assert.AreEqual(1, comparer.Compare(new PhoneInfo(0910000020, 0, 0), new PhoneInfo(0910000018, 0, 0)));
            }

            [Test]
            public void PhoneInfoComparer_Smaller()
            {
                PhoneInfoComparer comparer = new PhoneInfoComparer();
                Assert.AreEqual(-1, comparer.Compare(new PhoneInfo(0910000018, 0, 0), new PhoneInfo(0910000020, 0, 0)));

            }

            [Test]
            public void PhoneInfoComparer_EqualsPhoneNumber_ActivationDateLarger()
            {
                PhoneInfoComparer comparer = new PhoneInfoComparer();
                Assert.AreEqual(1, comparer.Compare(new PhoneInfo(0910000020, 20171123, 0), new PhoneInfo(0910000020, 20171122, 0)));
            }

            [Test]
            public void PhoneInfoComparer_EqualsPhoneNumber_ActivationDateSmaller()
            {
                PhoneInfoComparer comparer = new PhoneInfoComparer();
                Assert.AreEqual(-1, comparer.Compare(new PhoneInfo(0910000020, 20171121, 0), new PhoneInfo(0910000020, 20171122, 0)));
            }

            [Test]
            public void PhoneInfoComparer_Equals()
            {
                PhoneInfoComparer comparer = new PhoneInfoComparer();
                Assert.AreEqual(0, comparer.Compare(new PhoneInfo(0910000020, 20171122, 0), new PhoneInfo(0910000020, 20171122, 0)));
            }

            #endregion
        }

#endif

        #endregion
    }
}
