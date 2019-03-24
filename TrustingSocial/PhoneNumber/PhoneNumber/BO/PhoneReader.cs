using PhoneNumber.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.IO;

namespace PhoneNumber.BO
{
    public class PhoneReader : IEnumerator<PhoneInfo>, IEnumerable<PhoneInfo>, IDisposable
    {
        private bool mbHasNext;
        private StreamReader mStreamReader;
        private string filePath;
        private PhoneInfo current;

        public PhoneReader(String _path)
        {
            filePath = _path;
            //current = new PhoneInfo(0, 0, 0);
            OpenFile();
        }

        public PhoneInfo Current => current;

        object IEnumerator.Current => current;

        public void Dispose()
        {
            if (mStreamReader != null)
            {
                mStreamReader.Dispose();
            }
        }

        public IEnumerator<PhoneInfo> GetEnumerator()
        {
            return this;
        }

        public bool MoveNext()
        {
            string line;
            mbHasNext = false;
            try
            {
                while (!mbHasNext && (line = mStreamReader.ReadLine()) != null)
                {
                    mbHasNext = PhoneInfo.ParsePhoneInfo(line, out current);
                }
            }
            catch (IOException ex)
            {
                Console.WriteLine(ex);
            }

            return mbHasNext;
        }

        public void Reset()
        {
            OpenFile();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return this;
        }

        private void OpenFile()
        {
            mbHasNext = false;
            try
            {
                Dispose();
                if (File.Exists(filePath))
                {
                    FileStream fileStream = new FileStream(filePath, FileMode.Open, FileAccess.Read);
                    mStreamReader = new StreamReader(fileStream);
                    mbHasNext = true;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }
    }
}
