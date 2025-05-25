package za.co.carhire.service.impl.reservation;

/* PaymentService.java
 * Sanele Zondi (221602011)
 * Due Date: 25/05/2025
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.repository.reservation.IPaymentRepository;
import za.co.carhire.service.reservation.IPaymentService;

import java.util.Set;

@Service
public abstract class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public Set<Payment> getPayments() {
        return Set.of();
    }

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(Integer paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public Payment update(Payment payment) {
        if (paymentRepository.existsById(payment.getPaymentID())) {
            return paymentRepository.save(payment);
        }
        return null;
    }

    @Override
    public void delete(Integer paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}


