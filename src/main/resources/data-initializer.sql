-- Select the database
USE lgrentalsdb;

-- Note: Passwords are BCrypt encoded
-- Admin password: admin123
-- Customer password: customer123

INSERT INTO users (user_id, id_number, first_name, last_name, email, date_of_birth, phone_number, password, role)
VALUES
(1, 9001015800082, 'Admin', 'User', 'admin@carhire.co.za', '1990-01-15', '0211234567',
 '$2a$10$YourBCryptHashHereForAdmin123', 'ADMIN'),
(2, 9505201234088, 'John', 'Doe', 'customer@example.com', '1995-05-20', '0829876543',
 '$2a$10$YourBCryptHashHereForCustomer123', 'CUSTOMER');

INSERT INTO car_type (car_typeid, type, fuel_type, number_of_wheels, number_of_seats)
VALUES
(1, 'Economy', 'Petrol', 4, 5),
(2, 'Sedan', 'Petrol', 4, 5),
(3, 'SUV', 'Petrol', 4, 7),
(4, 'Luxury', 'Petrol', 4, 5),
(5, 'Sports', 'Petrol', 4, 2),
(6, 'Convertible', 'Petrol', 4, 2),
(7, 'Minivan', 'Petrol', 4, 8),
(8, 'Electric', 'Electric', 4, 5),
(9, 'Hybrid', 'Hybrid', 4, 5);

-- Economy Cars (car_typeid = 1)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(1, 'Corolla', 'Toyota', 2022, 350.0, true, 1),
(2, 'Civic', 'Honda', 2023, 380.0, true, 1),
(3, 'Sentra', 'Nissan', 2022, 320.0, true, 1),
(4, 'Elantra', 'Hyundai', 2023, 340.0, true, 1);

-- Sedan Cars (car_typeid = 2)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(5, 'Camry', 'Toyota', 2023, 450.0, true, 2),
(6, 'Accord', 'Honda', 2022, 480.0, true, 2),
(7, 'Altima', 'Nissan', 2023, 420.0, false, 2),
(8, 'Sonata', 'Hyundai', 2022, 410.0, true, 2);

-- SUV Cars (car_typeid = 3)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(9, 'RAV4', 'Toyota', 2023, 550.0, true, 3),
(10, 'CR-V', 'Honda', 2022, 580.0, true, 3),
(11, 'X5', 'BMW', 2023, 850.0, true, 3),
(12, 'Explorer', 'Ford', 2022, 620.0, false, 3),
(13, 'Grand Cherokee', 'Jeep', 2023, 650.0, true, 3);
-- Luxury Cars (car_typeid = 4)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(14, 'S-Class', 'Mercedes-Benz', 2023, 1200.0, true, 4),
(15, '7 Series', 'BMW', 2023, 1100.0, true, 4),
(16, 'A8', 'Audi', 2022, 1050.0, false, 4),
(17, 'Continental', 'Bentley', 2023, 2500.0, true, 4);
-- Sports Cars (car_typeid = 5)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(18, '911', 'Porsche', 2023, 1500.0, true, 5),
(19, 'Corvette', 'Chevrolet', 2023, 900.0, true, 5),
(20, 'GT-R', 'Nissan', 2022, 1100.0, false, 5),
(21, 'Supra', 'Toyota', 2023, 850.0, true, 5);
-- Convertible Cars (car_typeid = 6)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(22, 'Miata', 'Mazda', 2023, 600.0, true, 6),
(23, 'Z4', 'BMW', 2022, 950.0, true, 6),
(24, 'Mustang Convertible', 'Ford', 2023, 700.0, true, 6);
-- Minivan Cars (car_typeid = 7)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(25, 'Sienna', 'Toyota', 2023, 580.0, true, 7),
(26, 'Odyssey', 'Honda', 2022, 560.0, true, 7),
(27, 'Pacifica', 'Chrysler', 2023, 590.0, false, 7);
-- Electric Cars (car_typeid = 8)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(28, 'Model 3', 'Tesla', 2023, 800.0, true, 8),
(29, 'Model Y', 'Tesla', 2023, 900.0, true, 8),
(30, 'ID.4', 'Volkswagen', 2022, 650.0, true, 8);
-- Hybrid Cars (car_typeid = 9)
INSERT INTO car (carid, model, brand, year, rental_price, availability, car_typeid)
VALUES
(31, 'Prius', 'Toyota', 2023, 400.0, true, 9),
(32, 'Insight', 'Honda', 2022, 380.0, true, 9);

