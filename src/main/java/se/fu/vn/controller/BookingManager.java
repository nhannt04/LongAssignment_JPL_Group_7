package se.fu.vn.controller;

import se.fu.vn.model.Appointment;
import se.fu.vn.model.Services;
import se.fu.vn.model.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Users> users = new ArrayList<>();
    private List<Services> services = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

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
}
