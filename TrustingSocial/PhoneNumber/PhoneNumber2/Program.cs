using PhoneNumber.BO;
using PhoneNumber.Models;
using System;

namespace PhoneNumber
{
    class Program
    {
        public class Const
        {
            public static string INPUT_PATH = @"input.csv";
            public static string OUTPUT_PATH = @"output.csv";
        }

        static void Main(string[] args)
        {
            PhoneInfo[] phoneList = new PhoneInfo[50000000];
            int size = 0;
            using (PhoneReader phoneReader = new PhoneReader(Const.INPUT_PATH))
            {
                foreach (PhoneInfo phoneInfo in phoneReader)
                {
                    phoneList[size++] = phoneInfo;
                }
            }

            Phone_BO.ExportActivationDate(phoneList, size, Const.OUTPUT_PATH);
        }
    }
}