package se.fu.vn.controller;

import se.fu.vn.model.Appointment;
import se.fu.vn.model.Services;

import java.security.Provider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Services> services = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

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
}
