package domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;
import org.springframework.util.Assert;

@Entity
@Table(name = "users")
public class User extends PersistentEntity implements Comparable<User> {

	public final static int UPCOMINGBIRTHDAYSMAX = 20;
	public final static int FIELD_MAX_SIZE = 30;
	public final static String VALID_MAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Column(nullable = false, length = FIELD_MAX_SIZE)
	private String name;
	@Column(nullable = false, length = FIELD_MAX_SIZE)
	private String surname;
	@Column(nullable = false, unique = true, length = FIELD_MAX_SIZE)
	private String username;
	@Column(nullable = false, length = FIELD_MAX_SIZE)
	private String email;
	@Column(nullable = false, length = FIELD_MAX_SIZE)
	private String password;

	@Column(nullable = false)
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime birthday; // yyyy-mm-dd

	@OneToOne(cascade = CascadeType.ALL)
	private Photo photo;

	private boolean hasPhoto = false;

	@ManyToMany
	@JoinTable(name = "friends")
	private List<User> friends = new ArrayList<User>();

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Publication> publications = new ArrayList<Publication>();

	@OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Notification> notifications = new ArrayList<Notification>();

	private Preferences prefs;

	protected User() {
	}

	public User(final String name, final String surname, final String nickname,
			final String email, final DateTime birthday, final String password,
			final boolean hasphoto) {
		this.setName(name);
		this.setSurname(surname);
		this.setUsername(nickname);
		this.setEmail(email);
		this.setBirthday(birthday);
		this.setPassword(password);
		this.setPrefs(new Preferences());
	}

	public List<User> getFriends() {
		return this.friends;
	}

	public List<Publication> getPublications() {
		Collections.sort(publications);
		return publications;
	}

	public List<StateUpdate> getStateUpdates() {
		List<StateUpdate> stateUpdates = new ArrayList<StateUpdate>();
		for (Publication publication : publications) {
			if (publication.getClass().equals(StateUpdate.class)) {
				stateUpdates.add((StateUpdate) publication);
			}
		}
		return stateUpdates;
	}

	public List<Interest> getInterests() {
		List<Interest> interests = new ArrayList<Interest>();
		for (Publication publication : publications) {
			if (publication.getClass().equals(Interest.class)) {
				interests.add((Interest) publication);
			}
		}
		return interests;
	}

	public List<Notification> getNotifications() {
		Collections.sort(notifications);
		return notifications;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		if (name == null || name.length() == 0
				|| name.length() > FIELD_MAX_SIZE) {
			throw new IllegalStateException();
		}
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		if (surname == null || surname.length() == 0
				|| surname.length() > FIELD_MAX_SIZE) {
			throw new IllegalStateException();
		}
		this.surname = surname;
	}

	public void setHasPhoto(final boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		if (username == null || username.length() == 0
				|| username.length() > FIELD_MAX_SIZE) {
			throw new IllegalStateException();
		}
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		@SuppressWarnings("static-access")
		Pattern emailPattern = Pattern.compile(this.VALID_MAIL);
		if (email == null || email.length() == 0
				|| email.length() > FIELD_MAX_SIZE
				|| !emailPattern.matcher(email).find()) {
			throw new IllegalStateException();
		}
		this.email = email;
	}

	public DateTime getBirthday() {
		return this.birthday;
	}

	public void setBirthday(final DateTime birthday) {
		if (birthday == null || birthday.isAfterNow()) {
			throw new IllegalStateException();
		}
		this.birthday = birthday;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		if (password == null || password.length() == 0
				|| password.length() > FIELD_MAX_SIZE) {
			throw new IllegalStateException();
		}
		this.password = password;
	}

	public Photo getPhoto() {
		return this.photo;
	}

	public void setPhoto(final Photo photo) {
		if (photo != null) {
			this.photo = photo;
			this.hasPhoto = true;
		}
	}

