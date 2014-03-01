/**
 * 
 */
package net.skweez.forum.logic;

import javax.ws.rs.core.SecurityContext;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;
import net.skweez.forum.model.User;
import net.skweez.forum.server.HibernateHelper;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * The user logic handles all user related tasks
 * 
 * @author elm
 * 
 */
public class UserLogic {
	/**
	 * Find a user by its uid. If non is found create a new user. Role
	 * informations for the new user are extracted from the security context.
	 * 
	 * @param uid
	 *            the uid of the new user
	 * @param sec
	 *            the security context
	 * @return the new user. If a user with that uid already exists this user is
	 *         returned
	 */
	public User findOrCreateUser(String uid, SecurityContext sec) {
		User user = getUser(uid);

		if (user != null || sec == null) {
			return user;
		}

		user = new User(uid);

		if (sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_USER))) {
			user.addRole(Config.getValue(Setting.ROLE_NAME_USER));
		}
		if (sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_ADMIN))) {
			user.addRole(Config.getValue(Setting.ROLE_NAME_ADMIN));
		}

		Session session = HibernateHelper.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();

		return user;
	}

	/**
	 * @param uid
	 *            the uid
	 * @return the user for the uid. Returns null if no such user exists.
	 */
	public User getUser(String uid) {
		Session session = HibernateHelper.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq(User.UID_COLUMN_NAME, uid)).uniqueResult();
		session.getTransaction().commit();
		return user;
	}
}
