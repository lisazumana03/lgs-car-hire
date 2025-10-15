-- Drop tables with foreign key dependencies first
DROP TABLE IF EXISTS booking_car;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS insurance;
DROP TABLE IF EXISTS maintenance;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS pricing_rule;
DROP TABLE IF EXISTS car_type;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS support_ticket;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS location;

-- Create base tables first (no dependencies)

-- Users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone_number VARCHAR(50),
    date_of_birth DATE,
    id_number VARCHAR(50),
    license_number VARCHAR(50),
    role VARCHAR(50)
);

-- Location table
CREATE TABLE location (
    locationid INT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(255),
    street_number VARCHAR(50),
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
    base_daily_rate DOUBLE,
    weekend_rate DOUBLE,
    weekly_rate DOUBLE,
    monthly_rate DOUBLE,
    seasonal_multiplier DOUBLE,
    valid_from DATE,
    valid_to DATE,
    active BOOLEAN,
    CONSTRAINT fk_pricing_cartype FOREIGN KEY (car_type_id) REFERENCES car_type(car_typeid)
);

-- Booking table
CREATE TABLE booking (
    bookingid INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    booking_date_and_time DATETIME,
    start_date DATETIME,
    end_date DATETIME,
    pickup_location_id INT,
    dropoff_location_id INT,
    booking_status VARCHAR(50),
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_booking_pickup FOREIGN KEY (pickup_location_id) REFERENCES location(locationid),
    CONSTRAINT fk_booking_dropoff FOREIGN KEY (dropoff_location_id) REFERENCES location(locationid)
);

-- Booking-Car join table (many-to-many)
CREATE TABLE booking_car (
    booking_id INT NOT NULL,
    car_id INT NOT NULL,
    PRIMARY KEY (booking_id, car_id),
    CONSTRAINT fk_bookingcar_booking FOREIGN KEY (booking_id) REFERENCES booking(bookingid),
    CONSTRAINT fk_bookingcar_car FOREIGN KEY (car_id) REFERENCES car(carid)
);

-- Payments table
CREATE TABLE payments (
    paymentid INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    amount DOUBLE,
    payment_method VARCHAR(50),
    payment_status VARCHAR(50),
    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES booking(bookingid)
);

-- Invoices table
CREATE TABLE invoices (
    invoiceid INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    payment_id INT,
    issue_date DATE,
    due_date DATE,
    sub_total DOUBLE,
    tax_amount DOUBLE,
    total_amount DOUBLE,
    status VARCHAR(50),
    CONSTRAINT fk_invoice_booking FOREIGN KEY (booking_id) REFERENCES booking(bookingid),
    CONSTRAINT fk_invoice_payment FOREIGN KEY (payment_id) REFERENCES payments(paymentid)
);

-- Insurance table
CREATE TABLE insurance (
    insuranceid INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT,
    policy_number VARCHAR(100),
    insurance_provider VARCHAR(255),
    insurance_start_date DATE,
    insurance_cost DOUBLE,
    status VARCHAR(50),
    mechanic VARCHAR(255),
    CONSTRAINT fk_insurance_car FOREIGN KEY (car_id) REFERENCES car(carid)
);

-- Maintenance table
CREATE TABLE maintenance (
    maintenanceid INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT,
    maintenance_date DATE,
    description TEXT,
    cost DOUBLE,
    mechanic_name VARCHAR(255),
    CONSTRAINT fk_maintenance_car FOREIGN KEY (car_id) REFERENCES car(carid)
);

-- Review table
CREATE TABLE review (
    reviewid INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT,
    full_name VARCHAR(255),
    rating INT,
    comment TEXT,
    CONSTRAINT fk_review_car FOREIGN KEY (car_id) REFERENCES car(carid)
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

-- Support Ticket table
CREATE TABLE support_ticket (
    ticketid INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    subject VARCHAR(255),
    description TEXT,
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);
