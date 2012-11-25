package repositories.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import repositories.api.UserRepo;
import domain.entities.User;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo implements
		UserRepo {

//	@Autowired
//	private EmailService emailService;
//	@Value("#{friendsuggest['suggest.inCommon']}")
//	private String inCommon;
//	@Value("#{friendsuggest['suggest.total']}")
//	private String total;

	@Autowired
	public HibernateUserRepo(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User getByID(final Integer id) {
		return this.get(User.class, id);
	}

	@Override
	public User getByNick(final String nick) {
		if (this.existUsername(nick)) {
			return (User) (this.find("from User where username = ?", nick)
					.get(0));
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public List<User> getByPattern(String pattern) {

		final String[] names = pattern.split(" ");
		String query = "";
		int i = 0;
		int k = 0;
		String[] parameters = new String[names.length * 2];
		pattern = "%" + pattern.toLowerCase() + "%";
		final List<User> users = this.find(
				"from User WHERE LOWER(name) LIKE ? OR LOWER(surname) LIKE ? ",
				(Object) pattern, (Object) pattern);

		return users;

	}

	@Override
	public boolean existEmail(final String email) {
		return !this.find("from User where email = ?", email).isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getZeroFriends(final User user) {
		final List<User> users = new ArrayList<User>();
		for (User u : user.getFriends()) {
			users.addAll(u.getFriends());
			users.add(u);
		}
		if (users.isEmpty()) {
			return this.find("from User");
		}
		String hql = "from User u where u NOT IN (:users)";
		Query q = this.getSession().createQuery(hql);
		q.setParameterList("users", users);
		return q.list();
	}

	@Override
	public void save(final User u) {
		super.save(u);
	}

	@Override
	public boolean existUsername(final String username) {
		return !this.find("from User where username = ?", username).isEmpty();
	}

	@Override
	public User getByEmail(final String email) {
		if (this.existEmail(email)) {
			return (User) this.find("from User where email = ?", email).get(0);
		}
		return null;
	}

//	@Override
//	public boolean sendNewPassEmail(final User user, final String url) {
//		return this.emailService.sendMail(user, url);
//	}

//	@Override
//	public List<User> suggestFriends(final User user) {
//		List<User> res = new ArrayList<User>();
//		int common = Integer.valueOf(inCommon);
//		
//		// agrego usuarios con N o mas amigos en comun
//		for (User friend : user.getFriends()) {
//			for (User s : friend.getFriends()) {
//				int count = 0;
//				for (User u : user.getFriends()) {
//					if (u.getFriends().contains(s)) {
//						count++;
//					}
//				}
//				if (count >= common && !s.equals(user)
//						&& res.size() < Integer.valueOf(total)
//						&& !user.getFriends().contains(s) && !res.contains(s))
//					res.add(s);
//			}
//		}
//
//		// voy bajando el valor de N hasta 0 y agrego usuarios
//		while (res.size() <= Integer.valueOf(total) && --common > 0) {
//			for (User friend : user.getFriends()) {
//				for (User s : friend.getFriends()) {
//					int count = 0;
//					for (User u : user.getFriends()) {
//						if (u.getFriends().contains(s)) {
//							count++;
//						}
//					}
//					if (count == common && !s.equals(user)
//							&& res.size() < Integer.valueOf(total)
//							&& !user.getFriends().contains(s)
//							&& !res.contains(s))
//						res.add(s);
//				}
//			}
//		}

//		// si llegue a 0 y no alcance el numero de amigos solicitados,
//		// agrego usuarios que no tengan amigos en comun
//		if (res.size() < Integer.valueOf(total)) {
//			final List<User> searchResults = this.getZeroFriends(user);
//			for (User u : searchResults) {
//				if (!user.getFriends().contains(u)
//						&& res.size() < Integer.valueOf(total)
//						&& !u.equals(user) && !res.contains(u))
//					res.add(u);
//			}
//		}
//		return res;
//	}

}
