package sozialnetz.web.base;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sozialnetz.domain.repositories.api.UserRepo;
import sozialnetz.web.ProfilePage;
import sozialnetz.web.SozialneztSession;


public class NotLoggedHeaderPanel extends Panel {

	@SpringBean
	private UserRepo userRepo;
	
	private String username;
	private String password;

	private static final long serialVersionUID = 1L;

	public NotLoggedHeaderPanel(String id) {
		super(id);
		//login form
		Form<NotLoggedHeaderPanel> loginform = new Form<NotLoggedHeaderPanel>("loginform",
				new CompoundPropertyModel<NotLoggedHeaderPanel>(this)){
			@Override
			public void onSubmit(){
				SozialneztSession session = SozialneztSession.get();
				if (session.signIn(username, password,userRepo)){
					if(!continueToOriginalDestination()){ //TODO TEST THIS
						setResponsePage(new ProfilePage());
					}else{
						error(getString("invalidLogin"));//TODO TEST SHIT
					}
				}
			}
		};
		loginform.add(new TextField<String>("username").setRequired(true));
		loginform.add(new TextField<String>("password").setRequired(true));
		add(loginform);
	}

}
