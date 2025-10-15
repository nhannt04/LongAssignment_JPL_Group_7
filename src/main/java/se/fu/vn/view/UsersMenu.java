package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.dao.UserDao;
import se.fu.vn.enums.Enums;
import se.fu.vn.model.Users;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UsersMenu {
    static void manageUsers(BookingManager bookingManager, Scanner sc) {
        List<Users> users = bookingManager.getUsers();
        while (true) {
            System.out.println("\n--- Quản lý người dùng ---");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            int c;
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(" Vui lòng nhập lựa chọn!");
                    continue;
                }
                c = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Vui lòng nhập số hợp lệ!");
                continue;
            }
            if (c == 0) break;
            switch (c) {
                case 1:
                    int id = users.size()+1;
                    System.out.print("Tên đăng nhập: ");
                    String user = sc.nextLine().trim();
                    if (user.isEmpty()) {
                        System.out.println(" Tên đăng nhập không được để trống!");
                        break;
                    }
                    System.out.print("Mật khẩu: ");
                    String pass = sc.nextLine().trim();
                    if (pass.isEmpty()) {
                        System.out.println(" Mật khẩu không được để trống!");
                        break;
                    }
                    System.out.print("Họ tên: ");
                    String name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println(" Họ tên không được để trống!");
                        break;
                    }
                    System.out.print("Vai trò (admin/customer): ");
                    String roleInput = sc.nextLine().trim();
                    Enums role = Enums.valueOf(roleInput.toUpperCase());
                    Users newUser  = new Users(id, user, pass, name, role);
                    users.add(newUser);
                    UserDao dao = new UserDao();
                    try {
                        int generatedId = dao.insert(newUser);
                        System.out.println(" ✅ Đã thêm vào database với ID: " + generatedId);
                    } catch (SQLException e) {
                        System.out.println(" ❌ Lỗi khi lưu xuống database: " + e.getMessage());
                    }
                    break;
                case 2:
                    users.forEach(System.out::println);
                    break;
                default:
                    System.out.println(" Sai lựa chọn!");
                    break;
            }
        }
    }
}
