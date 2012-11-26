package sozialnetz.web.publication;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sozialnetz.domain.entities.InterestsType;
import sozialnetz.domain.entities.StateUpdate;
import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.PublicationRepo;
import sozialnetz.domain.repositories.api.UserRepo;
import sozialnetz.web.ApplicationException;
import sozialnetz.web.SozialnetzSession;
import sozialnetz.web.user.ProfilePage;

@SuppressWarnings("serial")
public class LikePanel extends Panel {

	@SpringBean
	private UserRepo userRepo;
	@SpringBean
	private PublicationRepo publicationRepo;
	
	private transient User currentUser;


	// TODO do converter for publication
	public LikePanel(String id, final String profileUserName, final StateUpdate update) {
		super(id);
		SozialnetzSession session = SozialnetzSession.get();
		currentUser = userRepo.getByNick(session.getUsername());
		Form<LikePanel> likeForm = new Form<LikePanel>(
				"likeForm", new CompoundPropertyModel<LikePanel>(this)) {
			@Override
			public void onSubmit() {
				try {
					User profileUser = userRepo.getByNick(profileUserName);
					profileUser.addInterest(currentUser, update,
							InterestsType.LIKE);
					setResponsePage(new ProfilePage(new PageParameters().add(
							"username", profileUser.getUsername())));
				} catch (ApplicationException e) {
					error(getString(e.getClass().getSimpleName(),
							new Model<ApplicationException>(e)));
				}
			}
		};
		add(likeForm.setVisible(!update.isLikedByUser(currentUser)));
		
		add(new Label("likeLabel","Te gusta").setVisible(update.isLikedByUser(currentUser)));

	}

}
