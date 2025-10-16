package za.co.carhire.domain.reservation;

/**
 * Payment methods supported by the car hire system
 * Updated: Added PayFast payment method (South African payment gateway)
 */
public enum PaymentMethod {
    PAYFAST,         // PayFast payment gateway (South African payment processor)
    CREDIT_CARD,     // Direct credit card payment
    DEBIT_CARD,      // Direct debit card payment
    EFT,             // Electronic Funds Transfer
    INSTANT_EFT,     // Instant EFT (via PayFast)
    CASH             // Cash payment
}
