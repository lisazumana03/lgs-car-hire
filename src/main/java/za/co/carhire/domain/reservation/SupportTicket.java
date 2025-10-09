package za.co.carhire.domain.reservation;

/*
Olwethu Tshingo - 222634383
Date: 30 2025
Updated: 09 October 2025
 */

import jakarta.persistence.*;
import za.co.carhire.domain.authentication.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class SupportTicket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer ticketID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    protected User user;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    protected Booking booking;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    protected User assignedTo;

    @Column(nullable = false, length = 255)
    protected String subject;

    @Column(length = 2000)
    protected String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TicketPriority priority = TicketPriority.MEDIUM;

    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    @Column
    protected LocalDateTime resolvedAt;

    public SupportTicket() {
    }

    private SupportTicket(Builder builder) {
        this.ticketID = builder.ticketID;
        this.user = builder.user;
        this.booking = builder.booking;
        this.assignedTo = builder.assignedTo;
        this.subject = builder.subject;
        this.description = builder.description;
        this.status = builder.status;
        this.priority = builder.priority;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.resolvedAt = builder.resolvedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public User getUser(){
        return user;
    }

    public Booking getBooking(){
        return booking;
    }

    public User getAssignedTo(){
        return assignedTo;
    }

    public String getSubject(){
        return subject;
    }

    public String getDescription(){
        return description;
    }

    public TicketStatus getStatus(){
        return status;
    }

    public void setStatus(TicketStatus status){
        this.status = status;
    }

    public TicketPriority getPriority(){
        return priority;
    }

    public void setPriority(TicketPriority priority){
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public LocalDateTime getResolvedAt(){
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt){
        this.resolvedAt = resolvedAt;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketID=" + ticketID +
                ", user=" + user +
                ", booking=" + booking +
                ", assignedTo=" + assignedTo +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", resolvedAt=" + resolvedAt +
                '}';
    }

    public static class Builder{
        protected Integer ticketID;
        protected User user;
        protected Booking booking;
        protected User assignedTo;
        protected String subject;
        protected String description;
        protected TicketStatus status = TicketStatus.OPEN;
        protected TicketPriority priority = TicketPriority.MEDIUM;
        protected LocalDateTime createdAt;
        protected LocalDateTime updatedAt;
        protected LocalDateTime resolvedAt;

        public Builder setTicketID(Integer ticketID) {
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

        public Builder setAssignedTo(User assignedTo) {
            this.assignedTo = assignedTo;
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

        public SupportTicket.Builder copy(SupportTicket support){
            this.ticketID = support.getTicketID();
            this.user = support.getUser();
            this.booking = support.getBooking();
            this.assignedTo = support.getAssignedTo();
            this.subject = support.getSubject();
            this.description = support.getDescription();
            this.status = support.getStatus();
            this.priority = support.getPriority();
            this.createdAt = support.getCreatedAt();
            this.updatedAt = support.getUpdatedAt();
            this.resolvedAt = support.getResolvedAt();
            return this;
        }

        public SupportTicket build(){
            return new SupportTicket(this);
        }
    }

}
