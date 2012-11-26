package sozialnetz.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class PublicProfilePanel extends Panel {

	private String nameandsurname;
	private String username;

	private static final long serialVersionUID = 1L;

	public PublicProfilePanel(String id) {
		super(id);
		add(new Label("nameandsurname", nameandsurname));
		add(new Label("username", username));
	}

}
