package sozialnetz.domain.repositories.api;

import sozialnetz.domain.entities.Comment;
import sozialnetz.domain.entities.StateUpdate;

public interface PublicationRepo {

	StateUpdate getPublicationById(Integer id);

	public Comment getCommentById(Integer valueOf);

}
