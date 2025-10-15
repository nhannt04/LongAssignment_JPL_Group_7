package se.fu.vn.controller;

import se.fu.vn.dao.UserDao;
import se.fu.vn.model.Users;

public class LoginUsers {
    private static final UserDao userDao = new UserDao();
    
    public static Users login(String username, String password, BookingManager bookingManager) {
        try {
            Users user = userDao.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return null;
    }
}
