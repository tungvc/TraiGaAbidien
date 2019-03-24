using System;
using System.Collections.Generic;
using System.Text;

namespace PhoneNumber.Models
{
    public struct PhoneInfo
    {
        public static class Const
        {
            public static string FORTMAT_DAY = "yyyy-MM-dd";
        }

        public class PhoneInfoComparer : IComparer<PhoneInfo>
        {
            public int Compare(PhoneInfo x, PhoneInfo y)
            {
                return (int)(x.phoneNumber - y.phoneNumber);
            }
        }

        static Random oR = new Random();
        public long phoneNumber;
        private int activationDate;
        private int deactivationDate;

        public PhoneInfo(long _phoneNumber, int _activationDate, int _deactivationDate)
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
                        long phoneNumber = oR.Next();// long.Parse(phoneInfoParts[0]);
                        int activationDate = (int)DateTimeOffset.ParseExact(phoneInfoParts[1], Const.FORTMAT_DAY, null).ToUnixTimeSeconds();
                        int deactivationDate = 0;
                        if (phoneInfoParts.Length > 2 && phoneInfoParts[2] != null && phoneInfoParts[2].Length > 0)
                        {
                            deactivationDate = (int)DateTimeOffset.ParseExact(phoneInfoParts[2], Const.FORTMAT_DAY, null).ToUnixTimeSeconds();
                        }

                        _phoneInfo = new PhoneInfo(phoneNumber, activationDate, deactivationDate);
                        result = true;
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine(ex);
                    }
                }
            }

            return result;
        }
    }
}
