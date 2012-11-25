package sozialnetz.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publication extends PersistentEntity implements
		Comparable<Publication> {
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime date;
	
	@ManyToOne(optional=false)
	private User owner;

	protected Publication(){
	}
	
	public Publication(User owner){
		Assert.notNull(owner);
		this.owner = owner;
		this.date = new DateTime();
	}
	public User getOwner() {
		return this.owner;
	}

	public DateTime getDate() {
		return this.date;
	}
	
	@Override
	public int compareTo(Publication o) {
		return o.getDate().compareTo(this.date);
	}
	
}
