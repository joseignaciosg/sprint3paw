package repositories.Impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import repositories.api.PublicationRepo;
import domain.entities.Comment;
import domain.entities.StateUpdate;

@Repository
public class HibernatePublicationRepo extends AbstractHibernateRepo implements
		PublicationRepo {


	@Autowired
	public HibernatePublicationRepo(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public StateUpdate getPublicationById(final Integer id) {
		return this.get(StateUpdate.class, id);
	}

	@Override
	public Comment getCommentById(final Integer id) {
		return this.get(Comment.class, id);
	}



}
