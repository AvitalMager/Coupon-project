package com.avital.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.avital.coupons.beans.User;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.enums.UserType;
import com.avital.coupons.exceptions.ApplicationException;
import com.avital.coupons.utils.JdbcUtils;

public class UsersDao {

	public boolean isUserNameExists(String userName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM users WHERE username=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userName);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// If there are still rows in the result table
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "Username already exists" + userName);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public long createUser(User user) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "INSERT INTO users (username, password, user_type, company_id, first_name, last_name) VALUES(?,?,?,?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getUserType().name());
			preparedStatement.setString(5, user.getFirstName());
			preparedStatement.setString(6, user.getLastName());

			if (user.getCompanyId() == null) {
				preparedStatement.setObject(4, null);
			} else {
				preparedStatement.setLong(4, user.getCompanyId());
			}

			// Executing the update
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the user was created, returning the user ID
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}

			throw new ApplicationException(ErrorType.COULD_NOT_GENERATE_ID, "could not generate Id" + user.toString());

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, user.toString());

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void updateUser(User user) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "update users set username=?, password=?, user_type=?, company_id=?, first_name=?, last_name=? where id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getUserType().name());
			preparedStatement.setString(5, user.getFirstName());
			preparedStatement.setString(6, user.getLastName());
			preparedStatement.setLong(7, user.getId());

			if (user.getCompanyId() == null) {
				preparedStatement.setObject(4, null);
			} else {
				preparedStatement.setLong(4, user.getCompanyId());
			}

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not update user details" + user.toString());

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteUser(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete from users where id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not delete user. id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public User getUser(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM users WHERE id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// If there are still rows in the result table
			if (resultSet.next()) {
				return createUserFromResultSet(resultSet);
			}

			return null;

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get user details. user id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	private User createUserFromResultSet(ResultSet resultSet) throws ApplicationException {

		// Creating user
		User user = new User();

		try {
			user.setId(resultSet.getLong("id"));
			user.setUserName(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setFirstName(resultSet.getString("first_name"));
			user.setLastName(resultSet.getString("last_name"));
			user.setCompanyId((Long) resultSet.getObject("company_id"));

			UserType type = UserType.valueOf(resultSet.getString("user_type"));
			user.setUserType(type);

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "failed, can not create user from resultset");
		}
		return user;
	}

	public List<User> getAllUsers() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// Creating arrayList "Users"
		List<User> user = new ArrayList<User>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM users";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				user.add(createUserFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not get allUssers details");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

		return user;

	}

	public UserType login(String userName, String password) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from users where username=? and password=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);

			// Executing the query
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.getResultSet();

			if (resultSet.next()) {

				UserType type = UserType.valueOf(resultSet.getString("user_type"));
				System.out.println("User authenticated successfully");
				return type;
			} else {
				System.out.println("Invalid username or password!");
			}

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "login failed ");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return null;

	}
}
