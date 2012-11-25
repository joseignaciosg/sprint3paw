package domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "interests")
public class Interest extends Publication {

	@ManyToOne(optional=false)
	private StateUpdate update;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private InterestsType type;

	protected Interest() {
		super();
	}

	public Interest(final StateUpdate update,
			final User from, final InterestsType type) {
		super(from);
		Assert.notNull(type);
		this.update = update;
		this.type = type;

	}

	public InterestsType getType() {
		return type;
	}

	public StateUpdate getUpdate() {
		return this.update;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((update == null) ? 0 : update.hashCode());
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
		Interest other = (Interest) obj;
		if (update == null) {
			if (other.update != null)
				return false;
		} else if (!update.equals(other.update))
			return false;
		return true;
	}

}
