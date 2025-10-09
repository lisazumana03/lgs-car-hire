package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketPriority;
import za.co.carhire.domain.reservation.TicketStatus;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.service.reservation.IBookingService;
import za.co.carhire.service.reservation.ISupportTicketService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
Support Ticket Data Initializer
Date: 09 October 2025
*/

@Component
@Order(6) // Run after BookingDataInitializer (Order 5)
public class SupportTicketDataInitializer implements CommandLineRunner {

    @Autowired
    private ISupportTicketService supportTicketService;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private UserService userService;

    @Value("${app.init.create-sample-support-tickets:true}")
    private boolean createSampleTickets;

    @Override
    public void run(String... args) throws Exception {
        if (!createSampleTickets) {
            System.out.println("Sample support ticket creation is disabled.");
            return;
        }

        // Check if support tickets already exist
        if (!supportTicketService.getTickets().isEmpty()) {
            System.out.println("Support tickets already exist. Skipping sample data creation.");
            return;
        }

        System.out.println("============================================");
        System.out.println("Creating sample support ticket data...");
        System.out.println("============================================");

        int ticketCount = createSampleSupportTickets();

        System.out.println("Sample support ticket data created successfully!");
        System.out.println("Total Support Tickets: " + ticketCount);
        System.out.println("============================================");
    }

    private int createSampleSupportTickets() {
        // Get users
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            System.out.println("No users available for support tickets. Please run UserDataInitializer first.");
            return 0;
        }

        // Get bookings
        Set<Booking> bookingSet = bookingService.getBookings();
        List<Booking> bookings = new ArrayList<>(bookingSet);

        if (bookings.isEmpty()) {
            System.out.println("No bookings available for support tickets. Please run BookingDataInitializer first.");
            return 0;
        }

        int ticketCount = 0;

        // Ticket 1: Critical issue - car breakdown (related to first booking)
        if (bookings.size() > 0 && users.size() > 1) {
            SupportTicket ticket1 = new SupportTicket.Builder()
                    .setUser(users.get(1)) // Test User
                    .setBooking(bookings.get(0))
                    .setSubject("Car breakdown - Engine issue")
                    .setDescription("The rental car (Toyota Corolla) has broken down on the N1 highway. " +
                            "The engine is making strange noises and won't start. Need immediate assistance.")
                    .setStatus(TicketStatus.IN_PROGRESS)
                    .setPriority(TicketPriority.CRITICAL)
                    .build();
            supportTicketService.create(ticket1);
            ticketCount++;
            System.out.println("  ✓ Created ticket: Car breakdown - CRITICAL (IN_PROGRESS)");
        }

        // Ticket 2: High priority - billing dispute (related to second booking)
        if (bookings.size() > 1 && users.size() > 0) {
            SupportTicket ticket2 = new SupportTicket.Builder()
                    .setUser(users.get(0)) // Admin User
                    .setBooking(bookings.get(1))
                    .setSubject("Billing dispute - Incorrect charges")
                    .setDescription("I was charged for two days of rental that I did not use. " +
                            "The invoice shows 10 days but I only used 8 days. Please review and adjust.")
                    .setStatus(TicketStatus.OPEN)
                    .setPriority(TicketPriority.HIGH)
                    .build();
            supportTicketService.create(ticket2);
            ticketCount++;
            System.out.println("  ✓ Created ticket: Billing dispute - HIGH (OPEN)");
        }

        // Ticket 3: Medium priority - modification request (related to third booking)
        if (bookings.size() > 2 && users.size() > 1) {
            SupportTicket ticket3 = new SupportTicket.Builder()
                    .setUser(users.get(1)) // Test User
                    .setBooking(bookings.get(2))
                    .setSubject("Request to change drop-off location")
                    .setDescription("I would like to change my drop-off location from Durban Beach to " +
                            "Durban Airport. Is this possible and are there any additional charges?")
                    .setStatus(TicketStatus.OPEN)
                    .setPriority(TicketPriority.MEDIUM)
                    .build();
            supportTicketService.create(ticket3);
            ticketCount++;
            System.out.println("  ✓ Created ticket: Change drop-off location - MEDIUM (OPEN)");
        }

