package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager configManager;
    private static final Properties prop = new Properties();

    private ConfigManager() throws IOException {
        InputStream inputStream = ConfigManager.class.getResourceAsStream("../resource/config.properties");
        prop.load(inputStream);
    }

    public static ConfigManager getInstance() throws IOException {
        if(configManager == null){
            synchronized (ConfigManager.class){
                configManager = new ConfigManager();
            }
        }
        return configManager;
    }

    public String getString(String key){
        return System.getProperty(key, prop.getProperty(key));
    }
}
