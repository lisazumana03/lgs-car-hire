package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.ISupportTicketRepository;
import za.co.carhire.service.vehicle.ICarService;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Order(6)  // Run after Users, Cars, Locations, Maintenance, Bookings, Reviews
public class SupportTicketDataInitializer implements CommandLineRunner {

    @Autowired
    private ISupportTicketRepository ticketRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICarService carService;

    @Autowired
    private IBookingRepository bookingRepository;

    @Value("${app.database.init.tickets.enabled:true}")
    private boolean initTicketsEnabled;

    @Value("${app.database.init.tickets.clear-existing:false}")
    private boolean clearExistingTickets;

    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initTicketsEnabled) {
            System.out.println("Support ticket data initialization is disabled. Set app.database.init.tickets.enabled=true to enable.");
            return;
        }

        List<SupportTicket> existingTickets = ticketRepository.findAll();

        if (!existingTickets.isEmpty() && !clearExistingTickets) {
            System.out.println("Database already contains " + existingTickets.size() + " support tickets.");
            System.out.println("Skipping ticket initialization. Set app.database.init.tickets.clear-existing=true to override.");
            return;
        }

        if (clearExistingTickets && !existingTickets.isEmpty()) {
            System.out.println("Clearing existing support tickets as requested...");
            ticketRepository.deleteAll();
            System.out.println("Existing support tickets cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing support ticket data...");
        System.out.println("============================================");

        createSampleTickets();

        System.out.println("============================================");
        System.out.println("Support ticket initialization completed successfully!");
        System.out.println("--------------------------------------------");
        displayTicketSummary();
        System.out.println("============================================");
    }

    private void createSampleTickets() {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .toList();
        List<User> staff = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.ADMIN)
                .toList();
        Set<Car> cars = carService.getCars();
        List<Booking> bookings = bookingRepository.findAll();

        if (users.isEmpty()) {
            System.out.println("No customer users found. Skipping ticket creation.");
            return;
        }

        System.out.println("Creating sample support tickets...");

        int ticketsCreated = 0;

        // Create 15-25 diverse tickets
        int ticketCount = RANDOM.nextInt(11) + 15;

        for (int i = 0; i < ticketCount; i++) {
            User randomUser = users.get(RANDOM.nextInt(users.size()));
            User assignedStaff = staff.isEmpty() ? null :
                (RANDOM.nextInt(10) < 7 ? staff.get(RANDOM.nextInt(staff.size())) : null); // 70% assigned

            SupportTicket ticket = createRandomTicket(randomUser, assignedStaff, cars, bookings);
            if (ticket != null) {
                ticketRepository.save(ticket);
                ticketsCreated++;
                System.out.println("✓ Created " + ticket.getCategory() + " ticket: " + ticket.getSubject() +
                                 " (Priority: " + ticket.getPriority() + ", Status: " + ticket.getStatus() + ")");
            }
        }

        System.out.println("\n" + ticketsCreated + " sample support tickets created.");
    }

    private SupportTicket createRandomTicket(User user, User assignedStaff, Set<Car> cars, List<Booking> bookings) {
        TicketCategory category = getRandomCategory();
        TicketPriority priority = getRandomPriority(category);
        TicketStatus status = getRandomStatus(assignedStaff != null);

        String subject = generateSubject(category);
        String description = generateDescription(category);

        // Link to booking/car if relevant
        Booking relatedBooking = null;
        Car relatedCar = null;

        if (category == TicketCategory.BOOKING_ISSUE || category == TicketCategory.VEHICLE_PROBLEM) {
            // Try to find a booking for this user
            relatedBooking = bookings.stream()
                    .filter(b -> b.getUser() != null && b.getUser().getUserId().equals(user.getUserId()))
                    .findFirst()
                    .orElse(null);

            if (relatedBooking != null && category == TicketCategory.VEHICLE_PROBLEM) {
                relatedCar = relatedBooking.getCar();
            }
        }

        // Generate timestamps
        LocalDateTime createdAt = LocalDateTime.now().minusDays(RANDOM.nextInt(30));
        LocalDateTime firstResponseAt = null;
        LocalDateTime resolvedAt = null;
        LocalDateTime closedAt = null;

        if (assignedStaff != null && status != TicketStatus.OPEN) {
            firstResponseAt = createdAt.plusHours(RANDOM.nextInt(48) + 1);
        }

        if (status == TicketStatus.RESOLVED || status == TicketStatus.CLOSED) {
            resolvedAt = createdAt.plusDays(RANDOM.nextInt(5) + 1);
        }

        if (status == TicketStatus.CLOSED) {
            closedAt = resolvedAt != null ? resolvedAt.plusHours(RANDOM.nextInt(24) + 1) : createdAt.plusDays(7);
        }

        String resolution = (status == TicketStatus.RESOLVED || status == TicketStatus.CLOSED) ?
                generateResolution(category) : null;

        Integer satisfaction = (status == TicketStatus.CLOSED && RANDOM.nextInt(10) < 7) ?
                RANDOM.nextInt(3) + 3 : null; // 3-5 stars, 70% chance

        return new SupportTicket.Builder()
                .setUser(user)
                .setBooking(relatedBooking)
                .setCar(relatedCar)
                .setAssignedStaff(assignedStaff)
                .setSubject(subject)
                .setDescription(description)
                .setStatus(status)
                .setPriority(priority)
                .setCategory(category)
                .setContactEmail(user.getEmail())
                .setContactPhone(user.getPhoneNumber())
                .setResolution(resolution)
                .setCreatedAt(createdAt)
                .setFirstResponseAt(firstResponseAt)
                .setResolvedAt(resolvedAt)
                .setClosedAt(closedAt)
                .setSatisfactionRating(satisfaction)
                .build();
    }

    private TicketCategory getRandomCategory() {
        TicketCategory[] categories = TicketCategory.values();
        // Weight common categories more heavily
        int rand = RANDOM.nextInt(100);
        if (rand < 30) return TicketCategory.BOOKING_ISSUE;
        if (rand < 50) return TicketCategory.VEHICLE_PROBLEM;
        if (rand < 65) return TicketCategory.PAYMENT_ISSUE;
        if (rand < 75) return TicketCategory.ACCOUNT_ISSUE;
        if (rand < 85) return TicketCategory.TECHNICAL_SUPPORT;
        return categories[RANDOM.nextInt(categories.length)];
    }

    private TicketPriority getRandomPriority(TicketCategory category) {
        // Some categories naturally have higher priority
        if (category == TicketCategory.ACCIDENT_REPORT || category == TicketCategory.DAMAGE_REPORT) {
            return RANDOM.nextBoolean() ? TicketPriority.URGENT : TicketPriority.HIGH;
        }
        if (category == TicketCategory.VEHICLE_PROBLEM) {
            return RANDOM.nextInt(10) < 7 ? TicketPriority.HIGH : TicketPriority.MEDIUM;
        }

        int rand = RANDOM.nextInt(100);
        if (rand < 10) return TicketPriority.LOW;
        if (rand < 60) return TicketPriority.MEDIUM;
        if (rand < 85) return TicketPriority.HIGH;
        return TicketPriority.URGENT;
    }

    private TicketStatus getRandomStatus(boolean hasStaff) {
        if (!hasStaff) {
            return TicketStatus.OPEN;
        }

        int rand = RANDOM.nextInt(100);
        if (rand < 15) return TicketStatus.OPEN;
        if (rand < 25) return TicketStatus.ASSIGNED;
        if (rand < 45) return TicketStatus.IN_PROGRESS;
        if (rand < 55) return TicketStatus.WAITING_CUSTOMER;
        if (rand < 75) return TicketStatus.RESOLVED;
        return TicketStatus.CLOSED;
    }

    private String generateSubject(TicketCategory category) {
        Map<TicketCategory, String[]> subjects = new HashMap<>();

        subjects.put(TicketCategory.BOOKING_ISSUE, new String[]{
                "Unable to complete booking", "Booking confirmation not received",
                "Cannot modify my booking", "Booking shows wrong dates",
                "Payment processed but no booking confirmation"
        });

        subjects.put(TicketCategory.VEHICLE_PROBLEM, new String[]{
                "Car making unusual noise", "Check engine light is on",
                "Air conditioning not working", "Flat tire during rental",
                "Vehicle has mechanical issues", "Car won't start"
        });

        subjects.put(TicketCategory.PAYMENT_ISSUE, new String[]{
                "Incorrect charge on my card", "Deposit not refunded",
                "Double charged for rental", "Unable to process payment",
                "Billing discrepancy"
        });

        subjects.put(TicketCategory.ACCOUNT_ISSUE, new String[]{
                "Cannot log into my account", "Need to update my information",
                "Forgot password - reset not working", "Account locked"
        });

        subjects.put(TicketCategory.TECHNICAL_SUPPORT, new String[]{
                "Website not loading properly", "Mobile app crashing",
                "Cannot upload documents", "Error message when booking"
        });

        subjects.put(TicketCategory.FEEDBACK, new String[]{
                "Excellent service - thank you!", "Suggestion for improvement",
                "Great experience overall", "Positive feedback"
        });

        subjects.put(TicketCategory.COMPLAINT, new String[]{
                "Car was not clean", "Poor customer service",
                "Vehicle not as described", "Long wait time at pickup"
        });

        subjects.put(TicketCategory.DAMAGE_REPORT, new String[]{
                "Scratch on vehicle door", "Minor dent found",
                "Windshield chip", "Bumper damage"
        });

        subjects.put(TicketCategory.ACCIDENT_REPORT, new String[]{
                "Minor accident during rental", "Collision - need assistance",
                "Accident report needed", "Emergency - vehicle accident"
        });

        subjects.put(TicketCategory.LOST_PROPERTY, new String[]{
                "Left phone in vehicle", "Lost item in car",
                "Forgot sunglasses in rental", "Personal belongings left behind"
        });

        String[] options = subjects.getOrDefault(category, new String[]{"General inquiry"});
        return options[RANDOM.nextInt(options.length)];
    }

    private String generateDescription(TicketCategory category) {
        Map<TicketCategory, String[]> descriptions = new HashMap<>();

        descriptions.put(TicketCategory.BOOKING_ISSUE, new String[]{
                "I tried to complete my booking but the system keeps timing out. I've entered all my information multiple times but cannot finalize the reservation.",
                "I made a booking yesterday but haven't received any confirmation email. My payment went through but I have no booking reference number.",
                "I need to change the pickup date for my reservation but the modify option is not working on the website."
        });

        descriptions.put(TicketCategory.VEHICLE_PROBLEM, new String[]{
                "The vehicle is making a strange rattling noise when I accelerate. It started about an hour into my rental. Should I bring it in?",
                "The check engine light came on while I was driving. The car seems to be running fine but I'm concerned. What should I do?",
                "The air conditioning in the car is not working. It's very hot today and this is uncomfortable for my family."
        });

        descriptions.put(TicketCategory.PAYMENT_ISSUE, new String[]{
                "I was charged twice for the same rental. I can see two identical charges on my credit card statement. Please refund the duplicate charge.",
                "I returned the vehicle 3 weeks ago but my deposit hasn't been refunded yet. Can you please check on this?",
                "The final charge is different from the quote I received when booking. Can you explain the additional charges?"
        });

        String[] options = descriptions.getOrDefault(category,
                new String[]{"This is a general inquiry regarding my rental experience."});
        return options[RANDOM.nextInt(options.length)];
    }

    private String generateResolution(TicketCategory category) {
        String[] resolutions = {
                "Issue has been resolved. We've processed the necessary adjustments to your account.",
                "Problem fixed. Our technical team has addressed the issue and everything should be working now.",
                "Resolved: We've issued a refund/credit to your account. Please allow 3-5 business days for processing.",
                "Completed: We've updated your booking/account as requested. Please verify the changes.",
                "Issue resolved: Vehicle has been inspected and repaired. We apologize for the inconvenience.",
                "Closed: This has been handled by our team. Thank you for bringing this to our attention."
        };
        return resolutions[RANDOM.nextInt(resolutions.length)];
    }

    private void displayTicketSummary() {
        List<SupportTicket> allTickets = ticketRepository.findAll();

        // Status counts
        Map<TicketStatus, Long> statusCounts = new EnumMap<>(TicketStatus.class);
        for (TicketStatus status : TicketStatus.values()) {
            statusCounts.put(status, ticketRepository.countByStatus(status));
        }

        // Priority counts
        long highPriority = allTickets.stream().filter(t ->
                t.getPriority() == TicketPriority.HIGH ||
                t.getPriority() == TicketPriority.URGENT ||
                t.getPriority() == TicketPriority.EMERGENCY).count();

        // Satisfaction
        Double avgSatisfaction = ticketRepository.getAverageSatisfactionRating();

        // Assigned vs unassigned
        long assigned = allTickets.stream().filter(t -> t.getAssignedStaff() != null).count();
        long unassigned = allTickets.size() - assigned;

        System.out.println("Support Ticket Summary:");
        System.out.println("- Total Tickets: " + allTickets.size());
        System.out.println("\nBy Status:");
        statusCounts.forEach((status, count) -> {
            if (count > 0) {
                System.out.println("  - " + status + ": " + count);
            }
        });
        System.out.println("\nBy Priority:");
        System.out.println("  - High/Urgent/Emergency: " + highPriority);
        System.out.println("  - Medium/Low: " + (allTickets.size() - highPriority));
        System.out.println("\nAssignment:");
        System.out.println("  - Assigned: " + assigned);
        System.out.println("  - Unassigned: " + unassigned);
        if (avgSatisfaction != null) {
            System.out.println("\nAverage Satisfaction: " + String.format("%.2f", avgSatisfaction) + "/5.0");
        }
    }
}
