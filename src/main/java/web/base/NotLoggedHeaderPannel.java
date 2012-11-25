package web.base;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import repositories.api.UserRepo;
import web.ProfilePage;
import web.SozialneztSession;



public class NotLoggedHeaderPannel extends Panel {

	@SpringBean
	private UserRepo userRepo;
	
	private String username;
	private String password;

	private static final long serialVersionUID = 1L;

	public NotLoggedHeaderPannel(String id) {
		super(id);
		//login form
		Form<NotLoggedHeaderPannel> loginform = new Form<NotLoggedHeaderPannel>("logginform",
				new CompoundPropertyModel<NotLoggedHeaderPannel>(this)){
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
