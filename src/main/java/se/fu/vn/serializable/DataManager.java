package se.fu.vn.serializable;

import se.fu.vn.controller.BookingManager;
import se.fu.vn.model.Appointment;
import se.fu.vn.model.Services;
import se.fu.vn.model.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager implements Serializable {
    private static final String FILE_NAME = "booking.dat";

    private BookingManager bookingManager;

    public DataManager() {
    }

    public DataManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    public BookingManager getBookingManager() {
        return bookingManager;
    }

    public void setBookingManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    private void createFileIfNotExist() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(new ArrayList<Users>());
                    oos.writeObject(new ArrayList<Services>());
                    oos.writeObject(new ArrayList<Appointment>());
                }
            } catch (IOException e) {
                System.out.println("Không thể tạo file: " + e.getMessage());
            }
        }
    }

    public void saveData(BookingManager bookingManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookingManager.getUsers());
            oos.writeObject(bookingManager.getServices());
            oos.writeObject(bookingManager.getAppointments());
            System.out.println(" dữ liệu đã được lưu vào " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    public BookingManager loadData() {
        createFileIfNotExist();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Users> users = (List<Users>) ois.readObject();
            List<Services> services = (List<Services>) ois.readObject();
            List<Appointment> appointments = (List<Appointment>) ois.readObject();

            BookingManager manager = new BookingManager();
            manager.setUsers(users);
            manager.setServices(services);
            manager.setAppointments(appointments);
            System.out.println(" dữ liệu đã được tải từ file.");
            return manager;
        } catch (Exception e) {
            System.out.println(" lỗi khi đọc dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return new BookingManager();
        }
    }
}
