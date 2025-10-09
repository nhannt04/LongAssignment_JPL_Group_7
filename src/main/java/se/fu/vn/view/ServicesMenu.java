package se.fu.vn.view;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.model.Services;

import java.util.List;
import java.util.Scanner;

public class ServicesMenu {
    public static void manageServices(BookingManager bookingManager, Scanner sc) {
        List<Services> services = bookingManager.getServices();
        while (true) {
            System.out.println("\n--- Quản lý dịch vụ ---");
            System.out.println("1. Thêm dịch vụ");
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
                case 1: {
                    System.out.print("ID dịch vụ: ");
                    int id;
                    try {
                        id = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID phải là số!");
                        break;
                    }

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

                    services.add(new Services(id, name, desc, dur, price));
                    System.out.println(" Đã thêm!");
                    break;
                }
                case 2:
                    services.forEach(System.out::println);
                    break;
                default:
                    System.out.println(" Sai lựa chọn!");
                    break;
            }
        }
    }
}
