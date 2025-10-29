package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.model.Appointment;
import se.fu.vn.model.Users;
import se.fu.vn.serializable.DataManager;

import java.time.LocalDateTime;
import java.util.List;
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
            System.out.println("3. Cập Nhật Thời Gian Hẹn");
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

                case 3:
                    List<Appointment> userAppointments = bookingManager.getAppointmentsByUserId(user.getUserId());
                    for (Appointment appointment : userAppointments) {
                        System.out.println(appointment);
                    }
                    System.out.println("Nhập Id appointment muốn cập nhật: ");
                    int appointmentId = Integer.parseInt(sc.nextLine().trim());
                    Appointment appointment = bookingManager.getAppointmentById(appointmentId);
                    if (appointment == null) {
                        System.out.println("Không tìm thấy appointment với Id: " + appointmentId);
                        break;
                    }
                    System.out.println("Nhap thời gian hẹn mới (phút): ");
                    LocalDateTime time = DateInputHandle.inputDateHandle();
                    bookingManager.updateTimeAppointment(appointmentId, time);
                    System.out.println("Cập nhật thành công!");
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
