package com.avital.coupons.tester;

import java.util.List;

import com.avital.coupons.beans.User;
import com.avital.coupons.dao.CouponsDao;
import com.avital.coupons.dao.UsersDao;
import com.avital.coupons.enums.UserType;

public class Tester {

	public static void main(String[] args) throws Exception {
		testCreateUser();
		//testDeleteUser();
		//testGetUser();
		//testerUpdateUser();
		//testerGetAllUsers();
		//isUserNameExists();
		//testLogin();
	}

	private static void isUserNameExists() {
		UsersDao usersDao = new UsersDao();
		try {
			boolean users = usersDao.isUserNameExists("Haim");
			
			System.out.println(users);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void testLogin() {
		UsersDao usersDao = new UsersDao();
		try {
			
			UserType userType = usersDao.login("Avital Mager", "1234");
			
			System.out.println(userType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

private static void testGetUser() throws Exception {
	
	UsersDao usersDao = new UsersDao();
	
	User user = usersDao.getUser(1);
	
	System.out.println(user);
	
	
	
}

public static void testCreateUser() throws Exception {
	UsersDao usersDao = new UsersDao();
	User user = new User("sawr", "1234", null, UserType.CUSTOMER, "Tal", "Sharon");
	
	System.out.println(usersDao.createUser(user));
	System.out.println();
}

public static void testDeleteUser() throws Exception {
	UsersDao usersDao = new UsersDao();
	usersDao.deleteUser(11);

}

public static void testerUpdateUser() throws Exception {
	
	
	UsersDao usersDao = new UsersDao();
	
	User user= usersDao.getUser(17);
	
	user.setFirstName("Shoshi");
	
	usersDao.updateUser(user);
	
	System.out.println(user);
}

public static void testerGetAllUsers() throws Exception {
	
	UsersDao usersDao = new UsersDao();
	
	List<User> user= usersDao.getAllUsers();
	
	System.out.println(user);
	
}

}
