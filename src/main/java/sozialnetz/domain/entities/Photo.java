package sozialnetz.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.springframework.util.Assert;

@Entity
public class Photo extends PersistentEntity {

	@Lob
	@Column(nullable = false)
	private byte[] photo;

	protected Photo() {
	}

	public Photo(byte[] photo) {
		Assert.notNull(photo);
		this.photo = photo;

	}

	public byte[] getPhoto() {
		return photo;
	}


}
