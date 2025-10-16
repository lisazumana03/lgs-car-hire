-- Add map-based location support
-- Add latitude, longitude, and fullAddress columns to location table

ALTER TABLE location
ADD COLUMN latitude DOUBLE,
ADD COLUMN longitude DOUBLE,
ADD COLUMN full_address VARCHAR(500);

-- Make location foreign keys nullable in booking table (if not already)
ALTER TABLE booking
MODIFY COLUMN pickup_location_id INT NULL,
MODIFY COLUMN dropoff_location_id INT NULL;

-- Add comments to columns for documentation
ALTER TABLE location
MODIFY COLUMN latitude DOUBLE COMMENT 'Latitude coordinate from Google Maps',
MODIFY COLUMN longitude DOUBLE COMMENT 'Longitude coordinate from Google Maps',
MODIFY COLUMN full_address VARCHAR(500) COMMENT 'Full formatted address from Google Maps';

