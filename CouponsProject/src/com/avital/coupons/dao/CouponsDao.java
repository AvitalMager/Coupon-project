package com.avital.coupons.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.beans.Coupon;
import com.avital.coupons.beans.User;
import com.avital.coupons.enums.CouponCategory;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.enums.UserType;
import com.avital.coupons.exceptions.ApplicationException;
import com.avital.coupons.utils.JdbcUtils;

public class CouponsDao {

	public boolean isCouponNameExist(String name) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM coupons WHERE coupon_name=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, name);

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
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "Coupon name already exists" + name);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public long createCoupon(Coupon coupons) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "INSERT INTO coupons (coupon_name, price, description, start_date, end_date, category, amount, company_id, image) VALUES(?,?,?,?,?,?,?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, coupons.getName());
			preparedStatement.setFloat(2, coupons.getPrice());
			preparedStatement.setString(3, coupons.getDescription());
			preparedStatement.setDate(4, coupons.getStartDate());
			preparedStatement.setDate(5, coupons.getEndDate());
			preparedStatement.setString(6, coupons.getCouponCategory().name());
			preparedStatement.setInt(7, coupons.getAmount());
			preparedStatement.setLong(8, coupons.getCompanyId());
			preparedStatement.setString(9, coupons.getImage());

			// Executing the update
			preparedStatement.executeUpdate();
			// Receiving the Id from resultset
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the company was created, returning the coupon ID
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}

			throw new ApplicationException(ErrorType.COULD_NOT_GENERATE_ID,
					"could not generate Id" + coupons.toString());

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, coupons.toString());
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public void updateCoupon(Coupon coupons) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "update coupons set coupon_name =?, price=?, description=?, start_date=?, end_date=?, category=?, amount=?, image=?  where id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, coupons.getName());
			preparedStatement.setFloat(2, coupons.getPrice());
			preparedStatement.setString(3, coupons.getDescription());
			preparedStatement.setDate(4, coupons.getStartDate());
			preparedStatement.setDate(5, coupons.getEndDate());
			preparedStatement.setString(6, coupons.getCouponCategory().name());
			preparedStatement.setInt(7, coupons.getAmount());
			preparedStatement.setString(8, coupons.getImage());
			preparedStatement.setLong(9, coupons.getId());

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not update coupon details" + coupons.toString());
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public Coupon deleteCoupon(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete from coupons where id=?";

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
					"failed, company has been not deleted.Id: " + id);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return null;

	}

	public Coupon getCoupon(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM coupons WHERE id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// If there are still rows in the result table
			if (resultSet.next()) {
				return createCouponFromResultSet(resultSet);
			}

			return null;

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get coupon details. coupon id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	private Coupon createCouponFromResultSet(ResultSet resultSet) throws ApplicationException {

		// Creating coupon 
		Coupon coupons = new Coupon();

		try {
			coupons.setId(resultSet.getLong("id"));
			coupons.setName(resultSet.getString("coupon_name"));
			coupons.setPrice(resultSet.getFloat("price"));
			coupons.setDescription(resultSet.getString("description"));
			coupons.setStartDate(resultSet.getDate("start_date"));
			coupons.setEndDate(resultSet.getDate("end_date"));
			coupons.setAmount(resultSet.getInt("amount"));
			coupons.setImage(resultSet.getString("image"));
			coupons.setCompanyId(resultSet.getLong("company_id"));

			CouponCategory category = CouponCategory.valueOf(resultSet.getString("category"));
			coupons.setCouponCategory(category);

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not create coupon from resultset");

		}
		return coupons;
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// creating arrayList "coupons"
		List<Coupon> coupons = new ArrayList<Coupon>();
		;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM coupons";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				coupons.add(createCouponFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not get allCoupons details");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

		return coupons;

	}

	public List<Coupon> getAllCouponsByCompanyId(Long companyId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// creating arrayList "coupons"
		List<Coupon> coupons;
		coupons = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM coupons WHERE company_id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);

			// Executing the query
			preparedStatement.executeQuery();

			
			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				coupons.add(createCouponFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get AllCouponsByCompanyId details. Id: " + companyId);
		}
		return coupons;

	}

	public List<Coupon> getAllCouponsByType(CouponCategory couponCategory) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// creating arrayList "coupons"
		List<Coupon> coupons;
		coupons = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM coupons WHERE category=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, couponCategory.name());

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				coupons.add(createCouponFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get AllCouponsByType details" + couponCategory);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return coupons;
	}

	public void deleteOldCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// Getting current time and date
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete from coupons where end_date=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setDate(1, date);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not delete old coupons");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);

		}

	}

	public void deleteCouponsByCompanyId(long companyId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete  from  coupons where company_id =?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyId);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not delete CouponsByCompanyId" + companyId);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);

		}

	}
	
	public Coupon getAmountOfAvailableCoupons(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT amount from coupons WHERE id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// If there are still rows in the result table
			if (resultSet.next()) {
				return createCouponFromResultSet(resultSet);
			}

			return null;

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get coupon details. coupon id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	

}
