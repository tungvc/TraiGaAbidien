using PhoneNumber.BO;
using PhoneNumber.Models;
using System;

namespace PhoneNumber
{
    class Program
    {
        static void Main(string[] args)
        {

            long lStartTime = DateTimeOffset.Now.ToUnixTimeMilliseconds();

            PhoneInfo[] oA = new PhoneInfo[50000000];

            int index = 0;
            using (PhoneReader phoneReader = new PhoneReader("M:/phone.txt"))
            {
                foreach (PhoneInfo phoneInfo in phoneReader)
                {
                    oA[index++] = phoneInfo;
                    if (index % 1000000 == 0)
                    {
                        Console.WriteLine(index);
                    }
                }
            }

            //long[] oA = new long[50000000];
            //Random oR = new Random();
            //for (int i = 0; i < 50 * 1000 * 1000; i++)
            //{
            //    //oA[i] = new PhoneInfo(oR.Next(), oR.Next(), oR.Next());
            //    oA[i] = oR.Next();
            //    if (i % 1000000 == 0)
            //    {

            //        Console.WriteLine(i);
            //    }
            //}

            long lEndInputTime = DateTimeOffset.Now.ToUnixTimeMilliseconds();
            Console.WriteLine(lEndInputTime - lStartTime);
            PhoneInfo.PhoneInfoComparer comparer = new PhoneInfo.PhoneInfoComparer();
            Array.Sort(oA, 0, index, comparer);
            //Array.Sort(oA, 0, index);

            for (int i = 0; i < 50 * 1000 * 1000; i++)
            {
                if (i < 20)
                    //Console.WriteLine(x.phoneNumber);
                    Console.WriteLine(oA[i].phoneNumber);
            }

            long lEndSortTime = DateTimeOffset.Now.ToUnixTimeMilliseconds();
            Console.WriteLine(lEndSortTime - lEndInputTime);

            Console.ReadLine();
        }
    }
}