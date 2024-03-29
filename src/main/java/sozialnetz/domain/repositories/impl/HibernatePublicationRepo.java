package sozialnetz.domain.repositories.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sozialnetz.domain.entities.Comment;
import sozialnetz.domain.entities.StateUpdate;
import sozialnetz.domain.repositories.api.PublicationRepo;


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
