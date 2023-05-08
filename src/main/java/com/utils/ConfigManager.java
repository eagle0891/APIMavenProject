package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    public static Properties prop = new Properties();

    public ConfigManager() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    "src/resource/config.properties");
            prop.load(ip);
        } catch (IOException e){
            System.out.println("** Can't find the config.properties file... **");
            e.printStackTrace();
        }
    }

    public String getString(String key){
        return System.getProperty(key, prop.getProperty(key));
    }
}
