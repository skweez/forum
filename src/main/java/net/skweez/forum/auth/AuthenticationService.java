package net.skweez.forum.auth;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;

public abstract class AuthenticationService {

	private static AuthenticationService configuredService;

	public static AuthenticationService getService() {
		if (configuredService == null) {
			try {
				return (AuthenticationService) Class.forName(
						Config.getValue(Setting.AUTHENTICATION_SERVICE))
						.newInstance();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}
		return configuredService;
	}

	public abstract boolean authenticate(String username, char[] password);

}
