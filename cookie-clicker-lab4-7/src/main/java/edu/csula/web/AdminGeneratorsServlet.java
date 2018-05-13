package edu.csula.web;

import edu.csula.models.Generator;
import edu.csula.storage.GeneratorsDAO;
import edu.csula.storage.UsersDAO;
import edu.csula.storage.mysql.Database;
import edu.csula.storage.mysql.GeneratorsDAOImpl;
import edu.csula.storage.servlet.UsersDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import edu.csula.storage.servlet.GeneratorsDAOImpl;


@WebServlet("/admin/generators")
public class AdminGeneratorsServlet extends HttpServlet {
    Database context = new Database();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        UsersDAO users = new UsersDAOImpl(request.getSession());
        if (users.getAuthenticatedUser().equals(Optional.empty())) {
            response.sendRedirect("/cs3220stu115/admin/auth");
            return;
        }

        //GeneratorsDAO dao = new GeneratorsDAOImpl(getServletContext());
        GeneratorsDAO dao = new GeneratorsDAOImpl(context);
        List<Generator> generators = dao.getAll();
        dao.getAll();


        int startId = 0;

        if (request.getAttribute("id") == null || request.getAttribute("id") == "") {
            request.setAttribute("id", (dao.getAll().size() + 1));
        } else {
            request.setAttribute("id", request.getAttribute("id"));
        }

        request.setAttribute("generators", generators);
        System.out.println(dao.getAll());
        request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle upsert transaction
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));


        GeneratorsDAO dao = new GeneratorsDAOImpl(context);
        List<Generator> generators = dao.getAll();
        System.out.println(dao.getAll());
        List<Generator> change = new ArrayList<Generator>();


        if (action.equals("delete")) {
            dao.remove(id);
            generators.remove(id - 1);
            request.setAttribute("id", id);
            request.setAttribute("generators", generators);
            request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request, response);
            return;
        } else if (action.equals("add")) {

            String name = request.getParameter("generatorName");
            int rate = Integer.parseInt(request.getParameter("generatorRate"));
            int cost = Integer.parseInt(request.getParameter("generatorCost"));
            int unlock = Integer.parseInt(request.getParameter("unlockAt"));
            String description = request.getParameter("description");

            //int triggerAt = Integer.parseInt(request.getParameter("trigger"));


            Generator generator = new Generator(id, name, description, rate, cost, unlock);


            //first entry WORKING
            if (generators.size() == 0) {
                dao.add(generator);
                generators.add(generator);
                request.setAttribute("id", id + 1);
                request.setAttribute("generators", generators);
                request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request, response);

//				return;
                //Up to here is working
            } else {
                for (Generator g : generators) {
                    if (g.getId() == id) {
                        change.add(generator);
                    }
                }

                if (!change.isEmpty()) {
                    dao.set(id, generator);
                    generators.set(id, change.get(0));
                    request.setAttribute("generators", generators);
                    response.sendRedirect(request.getRequestURL().toString());
                    request.setAttribute("id", id);
                    return;
                } else {
                    dao.add(generator);
                    generators.add(generator);
                    request.setAttribute("id", id + 1);
                    request.setAttribute("generators", generators);
                    request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request, response);

                }
            }
            request.setAttribute("generators", generators);
            request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request, response);


            return;
        }
        doGet(request, response);
    }
}

