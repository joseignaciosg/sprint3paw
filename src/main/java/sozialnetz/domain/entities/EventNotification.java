package sozialnetz.domain.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.util.Assert;

@Entity
public class EventNotification extends Notification {

	@OneToOne(optional=false)
	private Event event;

	public EventNotification(final User from, final User to,
			final boolean read, Event event) {
		super(from, to, NotificationType.EVENTINVITATION, read);
		Assert.notNull(event);
		this.event = event;
	}

	protected EventNotification() {
		super();
	}

	public Event getEvent() {
		return this.event;
	}

}
