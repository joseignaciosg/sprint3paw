package sozialnetz.web.base;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import sozialnetz.domain.entities.User;
import sozialnetz.web.SozialneztSession;
import sozialnetz.web.user.ProfilePage;

@SuppressWarnings("serial")
public class LoggedHeaderPanel extends Panel {

	public LoggedHeaderPanel(String id) {

		super(id);
		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				getSozialneztSession().signOut();
				setResponsePage(getApplication().getHomePage());
			}
		});

		add(new Link<User>("logoLink") {
			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(new PageParameters().add(
						"username", getSozialneztSession().getUsername())));
			}
		});
	}

	protected SozialneztSession getSozialneztSession() {
		return (SozialneztSession) getSession();
	}

}
