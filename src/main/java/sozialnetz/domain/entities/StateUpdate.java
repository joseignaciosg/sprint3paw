package sozialnetz.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

@Entity
public class StateUpdate extends Publication {

	public static final int STATE_UPDATE_MAX_TEXT = 200;

	@OneToOne
	private User from; // el que la hace

	@Column(nullable = false, length = STATE_UPDATE_MAX_TEXT)
	private String text;

	@OneToMany(cascade = CascadeType.ALL)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Interest> interests = new ArrayList<Interest>();

	@OneToMany(cascade = CascadeType.ALL)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Comment> comments = new ArrayList<Comment>();

	protected StateUpdate() {
	}

	public StateUpdate(final User from, final User owner, final String text) {
		super(owner);
		Assert.notNull(from);
		this.from = from;
		this.setText(text);
	}

	public List<Interest> getInterests() {
		return interests;
	}

	private void setText(String text) {
		if (text == null || "".equals(text.trim())
				|| text.length() > STATE_UPDATE_MAX_TEXT) {
			throw new IllegalStateException();
		}
		this.text = text;
	}

	public void addInterest(Interest interest) {
		interests.add(interest);
	}

	public User getFrom() {
		return this.from;
	}

	public String getText() {
		return this.text;
	}

	public boolean isLikedByUser(User user) {
		for (Interest interest : this.getInterests()) {
			if (interest.getOwner().equals(user)) {
				if (interest.getType().equals(InterestsType.LIKE)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSharedByUser(User user) {
		for (Interest interest : this.getInterests()) {
			if (interest.getOwner().equals(user)) {
				if (interest.getType().equals(InterestsType.SHARE)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void addComment(String text, User user/* el que hace el comentario */) {
		/* owner es el dueño del muro en el que está el comentario */
		/* from es el que hizo la publicación */
		// si no son amigos no puede hacer un like
		if (!getOwner().equals(user) && !getOwner().isFriend(user)) {
			return;
		}
		this.getOwner().addNotification(user, NotificationType.COMMENT);
		this.getFrom().addNotification(user, NotificationType.COMMENTOPUB);
		for (Comment comm : this.getComments()) {
			comm.getOwner()
					.addNotification(user, NotificationType.COMMENTSHARE);
		}
		this.comments.add(new Comment(new DateTime(), text, user));
	}

	public void deleteComment(Comment comment, User current) {
		if (this.getClass().equals(StateUpdate.class)
				&& this.getOwner().equals(current)) {
			this.comments.remove(comment);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		StateUpdate other = (StateUpdate) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
