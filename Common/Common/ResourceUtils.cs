using System.IO;
using System.Reflection;

namespace Common
{
    public static class ResourceUtils
    {
        public static List<string> GetDataFromResource(Assembly assembly, string path)
        {
            var txtFile = new List<string>();

            using var stream = assembly.GetManifestResourceStream(path);
            if (stream != null)
            {
                using var reader = new StreamReader(stream);

                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    txtFile.Add(line);
                }
            }
            return txtFile;
        }
    }
}