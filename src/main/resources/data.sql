-- LG Rentals Car Hire Database Initial Data
-- This file should be placed in src/main/resources/
-- Spring Boot will automatically execute this on startup if spring.sql.init.mode=always

-- Insert Car Types
INSERT INTO car_type (car_type_id, type, fuel_type, number_of_wheels, number_of_seats) VALUES
(1, 'Economy', 'Petrol', 4, 5),
(2, 'Sedan', 'Petrol', 4, 5),
(3, 'SUV', 'Petrol', 4, 7),
(4, 'Luxury', 'Petrol', 4, 5),
(5, 'Sports', 'Petrol', 4, 2),
(6, 'Convertible', 'Petrol', 4, 2),
(7, 'Minivan', 'Petrol', 4, 8),
(8, 'Electric', 'Electric', 4, 5),
(9, 'Hybrid', 'Hybrid', 4, 5),
(10, 'Motorcycle', 'Petrol', 2, 2);

-- Insert Cars
-- Economy Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('Corolla', 'Toyota', 2022, true, 350.0, 'https://example.com/images/toyota-corolla.jpg'),
('Civic', 'Honda', 2023, true, 380.0, 'https://example.com/images/honda-civic.jpg'),
('Sentra', 'Nissan', 2022, true, 320.0, 'https://example.com/images/nissan-sentra.jpg'),
('Elantra', 'Hyundai', 2023, true, 340.0, 'https://example.com/images/hyundai-elantra.jpg');

-- Sedan Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('Camry', 'Toyota', 2023, true, 450.0, 'https://example.com/images/toyota-camry.jpg'),
('Accord', 'Honda', 2022, true, 480.0, 'https://example.com/images/honda-accord.jpg'),
('Altima', 'Nissan', 2023, false, 420.0, 'https://example.com/images/nissan-altima.jpg'),
('Sonata', 'Hyundai', 2022, true, 410.0, 'https://example.com/images/hyundai-sonata.jpg'),
('Malibu', 'Chevrolet', 2023, true, 430.0, 'https://example.com/images/chevrolet-malibu.jpg');

-- SUV Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('RAV4', 'Toyota', 2023, true, 550.0, 'https://example.com/images/toyota-rav4.jpg'),
('CR-V', 'Honda', 2022, true, 580.0, 'https://example.com/images/honda-crv.jpg'),
('X5', 'BMW', 2023, true, 850.0, 'https://example.com/images/bmw-x5.jpg'),
('Explorer', 'Ford', 2022, false, 620.0, 'https://example.com/images/ford-explorer.jpg'),
('Grand Cherokee', 'Jeep', 2023, true, 650.0, 'https://example.com/images/jeep-grand-cherokee.jpg'),
('Highlander', 'Toyota', 2023, true, 600.0, 'https://example.com/images/toyota-highlander.jpg');

-- Luxury Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('S-Class', 'Mercedes-Benz', 2023, true, 1200.0, 'https://example.com/images/mercedes-s-class.jpg'),
('7 Series', 'BMW', 2023, true, 1100.0, 'https://example.com/images/bmw-7-series.jpg'),
('A8', 'Audi', 2022, false, 1050.0, 'https://example.com/images/audi-a8.jpg'),
('Continental', 'Bentley', 2023, true, 2500.0, 'https://example.com/images/bentley-continental.jpg'),
('LS', 'Lexus', 2023, true, 950.0, 'https://example.com/images/lexus-ls.jpg');

-- Sports Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('911', 'Porsche', 2023, true, 1500.0, 'https://example.com/images/porsche-911.jpg'),
('Corvette', 'Chevrolet', 2023, true, 900.0, 'https://example.com/images/chevrolet-corvette.jpg'),
('GT-R', 'Nissan', 2022, false, 1100.0, 'https://example.com/images/nissan-gtr.jpg'),
('Supra', 'Toyota', 2023, true, 850.0, 'https://example.com/images/toyota-supra.jpg'),
('M4', 'BMW', 2023, true, 950.0, 'https://example.com/images/bmw-m4.jpg');

