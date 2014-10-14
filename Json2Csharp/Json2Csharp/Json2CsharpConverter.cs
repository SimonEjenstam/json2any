using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;


namespace Json2Csharp
{
    public class Json2CsharpConverter
    {

        private const String DEFAULT_ASSETS_PATH = @"assets";

        private String mAssetsDirPath = null;

        public String AssetsDirPath
        {
            get
            {
                return mAssetsDirPath;
            }
        }


        public Json2CsharpConverter() : this(Path.Combine(Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location), DEFAULT_ASSETS_PATH))
        {
           
        }

        public Json2CsharpConverter(String assetsDirPath)
        {
            if (assetsDirPath != null)
            {
                if (Directory.Exists(assetsDirPath))
                {
                    mAssetsDirPath = assetsDirPath;
                } else {
                    throw new DirectoryNotFoundException("No directory exists with that name");
                }
            }
            else
            {
                throw new ArgumentNullException("You must supply a valid path");
            }
            
        }
    }
}
