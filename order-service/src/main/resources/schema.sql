DROP TABLE IF EXISTS tbl_orders CASCADE;
DROP TABLE IF EXISTS tbl_order_line_items CASCADE;

CREATE TABLE tbl_orders
(
    order_id     BIGINT  NOT NULL AUTO_INCREMENT,
    order_number VARCHAR NOT NULL,
    PRIMARY KEY (order_id)
);

CREATE TABLE tbl_order_line_items
(
    order_line_items_id BIGINT  NOT NULL AUTO_INCREMENT,
    order_id            BIGINT  NOT NULL,
    sku_code            VARCHAR NOT NULL,
    price               DOUBLE  NOT NULL DEFAULT 0.0,
    quantity            INT     NOT NULL DEFAULT 0,
    PRIMARY KEY (order_line_items_id),
    FOREIGN KEY (order_id) REFERENCES tbl_orders (order_id) ON DELETE CASCADE
);