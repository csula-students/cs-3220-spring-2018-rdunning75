package edu.csula.web;

import edu.csula.models.Generator;
import edu.csula.storage.GeneratorsDAO;
import edu.csula.storage.UsersDAO;
import edu.csula.storage.servlet.GeneratorsDAOImpl;
import edu.csula.storage.servlet.UsersDAOImpl;



import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@WebServlet("/admin/generators")
public class AdminGeneratorsServlet extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		UsersDAO users = new UsersDAOImpl(request.getSession());
		if(users.getAuthenticatedUser().equals(Optional.empty())){
			response.sendRedirect("/admin/auth");
			return;
		}

		GeneratorsDAO dao = new GeneratorsDAOImpl(getServletContext());
		List<Generator> generators = dao.getAll();
		int startId = 0;

		if(request.getAttribute("id") == null || request.getAttribute("id") == ""){
			request.setAttribute("id",generators.size());
		} else {
			request.setAttribute("id", request.getAttribute("id"));
		}


		request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request,response);
	}


	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: handle upsert transaction
		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));


		GeneratorsDAO dao = new GeneratorsDAOImpl(getServletContext());
		List<Generator> generators = dao.getAll();
		List<Generator> change = new ArrayList<Generator>();


		if (action.equals("delete")) {
			generators.remove(id);
			request.setAttribute("id", id);
			request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request,response);
			return;
		} else if(action.equals("add")) {

			String name = request.getParameter("generatorName");
			int rate = Integer.parseInt(request.getParameter("generatorRate"));
			int cost = Integer.parseInt(request.getParameter("generatorCost"));
			int unlock = Integer.parseInt(request.getParameter("unlockAt"));
			String description = request.getParameter("description");

			//int triggerAt = Integer.parseInt(request.getParameter("trigger"));


			Generator event = new Generator(id,name,description,rate,cost,unlock);


			//first entry WORKING
			if (generators.size() == 0) {
				generators.add(event);
				request.setAttribute("id", id + 1);
				//	response.sendRedirect(request.getRequestURL().toString());
				request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request,response);

//				return;
				//Up to here is working
			} else {
				for (Generator g : generators) {
					if (g.getId() == id) {
						change.add(event);
					}
				}

				if (!change.isEmpty()) {
					generators.set(id, change.get(0));
					request.setAttribute("generatorList",generators);
					response.sendRedirect(request.getRequestURL().toString());
					//	request.getRequestDispatcher("/admin-events.jsp").forward(request,response);
					request.setAttribute("id", id);
					return;
				} else {
					generators.add(event);
					request.setAttribute("id", id + 1);
					request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request,response);

				}
			}
			request.setAttribute("generatorList",generators);
			request.getRequestDispatcher("/WEB-INF/admin-generators.jsp").forward(request,response);


			return;
		}
		doGet(request, response);
	}
}

