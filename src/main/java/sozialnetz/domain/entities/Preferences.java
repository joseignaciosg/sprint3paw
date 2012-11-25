package sozialnetz.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Preferences {

	private final static int UPCOMINGBIRTHDAYSDEFAULT = 3;

	@Column(nullable = false)
	private int upcomingFriendsBirthdaysNumber;

	protected Preferences() {
		setUpcomingFriendsBirthdaysNumber(UPCOMINGBIRTHDAYSDEFAULT);
	}

	public int getUpcomingFriendsBirthdaysNumber() {
		return upcomingFriendsBirthdaysNumber;
	}

	public void setUpcomingFriendsBirthdaysNumber(
			int upcomingFriendsBirthdaysNumber) {
		if (upcomingFriendsBirthdaysNumber >= 0) {
			this.upcomingFriendsBirthdaysNumber = upcomingFriendsBirthdaysNumber;
		}
	}

}
