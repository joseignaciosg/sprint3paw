package sozialnetz.domain.repositories.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public abstract class AbstractHibernateRepo {
	private final SessionFactory sessionFactory;

	public AbstractHibernateRepo(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(final Class<T> type, final Serializable id) {
		return (T) this.getSession().get(type, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> find(final String hql, final Object... params) {
		final Session session = this.getSession();

		final Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		final List<T> list = query.list();
		return list;
	}

	protected org.hibernate.classic.Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public Serializable save(final Object o) {
		return this.getSession().save(o);
	}
	


}
