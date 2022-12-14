package com.avital.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.beans.User;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;
import com.avital.coupons.utils.JdbcUtils;

public class CompaniesDao {

	public boolean isCompanyNameExist(String companyName) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM companies WHERE company_name=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, companyName);

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
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "Company name already exists" + companyName);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public long createCompany(Company company) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "INSERT INTO companies (company_name, phone_number, address) VALUES(?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getPhoneNumber());
			preparedStatement.setString(3, company.getAddress());

			// Executing the update
			preparedStatement.executeUpdate();
			// Receiving the Id from resultset
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the company was created, returning the company ID
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}
			throw new ApplicationException(ErrorType.COULD_NOT_GENERATE_ID,
					"could not generate Id" + company.toString());

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, company.toString());

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public void deleteCompany(long id) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete from companies where id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, company has been not deleted. company id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Company getCompany(long id) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM companies WHERE id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// If there are still rows in the result table
			if (resultSet.next()) {
				return createCompanyFromResultSet(resultSet);
			}

		} catch (Exception e) {

			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get companies details. company id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return null;
	}

	private Company createCompanyFromResultSet(ResultSet resultSet) throws ApplicationException {

		// Creating company from resultSet
		Company companies = new Company();

		try {
			companies.setId(resultSet.getInt("id"));
			companies.setCompanyName(resultSet.getString("company_name"));
			companies.setPhoneNumber(resultSet.getString("phone_number"));
			companies.setAddress(resultSet.getString("address"));
			
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "failed, can not create company from resultset");
		}
		return companies;
	}

	public void updateCompany(Company company) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "update companies set company_name=?, phone_number=?, address=? where id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getPhoneNumber());
			preparedStatement.setString(3, company.getAddress());
			preparedStatement.setLong(4, company.getId());

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {

			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not update company details" + company.toString());

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<Company> getAllCompanies() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// creating arrayList "companies"
		List<Company> companies = new ArrayList<Company>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM companies";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				companies.add(createCompanyFromResultSet(resultSet));
			}

		} catch (Exception e) {

			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not get allCompanies details");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return companies;

	}
}
