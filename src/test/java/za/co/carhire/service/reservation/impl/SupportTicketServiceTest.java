package za.co.carhire.service.reservation.impl;

/*
Support Ticket Service Test
Date: 09 October 2025
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.reservation.ISupportTicketRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupportTicketServiceTest {

    @InjectMocks
    private SupportTicketService supportTicketService;

    @Mock
    private ISupportTicketRepository supportTicketRepository;

    @Mock
    private IUserRepository userRepository;

    private User testUser;
    private User supportStaff;
    private Booking testBooking;
    private SupportTicket testTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test user
        testUser = new User.Builder()
                .setUserId(1)
                .setName("Test User")
                .setEmail("test@example.com")
                .setIdNumber(9001015800084L)
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setRole(UserRole.USER)
                .build();

        // Create support staff
        supportStaff = new User.Builder()
                .setUserId(2)
                .setName("Support Staff")
                .setEmail("support@example.com")
                .setIdNumber(8505105800088L)
                .setDateOfBirth(LocalDate.of(1985, 5, 10))
                .setPhoneNumber("0827654321")
                .setPassword("password456")
                .setRole(UserRole.ADMIN)
                .build();

        // Create test booking
        testBooking = new Booking.Builder()
                .setBookingID(100)
                .setUser(testUser)
                .setBookingStatus(BookingStatus.CONFIRMED)
                .build();

        // Create test ticket
        testTicket = new SupportTicket.Builder()
                .setTicketID(1)
                .setUser(testUser)
                .setBooking(testBooking)
                .setSubject("Test Issue")
                .setDescription("This is a test support ticket")
                .setStatus(TicketStatus.OPEN)
                .setPriority(TicketPriority.MEDIUM)
                .build();
    }

    @Test
    void testCreate() {
        // Given
        when(supportTicketRepository.save(testTicket)).thenReturn(testTicket);

        // When
        SupportTicket created = supportTicketService.create(testTicket);

        // Then
        assertNotNull(created);
        assertEquals(testTicket, created);
        verify(supportTicketRepository, times(1)).save(testTicket);

        System.out.println("Created ticket: " + created);
    }

    @Test
    void testRead() {
        // Given
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(testTicket));

        // When
        SupportTicket found = supportTicketService.read(1);

        // Then
        assertNotNull(found);
        assertEquals(testTicket, found);
        verify(supportTicketRepository, times(1)).findById(1);

        System.out.println("Found ticket: " + found);
    }

    @Test
    void testReadNotFound() {
        // Given
        when(supportTicketRepository.findById(999)).thenReturn(Optional.empty());

        // When
        SupportTicket found = supportTicketService.read(999);

        // Then
        assertNull(found);
        verify(supportTicketRepository, times(1)).findById(999);

        System.out.println("Correctly returned null for non-existent ticket");
    }

    @Test
    void testUpdate() {
        // Given
        when(supportTicketRepository.existsById(1)).thenReturn(true);
        when(supportTicketRepository.save(testTicket)).thenReturn(testTicket);

        // When
        SupportTicket updated = supportTicketService.update(testTicket);

        // Then
        assertNotNull(updated);
        assertEquals(testTicket, updated);
        verify(supportTicketRepository, times(1)).existsById(1);
        verify(supportTicketRepository, times(1)).save(testTicket);

        System.out.println("Updated ticket: " + updated);
    }

    @Test
    void testUpdateNonExistent() {
        // Given
        when(supportTicketRepository.existsById(999)).thenReturn(false);

        SupportTicket nonExistent = new SupportTicket.Builder()
                .setTicketID(999)
                .setUser(testUser)
                .setSubject("Non-existent")
                .build();

        // When
        SupportTicket updated = supportTicketService.update(nonExistent);

        // Then
        assertNull(updated);
        verify(supportTicketRepository, times(1)).existsById(999);
        verify(supportTicketRepository, never()).save(any());

        System.out.println("Correctly returned null for update of non-existent ticket");
    }

    @Test
    void testDelete() {
        // Given
        doNothing().when(supportTicketRepository).deleteById(1);

        // When
        supportTicketService.delete(1);

        // Then
        verify(supportTicketRepository, times(1)).deleteById(1);

        System.out.println("Successfully deleted ticket with ID: 1");
    }

    @Test
    void testGetTickets() {
        // Given
        List<SupportTicket> tickets = Arrays.asList(testTicket);
        when(supportTicketRepository.findAll()).thenReturn(tickets);

        // When
        Set<SupportTicket> result = supportTicketService.getTickets();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(testTicket));
        verify(supportTicketRepository, times(1)).findAll();

        System.out.println("Retrieved " + result.size() + " tickets");
    }

    @Test
    void testGetTicketsByUser() {
        // Given
        List<SupportTicket> tickets = Arrays.asList(testTicket);
        when(supportTicketRepository.findByUser_UserId(1)).thenReturn(tickets);

        // When
        Set<SupportTicket> result = supportTicketService.getTicketsByUser(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(supportTicketRepository, times(1)).findByUser_UserId(1);

        System.out.println("Retrieved " + result.size() + " tickets for user ID: 1");
    }

    @Test
    void testGetTicketsByStatus() {
        // Given
        List<SupportTicket> tickets = Arrays.asList(testTicket);
        when(supportTicketRepository.findByStatus(TicketStatus.OPEN)).thenReturn(tickets);

        // When
        Set<SupportTicket> result = supportTicketService.getTicketsByStatus(TicketStatus.OPEN);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(supportTicketRepository, times(1)).findByStatus(TicketStatus.OPEN);

        System.out.println("Retrieved " + result.size() + " tickets with status: OPEN");
    }

    @Test
    void testGetTicketsByBooking() {
        // Given
        List<SupportTicket> tickets = Arrays.asList(testTicket);
        when(supportTicketRepository.findByBooking_BookingID(100)).thenReturn(tickets);

        // When
        Set<SupportTicket> result = supportTicketService.getTicketsByBooking(100);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(supportTicketRepository, times(1)).findByBooking_BookingID(100);

        System.out.println("Retrieved " + result.size() + " tickets for booking ID: 100");
    }

    @Test
    void testUpdateStatus() {
        // Given
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(supportTicketRepository.save(any(SupportTicket.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        SupportTicket updated = supportTicketService.updateStatus(1, TicketStatus.IN_PROGRESS);

        // Then
        assertNotNull(updated);
        assertEquals(TicketStatus.IN_PROGRESS, updated.getStatus());
        assertNull(updated.getResolvedAt()); // Should not be set for IN_PROGRESS
        verify(supportTicketRepository, times(1)).findById(1);
        verify(supportTicketRepository, times(1)).save(any(SupportTicket.class));

        System.out.println("Updated ticket status to: " + updated.getStatus());
    }

    @Test
    void testUpdateStatusToResolved() {
        // Given
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(supportTicketRepository.save(any(SupportTicket.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        SupportTicket updated = supportTicketService.updateStatus(1, TicketStatus.RESOLVED);

        // Then
        assertNotNull(updated);
        assertEquals(TicketStatus.RESOLVED, updated.getStatus());
        assertNotNull(updated.getResolvedAt()); // Should be set for RESOLVED
        verify(supportTicketRepository, times(1)).findById(1);
        verify(supportTicketRepository, times(1)).save(any(SupportTicket.class));

        System.out.println("Updated ticket status to RESOLVED with resolvedAt: " + updated.getResolvedAt());
    }

    @Test
    void testUpdateStatusNonExistent() {
        // Given
        when(supportTicketRepository.findById(999)).thenReturn(Optional.empty());

        // When
        SupportTicket updated = supportTicketService.updateStatus(999, TicketStatus.RESOLVED);

        // Then
        assertNull(updated);
        verify(supportTicketRepository, times(1)).findById(999);
        verify(supportTicketRepository, never()).save(any());

        System.out.println("Correctly returned null for status update of non-existent ticket");
    }

    @Test
    void testAssignTicket() {
        // Given
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(userRepository.findById(2)).thenReturn(Optional.of(supportStaff));
        when(supportTicketRepository.save(any(SupportTicket.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        SupportTicket assigned = supportTicketService.assignTicket(1, 2);

        // Then
        assertNotNull(assigned);
        assertEquals(supportStaff, assigned.getAssignedTo());
        assertEquals(TicketStatus.IN_PROGRESS, assigned.getStatus());
        verify(supportTicketRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findById(2);
        verify(supportTicketRepository, times(1)).save(any(SupportTicket.class));

        System.out.println("Assigned ticket to: " + assigned.getAssignedTo().getName());
    }

    @Test
    void testAssignTicketNonExistentUser() {
        // Given
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // When
        SupportTicket assigned = supportTicketService.assignTicket(1, 999);

        // Then
        assertNull(assigned);
        verify(supportTicketRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findById(999);
        verify(supportTicketRepository, never()).save(any());

        System.out.println("Correctly returned null for assignment to non-existent user");
    }

    @Test
    void testResolveTicket() {
        // Given
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(supportTicketRepository.save(any(SupportTicket.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        SupportTicket resolved = supportTicketService.resolveTicket(1);

        // Then
        assertNotNull(resolved);
        assertEquals(TicketStatus.RESOLVED, resolved.getStatus());
        assertNotNull(resolved.getResolvedAt());
        verify(supportTicketRepository, times(1)).findById(1);
        verify(supportTicketRepository, times(1)).save(any(SupportTicket.class));

        System.out.println("Resolved ticket with ID: " + resolved.getTicketID());
    }
}
