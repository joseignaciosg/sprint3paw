package sozialnetz.web.base;

import org.apache.wicket.markup.html.link.Link;

import sozialnetz.web.HomePage;
import sozialnetz.web.SozialneztSession;


public abstract class SecuredPage extends BasePage {

	public SecuredPage() {
		SozialneztSession session = getDemoWicketSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new HomePage());
		}

	}

	protected SozialneztSession getDemoWicketSession() {
		return (SozialneztSession) getSession();
	}
}