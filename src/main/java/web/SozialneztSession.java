package web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class SozialneztSession extends WebSession {

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

	public boolean signIn(String username, String password) {
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
