package edu.csula.storage.servlet;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletContext;

import edu.csula.storage.GeneratorsDAO;
import edu.csula.models.*;

/**
 * To abstract the storage access from the business layer using ServletContext
 * (application scope). This implementation will store the underlying data under
 * the application scope and read from it accordingly.
 *
 * As ServletContext is like a global HashMap, it's important for you to add a
 * context name to separate out the different section of data (e.g. events vs
 * generators) so that you can have the application scope looks like below:
 *
 * ```json
 * {
 *   "events": [
 *     { "id": 0, "name": "event-1", "description": "..." }
 *   ],
 *   "generators": [
 *     { "id": 0, "name": "generator-1", "description": "..." }
 *   ]
 * }
 * ```
 */
public class GeneratorsDAOImpl implements GeneratorsDAO {
	private final ServletContext context;
	protected static final String CONTEXT_NAME = "generators";

	public GeneratorsDAOImpl(ServletContext context) {
		this.context = context;
	}

	@Override
	public List<Generator> getAll() {
		List<Generator> generators = (List<Generator>) context.getAttribute(CONTEXT_NAME);

		if(generators == null){
			generators = new ArrayList<Generator>();
			context.setAttribute(CONTEXT_NAME,generators);
			return generators;
		}

		context.setAttribute(CONTEXT_NAME,generators);
		// TODO: get a list of generators from the context
		return generators;
	}

	@Override
	public Optional<Generator> getById(int id) {
		// TODO: get a certain generator from context


		List<Generator> generators = getAll();

		// Generic event for mutation
		Generator specificGenerator = new Generator(0,"","",0,0,0);

		//loop through to find event with an id
		for(Generator g : generators) {
			if (g.getId() == id){
				specificGenerator = g;
			}
		}
		//return the event
		Optional<Generator> generator = Optional.of(specificGenerator);
		return generator;
	}

	@Override
	public void set(int id, Generator generator) {
		// TODO: change a certain generator from context
		List<Generator> generators = getAll();
		generators.set(id-1,generator);
		context.setAttribute(CONTEXT_NAME,generators);
	}

	@Override
	public void add(Generator generator) {
		// TODO: add a new generator to the context
		List<Generator> generators = getAll();
		generators.add(generator);
		context.setAttribute(CONTEXT_NAME,generators);
	}

	@Override
	public void remove(int id) {
		// TODO: remove a single generator from the context
		List<Generator> generators = getAll();
		Generator genForRemoval = getById(id).get();
		generators.remove(genForRemoval);
		context.setAttribute(CONTEXT_NAME,generators);
	}
}
