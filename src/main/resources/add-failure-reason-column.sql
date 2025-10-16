-- Add missing failure_reason column to payments table
USE lgrentalsdb_new;

ALTER TABLE payments
ADD COLUMN failure_reason VARCHAR(500) AFTER merchant_id;
