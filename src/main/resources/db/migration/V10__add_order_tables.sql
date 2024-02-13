CREATE TABLE IF NOT EXISTS orders_schema.items
(
    id        UUID PRIMARY KEY,
    item_name VARCHAR(50) NOT NULL,
    price     NUMERIC(8, 2) NOT NULL CHECK ( price > 0 )
);

CREATE TABLE IF NOT EXISTS orders_schema.orders
(
    id          UUID PRIMARY KEY,
    merchant_id UUID NOT NULL,
    total       NUMERIC(10, 2) NOT NULL CHECK ( total > 0 ),
    state       VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders_schema.shipments
(
    id       UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    total NUMERIC(10, 2) NOT NULL CHECK ( total > 0 ),
    shipment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_shipment
        FOREIGN KEY (order_id) REFERENCES orders_schema.orders
);

CREATE TABLE IF NOT EXISTS orders_schema.order_items
(
    order_id UUID NOT NULL ,
    item_id  UUID NOT NULL ,
    PRIMARY KEY (order_id, item_id),
    CONSTRAINT fk_order_items_item
        FOREIGN KEY (item_id) REFERENCES orders_schema.items,
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders_schema.orders
);

CREATE TABLE IF NOT EXISTS orders_schema.shipped_items
(
    shipment_id UUID NOT NULL ,
    item_id     UUID NOT NULL ,
    PRIMARY KEY (shipment_id, item_id),
    CONSTRAINT fk_shipped_items_item
        FOREIGN KEY (item_id) REFERENCES orders_schema.items,
    CONSTRAINT fk_shipped_items_shipment
        FOREIGN KEY (shipment_id) REFERENCES orders_schema.shipments
);
