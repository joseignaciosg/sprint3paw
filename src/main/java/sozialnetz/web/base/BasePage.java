package sozialnetz.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import sozialnetz.web.SozialneztSession;
import sozialnetz.web.user.ProfileLinkPanel;


public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public BasePage() {
		boolean logged = false;
		SozialneztSession session = (SozialneztSession)getSession();
		if(!session.isSignedIn()){
			add(new NotLoggedHeaderPanel("headerPanel"));
		}else{
			add(new LoggedHeaderPanel("headerPanel"));
		}
		
	}
}
