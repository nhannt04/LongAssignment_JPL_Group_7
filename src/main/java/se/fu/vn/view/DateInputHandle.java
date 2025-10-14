package se.fu.vn.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateInputHandle {
    public static LocalDateTime inputDateHandle() {
        LocalDateTime date = null;

        while (true) {
            try {
                System.out.print("Nhập ngày (dd-MM-yyyy HH:mm:ss): ");
                String input =  new Scanner(System.in).nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                date = LocalDateTime.parse(input, formatter);
                if (date.isBefore(LocalDateTime.now())) {
                    System.out.println("Vui lòng nập ngày tương lai");
                    continue;
                }
                return date;
            } catch (Exception e) {
                System.out.println("Nhập sai định dạng, vui lòng nhập lại!");
            }
        }
    }
}
