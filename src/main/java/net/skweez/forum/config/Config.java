package net.skweez.forum.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Config INSTANCE = new Config();

	private Properties configProperties = new Properties();

	public Config() {
		try {
			configProperties.load(new FileInputStream("config/forum.config"));
			System.out.println("Configured with config file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(Option option) {
		return INSTANCE.configProperties.getProperty(option.getKey());
	}
}
