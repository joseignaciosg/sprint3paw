package domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

@Embeddable
public class Guest {

	@ManyToOne(optional=false)
	private User guest;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GuestType guestType;

	public Guest(User guest, GuestType guestType) {
		super();
		Assert.notNull(guest);
		this.guest = guest;
		setGuestType(guestType);
	}

	protected Guest() {
	}

	public User getGuest() {
		return guest;
	}

	public GuestType getGuestType() {
		return guestType;
	}

	public void setGuestType(GuestType guestType) {
		Assert.notNull(guestType);
		this.guestType = guestType;
	}

	public void confirm() {
		this.guestType = GuestType.GOING;
	}

	public void notGoing() {
		this.guestType = GuestType.NOTGOING;

	}

	public void dontKnow() {
		this.guestType = GuestType.DONTKNOW;

	}

}
