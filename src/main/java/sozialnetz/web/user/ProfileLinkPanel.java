package sozialnetz.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import sozialnetz.domain.entities.User;
import sozialnetz.web.EntityModel;

public class ProfileLinkPanel extends Panel {

	public ProfileLinkPanel(String id, User user) {
		super(id);
		setDefaultModel(new EntityModel<User>(User.class, user));
		add(new Link<User>("profileLink") {
			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(new PageParameters().add(
						"username", user().getUsername())));
			}
		}.add(new Label("completename", user().getName() + " "
				+ user().getSurname())));
	}

	private User user() {
		return (User) getDefaultModelObject();
	}
}
