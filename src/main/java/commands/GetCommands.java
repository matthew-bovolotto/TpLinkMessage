package commands;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class GetCommands {

    InputStream inputStream;

    private static PropertiesConfiguration configuration;

    static {
        try {
            configuration = new PropertiesConfiguration("commandOptions.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    public synchronized String getPropertyValues(String property) throws IOException{

        /*Properties properties = new Properties();

        inputStream = getClass().getClassLoader().getResourceAsStream("commandOptions.properties");

        if(inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file 'commandOptions.properties' not found in the classpath");
        }

        return properties.getProperty(property);*/
        return (String) configuration.getProperty(property);
    }

    public ArrayList<String> getPropertyList() throws IOException{

        Properties properties = new Properties();

        inputStream = getClass().getClassLoader().getResourceAsStream("commandOptions.properties");

        if(inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file 'commandOptions.properties' not found in the classpath");
        }

        StringWriter writer = new StringWriter();
        properties.list(new PrintWriter(writer));
        String props = writer.getBuffer().toString();

        ArrayList<String> propsList = new ArrayList<String>();

        String[] propertyList = props.split("\\r?\\n");
        for(String property : propertyList){
            if(!property.contains("-- listing properties --")){
                String command = property.split("=")[0];
                propsList.add(command);
            }
        }

        return propsList;
    }
}
