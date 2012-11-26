package sozialnetz.web.user;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.UserRepo;
import sozialnetz.web.SozialneztSession;
import sozialnetz.web.base.SecuredPage;

public class ProfilePage extends SecuredPage {

	@SpringBean
	private UserRepo userRepo;

	private static final long serialVersionUID = 1L;

	public ProfilePage(PageParameters parameters) {
		StringValue username = parameters.get("username");
		final User user = userRepo.getByNick(username.toString());
		SozialneztSession session = (SozialneztSession) getSession();
		User currentUser = userRepo.getByNick(session.getUsername());

		boolean isCurrent = user.equals(currentUser);

		add(new Link("editupcomingbirthdaynumber") {
			@Override
			public void onClick() {
				setResponsePage(new EditUpcomingBirthdaysPage(user,
						ProfilePage.this));
			}
		}.setVisible(isCurrent));
	}
}
