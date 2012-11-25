package sozialnetz.web.user;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import sozialnetz.web.base.BasePage;

public class ProfilePage extends BasePage {
	
	

	private static final long serialVersionUID = 1L;
	
	public ProfilePage(PageParameters parameters){
		 StringValue username = parameters.get("username");
//		 StringValue visibility = parameters.get("visibility");TODO es una idea
		 
		 //TODO determinar si es profile de amigo, de no amigo o propio
		 String visibility = "public";
		 
		 if (visibility.equals("public")){
			 add(new PublicProfilePanel("publicprofilepanel")); 
		 }else if (visibility.equals("friend")){
			 
		 }else{ //own
			 
		 }
	}
}
