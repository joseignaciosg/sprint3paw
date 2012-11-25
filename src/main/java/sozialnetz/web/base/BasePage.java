package sozialnetz.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import sozialnetz.web.SozialneztSession;


public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public BasePage() {
		boolean logged = false;
		if(!logged){
			add(new NotLoggedHeaderPanel("headerPanel"));
		}else{
			add(new LoggedHeaderPanel("headerPanel"));
		}
		add(new Label("username", new PropertyModel<String>(SozialneztSession.get(), "username")));
	}
}
