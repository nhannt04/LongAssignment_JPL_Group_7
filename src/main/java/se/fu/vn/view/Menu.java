package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.serializable.DataManager;

import java.util.Scanner;

import static se.fu.vn.view.AppointmentMenu.manageAppointments;
import static se.fu.vn.view.ServicesMenu.manageServices;
import static se.fu.vn.view.UsersMenu.manageUsers;


public class Menu {

    public static void run(DataManager dataManager,BookingManager bookingManager,Scanner sc) {
        while (true) {
            System.out.println("\n===== QUẢN LÝ BOOKING =====");
            System.out.println("1. Quản lý người dùng");
            System.out.println("2. Quản lý dịch vụ");
            System.out.println("3. Quản lý lịch hẹn");
            System.out.println("4. Lưu dữ liệu");
            System.out.println("5. Xem toàn bộ dữ liệu");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int choice;
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(" Vui lòng nhập lựa chọn!");
                    continue;
                }
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Vui lòng nhập số hợp lệ!");
                continue;
            } catch (java.util.NoSuchElementException e) {
                System.out.println(" Lỗi nhập liệu. Vui lòng nhập số.");
                continue;
            }
            switch (choice) {
                case 1:
                    manageUsers(bookingManager, sc);
                    break;
                case 2:
                    manageServices(bookingManager, sc);
                    break;
                case 3:
                    manageAppointments(bookingManager, sc);
                    break;
                case 4:
                    if (bookingManager != null) {
                        dataManager.saveData(bookingManager);
                    } else {
                        System.out.println(" Lỗi: BookingManager không được khởi tạo");
                    }
                    break;
                case 5:
                    display(bookingManager);
                    break;
                case 0:
                    System.out.println(" Tạm biệt!");
                    return;
                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    private static void display(BookingManager bookingManager) {
        System.out.println("\n Danh sách người dùng:");
        bookingManager.getUsers().forEach(System.out::println);
        System.out.println("\n Danh sách dịch vụ:");
        bookingManager.getServices().forEach(System.out::println);
        System.out.println("\n Danh sách lịch hẹn:");
        bookingManager.getAppointments().forEach(System.out::println);

    }
}
