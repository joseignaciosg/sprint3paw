package sozialnetz.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

@Entity
public class Event extends PersistentEntity {

	public final static int EVENT_FIELD_MAX_SIZE = 255;
	
	@Column(nullable = false, length = EVENT_FIELD_MAX_SIZE)
	private String name;

	@Column(nullable = false, length = EVENT_FIELD_MAX_SIZE)
	private String description;

	@Column(nullable = false)
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime date;

	@ManyToOne(optional=false)
	private User owner;

	@OneToOne(cascade = CascadeType.ALL,optional=true)
	private Photo photo;

	@Column(nullable = false)
	private boolean hasPhoto = false;

	@CollectionOfElements
	private List<Guest> guests = new ArrayList<Guest>();

	protected Event() {
	}

	public Event(String name, String description, DateTime date, User owner) {
		super();
		Assert.notNull(name);
		Assert.notNull(description);
		Assert.notNull(date);
		Assert.notNull(owner);
		this.name = name;
		this.description = description;
		this.date = date;
		this.owner = owner;
		setHasPhoto(false);
		this.invite(getOwner());

	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public DateTime getDate() {
		return date;
	}

	public User getOwner() {
		return owner;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		Assert.notNull(photo);
		setHasPhoto(true);
		this.photo = photo;
	}

	public boolean getHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public List<User> getConfirmed() {
		List<User> confirmed = getGuests(GuestType.GOING);
		return confirmed;
	}

	public List<User> getGuests(GuestType guestType) {
		List<User> users = new ArrayList<User>();
		for (Guest guest : guests) {
			if (guest.getGuestType() == guestType)
				users.add(guest.getGuest());
		}
		return users;
	}

	public List<User> getDontKnow() {
		List<User> dontKnow = getGuests(GuestType.DONTKNOW);
		return dontKnow;
	}

	public List<User> getNotGoing() {
		List<User> notGoing = getGuests(GuestType.NOTGOING);
		return notGoing;
	}

	public List<User> getNoAnswer() {
		List<User> noAnswer = getGuests(GuestType.NOANSWER);
		return noAnswer;
	}

	public Guest getGuest(User u) {
		for (Guest guest : guests) {
			User user = guest.getGuest();
			if (u.equals(user))
				return guest;
		}
		return null;
	}

	public void invite(User u) {
		guests.add(new Guest(u, GuestType.NOANSWER));
		if (!(u.equals(this.owner)))
			u.addNotification(new EventNotification(getOwner(), u, false, this));
	}

	public List<User> getNonInvited() {
		List<User> friends = owner.getFriends();
		List<User> nonInvited = new ArrayList<User>();
		for (User user : friends) {
			boolean invited = false;
			for (int i = 0; i < guests.size() && !invited; i++) {
				// si el amigo ya está invitado
				if (user.equals(guests.get(i).getGuest()))
					invited = true;
			}
			if (!invited)
				nonInvited.add(user);
		}
		return nonInvited;
	}

	public void changeAnswer(User user, GuestType guestType) {
		Guest guest = getGuest(user);
		guest.setGuestType(guestType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (hasPhoto ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hasPhoto != other.hasPhoto)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

}