	public boolean getHasPhoto() {
		return this.hasPhoto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.username == null) ? 0 : this.username.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}

	public void addPublication(final Publication pub) {
		this.publications.add(pub);
	}

	public void addStateUpdate(final String text, final User from/* current */) {
		// si no son amigos no puede hacer una actualización de estado
		if (!this.equals(from) && !isFriend(from)) {
			return;
		}
		this.publications.add(new StateUpdate(from, this, text));
		if (!this.equals(from)) {
			this.notifications.add(new Notification(from, this,
					NotificationType.STATEUPDATE, false));
		}
	}

	public void addInterest(final User from/* owner */, final StateUpdate pub,
			InterestsType type) {
		// si no son amigos no puede hacer un like
		if (!this.equals(from) && !isFriend(from)) {
			return;
		}
		if (type.equals(InterestsType.LIKE)) {
			if (!this.existLike(from, pub)) {
				Interest like = new Interest(pub, from, InterestsType.LIKE);
				from.publications.add(like);
				addNotification(from, NotificationType.LIKE);
				pub.addInterest(like);
			}
		} else {
			if (!from.existShare(this, pub)) {
				Interest share = new Interest(pub, from, InterestsType.SHARE);
				from.publications.add(share);
				pub.addInterest(share);
			}
		}

	}

	public void addFriend(final User friend) {
		if (!this.isFriend(friend)) {
			friends.add(friend);
			List<User> friends = getBothFriends(friend);
			for (User u : friends) {
				u.addNotification(new NewFriendshipNotification(this, u, false,
						friend));
			}
		}
	}

	private List<User> getBothFriends(User friend) {
		List<User> bothFriends = new ArrayList<User>();
		for (User u : friends) {
			if (u.isFriend(friend))
				bothFriends.add(u);
		}
		return bothFriends;
	}

	/*
	 * Deletes a friend petition and creates a notification of acceptation if
	 * the user has accepted the friend petition
	 */
	public void acceptPetition(final Notification notif) {
		if (notif.getType().equals(NotificationType.PETITION)) {
			this.deleteNotification(notif);
			notif.getFrom().addNotification(notif.getTo(),
					NotificationType.ACEPTATION);
			friends.add(notif.getFrom());
			notif.getFrom().addFriend(this);
		}
	}

	public void addNotification(User from, NotificationType type) {
		if (!existsPreviousPetition(from, type)) {
			this.notifications.add(new Notification(from, this, type, false));
		}
	}

	public boolean existsPreviousPetition(User from, NotificationType type) {
		if (type.equals(NotificationType.PETITION)) {
			// se la mandaron y la aceptó o es uno mismo
			if (this.isFriend(from) || this.equals(from)) {
				return true;
			}
			// está pendiente
			for (Notification notif : notifications) {
				if (notif.getFrom().equals(from)
						&& notif.getType().equals(NotificationType.PETITION)) {
					return true;
				}
			}
		}
		return false;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}

	public boolean isFriend(final User user) {
		return this.friends.contains(user);
	}

	/*
	 * Sets all the not already read notifications as read
	 */
	public void readNotifications() {
		for (final Notification notif : this.notifications) {
			if (!notif.isRead()) {
				notif.read();
			}
		}
	}

	/*
	 * Returns the number of not read notifications
	 */
	public int getNumberNotReadNotifications() {
		int ans = 0;
		for (final Notification notif : this.notifications) {
			if (!notif.isRead()) {
				ans++;
			}
		}
		return ans;
	}

	public boolean deleteNotification(Notification notif) {
		return this.notifications.remove(notif);
	}

	public boolean refusePetition(Notification notif) {
		if (notif.getType().equals(NotificationType.PETITION)) {
			deleteNotification(notif);
		}
		return false;
	}

	public Notification getPetitionFrom(final User user) {
		for (final Notification notif : this.notifications) {
			if (notif.getFrom().equals(user)) {
				return notif;
			}
		}
		return null;
	}

	public Notification getFriendPetition(final User from) {
		for (final Notification notif : this.notifications) {
			if (notif.getType().equals(NotificationType.PETITION)
					&& notif.getFrom().equals(from)) {
				return notif;
			}
		}
		return null;
	}

	/*
	 * Returns true if user from has sent user to a friend petition
	 */
	public boolean checkForFriendPetition(final User from) {
		if (this.getFriendPetition(from) != null) {
			return true;
		}
		return false;
	}

	/*
	 * Return true if the is yet a like
	 */
	public boolean existLike(final User to, final StateUpdate pub) {
		for (Interest interest : getInterests()) {
			if (interest.getType().equals(InterestsType.LIKE)
					&& interest.getUpdate().equals(pub)
					&& interest.getUpdate().getOwner().equals(to)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Return true if the is yet a like
	 */
	public boolean existShare(final User to, final StateUpdate pub) {
		for (Interest interest : getInterests()) {
			if (interest.getType().equals(InterestsType.SHARE)
					&& interest.getUpdate().equals(pub)
					&& interest.getUpdate().getOwner().equals(to)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", username="
				+ username + "]";
	}

	public void deletePublication(Publication pub, User user) {
		if (pub.getClass().equals(StateUpdate.class) && this.equals(user)) {
			for (int i = 0; i < this.getPublications().size(); i++) {
				Publication publ = this.getPublications().get(i);
				if (publ.getClass().equals(Interest.class)
						&& ((StateUpdate) pub).getInterests().contains(publ)) {
					this.publications.remove(pub);
				}
			}
			this.publications.remove(pub);
		}
	}

	public Preferences getPrefs() {
		return prefs;
	}

	private void setPrefs(Preferences prefs) {
		Assert.notNull(prefs);
		this.prefs = prefs;
	}

	public void setPrefs(int numberOfDays) {
		if (numberOfDays >= 0) {
			this.prefs.setUpcomingFriendsBirthdaysNumber(numberOfDays);
		}
	}

	public boolean hasUpCommingBirthday(Preferences prefs) {
		DateTime current = new DateTime();
		DateTime aux = new DateTime(current.getYear(),
				birthday.getMonthOfYear(), birthday.getDayOfMonth(),
				birthday.getHourOfDay(), 0, 0, 0);
		int days = Days.daysBetween( current.toDateMidnight(),aux.toDateMidnight())
				.getDays();
		if( days >= 0 && days <= prefs.getUpcomingFriendsBirthdaysNumber() ){
			return true;
		}
		return false;
	}

	public boolean hasFriendsUpcomingBirthdays() {
		return getFriensUpcomingBirthdaysList().size() != 0;
	}

	public List<User> getFriensUpcomingBirthdaysList() {
		List<User> upcoming = new ArrayList<User>();
		for (User friend : friends) {
			if (friend.hasUpCommingBirthday(prefs)) {
				upcoming.add(friend);
			}
		}
		Collections.sort(upcoming);
		return upcoming;
	}

	@Override
	public int compareTo(User o) {
		return this.birthday.getDayOfWeek() > o.getBirthday().getDayOfWeek() ? 1
				: -1;
	}

}
