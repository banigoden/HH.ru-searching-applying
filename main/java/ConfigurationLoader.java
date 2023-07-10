import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
    private Properties properties;

    public ConfigurationLoader(String filePath) {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception if unable to load the configuration file
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
