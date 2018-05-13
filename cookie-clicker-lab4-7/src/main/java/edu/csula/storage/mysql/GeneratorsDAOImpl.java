package edu.csula.storage.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.csula.storage.GeneratorsDAO;
import edu.csula.storage.Database;
import edu.csula.models.Generator;

public class GeneratorsDAOImpl implements GeneratorsDAO {
	private final Database context;

	// TODO: fill the Strings with the SQL queries as "prepated statements" and
	//       use these queries variable accordingly in the method below
	protected static final String getAllQuery = "SELECT * FROM generators";
	protected static final String getByIdQuery = "SELECT * FROM generators WHERE id = (?)";
	protected static final String setQuery = "UPDATE generators SET name = ?, description = ?, rate = ?, base_cost = ?, unlock_at = ? WHERE id = ?";
	protected static final String addQuery = "INSERT INTO generators (id,name,description,rate, base_cost,unlock_at) VALUES (?,?,?,?,?,?)";
	protected static final String removeQuery = "DELETE FROM generators WHERE id = ? ";



	public GeneratorsDAOImpl(Database context) {
		this.context = context;
	}

	@Override
	public List<Generator> getAll() {
		// TODO: get all Generators from jdbc
		List<Generator> Generators = new ArrayList<Generator>();

		try (Connection c = context.getConnection(); Statement stmt = c.createStatement()){
			ResultSet results = stmt.executeQuery(getAllQuery);

			while(results.next()){
				int id =  results.getInt(1);
				String name = results.getString(2);
				String description = results.getString(3);
				int rate = results.getInt(4);
				int base_cost = results.getInt(5);
				int unlock = results.getInt(6);
				Generators.add(new Generator(id,name,description,rate,base_cost,unlock));
			}
			for(Generator e :Generators){
				System.out.println(e.getId());
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		};
		return Generators;
	}

	@Override
	public Optional<Generator> getById(int id) {
		// TODO: get specific Generator by id
		Optional<Generator> Generator = Optional.empty();
			Integer idObj = id;


		try (Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(getByIdQuery)) {

			stmt.setString(1,idObj.toString());
			ResultSet results = stmt.executeQuery();

			String name = results.getString(2);
			String description = results.getString(3);
			int rate = results.getInt(4);
			int base_cost = results.getInt(5);
			int unlock = results.getInt(6);
			int created_by = results.getInt(7);

			Generator specificGenerator = new Generator(id,name,description,rate,base_cost,unlock);
			Generator = Optional.of(specificGenerator);
			return Generator;

		}catch (SQLException e){
			e.printStackTrace();
		}

		return Optional.empty();
	}

	@Override
	public void set(int id, Generator Generator) {
		// TODO: update specific Generator by id
		//Generator oldGenerator = getById(id).get();



		try (Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(setQuery)){

			Integer rate = Generator.getRate();
			Integer cost = Generator.getBaseCost();
			Integer unlock =  Generator.getUnlockAt();
			Integer idObj = id;


			stmt.setString(1, Generator.getName());
			stmt.setString(2, Generator.getDescription());
			stmt.setString(3, rate.toString());
			stmt.setString(5, cost.toString());
			stmt.setString(6, unlock.toString());
			stmt.setString(4, idObj.toString());
			stmt.executeUpdate();

		} catch (SQLException e){
			e.printStackTrace();
		}

	}

	@Override
	public void add(Generator generator) {
		// TODO: implement jdbc logic to add a new Generator

		try(Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(addQuery)){

			stmt.setInt(1,generator.getId());
			stmt.setString(2,generator.getName());
			stmt.setString(3,generator.getDescription());
			stmt.setInt(4,generator.getRate());
			stmt.setInt(5,generator.getBaseCost());
			stmt.setInt(6, generator.getUnlockAt());

//			if((Integer)Generator.getCreatedBy() != null) {
//				stmt.setInt(5, Generator.getCreatedBy());
//			} else {
//				stmt.setInt(5,0);
//			}

			stmt.executeUpdate();




		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public void remove(int id) {
		// TODO: implement jdbc logic to remove Generator by id
		try(Connection c = context.getConnection(); PreparedStatement stmt = c.prepareStatement(removeQuery)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
