package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.model.Appointment;
import se.fu.vn.model.Services;
import se.fu.vn.model.Users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppointmentMenu {
    public static void manageAppointments(BookingManager bookingManager, Scanner sc) {
        List<Appointment> apps = bookingManager.getAppointments();
        List<Services> services = bookingManager.getServices();
        List<Users> users = bookingManager.getUsers();
        while (true) {
            System.out.println("\n--- Quản lý lịch hẹn ---");
            System.out.println("1. Tạo lịch hẹn");
            System.out.println("2. Hiển thị lịch hẹn");
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
                    int id = apps.size() + 1;
                    System.out.println("\n Danh sách người dùng:");
                    bookingManager.getUsers().forEach(System.out::println);

                    int cusId;
                    while (true) {
                        System.out.print("ID khách hàng: ");
                        try {
                            cusId = Integer.parseInt(sc.nextLine().trim());
                            if (!bookingManager.findUser(cusId, users)) {
                                System.out.println("❌ Không tìm thấy khách hàng có ID này. Nhập lại!");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("❌ ID khách hàng phải là số! Nhập lại!");
                        }
                    }

                    System.out.print("Tên KH: ");
                    String name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println(" Tên khách hàng không được để trống!");
                        break;
                    }

                    System.out.print("Email KH: ");
                    String email = sc.nextLine().trim();
                    if (email.isEmpty()) {
                        System.out.println(" Email không được để trống!");
                        break;
                    }

                    System.out.println("\n Danh sách dịch vụ:");
                    bookingManager.getServices().forEach(System.out::println);

                    int serviceId;
                    while (true) {
                        System.out.print("ID dịch vụ: ");
                        try {
                            serviceId = Integer.parseInt(sc.nextLine().trim());
                            if (!bookingManager.findService(serviceId, services)) {
                                System.out.println("❌ Không tìm thấy dịch vụ có ID này. Nhập lại!");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("❌ ID dịch vụ phải là số! Nhập lại!");
                        }
                    }


                    LocalDateTime time = DateInputHandle.inputDateHandle();
                    apps.add(new Appointment(id, cusId, name, email, serviceId, time, "PENDING", LocalDateTime.now()));
                    System.out.println(" Lịch hẹn đã được tạo!");

                    break;
                case 2:
                    apps.forEach(System.out::println);
                    break;
                default:
                    System.out.println(" Sai lựa chọn!");
                    break;
            }
        }
    }
}
