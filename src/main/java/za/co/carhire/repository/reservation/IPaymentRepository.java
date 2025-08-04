//package za.co.carhire.repository.reservation;
//
///* IPaymentRepository.java
// * Sanele Zondi (221602011)
// * Due Date: 25/05/2025
// * */
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import za.co.carhire.domain.reservation.*;
//
//import java.util.List;
//
//@Repository
//public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
//    List<Payment> findByBooking(Booking booking);
//    List<Payment> findByPaymentMethod(String paymentMethod);
//    List<Payment> findById(int paymentID);
//}