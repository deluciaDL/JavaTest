DROP TABLE IF EXISTS PRICES;
CREATE TABLE PRICES (
                        ID INT AUTO_INCREMENT  PRIMARY KEY,
                        START_DATE TIMESTAMP NOT NULL,
                        END_DATE TIMESTAMP NOT NULL,
                        BRAND_ID INT NOT NULL,
                        PRODUCT_ID INT NOT NULL,
                        FEE_ID INT NOT NULL,
                        PRIORITY INT NOT NULL,
                        PRICE DOUBLE PRECISION NOT NULL,
                        CURRENCY VARCHAR(10)
);