package edu.csula.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	

	
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter display = response.getWriter();
		EventsDAO dao = new EventsDAOImpl(getServletContext());
		// TODO: render the events page HTMl
		
		
		String table = "<div id=\\\"table\\\">\n "
				+ "<table>" + 
				"		<tr>" + 
				"	<th>Name</th>" + 
				"	<th>Description</th>" + 
				"	<th>Trigger At</th>" + 
				"	</tr>" ;
		
		if(dao.getAll().size() == 0) {
			System.out.println("theres nothing here boss");
		}
		
		// this loop sets the ids on the tables to be pased to do post when you wish to edit a value.
		int id = 0;
		for (Event e : dao.getAll()) {
			
			table += "<tr>" + 
					"		<td>" + e.getName() + "</td>\n" +
					"		<td>" + e.getDescription()+ "</td>\n" +
					"		<td>" + e.getTriggerAt()+ "</td>\n" +
					"		<td> <form method=\"DELETE\"<a href=\"events?id=" +id+ " \" >edit</a> | delete </td>\n"+
					"</tr>" ;	
			id++;
		}
		
		
		
		table += "</table>\n </div>";
		//System.out.println(table);
		
		
		String editId;
				
		if(request.getParameter("id") != null) {
			editId ="<input type=\"hidden\" name=\"id\" value = "+(Integer.parseInt(request.getParameter("id")))+" >";
			System.out.println("hello world");
			System.out.println();
		} else {
			 editId ="<input type=\"hidden\" name=\"id\" value = "+id+" >";
		}
		
		
		
		
		
		display.print("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=\"UTF-8\">\n" + 
				"<title>IncraMetal-game</title>\n" + 
				"<link rel=\"stylesheet\" href=\"..\\css\\basic.css\"> \n"+ 
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
	public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: handle upsert transaction

		EventsDAO dao = new EventsDAOImpl(getServletContext());
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/events");
		//Iterator<Event> iterator = events.iterator();
		String name = request.getParameter("eventName");
		String description = request.getParameter("description");
		int triggerAt =  Integer.parseInt(request.getParameter("trigger"));
		//this will set the id to the current parameter in the url named ID, allegedly
		int id = Integer.parseInt(request.getParameter("id"));
		//should print out zero first, then one, then two, etc...
		System.out.println("THIS IS THE ID TAKEN FROM THE PAREMETER: "+id);
		int i = 0;
		Event event = new Event(id,name,description,triggerAt);
		
		//first entry WORKING
		if(dao.getAll().size() == 0) {
			dao.getAll().add(event);
			System.out.println("WORKING 1 :");
			System.out.println(dao.getById(id));
			System.out.println();
			response.sendRedirect("events");
			return;
		}
//		

		
		//subsequent entries 
		id++;
//
//		for(Event e: dao.getAll()) {
//			if (events.iterator().next().getId() != id ){
//				System.out.println("this is the id:" + id);
//				events.add(new Event(id,name,description,triggerAt));
//				System.out.println("WORKING 2 :");
//				System.out.println("Iterator event id: "+ events.iterator().next().getId());
//				i = events.size();
//				System.out.println("size of collection:" + i);
//				System.out.println();
//				test = false;
//			} else if (events.iterator().next().getId() == id ) {
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
	doGet(request,response);
		
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
}
