using PhoneNumber.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.IO;
using NUnit.Framework;

namespace PhoneNumber.BO
{
    public class PhoneReader : IEnumerator<PhoneInfo>, IEnumerable<PhoneInfo>, IDisposable
    {
        private bool hasNext;
        private FileStream fileStream;
        private StreamReader streamReader;
        private string filePath;
        private PhoneInfo current;

        public PhoneReader(String _path)
        {
            filePath = _path;
            OpenFile();
        }

        public PhoneInfo Current => current;

        object IEnumerator.Current => current;

        public void Dispose()
        {
            streamReader?.Dispose();
            fileStream?.Dispose();
        }

        public IEnumerator<PhoneInfo> GetEnumerator()
        {
            return this;
        }

        public bool MoveNext()
        {
            if (hasNext)
            {
                string line;
                hasNext = false;
                try
                {
                    while (!hasNext && (line = streamReader.ReadLine()) != null)
                    {
                        hasNext = PhoneInfo.ParsePhoneInfo(line, out current);
                    }
                }
                catch (IOException ex)
                {
                    Console.WriteLine(ex);
                }
            }

            return hasNext;
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
            hasNext = false;
            try
            {
                Dispose();
                if (File.Exists(filePath))
                {
                    fileStream = new FileStream(filePath, FileMode.Open, FileAccess.Read);
                    streamReader = new StreamReader(fileStream);
                    hasNext = true;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }

        #region TDD

#if (DEBUG)

        [TestFixture]
        [Category("PhoneReader")]
        public new sealed class Tests
        {
            Random random = new Random();
            String testFilePath = @"TestFolder/phone.txt";

            #region Setup / TearDown

            [TestFixtureSetUp]
            public void TestFixtureSetUp()
            {
                Directory.CreateDirectory(Path.GetDirectoryName(testFilePath));
            }

            [SetUp]
            public void SetUp()
            {
                using (StreamWriter file = new StreamWriter(testFilePath, false))
                {
                    file.WriteLine(@"PHONE_NUMBER,ACTIVATION_DATE,DEACTIVATION_DATE
0987000001,2016-03-21,2016-05-01
0987000002,2016-05-01,
0987000001,2016-06-01,2016-09-01
00987000003,2016-01-01,2016-01-10
000987000003,2016-01-01,2016-01-10");

                }
            }

            [TearDown]
            public void TearDown()
            {
                File.Delete(testFilePath);
            }

            [TestFixtureTearDown]
            public void TestFixtureTearDown()
            {
            }

            #endregion

            #region OpenFile

            [Test]
            public void OpenFile_AsExpected()
            {
                using (PhoneReader phoneReader = new PhoneReader(testFilePath))
                {
                    Assert.IsTrue(phoneReader.hasNext);
                    Assert.IsNotNull(phoneReader.streamReader);
                }
            }

            [Test]
            public void OpenFile_NotFound()
            {
                using (PhoneReader phoneReader = new PhoneReader("NotFound"))
                {
                    Assert.IsFalse(phoneReader.hasNext);
                    Assert.IsNull(phoneReader.streamReader);
                }
            }

            #endregion

            #region MoveNext

            [Test]
            public void MoveNext_AsExpected()
            {
                using (PhoneReader phoneReader = new PhoneReader(testFilePath))
                {
                    Assert.IsTrue(phoneReader.hasNext);
                    Assert.IsNotNull(phoneReader.streamReader);

                    Assert.IsTrue(phoneReader.MoveNext());
                    Assert.AreEqual("0987000001", phoneReader.Current.GetPhoneNumberString());
                    Assert.AreEqual(20160321, phoneReader.Current.activationDate);
                    Assert.AreEqual(20160501, phoneReader.Current.deactivationDate);

                    Assert.IsTrue(phoneReader.MoveNext());
                    Assert.AreEqual("0987000002", phoneReader.Current.GetPhoneNumberString());
                    Assert.AreEqual(20160501, phoneReader.Current.activationDate);
                    Assert.AreEqual(0, phoneReader.Current.deactivationDate);
                }
            }

            [Test]
            public void MoveNext_FileNotFound()
            {
                using (PhoneReader phoneReader = new PhoneReader("NotFound"))
                {
                    Assert.IsFalse(phoneReader.hasNext);
                    Assert.IsFalse(phoneReader.MoveNext());
                }
            }

            #endregion
        }

#endif

        #endregion
    }
}
