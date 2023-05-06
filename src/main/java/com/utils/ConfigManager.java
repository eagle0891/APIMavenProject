package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
//    private static ConfigManager configManager;
    public static Properties prop = new Properties();

    public ConfigManager() {
//        InputStream inputStream = ConfigManager.class.getResourceAsStream("../resource/config.properties");
//        prop.load(inputStream);

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

//    public static ConfigManager getInstance() throws IOException {
//        if(configManager == null){
//            synchronized (ConfigManager.class){
//                configManager = new ConfigManager();
//            }
//        }
//        return configManager;
//    }

    public String getString(String key){
        return System.getProperty(key, prop.getProperty(key));
    }
}
