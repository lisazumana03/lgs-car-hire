-- Create database if it doesn't exist and use it
CREATE DATABASE IF NOT EXISTS lgrentalsdb_new;
USE lgrentalsdb_new;

-- Disable foreign key checks to allow dropping tables in any order
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables with foreign key dependencies first (in reverse order of creation)
DROP TABLE IF EXISTS support_ticket;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS Maintenance;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS insurance;
DROP TABLE IF EXISTS pricing_rule;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS car_type;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS users;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create base tables first (no dependencies)

-- Users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    id_number BIGINT NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    license_number VARCHAR(50),
    role VARCHAR(50) NOT NULL
);

-- Location table
CREATE TABLE location (
    locationid INT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(255),
    street_number INT,
    street_name VARCHAR(255),
    city_or_town VARCHAR(255),
    province_or_state VARCHAR(255),
    postal_code VARCHAR(20),
    country VARCHAR(255)
);

-- CarType table
CREATE TABLE car_type (
    car_typeid INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    fuel_type VARCHAR(50) NOT NULL,
    transmission_type VARCHAR(50) NOT NULL,
    number_of_seats INT NOT NULL,
    number_of_doors INT,
    air_conditioned BOOLEAN NOT NULL,
    luggage_capacity INT,
    description VARCHAR(500)
);

-- Car table (depends on car_type and location)
CREATE TABLE car (
    carid INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    year INT,
    license_plate VARCHAR(255) UNIQUE,
    vin VARCHAR(255) UNIQUE,
    color VARCHAR(255),
    mileage INT,
    status VARCHAR(50),
    `condition` VARCHAR(50),
    image_data LONGBLOB,
    image_name VARCHAR(255),
    image_type VARCHAR(255),
    car_typeid INT,
    current_location_id INT,
    CONSTRAINT fk_car_cartype FOREIGN KEY (car_typeid) REFERENCES car_type(car_typeid),
    CONSTRAINT fk_car_location FOREIGN KEY (current_location_id) REFERENCES location(locationid)
);

-- Pricing Rule table
CREATE TABLE pricing_rule (
    pricing_rule_id INT AUTO_INCREMENT PRIMARY KEY,
    car_type_id INT NOT NULL,
    base_daily_rate DOUBLE NOT NULL,
    weekend_rate DOUBLE,
    weekly_rate DOUBLE,
    monthly_rate DOUBLE,
    seasonal_multiplier DOUBLE,
    valid_from DATE,
    valid_to DATE,
    active BOOLEAN,
    CONSTRAINT fk_pricing_cartype FOREIGN KEY (car_type_id) REFERENCES car_type(car_typeid)
);

-- Insurance table (must be created before booking)
CREATE TABLE insurance (
    insuranceid INT AUTO_INCREMENT PRIMARY KEY,
    insurance_start_date DATETIME,
    insurance_end_date DATETIME,
    insurance_cost DOUBLE,
    insurance_provider VARCHAR(255),
    coverage_type VARCHAR(50),
    deductible DOUBLE,
    policy_number VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE
);

-- Booking table
CREATE TABLE booking (
    bookingid INT AUTO_INCREMENT PRIMARY KEY,
    version BIGINT,
    user_id INT,
    car_id INT,
    booking_date_and_time DATETIME,
    start_date DATETIME,
    end_date DATETIME,
    pickup_location_id INT,
    dropoff_location_id INT,
    insurance_id INT,
    booking_status VARCHAR(50),
    rental_days INT,
    subtotal DOUBLE,
    tax_amount DOUBLE,
    discount_amount DOUBLE,
    total_amount DOUBLE,
    currency VARCHAR(10) DEFAULT 'ZAR',
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_booking_car FOREIGN KEY (car_id) REFERENCES car(carid),
    CONSTRAINT fk_booking_pickup FOREIGN KEY (pickup_location_id) REFERENCES location(locationid),
    CONSTRAINT fk_booking_dropoff FOREIGN KEY (dropoff_location_id) REFERENCES location(locationid),
    CONSTRAINT fk_booking_insurance FOREIGN KEY (insurance_id) REFERENCES insurance(insuranceid)
);

-- Payments table (PayFast integration - South African payment gateway)
CREATE TABLE payments (
    paymentid INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL UNIQUE,
    amount DOUBLE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) NOT NULL,
    transaction_reference VARCHAR(255),
    payment_date TIMESTAMP,
    currency VARCHAR(3) DEFAULT 'ZAR',
    -- PayFast specific fields
    payfast_payment_id VARCHAR(255) UNIQUE,
    payfast_transaction_id VARCHAR(255),
    merchant_id VARCHAR(255),
    -- Failure tracking
    failure_reason VARCHAR(500),
    -- Refund fields
    refund_amount DOUBLE,
    refund_date TIMESTAMP,
    refund_reason VARCHAR(500),
    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES booking(bookingid),
    INDEX idx_payments_payfast_id (payfast_payment_id),
    INDEX idx_payments_status (payment_status),
    INDEX idx_payments_date (payment_date)
);

-- Invoices table
CREATE TABLE invoices (
    invoiceid INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    payment_id INT NOT NULL,
    issue_date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    sub_total DOUBLE NOT NULL,
    tax_amount DOUBLE NOT NULL,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_invoice_booking FOREIGN KEY (booking_id) REFERENCES booking(bookingid),
    CONSTRAINT fk_invoice_payment FOREIGN KEY (payment_id) REFERENCES payments(paymentid)
);

-- Maintenance table
CREATE TABLE Maintenance (
    maintenanceid INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT NOT NULL,
    maintenance_date DATE NOT NULL,
    service_type VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    cost DOUBLE NOT NULL,
    mileage_at_service INT NOT NULL,
    next_service_date DATE,
    next_service_mileage INT,
    mechanic_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_maintenance_car FOREIGN KEY (car_id) REFERENCES car(carid) ON DELETE CASCADE
);

-- Review table (enhanced with user and booking relationships)
CREATE TABLE review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT NOT NULL,
    user_id INT NOT NULL,
    booking_id INT,
    rating INT NOT NULL,
    title VARCHAR(255),
    comment TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_verified BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_review_car FOREIGN KEY (car_id) REFERENCES car(carid) ON DELETE CASCADE,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_review_booking FOREIGN KEY (booking_id) REFERENCES booking(bookingid) ON DELETE SET NULL
);

-- Notifications table
CREATE TABLE notifications (
    notificationid INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    message TEXT,
    date_sent DATETIME,
    read_status BOOLEAN,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Support Ticket table (enhanced)
CREATE TABLE support_ticket (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    booking_id INT,
    car_id INT,
    assigned_to INT,
    subject VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    category VARCHAR(50) NOT NULL,
    contact_email VARCHAR(255),
    contact_phone VARCHAR(50),
    resolution TEXT,
    internal_notes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    resolved_at DATETIME,
    closed_at DATETIME,
    first_response_at DATETIME,
    satisfaction_rating INT,
    satisfaction_comment VARCHAR(500),
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id)
        REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_booking FOREIGN KEY (booking_id)
        REFERENCES booking(bookingid) ON DELETE SET NULL,
    CONSTRAINT fk_ticket_car FOREIGN KEY (car_id)
        REFERENCES car(carid) ON DELETE SET NULL,
    CONSTRAINT fk_ticket_assigned FOREIGN KEY (assigned_to)
        REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_status (status),
    INDEX idx_priority (priority),
    INDEX idx_created_at (created_at),
    INDEX idx_user_id (user_id)
);
