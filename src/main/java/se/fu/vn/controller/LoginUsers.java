package se.fu.vn.controller;

import se.fu.vn.model.Users;

public class LoginUsers {
    public static Users login(String username, String password, BookingManager bookingManager) {
        for(Users u : bookingManager.getUsers()) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
