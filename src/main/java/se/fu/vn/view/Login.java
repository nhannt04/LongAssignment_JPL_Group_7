package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.controller.LoginUsers;
import se.fu.vn.enums.Enums;
import se.fu.vn.model.Users;
import se.fu.vn.serializable.DataManager;

import java.util.Scanner;

public class Login {
    private static final Scanner sc = new Scanner(System.in);
    private static DataManager dataManager = new DataManager();
    private static BookingManager bookingManager;

    public static void run() {
        // tải dữ liệu
        bookingManager = dataManager.loadData();
        if (bookingManager == null) {
            System.out.println("⚠ Không thể tải dữ liệu, tạo mới BookingManager...");
            bookingManager = new BookingManager();
        }

        System.out.println("===== ĐĂNG NHẬP HỆ THỐNG =====");

        Users user = null;
        while (user == null) {
            System.out.print("Tên đăng nhập: ");
            String username = sc.nextLine();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            user = LoginUsers.login(username,password,bookingManager);

            if (user == null) {
                System.out.println("❌ Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại!\n");
            }
        }

        System.out.println("✅ Đăng nhập thành công! Xin chào, " + user.getFullName());
        if(user.getRole()== Enums.ADMIN){
            Menu.run(dataManager,bookingManager,sc);
        }else{
            MenuCustomer.run(dataManager,bookingManager,sc, user);
        }
    }
}
