package edu.csula.storage.servlet;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import edu.csula.models.*;
import edu.csula.storage.UsersDAO;
import sun.nio.cs.US_ASCII;

/**
 * To abstract the storage access from the business layer using HttpSession
 */
public class UsersDAOImpl implements UsersDAO {
	private final HttpSession context;
	protected static final String CONTEXT_NAME = "users";

	public UsersDAOImpl(HttpSession context) {
		this.context = context;
	}

	@Override
	public boolean authenticate(String username, String password) {
		// TODO: check if username/password combination is valid and store the username/password into the session
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User(0,"admin","cs3220password"));
        users.add(new User(1, "rdunnin", "abc123"));
        System.out.println("username checking: "+ username);
        System.out.println("password checking: "+ password);
        for(User u : users){

            if(u.getUsername().equals(username)  && u.getPassword().equals(password)){
                context.setAttribute(CONTEXT_NAME,u);
                return true;
            }
        }

    return false;
	}

	@Override
	public Optional<User> getAuthenticatedUser() {
		// TODO: return the authenticated user if there is any
        Optional<User> userList = Optional.empty();

        if(context.getAttribute(CONTEXT_NAME) != null){
            userList = Optional.of((User)context.getAttribute(CONTEXT_NAME));
            return userList;
        }
        return userList;
	}

	@Override
	public void logout() {
		// TOOD: log user out using `invalidate`
        context.invalidate();
        //System.out.println(context.getAttribute(CONTEXT_NAME));

	}
}
