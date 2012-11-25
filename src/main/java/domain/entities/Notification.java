package domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Notification extends PersistentEntity implements
		Comparable<Notification> {

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime date;

	@OneToOne(optional=false)
	private User from;

	@ManyToOne(optional=false)
	private User to; 

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	NotificationType type;

	@Column(nullable = false)
	private boolean read;

	protected Notification() {
	}

	public Notification(final User from, final User to,
			final NotificationType type, final boolean read) {
		Assert.notNull(from);
		Assert.notNull(to);
		Assert.notNull(read);
		Assert.notNull(type);
		this.from = from;
		this.to = to;
		this.type = type;
		this.read = read;
		this.date = new DateTime();
	}

	public User getFrom() {
		return this.from;
	}

	public User getTo() {
		return this.to;
	}

	public DateTime getDate() {
		return this.date;
	}

	public NotificationType getType() {
		return this.type;
	}

	public boolean isRead() {
		return this.read;
	}

	public void read() {
		this.read = true;
	}

	@Override
	public int compareTo(Notification arg0) {
		return arg0.getDate().compareTo(this.date);
	}

}
