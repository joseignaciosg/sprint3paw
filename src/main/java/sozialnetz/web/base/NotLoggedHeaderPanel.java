package sozialnetz.web.base;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.UserRepo;
import sozialnetz.web.SozialneztSession;
import sozialnetz.web.user.ProfilePage;

public class NotLoggedHeaderPanel extends Panel {

	@SpringBean
	private UserRepo userRepo;

	private String username;
	private String password;

	private static final long serialVersionUID = 1L;

	public NotLoggedHeaderPanel(String id) {
		super(id);
		Form<NotLoggedHeaderPanel> loginform = new Form<NotLoggedHeaderPanel>(
				"loginform", new CompoundPropertyModel<NotLoggedHeaderPanel>(
						this)) {
			@Override
			public void onSubmit() {
				SozialneztSession session = SozialneztSession.get();
				if (session.signIn(username, password, userRepo)) {
					if (!continueToOriginalDestination()) {
						setResponsePage(new ProfilePage(
								new PageParameters().add("username", username)));
					}
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};
		loginform.add(new TextField<String>("username").setRequired(true));
		loginform.add(new TextField<String>("password").setRequired(true));
		add(loginform);
		

		add(new Link<User>("logoLink") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
	}

}
