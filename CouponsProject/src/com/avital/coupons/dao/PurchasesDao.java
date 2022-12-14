package com.avital.coupons.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.avital.coupons.beans.Coupon;
import com.avital.coupons.beans.Purchase;
import com.avital.coupons.enums.CouponCategory;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.exceptions.ApplicationException;
import com.avital.coupons.utils.JdbcUtils;

public class PurchasesDao {

	public long createPurchase(Purchase purchases) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "INSERT INTO purchases (user_id, coupon_id, amount, time_of_purchase) VALUES(?,?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, purchases.getUserId());
			preparedStatement.setLong(2, purchases.getCouponId());
			preparedStatement.setTimestamp(4, purchases.getTimeOfPurchase());
			preparedStatement.setLong(3, purchases.getAmount());

			// Executing the update
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the purchase was created, returning the purchases ID
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}

			throw new ApplicationException(ErrorType.COULD_NOT_GENERATE_ID,
					"could not generate Id" + purchases.toString());

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, purchases.toString());

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void updatePurchase(Purchase purchases) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "update purchases set user_id=?, coupon_id=?, amount=?, time_of_purchase=?  where id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, purchases.getUserId());
			preparedStatement.setLong(2, purchases.getCouponId());
			preparedStatement.setTimestamp(4, purchases.getTimeOfPurchase());
			preparedStatement.setLong(3, purchases.getAmount());
			preparedStatement.setLong(5, purchases.getId());

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not update purchase details" + purchases.toString());

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Purchase deletePurchase(long id) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete from purchases where id=?";

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
					"failed, purchase has been not deleted.Id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return null;
	}

	public Purchase getPurchase(long id) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM purchases WHERE id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, id);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// If there are still rows in the result table
			if (resultSet.next()) {
				return createPurchasesFromResultSet(resultSet);
			}
			return null;

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get purchase details. id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	private Purchase createPurchasesFromResultSet(ResultSet resultSet) throws ApplicationException {

		// Creating purchase
		Purchase purchase = new Purchase();
		try {
			purchase.setId(resultSet.getLong("id"));
			purchase.setUserId(resultSet.getLong("user_id"));
			purchase.setCouponId(resultSet.getLong("coupon_id"));
			purchase.setAmount(resultSet.getInt("amount"));
			purchase.setTimeOfPurchase(resultSet.getTimestamp("time_of_purchase"));

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not create purchase from resultset");
		}

		return purchase;
	}

	public List<Purchase> getAllPurchases() throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// Creating arrayList "purchases"
		List<Purchase> purchases = new ArrayList<Purchase>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM purchases";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				purchases.add(createPurchasesFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not get AllPurchases details");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return purchases;

	}

	public List<Purchase> getAllPurchasesByUserId(long userId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// Creating arrayList "Purchases"
		List<Purchase> purchases;
		purchases = new ArrayList<Purchase>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "SELECT * FROM purchases WHERE user_id=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userId);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				purchases.add(createPurchasesFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get AllPurchasesByUserId details. Id: " + userId);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

		return purchases;

	}

	public List<Purchase> getAllPurchasesByCompanyId(long companyId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// creating arrayList "purchases"
		List<Purchase> purchases;
		purchases = new ArrayList<Purchase>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "select* from  purchases where coupon_id in (select id from coupons where company_id =?);";
			// Combining between the syntax and our connection

			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				purchases.add(createPurchasesFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get AllPurchasesByCompanyId details. Id: " + companyId);
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

		return purchases;

	}

	public List<Purchase> getPurchasedCouponsByMaxPrice(long userId, float maxPrice) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// Creating arrayList "purchases"
		List<Purchase> purchases;
		purchases = new ArrayList<Purchase>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "select* from  purchases join coupons ON purchases.coupon_id= coupons.id where (user_id= ?) & (coupons.price < ?) order by coupons.price desc;";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userId);
			preparedStatement.setFloat(2, maxPrice);

			// Executing the query
			preparedStatement.executeQuery();

			ResultSet resultSet = preparedStatement.getResultSet();

			// if there are still rows in the result table
			while (resultSet.next()) {
				purchases.add(createPurchasesFromResultSet(resultSet));
			}
		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not get PurchasedCouponsByMaxPrice details");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

		return purchases;

	}

	public void deletePurchasesByCompanyId(long id) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete  from  purchases where coupon_id in (select id from coupons where company_id =?);";

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
					"failed, can not delete PurchasesByCompanyId. Id: " + id);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public void deletePurchasesByUserId(long userId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete  from  purchases where user_id=?;";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, userId);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not delete PurchasesByUserId. Id: " + userId);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public void deleteOldCouponsPurchases() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// Getting current time and date
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete  from  purchases where coupon_id in (select id from coupons where end_date =?);";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setDate(1, date);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, "failed, can not delete OldCouponsPurchases");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);

		}

	}

	public void deletePurchasesByCouponId(long couponId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete  from  purchases where coupon_id=?;";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, couponId);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// If there was an exception in "try" block above, it is caught here and
			// notifies the level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed, can not delete PurchasesByCouponId" + couponId);

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

}
