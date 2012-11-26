package sozialnetz.web.publication;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import sozialnetz.domain.entities.StateUpdate;

@SuppressWarnings("serial")
public class StateUpdatePanel extends Panel {

	public StateUpdatePanel(String id, StateUpdate update) {
		super(id);
		add(new Label("stateUpdateText", update.getText()));
		boolean isCurrent = update.getOwner().equals(update.getFrom());
		add(new Label("from", update.getFrom().getUsername() + " publicó en el muro:").setVisible(!isCurrent));
	
	}

}
