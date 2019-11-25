package ru.fssprus.r82.utils;

/**
 * @author Chernyj Dmitry
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguration {
	private static class AppConfigHolder {
		private static Properties PROPS = new Properties();

		static {
			try {
				InputStream is = new FileInputStream("app.properties");
				PROPS.load(is);
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Failed to load app.properties");
			}
		}
	}

	private ApplicationConfiguration() {

	}

	public static String getItem(String key) {
		return AppConfigHolder.PROPS.getProperty(key);
	}

	public static void saveItem(String key, String value) {
		try {
			FileOutputStream out = new FileOutputStream("app.properties");
			AppConfigHolder.PROPS.setProperty(key, value);
			AppConfigHolder.PROPS.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new ExceptionInInitializerError("Failed to load app.properties");
		}
	}
}
