package sozialnetz.web.base;

import org.apache.wicket.markup.html.link.Link;

import sozialnetz.web.LoginPage;
import sozialnetz.web.SozialneztSession;


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