-- Convertible Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('Miata', 'Mazda', 2023, true, 600.0, 'https://example.com/images/mazda-miata.jpg'),
('Z4', 'BMW', 2022, true, 950.0, 'https://example.com/images/bmw-z4.jpg'),
('Mustang Convertible', 'Ford', 2023, true, 700.0, 'https://example.com/images/ford-mustang-convertible.jpg'),
('Boxster', 'Porsche', 2023, false, 1200.0, 'https://example.com/images/porsche-boxster.jpg');

-- Minivan Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('Sienna', 'Toyota', 2023, true, 580.0, 'https://example.com/images/toyota-sienna.jpg'),
('Odyssey', 'Honda', 2022, true, 560.0, 'https://example.com/images/honda-odyssey.jpg'),
('Pacifica', 'Chrysler', 2023, false, 590.0, 'https://example.com/images/chrysler-pacifica.jpg'),
('Carnival', 'Kia', 2023, true, 550.0, 'https://example.com/images/kia-carnival.jpg');

-- Electric Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('Model 3', 'Tesla', 2023, true, 800.0, 'https://example.com/images/tesla-model3.jpg'),
('Model Y', 'Tesla', 2023, true, 900.0, 'https://example.com/images/tesla-modely.jpg'),
('ID.4', 'Volkswagen', 2022, true, 650.0, 'https://example.com/images/vw-id4.jpg'),
('Leaf', 'Nissan', 2023, true, 450.0, 'https://example.com/images/nissan-leaf.jpg'),
('Mach-E', 'Ford', 2023, false, 750.0, 'https://example.com/images/ford-mache.jpg'),
('EV6', 'Kia', 2023, true, 700.0, 'https://example.com/images/kia-ev6.jpg');

-- Hybrid Cars
INSERT INTO car (model, brand, year, availability, rental_price, image_url) VALUES
('Prius', 'Toyota', 2023, true, 400.0, 'https://example.com/images/toyota-prius.jpg'),
('Insight', 'Honda', 2022, true, 380.0, 'https://example.com/images/honda-insight.jpg'),
('Ioniq', 'Hyundai', 2023, true, 390.0, 'https://example.com/images/hyundai-ioniq.jpg'),
('Camry Hybrid', 'Toyota', 2023, true, 480.0, 'https://example.com/images/toyota-camry-hybrid.jpg'),
('Accord Hybrid', 'Honda', 2023, false, 500.0, 'https://example.com/images/honda-accord-hybrid.jpg');

-- Update Cars with CarType relationships
-- Note: Depending on your JPA mapping strategy, you might need to adjust these updates

-- Associate Economy cars (car_id 1-4 with car_type_id 1)
UPDATE car_type SET car_id = 1 WHERE car_type_id = 1 AND car_id IS NULL LIMIT 1;

-- Associate Sedan cars (car_id 5-9 with car_type_id 2)
UPDATE car_type SET car_id = 5 WHERE car_type_id = 2 AND car_id IS NULL LIMIT 1;

-- Associate SUV cars (car_id 10-15 with car_type_id 3)
UPDATE car_type SET car_id = 10 WHERE car_type_id = 3 AND car_id IS NULL LIMIT 1;

-- Associate Luxury cars (car_id 16-20 with car_type_id 4)
UPDATE car_type SET car_id = 16 WHERE car_type_id = 4 AND car_id IS NULL LIMIT 1;

-- Associate Sports cars (car_id 21-25 with car_type_id 5)
UPDATE car_type SET car_id = 21 WHERE car_type_id = 5 AND car_id IS NULL LIMIT 1;

-- Associate Convertible cars (car_id 26-29 with car_type_id 6)
UPDATE car_type SET car_id = 26 WHERE car_type_id = 6 AND car_id IS NULL LIMIT 1;

-- Associate Minivan cars (car_id 30-33 with car_type_id 7)
UPDATE car_type SET car_id = 30 WHERE car_type_id = 7 AND car_id IS NULL LIMIT 1;

-- Associate Electric cars (car_id 34-39 with car_type_id 8)
UPDATE car_type SET car_id = 34 WHERE car_type_id = 8 AND car_id IS NULL LIMIT 1;

-- Associate Hybrid cars (car_id 40-44 with car_type_id 9)
UPDATE car_type SET car_id = 40 WHERE car_type_id = 9 AND car_id IS NULL LIMIT 1;