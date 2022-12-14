package com.avital.coupons.logic;

import java.util.List;

import com.avital.coupons.beans.Company;
import com.avital.coupons.beans.User;
import com.avital.coupons.dao.UsersDao;
import com.avital.coupons.enums.ErrorType;
import com.avital.coupons.enums.UserType;
import com.avital.coupons.exceptions.ApplicationException;

public class UserController {

	private UsersDao usersDao;

	public UserController() {
		this.usersDao = new UsersDao();
	}

	public long createUser(User user) throws ApplicationException {

		// Checking if user name exists
		isUserNameExists(user);

		// Validating user details before creation
		validateCreateUser(user);

		// Execute create user
		return this.usersDao.createUser(user);
	}

	public void updateUser(User user) throws ApplicationException {

		// Validating user details before update
		validateCreateUser(user);

		// Execute update user
		this.usersDao.updateUser(user);
	}

	private void validateCreateUser(User user) throws ApplicationException {

		// Checking if user name is not null
		if (user.getUserName() == null) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, user.toString());
		}

		// Checking id user name is not empty
		if (user.getUserName().isEmpty()) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, user.toString());
		}

		// Checking user name length
		if (user.getUserName().length() < 2) {
			throw new ApplicationException(ErrorType.NAME_TOO_SHORT, user.toString());
		}

	}

	public void isUserNameExists(User user) throws ApplicationException {

		// Checking if user name already exists
		if (this.usersDao.isUserNameExists(user.getUserName())) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, user.toString());
		}
	}

	public List<User> getAllUsers() throws ApplicationException {

		// Execute get all users
		return this.usersDao.getAllUsers();
	}

	public User getUser(long id) throws ApplicationException {

		// Execute get user
		return this.usersDao.getUser(id);

	}

	public void DeleteUser(long id) throws ApplicationException {

		// deleting purchases by user id
		PurchasesController purchasesController = new PurchasesController();
		purchasesController.deletePurchasesByUserId(id);

		// deleting user
		this.usersDao.deleteUser(id);

	}

	public UserType login(String userName, String password) throws ApplicationException {

		// Execute login
		return usersDao.login(userName, password);

	}

}
