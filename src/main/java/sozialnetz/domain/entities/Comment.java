package sozialnetz.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

@Entity
public class Comment extends PersistentEntity {

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime date;

	@Column(nullable = false, length = 200)
	private String text;

	@ManyToOne(optional=false)
	private User owner;

	protected Comment() {
	}

	public Comment(DateTime date, String text, User owner) {
		Assert.notNull(date);
		Assert.notNull(text);
		Assert.notNull(owner);
		this.date = date;
		this.text = text;
		this.owner = owner;
	}

	public User getOwner() {
		return this.owner;
	}

	public DateTime getDate() {
		return date;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Comment other = (Comment) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
	
	

}
