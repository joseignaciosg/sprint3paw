package sozialnetz.web.user;

import java.util.Collection;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.UserRepo;
import sozialnetz.web.EntityModel;
import sozialnetz.web.SozialnetzSession;
import sozialnetz.web.base.SecuredPage;
import sozialnetz.web.event.CreateEventPage;
import sozialnetz.web.publication.PublicationPanel;

@SuppressWarnings("serial")
public class ProfilePage extends SecuredPage {

	@SpringBean
	private UserRepo userRepo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProfilePage(PageParameters parameters) {

		StringValue username = parameters.get("username");
		final User user = userRepo.getByNick(username.toString());
        setDefaultModel(new CompoundPropertyModel(new EntityModel<User>(User.class, user)));
		SozialnetzSession session = (SozialnetzSession) getSession();
		User currentUser = userRepo.getByNick(session.getUsername());

		// profile user information
		add(new Label("currentUserName", user.getName()));
		add(new Label("currentUserSurname", user.getSurname()));
		add(new Label("currentUserUsername", user.getUsername()));
		add(new Label("currentUserEmail", user.getEmail()));
		add(new Label("currentUserBirthday", user.getBirthday().toString()));

		boolean isCurrent = user.equals(currentUser);

		add(new Link("editupcomingbirthdaynumber") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new EditUpcomingBirthdaysPage(user,
						ProfilePage.this));
			}
		}.setVisible(isCurrent));

		add(new Link("editphoto") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new EditPhotoPage());
			}
		}.setVisible(isCurrent));

		add(new Link("editinformation") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new EditInformationPage());
			}
		}.setVisible(isCurrent));

		add(new Link("editpass") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new EditPasswordPage());
			}
		}.setVisible(isCurrent));

		add(new Link("createevent") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new CreateEventPage());
			}
		}.setVisible(isCurrent));

		// adding friends
		add(new FriendListPanel("friends",
				new LoadableDetachableModel<Collection<User>>() {
					IModel<User> friendModel = new EntityModel<User>(
							User.class, (User)getDefaultModel().getObject());
					@Override
					protected Collection<User> load() {
						return friendModel.getObject().getFriends();
					}
				}));
		
		//adding publication form and list of publications 
		add(new PublicationPanel("publicationPanel", getDefaultModel()));

	}
}
