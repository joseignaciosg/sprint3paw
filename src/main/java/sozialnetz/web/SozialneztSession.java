package sozialnetz.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.UserRepo;


public class SozialneztSession extends WebSession {
	private static final long serialVersionUID = 1L;
	private String username;

	public static SozialneztSession get() {
		return (SozialneztSession) Session.get();
	}

	public SozialneztSession(Request request) {
		super(request);
	}

	public String getUsername() {
		return username;
	}

	public boolean signIn(String username, String password, UserRepo userRepo) {
		User user = userRepo.getByNick(username);
		if (user != null && user.getPassword().equals(password)) {
			this.username = username;
			return true;
		}
		return false;
	}

	public boolean isSignedIn() {
		return username != null;
	}

	public void signOut() {
		invalidate();
		clear();
	}
}
