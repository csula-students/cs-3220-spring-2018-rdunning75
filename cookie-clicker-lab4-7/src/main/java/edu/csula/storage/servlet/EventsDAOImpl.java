package edu.csula.storage.servlet;

import java.lang.reflect.Array;
import java.util.*;

import javax.servlet.ServletContext;

import edu.csula.storage.EventsDAO;
import edu.csula.models.Event;


/**
 * To abstract the storage access from the business layer using ServletContext
 * (application scope). This implementation will store the underlying data under
 * the application scope and read from it accordingly.
 *
 * As ServletContext is like a global HashMap, it's important for you to add a
 * context name to separate out the different section of data (e.g. events vs
 * generators) so that you can have the application scope looks like below:
 *
 * ```json
 * {
 *   "events": [
 *     { "id": 0, "name": "event-1", "description": "..." }
 *   ],
 *   "generators": [
 *     { "id": 0, "name": "generator-1", "description": "..." }
 *   ]
 * }
 * ```
 */
public class EventsDAOImpl implements EventsDAO {
	private final ServletContext context;
	protected static final String CONTEXT_NAME = "events";

	public EventsDAOImpl(ServletContext context) {
		this.context = context;
	}

	@Override
	public List<Event> getAll() {
		// TODO: read a list of events from context
		List<Event> events = (List<Event>) context.getAttribute(CONTEXT_NAME);

		if(events == null){
			// Calling Collections.emptyList() will make an empty list
			events = new ArrayList<Event>();
			context.setAttribute(CONTEXT_NAME,events);
			return events;
	} else {
		context.setAttribute(CONTEXT_NAME,events);
		return events;
	}

	}

	@Override
	public Optional<Event> getById(int id) {
		// TODO: get a certain event given its id from context (see getAll() on
		// getting a list first and get a certain one from the list)


		List<Event> events = getAll();

		// Generic event for mutation
		Event specificEvent = new Event(0,"","",0);

		//loop through to find event with an id
		for(Event e : events) {
			if (e.getId() == id){
				specificEvent = e;
			}
		}

		//return the event
		Optional<Event> event = Optional.of(specificEvent);
		return event;
	}

	@Override
	public void set(int id, Event event) {
		// TODO: set a certain event given id to be different from context
		List<Event> events = getAll();
		events.set(id-1,event);
		context.setAttribute(CONTEXT_NAME,events);
	}

	@Override
	public void add(Event event) {
		// TODO: add a new event to the context
		List<Event> events = getAll();
		events.add(event);
		context.setAttribute(CONTEXT_NAME,events);
	}

	@Override
	public void remove(int id) {
		List<Event> events = getAll();
		Event eventForRemoval = getById(id).get();
		events.remove(eventForRemoval);
		context.setAttribute(CONTEXT_NAME,events);
	}
}
