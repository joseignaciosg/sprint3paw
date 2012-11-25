package web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import web.SozialneztSession;

public class BasePage extends WebPage {

	public BasePage() {
		add(new Label("username", new PropertyModel<String>(SozialneztSession.get(), "username")));
	}
}
