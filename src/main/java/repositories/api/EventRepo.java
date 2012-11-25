package repositories.api;

import java.util.List;

import domain.entities.Event;
import domain.entities.User;

public interface EventRepo {

	public Event getEventByID(int id);
	
	public void add(Event event);
	
	public List<Event> upcomingEvents(User u);
}
