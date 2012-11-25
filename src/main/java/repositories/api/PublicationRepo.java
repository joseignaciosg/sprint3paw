package repositories.api;

import domain.entities.Comment;
import domain.entities.StateUpdate;

public interface PublicationRepo {

	StateUpdate getPublicationById(Integer id);

	public Comment getCommentById(Integer valueOf);

}
