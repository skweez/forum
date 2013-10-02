package net.skweez.forum.auth;

import java.util.Arrays;

/**
 * A stupid {@link AuthenticationService} that authenticates successfully if the
 * password equals the username.
 * 
 * @author mks
 */
public class MockAuthenticationService extends AuthenticationService {

	@Override
	public boolean authenticate(String username, char[] password) {
		return Arrays.equals(username.toCharArray(), password);
	}

}
