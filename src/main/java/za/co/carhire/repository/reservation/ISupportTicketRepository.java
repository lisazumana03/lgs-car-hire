package za.co.carhire.repository.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 16 October 2025 - Enhanced with comprehensive query methods
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.domain.vehicle.Car;

import java.util.List;
import java.util.Optional;

public interface ISupportTicketRepository extends JpaRepository<SupportTicket, Integer> {

    // Basic queries
    Optional<SupportTicket> findById(int ticketID);
    List<SupportTicket> findAll();

    // Filter by user
    List<SupportTicket> findByUser(User user);
    List<SupportTicket> findByUserUserId(Integer userId);

    // Filter by status
    List<SupportTicket> findByStatus(TicketStatus status);
    List<SupportTicket> findByStatusIn(List<TicketStatus> statuses);

    // Filter by priority
    List<SupportTicket> findByPriority(TicketPriority priority);

    // Filter by category
    List<SupportTicket> findByCategory(TicketCategory category);

    // Filter by assigned staff
    List<SupportTicket> findByAssignedStaff(User staff);
    List<SupportTicket> findByAssignedStaffUserId(Integer staffId);

    // Filter by booking
    List<SupportTicket> findByBooking(Booking booking);
    List<SupportTicket> findByBookingBookingID(int bookingId);

    // Filter by car
    List<SupportTicket> findByCar(Car car);
    List<SupportTicket> findByCarCarID(int carId);

    // Complex queries with sorting
    @Query("SELECT t FROM SupportTicket t WHERE t.status IN :statuses ORDER BY t.priority DESC, t.createdAt ASC")
    List<SupportTicket> findActiveTicketsByPriority(@Param("statuses") List<TicketStatus> statuses);

    @Query("SELECT t FROM SupportTicket t WHERE t.assignedStaff = :staff AND t.status NOT IN ('CLOSED', 'CANCELLED') ORDER BY t.priority DESC")
    List<SupportTicket> findOpenTicketsByStaff(@Param("staff") User staff);

    // Count queries
    @Query("SELECT COUNT(t) FROM SupportTicket t WHERE t.assignedStaff = :staff AND t.status NOT IN ('CLOSED', 'CANCELLED')")
    Long countOpenTicketsByStaff(@Param("staff") User staff);

    @Query("SELECT COUNT(t) FROM SupportTicket t WHERE t.status = :status")
    Long countByStatus(@Param("status") TicketStatus status);

    @Query("SELECT COUNT(t) FROM SupportTicket t WHERE t.user = :user AND t.status NOT IN ('CLOSED', 'CANCELLED')")
    Long countOpenTicketsByUser(@Param("user") User user);

    // Statistics queries
    @Query("SELECT AVG(t.satisfactionRating) FROM SupportTicket t WHERE t.satisfactionRating IS NOT NULL")
    Double getAverageSatisfactionRating();

    @Query("SELECT AVG(TIMESTAMPDIFF(HOUR, t.createdAt, t.resolvedAt)) FROM SupportTicket t WHERE t.resolvedAt IS NOT NULL")
    Double getAverageResolutionTimeInHours();

    @Query("SELECT t.category, COUNT(t) FROM SupportTicket t GROUP BY t.category ORDER BY COUNT(t) DESC")
    List<Object[]> getTicketCountByCategory();

    // Unassigned tickets
    @Query("SELECT t FROM SupportTicket t WHERE t.assignedStaff IS NULL AND t.status = 'OPEN' ORDER BY t.priority DESC, t.createdAt ASC")
    List<SupportTicket> findUnassignedOpenTickets();

    // Overdue tickets (no response within 24 hours)
    @Query("SELECT t FROM SupportTicket t WHERE t.firstResponseAt IS NULL AND TIMESTAMPDIFF(HOUR, t.createdAt, CURRENT_TIMESTAMP) > 24 AND t.status NOT IN ('CLOSED', 'CANCELLED')")
    List<SupportTicket> findOverdueTickets();
}
