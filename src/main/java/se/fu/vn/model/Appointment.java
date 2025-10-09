package se.fu.vn.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Appointment implements Serializable {
    private int appointmentId;
    private int customerId;
    private String customerName;
    private String customerEmail;
    private int serviceId;
    private LocalDateTime appointmentDate;
    private String status;
    private LocalDateTime createdAt;

    public Appointment() {
    }

    public Appointment(int appointmentId, int customerId, String customerName, String customerEmail, int serviceId, LocalDateTime appointmentDate, String status, LocalDateTime createdAt) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.serviceId = serviceId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", serviceId=" + serviceId +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
