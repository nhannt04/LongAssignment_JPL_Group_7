package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.model.Users;
import se.fu.vn.serializable.DataManager;

import java.util.Scanner;

import static se.fu.vn.view.AppointmentMenu.bookingAppointments;
import static se.fu.vn.view.AppointmentMenu.manageAppointments;
import static se.fu.vn.view.ServicesMenu.manageServices;

public class MenuCustomer {
    public static void run(DataManager dataManager, BookingManager bookingManager, Scanner sc, Users user) {
        while (true) {
            System.out.println("\n===== Đặt lịch hẹn =====");
            System.out.println("1. Đặṭ lịch hẹn");
            System.out.println("2. Lưu dữ liệu");
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
                    bookingAppointments(bookingManager, sc, user);
                    break;
                case 2:
                    if (bookingManager != null) {
                        dataManager.saveData(bookingManager);
                    } else {
                        System.out.println(" Lỗi: BookingAppointments không được khởi tạo");
                    }
                    break;
                case 0:
                    Login.run();
                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }
}
