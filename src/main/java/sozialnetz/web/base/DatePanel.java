package sozialnetz.web.base;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.joda.time.DateTime;

import com.ocpsoft.pretty.time.PrettyTime;

@SuppressWarnings("serial")
public class DatePanel extends Panel {

	public DatePanel(String id, DateTime date) {
		super(id);
		PrettyTime p = new PrettyTime();
		String pretty = p.format(date.toDate());
		add(new Label("prettydate", pretty));
	}
}
