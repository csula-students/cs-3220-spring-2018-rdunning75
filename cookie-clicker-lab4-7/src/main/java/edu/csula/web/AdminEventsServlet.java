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
			dao.getAll().size();
			int id = 0;



			String editId;

			System.out.println("id when the page is first loaded (should be zero)" + id);

			String table = "<div id=\"table\">\n "
					+ "<table>" +
					"		<tr>" +
					"	<th>Name</th>" +
					"	<th>Description</th>" +
					"	<th>Trigger At</th>" +
					"	</tr>";


			// this loop sets the ids on the tables to be pased to do post when you wish to edit a value.
			System.out.println("Size in the get method "+dao.getAll().size());
			for (Event e : dao.getAll()) {

				table += "<tr>" +
						"		<td>" + e.getName() + "</td>\n" +
						"		<td>" + e.getDescription() + "</td>\n" +
						"		<td>" + e.getTriggerAt() + "</td>\n" +
						"		<td>" + e.getId() +"</td>\n" +
						"		<td>  <a href=\"events?id=" + id + " \"> edit </a> | " +
						"<form name=\"delete\" method=\"POST\">" +
						"<input type=\"hidden\" name=\"action\" value =\"delete\">" +
						"<input type=\"hidden\" name=\"id\" value ="+ id +">" +
						"<button type=\"submit\" > delete </button> </form> </td>" +
						"</tr>";
				id++;
			}
			table += "</table>\n </div>";
			System.out.println("id after the table has been created" + id);

		if(request.getAttribute("id") != null){
			System.out.println(request.getAttribute("id") == null);
			id = (Integer) request.getAttribute("id");
		}





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
					"<title>Katts-game</title>\n" +
					"<link rel=\"stylesheet\" href=\"\\css\\basic.css\"> \n" +
					"</head>\n" +
					"<body>\n" +
					"	<div id=\"title\">\n" +
					"		<h1>IncraKatts Game Framework</h1>\n" +
					"	</div>\n" +
					"\n" +
					"	<nav>\n" +
					"		<a href=\"admin-info.html\">Game Information</a> | \n" +
					"		<a href=\"admin-generators.html\">Generators</a> | \n" +
					"			<a href=\"admin-events.html\">Events</a>\n" +
					"	</nav>\n" +
					"\n" +
					"\n" +
					"	<div class=\"container\">\n" +
					"	<form name = \"add\" method=\"POST\" id=\"form\">\n" +
					"			<div id=\"stuff\">\n" +
					"				Event Name: <br> <input name=\"eventName\" id=\"eventName\"type=\"text\" placeholder=\"Event Name...\">\n" +
					"				<br> \n" +
					"				Event Description <br> \n" +
					"				<textarea name=\"description\" id =\"description\" rows=\"5\" cols=\"30\"> Insert description here </textarea> <br>\n" +
					"\n" +
					"				Trigger at: <br> <input type=\"text\"placeholder=\"Trigger @ this number...\" name=\"trigger\"> <br>"
									+ editId +
					"				<input  type=\"submit\" value=\"Add/Edit\">\n" +
					"				<input type=\"hidden\" name=\"action\" value=\"add\">" +
					"			</div>\n" +
					"	</form>\n" +
								"\n" + table +
					"				<br>\n" +
					"			</div>\n" +
					"		</div>\n" +

					"\n" +
					"</body>\n" +
					"</html>");

			//response.sendRedirect("/admin/events");

	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: handle upsert transaction
		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(action);

		EventsDAO dao = new EventsDAOImpl(getServletContext());
		List<Event> events = dao.getAll();
		System.out.println("Size in the post method before insertion "+events.size());
		List<Event> change = new ArrayList<Event>();


		 if (action.equals("delete")) {
			events.remove(id);
			response.sendRedirect("\\admin\\events");
			return;
		} else if(action.equals("add")) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/events");
			String name = request.getParameter("eventName");
			String description = request.getParameter("description");
			int triggerAt = Integer.parseInt(request.getParameter("trigger"));

			System.out.println("id after the submit button is pressed, but before the event is added to the table : " + id);
//			System.out.println(delete);


			int i = 0;
			Event event = new Event(id, name, description, triggerAt);


			//first entry WORKING
			System.out.println("events.size() == 0 :"+ (events.size() == 0));
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
					System.out.println("Size in the post method after mutation "+events.size());
					return;
				} else {
					events.add(event);
					System.out.println("id after the submit button is pressed, and after the event is added to the table when the table already has events: " + id);

				}
			}
			System.out.println();
			request.setAttribute("id", id++);
			response.sendRedirect("\\admin\\events");
 			return;
		}
		doGet(request, response);
		}
	}


	
	
	

