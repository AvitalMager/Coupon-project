package com.avital.coupons.tester;

import java.util.List;

import com.avital.coupons.beans.User;
import com.avital.coupons.dao.UsersDao;
import com.avital.coupons.enums.UserType;
import com.avital.coupons.logic.UserController;

public class UsersControllerTester {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//testerGetAllUsers();
		//testerDeleteUser();
		//testControllerCreateUser();
		//testerUpdateUserController();
		//testerGetUser();
		testerLogin();
	}
	
	public static void testControllerCreateUser() throws Exception {
		
		UserController userController = new UserController();
		
		User user = new User("David", "7890", null, UserType.CUSTOMER, "David", "Shoam");
		
		userController.createUser(user);
		
		System.out.println(user);
	}
	
	public static void testerUpdateUserController() throws Exception {
		
		
		UserController userController = new UserController();
		
		User user= userController.getUser(18);
		
		user.setLastName("Bar");
		
		userController.updateUser(user);
		
		System.out.println(user);
	}
	
	public static void testerGetUser() throws Exception {
		
		UserController userController = new UserController();
		
		User users = userController.getUser(17);
		System.out.println(users);
	}
	
	public static void testerGetAllUsers() throws Exception {
		
		UserController userController = new UserController();
		
		
		List<User> user= userController.getAllUsers();
		
		System.out.println(user);
		
	}
	
	public static void testerDeleteUser() throws Exception {
		
		UserController userController = new UserController();
		
		userController.DeleteUser(12);
	}
	
	public static void testerLogin() throws Exception {
		
		UserController userController = new UserController();
		
		UserType userType = userController.login("avital", "123");
		System.out.println(userType);
	}
	

}
