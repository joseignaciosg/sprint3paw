package sozialnetz.web.base;

import org.apache.wicket.markup.html.link.Link;

import sozialnetz.web.HomePage;
import sozialnetz.web.SozialnetzSession;


public abstract class SecuredPage extends BasePage {

	public SecuredPage() {
		SozialnetzSession session = getDemoWicketSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new HomePage());
		}

	}

	protected SozialnetzSession getDemoWicketSession() {
		return (SozialnetzSession) getSession();
	}
}