package se.fu.vn.controller;

import se.fu.vn.connection.DBConnect;
import se.fu.vn.dao.AppointmentDao;
import se.fu.vn.dao.ServiceDao;
import se.fu.vn.dao.UserDao;
import se.fu.vn.model.Appointment;
import se.fu.vn.model.Services;
import se.fu.vn.model.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Users> users = new ArrayList<>();
    private List<Services> services = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    
    private final UserDao userDao = new UserDao();
    private final ServiceDao serviceDao = new ServiceDao();
    private final AppointmentDao appointmentDao = new AppointmentDao();

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void addUsers(Users user) {
        users.add(user);
    }

    public void deleteUsers(Users user) {
        users.remove(user);
    }

    public Users getUser(int id) {
        for (Users u : users) {
            if (u.getUserId()==id) {
                return u;
            }
        }
        return null;
    }

    public void updateUsers(Users user) {
       Users u = getUser(user.getUserId());
       u.setUserName(user.getUserName());
       u.setPassword(user.getPassword());
       u.setFullName(user.getFullName());
       users.add(u);
    }

    public List<Services> getServices() {
        return services;
    }

    public void addService(Services service) {
        services.add(service);
    }

    public void deleteService(Services service) {
        services.remove(service);
    }

    public Services getService(int id) {
        for (Services service : services) {
            if (service.getServiceId() == id) {
                return service;
            }
        }
        return null;
    }

    public void updateService(Services serviceNew) {
        Services service = getService(serviceNew.getServiceId());
        service.setName(serviceNew.getName());
        service.setDescription(serviceNew.getDescription());
        service.setDuration(serviceNew.getDuration());
        service.setPrice(serviceNew.getPrice());
        services.add(service);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void deleteAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    public Appointment getAppointment(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getServiceId() == id) {
                return appointment;
            }
        }
        return null;
    }

    public void updateAppointment(Appointment appointmentNew) {
        Appointment appointment = getAppointment(appointmentNew.getServiceId());
        appointment.setCustomerName(appointmentNew.getCustomerName());
        appointment.setCustomerEmail(appointmentNew.getCustomerEmail());
        appointment.setAppointmentDate(appointmentNew.getAppointmentDate());
        appointment.setStatus(appointmentNew.getStatus());
        appointment.setCreatedAt(LocalDateTime.now());
        appointments.add(appointment);
    }

    public boolean findUser(int id, List<Users> users) {
        for (Users user : users) {
            if (user.getUserId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean findService(int id, List<Services> services) {
        for (Services service : services) {
            if (service.getServiceId() == id) {
                return true;
            }
        }
        return false;
    }

    public void getAppointments(Users user, List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment.getCustomerId() == user.getUserId()) {
                System.out.println(appointment);
            }
        }
    }

    public void getAppointmentsbByUserId(int userId) {
        for (Appointment appointment : appointments) {
            if (appointment.getCustomerId() == userId) {
                System.out.println(appointment);
            }
        }
    }

    public void updateTimeAppointment(int appointmentId, LocalDateTime newTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                appointmentDao.updateTimeAppointment(appointmentId, newTime);
                break;
            }
        }
    }

    public Appointment getAppointmentById(int appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    public List<Appointment> getAppointmentsByUserId(int userId) {
        return appointmentDao.findAllByUser(userId);
    }
    
    // Synchronized method to check for appointment conflicts
    public synchronized boolean hasAppointmentConflict(int serviceId, LocalDateTime appointmentTime) {
        try (Connection conn = DBConnect.getConnection()) {
            return appointmentDao.hasConflict(conn, serviceId, appointmentTime, appointmentTime.plusMinutes(30));
        } catch (SQLException e) {
            System.err.println("Error checking appointment conflict: " + e.getMessage());
            return true; // Return true to prevent booking if there's an error
        }
    }
    
    // Synchronized method to create appointment with conflict checking
    public synchronized boolean createAppointment(Appointment appointment) {
        try (Connection conn = se.fu.vn.connection.DBConnect.getConnection()) {
            // Check for conflicts first
            if (hasAppointmentConflict(appointment.getServiceId(), appointment.getAppointmentDate())) {
                System.out.println("Time slot is already booked. Please choose another time.");
                return false;
            }
            
            // Create appointment in database
            int appointmentId = appointmentDao.insert(appointment);
            if (appointmentId > 0) {
                appointment.setAppointmentId(appointmentId);
                appointments.add(appointment);
                System.out.println("✅ Appointment created successfully with ID: " + appointmentId);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            return false;
        }
    }
    
    // Load data from database
    public void loadDataFromDatabase() {
        try {
            users = userDao.findAll();
            services = serviceDao.findAll();
            appointments = appointmentDao.findAll();
            System.out.println("Data loaded from database successfully");
        } catch (Exception e) {
            System.err.println("Error loading data from database: " + e.getMessage());
        }
    }
    
    // Update appointment status
    public synchronized boolean updateAppointmentStatus(int appointmentId, String newStatus) {
        try {
            appointmentDao.updateStatus(appointmentId, newStatus);
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentId() == appointmentId) {
                    appointment.setStatus(newStatus);
                    break;
                }
            }
            System.out.println("Appointment status updated to: " + newStatus);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating appointment status: " + e.getMessage());
            return false;
        }
    }
    
    // Getter methods for DAO objects
    public ServiceDao getServiceDao() {
        return serviceDao;
    }
    
    public UserDao getUserDao() {
        return userDao;
    }
    
    public AppointmentDao getAppointmentDao() {
        return appointmentDao;
    }
}
