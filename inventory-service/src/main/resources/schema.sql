DROP TABLE IF EXISTS tbl_inventories CASCADE;

CREATE TABLE tbl_inventories
(
    inventory_id BIGINT  NOT NULL AUTO_INCREMENT,
    sku_code     VARCHAR NOT NULL,
    quantity     INT     NOT NULL DEFAULT 0,
    PRIMARY KEY (inventory_id)
);