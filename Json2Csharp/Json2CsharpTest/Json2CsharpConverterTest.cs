using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Json2Csharp;

namespace Json2CsharpTest
{
    [TestClass]
    public class Json2CsharpConverterTest
    {
        [TestMethod]
        public void Init_DefaultConstructor()
        {   
            /* Test default constructor */
            Json2CsharpConverter converter = new Json2CsharpConverter();
            String assetsDirPath = converter.AssetsDirPath;
            Assert.IsNotNull(assetsDirPath);
        }
    }
}
