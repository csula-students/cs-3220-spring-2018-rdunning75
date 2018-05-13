package edu.csula.web;

import edu.csula.models.Event;
import edu.csula.storage.EventsDAO;
import edu.csula.storage.UsersDAO;
import edu.csula.storage.mysql.Database;
import edu.csula.storage.mysql.EventsDAOImpl;
import edu.csula.storage.servlet.UsersDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import edu.csula.storage.servlet.EventsDAOImpl;

@WebServlet("/admin/events")
public class AdminEventsServlet extends HttpServlet {
    int globaleId = 0;
    Database context = new Database();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter display = response.getWriter();

        UsersDAO users = new UsersDAOImpl(request.getSession());
        if (users.getAuthenticatedUser().equals(Optional.empty())) {
            response.sendRedirect("cs3220stu115/admin/auth");
            return;
        }

        EventsDAO dao = new EventsDAOImpl(context);

        List<Event> events = null;



            events = dao.getAll();


//        for (Event e : events) {
//            System.out.println(e.getId());
//        }


        if (request.getAttribute("id") == null || request.getAttribute("id") == "") {

                request.setAttribute("id", (dao.getAll().size() + 1));

        } else {
            request.setAttribute("id", request.getAttribute("id"));
        }


        // TODO: render the events page HTMl

        request.setAttribute("events", events);
        request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle upsert transaction
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));


        EventsDAO dao = new EventsDAOImpl(context);
        List<Event> events = null;

            events = dao.getAll();


        List<Event> change = new ArrayList<Event>();


        if (action.equals("delete")) {
            dao.remove(id);
            events.remove(id - 1);
            request.setAttribute("id", id);
            request.setAttribute("events", events);
            request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);
            return;
        } else if (action.equals("add")) {

            String name = request.getParameter("eventName");
            String description = request.getParameter("description");
            int triggerAt = Integer.parseInt(request.getParameter("trigger"));


            Event event = new Event(id, name, description, triggerAt);


            //first entry WORKING
            if (events.size() == 0) {
                dao.add(event);
                events.add(event);
                request.setAttribute("id", id + 1);
                request.setAttribute("events", events);
                request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);
            } else {
                for (Event e : events) {
                    if (e.getId() == id) {
                        change.add(event);
                    }
                }

                if (!change.isEmpty()) {
                    dao.set(id, event);
                    events.set(id - 1, event);
                    request.setAttribute("events", events);
                    response.sendRedirect(request.getRequestURL().toString());
                    request.setAttribute("id", id);
                    return;
                } else {

                    dao.add(event);
                    events.add(event);
                    request.setAttribute("id", id + 1);
                    request.setAttribute("events", events);
                    request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);

                }
            }
            request.setAttribute("events", events);
            request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);


            return;
        }
        doGet(request, response);
    }
}


	
	
	

