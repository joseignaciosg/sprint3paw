package sozialnetz.domain.repositories.api;

import java.util.List;

import sozialnetz.domain.entities.User;


public interface UserRepo {

	public User getByID(final Integer id);

	public User getByNick(final String nick);

	public List<User> getByPattern(final String pattern);

	public boolean existEmail(final String email);

	public List<User> getZeroFriends(final User u);

	public void save(final User u);

	public boolean existUsername(final String username);

	public User getByEmail(final String email);
	
//	public boolean sendNewPassEmail(final User user, final String url);
	
//	public List<User> suggestFriends(final User user);

}
