package za.co.carhire.factory.reservation;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketPriority;
import za.co.carhire.domain.reservation.TicketStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
Support Ticket Factory Test
Date: 09 October 2025
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupportTicketFactoryTest {

    @Test
    @Order(1)
    void testCreateTicket() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        String subject = "Unable to access my booking";
        String description = "I cannot view my recent booking details in the app.";

        // When
        SupportTicket ticket = SupportTicketFactory.createTicket(user, subject, description);

        // Then
        assertNotNull(ticket);
        assertEquals(user, ticket.getUser());
        assertEquals(subject, ticket.getSubject());
        assertEquals(description, ticket.getDescription());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
        assertEquals(TicketPriority.MEDIUM, ticket.getPriority());
        assertNull(ticket.getBooking());

        System.out.println(ticket);
    }

    @Test
    @Order(2)
    void testCreateTicketWithNullUser() {
        // Given
        String subject = "Test subject";
        String description = "Test description";

        // When
        SupportTicket ticket = SupportTicketFactory.createTicket(null, subject, description);

        // Then
        assertNull(ticket);
        System.out.println("Correctly returned null for null user");
    }

    @Test
    @Order(3)
    void testCreateTicketWithNullSubject() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        // When
        SupportTicket ticket = SupportTicketFactory.createTicket(user, null, "Description");

        // Then
        assertNull(ticket);
        System.out.println("Correctly returned null for null subject");
    }

    @Test
    @Order(4)
    void testCreateTicketWithEmptySubject() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        // When
        SupportTicket ticket = SupportTicketFactory.createTicket(user, "", "Description");

        // Then
        assertNull(ticket);
        System.out.println("Correctly returned null for empty subject");
    }

    @Test
    @Order(5)
    void testCreateTicketWithBooking() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        Booking booking = new Booking.Builder()
                .setBookingID(100)
                .setUser(user)
                .build();

        String subject = "Car breakdown during rental";
        String description = "The rental car broke down on the highway.";
        TicketPriority priority = TicketPriority.CRITICAL;

        // When
        SupportTicket ticket = SupportTicketFactory.createTicketWithBooking(
                user, booking, subject, description, priority);

        // Then
        assertNotNull(ticket);
        assertEquals(user, ticket.getUser());
        assertEquals(booking, ticket.getBooking());
        assertEquals(subject, ticket.getSubject());
        assertEquals(description, ticket.getDescription());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
        assertEquals(TicketPriority.CRITICAL, ticket.getPriority());

        System.out.println(ticket);
    }

    @Test
    @Order(6)
    void testCreateTicketWithBookingNoPriority() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        Booking booking = new Booking.Builder()
                .setBookingID(100)
                .setUser(user)
                .build();

        String subject = "Question about my rental";
        String description = "Can I extend my rental period?";

        // When
        SupportTicket ticket = SupportTicketFactory.createTicketWithBooking(
                user, booking, subject, description, null);

        // Then
        assertNotNull(ticket);
        assertEquals(booking, ticket.getBooking());
        assertEquals(TicketPriority.MEDIUM, ticket.getPriority()); // Should default to MEDIUM

        System.out.println(ticket);
    }

    @Test
    @Order(7)
    void testCreateTicketWithBookingNoBooking() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        String subject = "General inquiry";
        String description = "What are your operating hours?";

        // When
        SupportTicket ticket = SupportTicketFactory.createTicketWithBooking(
                user, null, subject, description, TicketPriority.LOW);

        // Then
        assertNotNull(ticket);
        assertNull(ticket.getBooking()); // Should handle null booking
        assertEquals(TicketPriority.LOW, ticket.getPriority());

        System.out.println(ticket);
    }

    @Test
    @Order(8)
    void testCopyTicket() {
        // Given
        User user = new User.Builder()
                .setUserId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        SupportTicket original = SupportTicketFactory.createTicket(
                user, "Original Subject", "Original Description");

        // When
        SupportTicket copy = SupportTicketFactory.copyTicket(original);

        // Then
        assertNotNull(copy);
        assertEquals(original.getUser(), copy.getUser());
        assertEquals(original.getSubject(), copy.getSubject());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getStatus(), copy.getStatus());
        assertEquals(original.getPriority(), copy.getPriority());

        System.out.println("Original: " + original);
        System.out.println("Copy: " + copy);
    }

    @Test
    @Order(9)
    void testCopyTicketWithNull() {
        // When
        SupportTicket copy = SupportTicketFactory.copyTicket(null);

        // Then
        assertNull(copy);
        System.out.println("Correctly returned null for null ticket copy");
    }
}
