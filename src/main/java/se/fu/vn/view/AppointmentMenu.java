package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppointmentMenu {
    public static void manageAppointments(BookingManager bookingManager, Scanner sc) {
        List<Appointment> apps = bookingManager.getAppointments();
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
                    System.out.print("ID lịch hẹn: ");
                    int id;
                    try {
                        id = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID lịch hẹn phải là số!");
                        break;
                    }

                    System.out.print("ID khách hàng: ");
                    int cusId;
                    try {
                        cusId = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID khách hàng phải là số!");
                        break;
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

                    System.out.print("ID dịch vụ: ");
                    int serviceId;
                    try {
                        serviceId = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID dịch vụ phải là số!");
                        break;
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
