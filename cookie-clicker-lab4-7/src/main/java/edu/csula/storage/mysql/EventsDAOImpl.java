package edu.csula.storage.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.*;

import edu.csula.storage.EventsDAO;
import edu.csula.storage.Database;
import edu.csula.models.Event;

public class EventsDAOImpl implements EventsDAO {
	private final Database context;

	// TODO: fill the Strings with the SQL queries as "prepated statements" and
	//       use these queries variable accordingly in the method below
	protected static final String getAllQuery = "SELECT * FROM events";
	protected static final String getByIdQuery = "SELECT * FROM events WHERE ID = (?)";
	protected static final String setQuery = "UPDATE events SET name = ?, description = ?, trigger_at = ? WHERE id = ?";
	protected static final String addQuery = "INSERT INTO events (id,name,description,trigger_at, created_by) VALUES (?,?,?,?,?)";
	protected static final String removeQuery = "DELETE FROM events WHERE id = ? ";



	public EventsDAOImpl(Database context) {
		this.context = context;
	}

	@Override
	public List<Event> getAll() {
		// TODO: get all events from jdbc
		List<Event> events = new ArrayList<Event>();

		try (Connection c = context.getConnection(); Statement stmt = c.createStatement()){
			ResultSet results = stmt.executeQuery(getAllQuery);

			while(results.next()){
				String name = results.getString(2);
				String description = results.getString(3);
				int triggerAt = results.getInt(4);
				int id =  results.getInt(1);
				events.add(new Event(id,name,description,triggerAt));
			}
			for(Event e :events){
                System.out.println(e.getId());
            }
		}
		catch (SQLException e){
			e.printStackTrace();
		};
		return events;
	}

	@Override
	public Optional<Event> getById(int id) {
		// TODO: get specific event by id
		Optional<Event> event = Optional.empty();


		try (Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(getByIdQuery)) {

			stmt.setInt(1,id);
			ResultSet results = stmt.executeQuery();
			String name = results.getString(2);
			String description = results.getString(3);
			int triggerAt = results.getInt(4);

			Event specificEvent = new Event(id,name,description,triggerAt);
			event = Optional.of(specificEvent);
			return event;

		}catch (SQLException e){
			e.printStackTrace();
		}

		return Optional.empty();
	}

	@Override
	public void set(int id, Event event) {
		// TODO: update specific event by id
		//Event oldEvent = getById(id).get();

		try (Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(setQuery)){

			Integer triggerAt = event.getTriggerAt();
			Integer idObj = id;


			stmt.setString(1, event.getName());
			stmt.setString(2, event.getDescription());
			stmt.setString(3, triggerAt.toString());
			stmt.setString(4, idObj.toString());
			stmt.executeUpdate();

		} catch (SQLException e){
			e.printStackTrace();
		}

		}

	@Override
	public void add(Event event) {
		// TODO: implement jdbc logic to add a new event

		try(Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(addQuery)){
			stmt.setInt(1,event.getId());
			stmt.setString(2,event.getName());
			stmt.setString(3,event.getDescription());
			stmt.setInt(4,event.getTriggerAt());
			if((Integer)event.getCreatedBy() != null) {
				stmt.setInt(5, event.getCreatedBy());
			} else {
				stmt.setInt(5,0);
			}

			stmt.executeUpdate();




		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public void remove(int id) {
		// TODO: implement jdbc logic to remove event by id
		try(Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(removeQuery)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
