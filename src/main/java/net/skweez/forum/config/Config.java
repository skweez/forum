package net.skweez.forum.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Simple class for configuring the software through a config file. Loads
 * settings from {@value #CONFIG_FILE}.
 * 
 * <p>
 * Currently we do not try different locations like /etc/, $HOME/.config/.
 * 
 * @author mks
 */
public class Config {

	/** Filesystem location of the config file. */
	private static final String CONFIG_FILE = "config/forum.config";

	/** Singleton instance. */
	private static Config INSTANCE = new Config();

	/** Config properties with the configured values. */
	private Properties configProperties = new Properties();

	/** Constructor. */
	public Config() {
		try {
			configProperties.load(new FileInputStream(CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a boolean value from a setting in the configuration file.
	 * 
	 * @return <code>true</code> if the setting is set to <code>"true"</code>
	 *         (ignoring case).
	 */
	public static boolean getBoolean(Setting setting) {
		return Boolean.parseBoolean(getValue(setting));

	}

	/**
	 * Get the value of a setting as configured in the configuration file. All
	 * available keys are enumerated in {@link Setting}.
	 * 
	 * @return The String as configured or <code>null</code> if it is not
	 *         configured.
	 */
	public static String getValue(Setting option) {
		return INSTANCE.configProperties.getProperty(option.getKey());
	}
}
