package edu.csula.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import edu.csula.models.State;
import edu.csula.storage.EventsDAO;
import edu.csula.storage.GeneratorsDAO;
import edu.csula.storage.mysql.Database;
import edu.csula.models.Generator;
import edu.csula.models.Event;

//import edu.csula.storage.servlet.GeneratorsDAOImpl;
import edu.csula.storage.mysql.EventsDAOImpl;
import edu.csula.storage.mysql.GeneratorsDAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/user/game")
public class GameServlet extends HttpServlet {
    Database context = new Database();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GeneratorsDAO daoGenerator = new GeneratorsDAOImpl(context);
        EventsDAO daoEvent = new EventsDAOImpl(context);
        int counter = 0;
        State state = new State(daoEvent.getAll(),daoGenerator.getAll(),counter);






        List<Generator> generator = daoGenerator.getAll();
        List<Event> events = daoEvent.getAll();



        response.setContentType("application/json");



        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String string = gson.toJson(state);
        request.setAttribute("state",string);
        System.out.println(string);
        request.setAttribute("generators", generator);
        request.setAttribute("counter", state.getCounter());





        request.getRequestDispatcher("/WEB-INF/game.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
