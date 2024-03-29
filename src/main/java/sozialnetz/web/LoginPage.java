package sozialnetz.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import sozialnetz.web.base.BasePage;

@SuppressWarnings("serial")
public class LoginPage extends BasePage {

	
	private transient String username;
	private transient String password;

	public LoginPage() {
		add(new FeedbackPanel("feedback"));
		Form<LoginPage> form = new Form<LoginPage>("loginForm", new CompoundPropertyModel<LoginPage>(this)) {
			@Override
			protected void onSubmit() {
				SozialnetzSession session = SozialnetzSession.get();
			}
		};

		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("login", new ResourceModel("login")));
		add(form);
	}
}
