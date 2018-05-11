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

import edu.csula.storage.GeneratorsDAO;
import edu.csula.storage.mysql.Database;
import edu.csula.models.Generator;
import edu.csula.storage.servlet.GeneratorsDAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/user/game")
public class GameServlet extends HttpServlet {
    Database context;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        context = new Database();
        GeneratorsDAO dao = new GeneratorsDAOImpl(request.getServletContext());
        dao.getAll();
        List<Generator> generator = dao.getAll();


        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

       // Generator

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();


       // String string = gson.toJson();



        request.getRequestDispatcher("/WEB-INF/game.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
