package sozialnetz.domain.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.util.Assert;

@Entity
public class NewFriendshipNotification extends Notification {

	@OneToOne(optional=false)
	private User newFriend;

	public NewFriendshipNotification(final User from, final User to,
			final boolean read, User newFriend) {
		super(from, to, NotificationType.MUTUALFRIEND, read);
		Assert.notNull(newFriend);
		this.newFriend = newFriend;
	}

	protected NewFriendshipNotification() {
		super();
	}

	public User getNewFriend() {
		return newFriend;
	}

}
