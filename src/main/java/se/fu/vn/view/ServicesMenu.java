package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.dao.ServiceDao;
import se.fu.vn.model.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ServicesMenu {


    public static void manageServices(BookingManager bookingManager, Scanner sc) {
        List<Services> services = bookingManager.getServices();
        while (true) {
            System.out.println("\n--- Quản lý dịch vụ ---");
            System.out.println("1. Thêm dịch vụ");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Sửa dịch vụ");
            System.out.println("4. Xóa dịch vụ");
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
                case 1: {
                    int id = services.size()+1;

                    System.out.print("Tên: ");
                    String name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println(" Tên không được để trống!");
                        break;
                    }

                    System.out.print("Mô tả: ");
                    String desc = sc.nextLine().trim();

                    System.out.print("Thời gian (phút): ");
                    int dur;
                    try {
                        dur = Integer.parseInt(sc.nextLine().trim());
                        if (dur <= 0) {
                            System.out.println(" Thời gian phải lớn hơn 0!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Thời gian phải là số!");
                        break;
                    }

                    System.out.print("Giá: ");
                    double price;
                    try {
                        price = Double.parseDouble(sc.nextLine().trim());
                        if (price < 0) {
                            System.out.println(" Giá không được âm!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Giá phải là số!");
                        break;
                    }
                    Services newService = new Services(id, name, desc, dur, price);
                    services.add(newService);
                    ServiceDao dao = new ServiceDao();
                    try {
                        int generatedId = dao.insert(newService);
                        System.out.println(" ✅ Đã thêm vào database với ID: " + generatedId);
                    } catch (SQLException e) {
                        System.out.println(" ❌ Lỗi khi lưu xuống database: " + e.getMessage());
                    }

                    break;

                }
                case 2:
                    services.forEach(System.out::println);
                    break;
                case 3:
                    updateService(bookingManager, sc);
                    break;
                case 4:
                    deleteService(bookingManager, sc);
                    break;
                default:
                    System.out.println("Sai lựa chọn!");
                    break;
            }
        }
    }
    
    private static void updateService(BookingManager bookingManager, Scanner sc) {
        List<Services> services = bookingManager.getServices();
        
        System.out.println("\n--- Danh sách dịch vụ ---");
        for (int i = 0; i < services.size(); i++) {
            Services service = services.get(i);
            System.out.println((i + 1) + ". ID: " + service.getServiceId() + 
                             " | Tên: " + service.getName() + 
                             " | Giá: " + service.getPrice() + 
                             " | Thời gian: " + service.getDuration() + " phút");
        }
        
        System.out.print("\nChọn dịch vụ cần sửa (số thứ tự): ");
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice < 1 || choice > services.size()) {
                System.out.println("❌ Lựa chọn không hợp lệ!");
                return;
            }
            
            Services selectedService = services.get(choice - 1);
            System.out.println("\nDịch vụ đã chọn: " + selectedService);
            
            System.out.print("Tên mới (Enter để giữ nguyên): ");
            String newName = sc.nextLine().trim();
            if (!newName.isEmpty()) {
                selectedService.setName(newName);
            }
            
            System.out.print("Mô tả mới (Enter để giữ nguyên): ");
            String newDesc = sc.nextLine().trim();
            if (!newDesc.isEmpty()) {
                selectedService.setDescription(newDesc);
            }
            
            System.out.print("Thời gian mới (phút, Enter để giữ nguyên): ");
            String durInput = sc.nextLine().trim();
            if (!durInput.isEmpty()) {
                try {
                    int newDur = Integer.parseInt(durInput);
                    if (newDur > 0) {
                        selectedService.setDuration(newDur);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Thời gian không hợp lệ!");
                }
            }
            
            System.out.print("Giá mới (Enter để giữ nguyên): ");
            String priceInput = sc.nextLine().trim();
            if (!priceInput.isEmpty()) {
                try {
                    double newPrice = Double.parseDouble(priceInput);
                    if (newPrice >= 0) {
                        selectedService.setPrice(newPrice);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Giá không hợp lệ!");
                }
            }
            
            // Update in database
            try {
                if (bookingManager.getServiceDao().update(selectedService)) {
                    System.out.println("Cập nhật dịch vụ thành công!");
                } else {
                    System.out.println("Không thể cập nhật dịch vụ!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật: " + e.getMessage());
            }
            
        } catch (NumberFormatException e) {
            System.out.println("vui lòng nhập số hợp lệ!");
        }
    }
    
    private static void deleteService(BookingManager bookingManager, Scanner sc) {
        List<Services> services = bookingManager.getServices();
        
        System.out.println("\n--- Danh sách dịch vụ ---");
        for (int i = 0; i < services.size(); i++) {
            Services service = services.get(i);
            System.out.println((i + 1) + ". ID: " + service.getServiceId() + 
                             " | Tên: " + service.getName() + 
                             " | Giá: " + service.getPrice());
        }
        
        System.out.print("\nChọn dịch vụ cần xóa (số thứ tự): ");
        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice < 1 || choice > services.size()) {
                System.out.println("Lựa chọn không hợp lệ!");
                return;
            }
            
            Services selectedService = services.get(choice - 1);
            System.out.println("\nDịch vụ sẽ bị xóa: " + selectedService);
            System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
            
            String confirm = sc.nextLine().trim().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                try {
                    if (bookingManager.getServiceDao().delete(selectedService.getServiceId())) {
                        services.remove(selectedService);
                        System.out.println("Xóa dịch vụ thành công!");
                    } else {
                        System.out.println("Không thể xóa dịch vụ!");
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi khi xóa: " + e.getMessage());
                }
            } else {
                System.out.println("Hủy bỏ thao tác xóa.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
        }
    }
}
