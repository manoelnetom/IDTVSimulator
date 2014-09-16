package br.org.sbtvd.config;

import java.util.*;
import java.io.*;

public class Settings {

	private static String property_filename = "config/settings.txt";

	private Properties properties;
	private static Settings instance;

	private Settings(String filename) {
		properties = new Properties();

		try {
			properties.load(new FileInputStream(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings(property_filename);
		}

		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
