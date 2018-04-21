package edu.csula.web;

import edu.csula.storage.servlet.UsersDAOImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/auth")
public class AuthenticationServlet extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		// TODO: render the authentication page HTML

		if(request.getParameter("logout") != null){
			doDelete(request,response);
			request.getRequestDispatcher("/WEB-INF/admin-signin.jsp").forward(request,response);
		} else {
			request.getRequestDispatcher("/WEB-INF/admin-signin.jsp").forward(request,response);
		}

	}

	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: handle login
		UsersDAOImpl usersDAO = new UsersDAOImpl(request.getSession());
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		if(usersDAO.authenticate(username,password)){
			response.sendRedirect("/admin/generators");
		//	request.getRequestDispatcher("/admin/generators").forward(request,response);
			return;
		} else {
			doGet(request,response);
		}
	}

    @Override
    public void doDelete( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle logout
		UsersDAOImpl usersDAO = new UsersDAOImpl(request.getSession());
		usersDAO.logout();
	}
}
