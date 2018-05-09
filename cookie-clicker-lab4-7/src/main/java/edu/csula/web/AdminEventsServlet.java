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

import java.sql.*;

import edu.csula.storage.UsersDAO;
//import edu.csula.storage.servlet.EventsDAOImpl;
import edu.csula.storage.mysql.EventsDAOImpl;
import edu.csula.storage.mysql.Database;
import edu.csula.storage.EventsDAO;
import edu.csula.models.Event;
import edu.csula.storage.servlet.UsersDAOImpl;

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
            response.sendRedirect("/admin/auth");
            return;
        }

        EventsDAO dao = new EventsDAOImpl(context);
        System.out.println(context + " THIS IS THE CONTExT");
        List<Event> events = null;

        try {
            events = dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Event e : events) {
            System.out.println(e.getId());
        }

        System.out.println("id in doGet: "+request.getAttribute("id"));

        if (request.getAttribute("id") == null || request.getAttribute("id") == "") {
            try {
                System.out.println("size of database table events:" + dao.getAll().size());
                request.setAttribute("id", (dao.getAll().size()+1));
                System.out.println(request.getAttribute("id"));
            } catch (SQLException e) {
                e.printStackTrace();
            }



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
        try {
            events = dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Event> change = new ArrayList<Event>();


        if (action.equals("delete")) {
            System.out.println("NOT WORKING");
            dao.remove(id);
            events.remove(id-1);
            request.setAttribute("id", id);
            request.setAttribute("events",events);
            request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);
            return;
        } else if (action.equals("add")) {
            System.out.println("WORKING pt1");
            String name = request.getParameter("eventName");
            String description = request.getParameter("description");
            int triggerAt = Integer.parseInt(request.getParameter("trigger"));


            Event event = new Event(id, name, description, triggerAt);
            System.out.println(event.getId()+ " "+event.getName() +" "+event.getDescription());


            //first entry WORKING
            if (events.size() == 0) {
                dao.add(event);
                events.add(event);
                System.out.println("WORKING add when no values in database");
                for(Event e : events) {
                    System.out.println(e.getId()+ " "+e.getName() +" "+e.getDescription());
                }

                request.setAttribute("id", id + 1);
                System.out.println("id in doPost after adding first event: "+ request.getAttribute("id"));
                request.setAttribute("events",events);
                request.getRequestDispatcher("/WEB-INF/admin-events.jsp").forward(request, response);
            } else {
                for (Event e : events) {
                    if (e.getId() == id) {
                        change.add(event);
                    }
                }
                System.out.println(change.isEmpty());

                if (!change.isEmpty()) {
                    System.out.println("WORKING change");
                    dao.set(id,event);
                    events.set(id-1,event);
                    request.setAttribute("events", events);
                    response.sendRedirect(request.getRequestURL().toString());
                    request.setAttribute("id", id);
                    return;
                } else {
                    System.out.println("WORKING add when values in database");

                    dao.add(event);
                    events.add(event);
                    for(Event e : events) {
                        System.out.println(e.getId()+ " "+e.getName() +" "+e.getDescription());
                    }
                    request.setAttribute("id", id + 1);
                    System.out.println("id after doPost when database has events: "+ request.getAttribute("id"));
                    System.out.println(request.getAttribute("id"));
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


	
	
	

