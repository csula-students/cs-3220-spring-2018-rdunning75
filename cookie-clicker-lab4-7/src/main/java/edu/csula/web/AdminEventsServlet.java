package edu.csula.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.csula.storage.servlet.EventsDAOImpl;
import edu.csula.storage.EventsDAO;
import edu.csula.models.Event;

@WebServlet("/admin/events")
public class AdminEventsServlet extends HttpServlet {
	int globaleId = 0;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter display = response.getWriter();
			EventsDAO dao = new EventsDAOImpl(getServletContext());
			// TODO: render the events page HTMl
			int id = 0;



			String editId;

			System.out.println("id when the page is first loaded (should be zero)" + id);

			String table = "<div id=\\\"table\\\">\n "
					+ "<table>" +
					"		<tr>" +
					"	<th>Name</th>" +
					"	<th>Description</th>" +
					"	<th>Trigger At</th>" +
					"	</tr>";


			// this loop sets the ids on the tables to be pased to do post when you wish to edit a value.

			for (Event e : dao.getAll()) {

				table += "<tr>" +
						"		<td>" + e.getName() + "</td>\n" +
						"		<td>" + e.getDescription() + "</td>\n" +
						"		<td>" + e.getTriggerAt() + "</td>\n" +
						"		<td>" + e.getId() +"</td>\n" +
						"		<td>  <a href=\"events?id=" + id + " \" >edit</a>| delete </td>\n" +
						"</tr>";
				id++;
			}
			System.out.println("id after the table has been created" + id);

		if(request.getAttribute("id") != null){
			System.out.println(request.getAttribute("id") == null);
			id = (Integer) request.getAttribute("id");
		}



		table += "</table>\n </div>";

			if (request.getParameter("id") != null) {
				editId = "<input type=\"hidden\" name=\"id\" value = " + (request.getParameter("id"))+1 + " >";
				System.out.println("ID taken from parameter when parameter IS NOT origonally null: "+(request.getParameter("id")+1));
			} else {
				editId = "<input type=\"hidden\" name=\"id\" value = " + id + " >";
				System.out.println("ID submited to parameter when parameter IS origonally null: "+id);
			}




			display.print("<!DOCTYPE html>\n" +
					"<html>\n" +
					"<head>\n" +
					"<meta charset=\"UTF-8\">\n" +
					"<title>IncraMetal-game</title>\n" +
					"<link rel=\"stylesheet\" href=\"\\css\\basic.css\"> \n" +
					"</head>\n" +
					"<body>\n" +
					"	<div id=\"title\">\n" +
					"		<h1>IncraMetal Game Framework</h1>\n" +
					"	</div>\n" +
					"\n" +
					"	<nav>\n" +
					"		<a href=\"admin-info.html\">Game Information</a> | \n" +
					"		<a href=\"admin-generators.html\">Generators</a> | \n" +
					"			<a href=\"admin-events.html\">Events</a>\n" +
					"	</nav>\n" +
					"\n" +
					"\n" +
					"	<form method=\"POST\" id=\"form\">\n" +
					"		<div class=\"container\">\n" +
					"			<div id=\"stuff\">\n" +
					"				Event Name: <br> <input name=\"eventName\" id=\"eventName\"type=\"text\" placeholder=\"Event Name...\">\n" +
					"				<br> \n" +
					"				Event Description <br> \n" +
					"				<textarea name=\"description\" id =\"description\" rows=\"5\" cols=\"30\"> Insert description here </textarea> <br>\n" +
					"\n" +
					"				Trigger at: <br> <input type=\"text\"placeholder=\"Trigger @ this number...\" name=\"trigger\"> <br>"
					+ editId +
					"				<input  type=\"submit\" value=\"Add/Edit\">\n" +
					"			</div>\n" +
					// adds in the table element from above
					"\n" + table +
					"				<br>\n" +
					"			</div>\n" +
					"		</div>\n" +
					"	</form>\n" +
					"\n" +
					"</body>\n" +
					"</html>");

			//response.sendRedirect("/admin/events");

	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: handle upsert transaction

		EventsDAO dao = new EventsDAOImpl(getServletContext());
		List<Event> events = dao.getAll();
		List<Event> change = new ArrayList<Event>();

		RequestDispatcher rd = request.getRequestDispatcher("/admin/events");
		String name = request.getParameter("eventName");
		String description = request.getParameter("description");
		int triggerAt = Integer.parseInt(request.getParameter("trigger"));
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("id after the submit button is pressed, but before the event is added to the table : " + id);

		//should print out zero first, then one, then two, etc...

		int i = 0;
		Event event = new Event(id, name, description, triggerAt);

		//first entry WORKING
		if (events.size() == 0) {
			events.add(event);
			System.out.println("id after the submit button is pressed, and after the event is added to the table when the table is empty: " + id);
		} else {
			for (Event e : events) {
				if (e.getId() == id) {
					change.add(event);
				}
			}
				if (!change.isEmpty()) {
					events.set(id, change.get(0));
					System.out.println("id after the submit button is pressed, and after the event has been changed: " + id);
					response.sendRedirect("\\admin\\events");
					return;
				} else {
					events.add(event);
					System.out.println("id after the submit button is pressed, and after the event is added to the table when the table already has events: " + id);

				}
		}


//		for (Event e : events) {
//			if (dao.getById(id).get().getId() != e.getId()) {
//				System.out.println(dao.getById(id).get());
//				System.out.println("name: " + dao.getById(id).get().getName());
//				System.out.println("Desc: " + dao.getById(id).get().getDescription());
//				System.out.println("Id  : " + dao.getById(id).get().getId());
//				System.out.println("Trig: " + dao.getById(id).get().getTriggerAt());
//				System.out.println();
//				events.add(event);
//			} else {
//				System.out.println(dao.getById(id).get());
//				System.out.println("name: " + dao.getById(id).get().getName());
//				System.out.println("Desc: " + dao.getById(id).get().getDescription());
//				System.out.println("Id  : " + dao.getById(id).get().getId());
//				System.out.println("Trig: " + dao.getById(id).get().getTriggerAt());
//				System.out.println();
//				events.set(dao.getById(id).get().getId(), event);
//			}
//		}

//
//		for(Event e: events) {
//			if (e.getId() != id) {
//				events.add(new Event(id,name,description,triggerAt));
//			} else {
//				e.equals(new Event(id,name,description,triggerAt));
//			}
//		}


//			} else if (iterator.next().getId()  == id){
//				iterator.next().equals(new Event(id,name,description,triggerAt));
//				System.out.println("WORKING 3 :");
//				System.out.println(id);
//				System.out.println(i);
//				iterator.remove();
//			} else {
//				iterator.remove();
//				break;
//			}
//			
//		}	


//		for(Event e : events ) {
//			System.out.println ("Name: "+e.getName());
//		}
//	int newid = Integer.parseInt((String) request.getAttribute("id"));
//	request.setAttribute("id", newid+1);;
			System.out.println();
			request.setAttribute("id",id++);
			doGet(request, response);

		}
	}


	
	
	
//	public void doDelete(HttpServletRequest request, HttpServletResponse response){
//		Collection<Event> deleteArray = new ArrayList<Event>();
//		int id = Integer.parseInt(request.getParameter("id"));
//			for(Event e: events) {
//				if(e.getId() == id) {
//					deleteArray.add(e);
//				}
//			}
//		events.removeAll(deleteArray);
//
//	}
//}