        // Ticket 4: Resolved ticket - minor issue (related to fourth booking if exists)
        if (bookings.size() > 3 && users.size() > 0) {
            SupportTicket ticket4 = new SupportTicket.Builder()
                    .setUser(users.get(0)) // Admin User
                    .setBooking(bookings.get(3))
                    .setSubject("Missing receipt")
                    .setDescription("I did not receive my rental receipt via email. Can you resend it?")
                    .setStatus(TicketStatus.RESOLVED)
                    .setPriority(TicketPriority.LOW)
                    .build();

            // Set resolved timestamp manually for demonstration
            SupportTicket savedTicket = supportTicketService.create(ticket4);
            savedTicket.setResolvedAt(LocalDateTime.now().minusDays(1));
            supportTicketService.update(savedTicket);

            ticketCount++;
            System.out.println("  ✓ Created ticket: Missing receipt - LOW (RESOLVED)");
        }

        // Ticket 5: General inquiry - no booking (standalone ticket)
        if (users.size() > 1) {
            SupportTicket ticket5 = new SupportTicket.Builder()
                    .setUser(users.get(1)) // Test User
                    .setBooking(null) // No booking associated
                    .setSubject("Question about insurance coverage")
                    .setDescription("I would like to know what is covered under the comprehensive insurance " +
                            "package. Does it include tire damage and windscreen replacement?")
                    .setStatus(TicketStatus.OPEN)
                    .setPriority(TicketPriority.LOW)
                    .build();
            supportTicketService.create(ticket5);
            ticketCount++;
            System.out.println("  ✓ Created ticket: Insurance inquiry - LOW (OPEN)");
        }

        // Ticket 6: Urgent issue - accident report (related to first booking)
        if (bookings.size() > 0 && users.size() > 1) {
            SupportTicket ticket6 = new SupportTicket.Builder()
                    .setUser(users.get(1)) // Test User
                    .setBooking(bookings.get(0))
                    .setSubject("Accident report - Minor collision")
                    .setDescription("I was involved in a minor collision in a parking lot. " +
                            "No injuries, but there is minor damage to the rear bumper. " +
                            "I have photos and the other driver's information. What should I do next?")
                    .setStatus(TicketStatus.IN_PROGRESS)
                    .setPriority(TicketPriority.HIGH)
                    .build();
            supportTicketService.create(ticket6);
            ticketCount++;
            System.out.println("  ✓ Created ticket: Accident report - HIGH (IN_PROGRESS)");
        }

        // Ticket 7: Closed ticket - complaint resolved (no booking)
        if (users.size() > 0) {
            SupportTicket ticket7 = new SupportTicket.Builder()
                    .setUser(users.get(0)) // Admin User
                    .setBooking(null)
                    .setSubject("Complaint about customer service")
                    .setDescription("I had a very poor experience at the Cape Town Airport location. " +
                            "The staff was rude and unhelpful during pickup.")
                    .setStatus(TicketStatus.CLOSED)
                    .setPriority(TicketPriority.MEDIUM)
                    .build();

            // Set resolved timestamp
            SupportTicket savedTicket = supportTicketService.create(ticket7);
            savedTicket.setResolvedAt(LocalDateTime.now().minusDays(3));
            supportTicketService.update(savedTicket);

            ticketCount++;
            System.out.println("  ✓ Created ticket: Customer service complaint - MEDIUM (CLOSED)");
        }

        // Ticket 8: Account issue - no booking
        if (users.size() > 1) {
            SupportTicket ticket8 = new SupportTicket.Builder()
                    .setUser(users.get(1)) // Test User
                    .setBooking(null)
                    .setSubject("Unable to access booking history")
                    .setDescription("I cannot view my past bookings on the website. " +
                            "The page keeps showing an error message. Can you help?")
                    .setStatus(TicketStatus.OPEN)
                    .setPriority(TicketPriority.MEDIUM)
                    .build();
            supportTicketService.create(ticket8);
            ticketCount++;
            System.out.println("  ✓ Created ticket: Account access issue - MEDIUM (OPEN)");
        }

        // Assign some tickets to support staff (if admin user exists, use them as support staff)
        if (users.size() > 0 && ticketCount > 0) {
            User adminUser = users.get(0); // Assume first user is admin/support staff

            // Get all tickets and assign the IN_PROGRESS ones
            Set<SupportTicket> allTickets = supportTicketService.getTickets();
            for (SupportTicket ticket : allTickets) {
                if (ticket.getStatus() == TicketStatus.IN_PROGRESS) {
                    SupportTicket updated = new SupportTicket.Builder()
                            .copy(ticket)
                            .setAssignedTo(adminUser)
                            .build();
                    supportTicketService.update(updated);
                }
            }
            System.out.println("  ✓ Assigned IN_PROGRESS tickets to: " + adminUser.getName());
        }

        return ticketCount;
    }
}
