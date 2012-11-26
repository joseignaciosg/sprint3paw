package sozialnetz.web.publication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sozialnetz.domain.entities.Interest;
import sozialnetz.domain.entities.Publication;
import sozialnetz.domain.entities.StateUpdate;
import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.UserRepo;
import sozialnetz.web.ApplicationException;
import sozialnetz.web.EntityModel;
import sozialnetz.web.SozialnetzSession;
import sozialnetz.web.base.DatePanel;
import sozialnetz.web.user.ProfilePage;

@SuppressWarnings("serial")
public class PublicationPanel extends Panel {

	@SpringBean
	private UserRepo userRepo;

	private transient String text;

	public PublicationPanel(String id, final IModel<?> iModel) {
		super(id);
		Form<PublicationPanel> publicationForm = new Form<PublicationPanel>(
				"publicationForm", new CompoundPropertyModel<PublicationPanel>(
						this)) {
			@Override
			public void onSubmit() {
				SozialnetzSession session = SozialnetzSession.get();
				User currentUser = userRepo.getByNick(session.getUsername());
				User profileUser = (User)iModel.getObject();
				try {
					profileUser.addStateUpdate(text, currentUser);
					setResponsePage(new ProfilePage(new PageParameters().add(
							"username", profileUser.getUsername())));
				} catch (ApplicationException e) {
					error(getString(e.getClass().getSimpleName(),
							new Model<ApplicationException>(e)));
				}
			}
		};
		publicationForm.add(new TextArea<String>("text").setRequired(true));
		add(publicationForm);

		// list of publications
		add(new RefreshingView<Publication>("publication") {
			@Override
			protected Iterator<IModel<Publication>> getItemModels() {
				List<IModel<Publication>> result = new ArrayList<IModel<Publication>>();
				User profileUser = (User)iModel.getObject();
				;
				for (Publication pub : profileUser.getPublications()) {
					result.add(new EntityModel<Publication>(Publication.class,
							pub));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<Publication> item) {
				Publication pub = item.getModelObject();
				if (pub.getClass().equals(StateUpdate.class)) { // TODO esta
																// bien esto?
					item.add(new StateUpdatePanel("pubPanel", (StateUpdate) pub));
				} else {
					item.add(new InterestPanel("pubPanel", (Interest) pub));
				}
				item.add(new DatePanel("publicationDate", pub.getDate()));
			}
		});
	}
}
