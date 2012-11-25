package web.base;

import org.apache.wicket.markup.html.link.Link;

import web.SozialneztSession;
import web.LoginPage;

public abstract class SecuredPage extends BasePage {

	public SecuredPage() {
		SozialneztSession session = getDemoWicketSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new LoginPage());
		}

		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				getDemoWicketSession().signOut();
				setResponsePage(getApplication().getHomePage());
			}
		});
	}

	protected SozialneztSession getDemoWicketSession() {
		return (SozialneztSession) getSession();
	}
}