package za.co.carhire.controller.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.SupportTicket;
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
    public ResponseEntity<SupportTicket> read(@PathVariable int id) {
        SupportTicket ticket = ticketService.read(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<SupportTicket> update(@RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(ticketService.update(ticket));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Set<SupportTicket>> getAll() {
        Set<SupportTicket> reviews = ticketService.getTickets();
        return ResponseEntity.ok(reviews);
    }
}
