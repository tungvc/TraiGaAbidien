using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GenerateTestCase
{
    class Program
    {
        static Random oR = new Random();
        static void Main(string[] args)
        {
            using (System.IO.StreamWriter file = new System.IO.StreamWriter(@"D:\phone.txt", false))
            {
                
                for (int i = 0; i < 50000000; i++)
                {
                    file.WriteLine($"{oR.Next()},{RandomDay().ToString("yyyy-MM-dd")},{RandomDay().ToString("yyyy-MM-dd")}");
                    if (i % 1000000 == 0)
                    {
                        Console.WriteLine(i);
                    }
                }
            }
        }

        static DateTime RandomDay()
        {
            DateTime start = new DateTime(1995, 1, 1);
            int range = (DateTime.Today - start).Days;
            return start.AddDays(oR.Next(range));
        }
    }
}
