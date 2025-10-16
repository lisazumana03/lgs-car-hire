package za.co.carhire.domain.reservation;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 16 October 2025 - Enhanced with comprehensive support ticket system
 */

import jakarta.persistence.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.vehicle.Car;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_ticket")
public class SupportTicket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketID;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password", "idNumber"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonIgnoreProperties({"user", "car", "payment"})
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonIgnoreProperties({"reviews", "maintenanceRecords", "imageData"})
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    @JsonIgnoreProperties({"password", "idNumber"})
    private User assignedStaff;

    // Core Fields
    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TicketPriority priority = TicketPriority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TicketCategory category;

    // Contact Information
    @Column(name = "contact_email", length = 255)
    private String contactEmail;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    // Resolution Information
    @Column(name = "resolution", columnDefinition = "TEXT")
    private String resolution;

    @Column(name = "internal_notes", columnDefinition = "TEXT")
    private String internalNotes;

    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "first_response_at")
    private LocalDateTime firstResponseAt;

    // Customer Satisfaction
    @Column(name = "satisfaction_rating")
    private Integer satisfactionRating;

    @Column(name = "satisfaction_comment", length = 500)
    private String satisfactionComment;

    public SupportTicket() {
    }

    private SupportTicket(Builder builder) {
        this.ticketID = builder.ticketID;
        this.user = builder.user;
        this.booking = builder.booking;
        this.car = builder.car;
        this.assignedStaff = builder.assignedStaff;
        this.subject = builder.subject;
        this.description = builder.description;
        this.status = builder.status;
        this.priority = builder.priority;
        this.category = builder.category;
        this.contactEmail = builder.contactEmail;
        this.contactPhone = builder.contactPhone;
        this.resolution = builder.resolution;
        this.internalNotes = builder.internalNotes;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.resolvedAt = builder.resolvedAt;
        this.closedAt = builder.closedAt;
        this.firstResponseAt = builder.firstResponseAt;
        this.satisfactionRating = builder.satisfactionRating;
        this.satisfactionComment = builder.satisfactionComment;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (status == TicketStatus.RESOLVED && resolvedAt == null) {
            resolvedAt = LocalDateTime.now();
        }
        if (status == TicketStatus.CLOSED && closedAt == null) {
            closedAt = LocalDateTime.now();
        }
    }

    // Getters
    public int getTicketID() {
        return ticketID;
    }

    public User getUser() {
        return user;
    }

    public Booking getBooking() {
        return booking;
    }

    public Car getCar() {
        return car;
    }

    public User getAssignedStaff() {
        return assignedStaff;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getResolution() {
        return resolution;
    }

    public String getInternalNotes() {
        return internalNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public LocalDateTime getFirstResponseAt() {
        return firstResponseAt;
    }

    public Integer getSatisfactionRating() {
        return satisfactionRating;
    }

    public String getSatisfactionComment() {
        return satisfactionComment;
    }

    // Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setAssignedStaff(User assignedStaff) {
        this.assignedStaff = assignedStaff;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
    }

    public void setFirstResponseAt(LocalDateTime firstResponseAt) {
        this.firstResponseAt = firstResponseAt;
    }

    public void setSatisfactionRating(Integer satisfactionRating) {
        this.satisfactionRating = satisfactionRating;
    }

    public void setSatisfactionComment(String satisfactionComment) {
        this.satisfactionComment = satisfactionComment;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketID=" + ticketID +
                ", user=" + (user != null ? user.getUserId() : "null") +
                ", booking=" + (booking != null ? booking.getBookingID() : "null") +
                ", car=" + (car != null ? car.getCarID() : "null") +
                ", assignedStaff=" + (assignedStaff != null ? assignedStaff.getUserId() : "null") +
                ", subject='" + subject + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", category=" + category +
                ", createdAt=" + createdAt +
                ", resolvedAt=" + resolvedAt +
                '}';
    }

    public static class Builder {
        private int ticketID;
        private User user;
        private Booking booking;
        private Car car;
        private User assignedStaff;
        private String subject;
        private String description;
        private TicketStatus status = TicketStatus.OPEN;
        private TicketPriority priority = TicketPriority.MEDIUM;
        private TicketCategory category;
        private String contactEmail;
        private String contactPhone;
        private String resolution;
        private String internalNotes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime resolvedAt;
        private LocalDateTime closedAt;
        private LocalDateTime firstResponseAt;
        private Integer satisfactionRating;
        private String satisfactionComment;

        public Builder setTicketID(int ticketID) {
            this.ticketID = ticketID;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder setAssignedStaff(User assignedStaff) {
            this.assignedStaff = assignedStaff;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStatus(TicketStatus status) {
            this.status = status;
            return this;
        }

        public Builder setPriority(TicketPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder setCategory(TicketCategory category) {
            this.category = category;
            return this;
        }

        public Builder setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
            return this;
        }

        public Builder setResolution(String resolution) {
            this.resolution = resolution;
            return this;
        }

        public Builder setInternalNotes(String internalNotes) {
            this.internalNotes = internalNotes;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setResolvedAt(LocalDateTime resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public Builder setClosedAt(LocalDateTime closedAt) {
            this.closedAt = closedAt;
            return this;
        }

        public Builder setFirstResponseAt(LocalDateTime firstResponseAt) {
            this.firstResponseAt = firstResponseAt;
            return this;
        }

        public Builder setSatisfactionRating(Integer satisfactionRating) {
            this.satisfactionRating = satisfactionRating;
            return this;
        }

        public Builder setSatisfactionComment(String satisfactionComment) {
            this.satisfactionComment = satisfactionComment;
            return this;
        }

        public Builder copy(SupportTicket ticket) {
            this.ticketID = ticket.ticketID;
            this.user = ticket.user;
            this.booking = ticket.booking;
            this.car = ticket.car;
            this.assignedStaff = ticket.assignedStaff;
            this.subject = ticket.subject;
            this.description = ticket.description;
            this.status = ticket.status;
            this.priority = ticket.priority;
            this.category = ticket.category;
            this.contactEmail = ticket.contactEmail;
            this.contactPhone = ticket.contactPhone;
            this.resolution = ticket.resolution;
            this.internalNotes = ticket.internalNotes;
            this.createdAt = ticket.createdAt;
            this.updatedAt = ticket.updatedAt;
            this.resolvedAt = ticket.resolvedAt;
            this.closedAt = ticket.closedAt;
            this.firstResponseAt = ticket.firstResponseAt;
            this.satisfactionRating = ticket.satisfactionRating;
            this.satisfactionComment = ticket.satisfactionComment;
            return this;
        }

        public SupportTicket build() {
            return new SupportTicket(this);
        }
    }
}
