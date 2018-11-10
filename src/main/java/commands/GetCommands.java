package commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetCommands {

    String result = "";
    InputStream inputStream;

    public String getPropertyValues(String property) throws IOException{

        Properties properties = new Properties();

        inputStream = getClass().getClassLoader().getResourceAsStream("commandOptions.properties");

        if(inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file 'commandOptions.properties' not found in the classpath");
        }

        return properties.getProperty(property);
    }
}
