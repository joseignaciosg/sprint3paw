package sozialnetz.domain.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sozialnetz.domain.entities.Event;
import sozialnetz.domain.entities.User;
import sozialnetz.domain.repositories.api.EventRepo;


@Repository
public class HibernateEventRepo extends AbstractHibernateRepo implements
		EventRepo {

	@Autowired
	public HibernateEventRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Event getEventByID(int id) {
		return this.get(Event.class, id);
	}

	@Override
	public void add(Event event) {
		this.save(event);
	}

	@Override
	public List<Event> upcomingEvents(User u) {
		List<Event> allEvents = find("from Event where ( date > NOW())");
		List<Event> resul = getInvitedEvents(allEvents, u);
		return resul;
	}

	private List<Event> getInvitedEvents(List<Event> allEvents, User u) {
		List<Event> resul = new ArrayList<Event>();
		for (Event event : allEvents) {
			if (event.getGuest(u) != null)
				resul.add(event);
		}
		return resul;
	}

}
