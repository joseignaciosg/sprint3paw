package sozialnetz.domain.repositories.api;

import java.util.List;

import sozialnetz.domain.entities.Event;
import sozialnetz.domain.entities.User;


public interface EventRepo {

	public Event getEventByID(int id);
	
	public void add(Event event);
	
	public List<Event> upcomingEvents(User u);
}
