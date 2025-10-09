package za.co.carhire.controller.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 09 October 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketStatus;
import za.co.carhire.service.reservation.ISupportTicketService;

import java.util.Set;

@RestController
@RequestMapping("/support")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class SupportTicketController {

    @Autowired
    private ISupportTicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<SupportTicket> create(@RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(ticketService.create(ticket));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SupportTicket> read(@PathVariable Integer id) {
        SupportTicket ticket = ticketService.read(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<SupportTicket> update(@RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(ticketService.update(ticket));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Set<SupportTicket>> getAll() {
        Set<SupportTicket> tickets = ticketService.getTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<SupportTicket>> getByUser(@PathVariable Integer userId) {
        Set<SupportTicket> tickets = ticketService.getTicketsByUser(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Set<SupportTicket>> getByStatus(@PathVariable TicketStatus status) {
        Set<SupportTicket> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Set<SupportTicket>> getByBooking(@PathVariable Integer bookingId) {
        Set<SupportTicket> tickets = ticketService.getTicketsByBooking(bookingId);
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SupportTicket> updateStatus(@PathVariable Integer id,
                                                       @RequestParam TicketStatus status) {
        SupportTicket ticket = ticketService.updateStatus(id, status);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<SupportTicket> assignTicket(@PathVariable Integer id,
                                                       @RequestParam Integer assignedToUserId) {
        SupportTicket ticket = ticketService.assignTicket(id, assignedToUserId);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<SupportTicket> resolveTicket(@PathVariable Integer id) {
        SupportTicket ticket = ticketService.resolveTicket(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
}
