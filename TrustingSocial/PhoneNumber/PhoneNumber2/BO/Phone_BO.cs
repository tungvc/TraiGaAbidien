using NUnit.Framework;
using PhoneNumber.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text;

namespace PhoneNumber.BO
{
    public class Phone_BO
    {
        public static void ExportActivationDate(PhoneInfo[] _phoneList, int _size, string _outputPath)
        {
            PhoneInfo.PhoneInfoComparer comparer = new PhoneInfo.PhoneInfoComparer();
            Array.Sort(_phoneList, 0, _size, comparer);

            int index = 0;
            using (StreamWriter outputFile = new StreamWriter(_outputPath, false))
            {
                outputFile.WriteLine("PHONE_NUMBER,REAL_ACTIVATION_DATE");
                while (index < _size)
                {
                    PhoneInfo currentPhoneInfo = _phoneList[index++];
                    while (index < _size && currentPhoneInfo.phoneNumber == _phoneList[index].phoneNumber)
                    {
                        if (currentPhoneInfo.deactivationDate > 0)
                        {
                            if (currentPhoneInfo.deactivationDate < _phoneList[index].activationDate)
                            {
                                // the owner of this phone number stop using
                                currentPhoneInfo = _phoneList[index];
                            }
                            else
                            {
                                // change from prepaid plan to postpaid plan, or vice versa
                                // update deactivation date
                                currentPhoneInfo.deactivationDate = _phoneList[index].deactivationDate;
                            }
                        }
                        index++;
                    }
                    outputFile.WriteLine($"{currentPhoneInfo.GetPhoneNumberString()},{currentPhoneInfo.GetActivationDateAsString()}");
                }
            }
        }

        #region TDD

#if (DEBUG)

        [TestFixture]
        [Category("PhoneReader")]
        public new sealed class Tests
        {
            Random random = new Random();
            String outputFilePath = @"TestFolder/phone.txt";

            #region Setup / TearDown

            [TestFixtureSetUp]
            public void TestFixtureSetUp()
            {
                Directory.CreateDirectory(Path.GetDirectoryName(outputFilePath));
            }

            #endregion

            #region OpenFile

            [Test]
            public void ExportActivationDate_Empty()
            {
                PhoneInfo[] phoneList = new PhoneInfo[100];
                ExportActivationDate(phoneList, 0, outputFilePath);

                Assert.AreEqual(@"PHONE_NUMBER,REAL_ACTIVATION_DATE
", File.ReadAllText(outputFilePath));
            }


            [Test]
            public void ExportActivationDate_NoDeactivationDate()
            {
                PhoneInfo[] phoneList = new PhoneInfo[100];
                PhoneInfo phoneInfo;

                PhoneInfo.ParsePhoneInfo("0987000001,2015-12-01,2016-12-01", out phoneInfo);
                phoneList[0] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2016-12-01,", out phoneInfo);
                phoneList[1] = phoneInfo;

                ExportActivationDate(phoneList, 2, outputFilePath);

                Assert.AreEqual(@"PHONE_NUMBER,REAL_ACTIVATION_DATE
0987000001,2015-12-01
", File.ReadAllText(outputFilePath));
            }

            [Test]
            public void ExportActivationDate_ChangePlan_RandomOrder()
            {
                PhoneInfo[] phoneList = new PhoneInfo[100];
                PhoneInfo phoneInfo;

                PhoneInfo.ParsePhoneInfo("0987000001,2016-12-01,2016-12-21", out phoneInfo);
                phoneList[0] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2013-01-01,2014-12-01", out phoneInfo);
                phoneList[1] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2014-12-01,2016-12-01", out phoneInfo);
                phoneList[2] = phoneInfo;

                ExportActivationDate(phoneList, 3, outputFilePath);

                Assert.AreEqual(@"PHONE_NUMBER,REAL_ACTIVATION_DATE
0987000001,2013-01-01
", File.ReadAllText(outputFilePath));
            }

            [Test]
            public void ExportActivationDate_StopAndReuseByAnotherUser()
            {
                PhoneInfo[] phoneList = new PhoneInfo[100];
                PhoneInfo phoneInfo;

                PhoneInfo.ParsePhoneInfo("0987000001,2017-12-01,2018-12-21", out phoneInfo);
                phoneList[0] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2013-01-01,2014-12-01", out phoneInfo);
                phoneList[1] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2014-12-01,2016-12-01", out phoneInfo);
                phoneList[2] = phoneInfo;

                ExportActivationDate(phoneList, 3, outputFilePath);

                Assert.AreEqual(@"PHONE_NUMBER,REAL_ACTIVATION_DATE
0987000001,2017-12-01
", File.ReadAllText(outputFilePath));
            }

            [Test]
            public void ExportActivationDate_MultiUser()
            {
                PhoneInfo[] phoneList = new PhoneInfo[100];
                PhoneInfo phoneInfo;

                PhoneInfo.ParsePhoneInfo("0987000001,2016-03-01,2016-05-01", out phoneInfo);
                phoneList[0] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000002,2016-02-01,2016-03-01", out phoneInfo);
                phoneList[1] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2016-01-01,2016-03-01", out phoneInfo);
                phoneList[2] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2016-12-01,", out phoneInfo);
                phoneList[3] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000002,2016-03-01,2016-05-01", out phoneInfo);
                phoneList[4] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000003,2016-01-01,2016-01-10", out phoneInfo);
                phoneList[5] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2016-09-01,2016-12-01", out phoneInfo);
                phoneList[6] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000002,2016-05-01,", out phoneInfo);
                phoneList[7] = phoneInfo;
                PhoneInfo.ParsePhoneInfo("0987000001,2016-06-01,2016-09-01", out phoneInfo);
                phoneList[8] = phoneInfo;

                ExportActivationDate(phoneList, 9, outputFilePath);

                Assert.AreEqual(@"PHONE_NUMBER,REAL_ACTIVATION_DATE
0987000001,2016-06-01
0987000002,2016-02-01
0987000003,2016-01-01
", File.ReadAllText(outputFilePath));
            }

            #endregion
        }

#endif

        #endregion

    }
}
