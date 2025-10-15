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
            System.out.println("3. Cập nhật trạng thái lịch hẹn");
            System.out.println("4. Xem lịch hẹn theo trạng thái");
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
                    Appointment newAppointment = new Appointment(id, cusId, name, email, serviceId, time, "PENDING", LocalDateTime.now());
                    if (bookingManager.createAppointment(newAppointment)) {
                        System.out.println("✅ Lịch hẹn đã được tạo thành công!");
                    } else {
                        System.out.println("❌ Không thể tạo lịch hẹn. Vui lòng thử lại!");
                    }

                    break;
                case 2:
                    apps.forEach(System.out::println);
                    break;
                case 3:
                    updateAppointmentStatus(bookingManager, sc);
                    break;
                case 4:
                    viewAppointmentsByStatus(bookingManager, sc);
                    break;
                default:
                    System.out.println(" Sai lựa chọn!");
                    break;
            }
        }
    }


    public static void bookingAppointments(BookingManager bookingManager, Scanner sc, Users users) {
        List<Appointment> apps = bookingManager.getAppointments();
        List<Services> services = bookingManager.getServices();
        while (true) {
            System.out.println("\n--- Quản lí lịch hẹn ---");
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

                    int cusId = users.getUserId();


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
                    Appointment newAppointment = new Appointment(id, cusId, name, email, serviceId, time, "PENDING", LocalDateTime.now());
                    if (bookingManager.createAppointment(newAppointment)) {
                        System.out.println("✅ Lịch hẹn đã được tạo thành công!");
                    } else {
                        System.out.println("❌ Không thể tạo lịch hẹn. Vui lòng thử lại!");
                    }

                    break;
                case 2:
                    bookingManager.getAppointments(users,apps);
                    break;
                default:
                    System.out.println(" Sai lựa chọn!");
                    break;
            }
        }
    }
    
    private static void updateAppointmentStatus(BookingManager bookingManager, Scanner sc) {
        List<Appointment> appointments = bookingManager.getAppointments();
        
        System.out.println("\n--- Danh sách lịch hẹn ---");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment app = appointments.get(i);
            System.out.println((i + 1) + ". ID: " + app.getAppointmentId() + 
                             " | Khách: " + app.getCustomerName() + 
                             " | Dịch vụ: " + app.getServiceId() + 
                             " | Thời gian: " + app.getAppointmentDate() + 
                             " | Trạng thái: " + app.getStatus());
        }
        
        System.out.print("\nChọn lịch hẹn cần cập nhật (số thứ tự): ");
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice < 1 || choice > appointments.size()) {
                System.out.println("Lựa chọn không hợp lệ!");
                return;
            }
            
            Appointment selectedAppointment = appointments.get(choice - 1);
            System.out.println("\nLịch hẹn đã chọn: " + selectedAppointment);
            
            System.out.println("\nTrạng thái có thể chọn:");
            System.out.println("1. PENDING");
            System.out.println("2. CONFIRMED");
            System.out.println("3. CANCELLED");
            System.out.println("4. COMPLETED");
            System.out.print("Chọn trạng thái mới (1-4): ");
            
            int statusChoice = Integer.parseInt(sc.nextLine().trim());
            String newStatus;
            switch (statusChoice) {
                case 1: newStatus = "PENDING"; break;
                case 2: newStatus = "CONFIRMED"; break;
                case 3: newStatus = "CANCELLED"; break;
                case 4: newStatus = "COMPLETED"; break;
                default:
                    System.out.println("Lựa chọn trạng thái không hợp lệ!");
                    return;
            }
            
            if (bookingManager.updateAppointmentStatus(selectedAppointment.getAppointmentId(), newStatus)) {
                System.out.println("Cập nhật trạng thái thành công!");
            } else {
                System.out.println("Không thể cập nhật trạng thái!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
        }
    }
    
    private static void viewAppointmentsByStatus(BookingManager bookingManager, Scanner sc) {
        System.out.println("\n--- Xem lịch hẹn theo trạng thái ---");
        System.out.println("1. PENDING");
        System.out.println("2. CONFIRMED");
        System.out.println("3. CANCELLED");
        System.out.println("4. COMPLETED");
        System.out.println("5. Tất cả");
        System.out.print("Chọn trạng thái (1-5): ");
        
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            String statusFilter;
            switch (choice) {
                case 1: statusFilter = "PENDING"; break;
                case 2: statusFilter = "CONFIRMED"; break;
                case 3: statusFilter = "CANCELLED"; break;
                case 4: statusFilter = "COMPLETED"; break;
                case 5: statusFilter = null; break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    return;
            }
            
            List<Appointment> appointments = bookingManager.getAppointments();
            System.out.println("\n--- Kết quả ---");
            
            boolean found = false;
            for (Appointment app : appointments) {
                if (statusFilter == null || app.getStatus().equals(statusFilter)) {
                    System.out.println("ID: " + app.getAppointmentId() + 
                                     " | Khách: " + app.getCustomerName() + 
                                     " | Email: " + app.getCustomerEmail() + 
                                     " | Dịch vụ: " + app.getServiceId() + 
                                     " | Thời gian: " + app.getAppointmentDate() + 
                                     " | Trạng thái: " + app.getStatus());
                    found = true;
                }
            }
            
            if (!found) {
                System.out.println("Không tìm thấy lịch hẹn nào với trạng thái này.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
        }
    }
}
