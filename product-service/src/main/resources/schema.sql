DROP TABLE IF EXISTS tbl_products CASCADE;

CREATE TABLE tbl_products
(
    product_id  BIGINT  NOT NULL AUTO_INCREMENT,
    name        VARCHAR NOT NULL,
    description TEXT             DEFAULT NULL,
    price       DOUBLE  NOT NULL DEFAULT 0.0,
    PRIMARY KEY (product_id)
);