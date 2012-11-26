package sozialnetz.web.base;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import sozialnetz.web.SozialneztSession;

public class LoggedHeaderPanel extends Panel{

	private static final long serialVersionUID = -7595429803193768578L;

	public LoggedHeaderPanel(String id) {
		
		super(id);
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
