-- Check if the PRICES table exists and create it if it does not
CREATE TABLE IF NOT EXISTS PRICES (
                                      ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      BRAND_ID INT,
                                      START_DATE TIMESTAMP,
                                      END_DATE TIMESTAMP,
                                      PRICE_LIST INT,
                                      PRODUCT_ID BIGINT,
                                      PRIORITY INT,
                                      PRICE DECIMAL(10, 2),
                                      CURR VARCHAR(3)
);

-- Insert initial data if the table is created
INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR)
VALUES (1, '2024-09-10T00:00:00', '2024-09-10T23:59:59', 1, 35455, 1, 35.50, 'EUR');