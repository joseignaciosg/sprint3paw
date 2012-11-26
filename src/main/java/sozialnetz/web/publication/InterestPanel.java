package sozialnetz.web.publication;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import sozialnetz.domain.entities.Interest;

@SuppressWarnings("serial")
public class InterestPanel extends Panel {

	public InterestPanel(String id, Interest interest) {
		super(id);

		add(new Label("likeLabel", "a " + interest.getOwner()
				+ "le gusta la publicación: ").setVisible(true));
		add(new Label("text", interest.getUpdate().getText()).setVisible(true));

	}

}
