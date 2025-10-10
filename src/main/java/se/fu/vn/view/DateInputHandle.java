package se.fu.vn.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateInputHandle {
    public static LocalDateTime inputDateHandle() {
        LocalDateTime date = null;

        while (date == null) {
            try {
                System.out.print("Nhập ngày (dd-MM-yyyy HH:mm:ss): ");
                String input =  new Scanner(System.in).nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                date = LocalDateTime.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Nhập sai định dạng, vui lòng nhập lại!");
            }
        }
        return date;
    }
}
