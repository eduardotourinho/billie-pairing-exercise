CREATE TABLE IF NOT EXISTS orders_schema.items
(
    id        UUID PRIMARY KEY,
    item_name VARCHAR(50) NOT NULL,
    price     NUMERIC(8, 2) CHECK ( price > 0 )
);

CREATE TABLE IF NOT EXISTS orders_schema.orders
(
    id          UUID PRIMARY KEY,
    merchant_id UUID NOT NULL,
    total       NUMERIC(10, 2) CHECK ( total > 0 ),
    state       VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS orders_schema.shipments
(
    id       UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    shipment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_shipment
        FOREIGN KEY (order_id) REFERENCES orders_schema.orders
);

CREATE TABLE IF NOT EXISTS orders_schema.order_items
(
    order_id UUID,
    item_id  UUID,
    PRIMARY KEY (order_id, item_id),
    CONSTRAINT fk_order_items
        FOREIGN KEY (item_id) REFERENCES orders_schema.items
);

CREATE TABLE IF NOT EXISTS orders_schema.shipped_items
(
    shipment_id UUID,
    item_id     UUID,
    PRIMARY KEY (shipment_id, item_id),
    CONSTRAINT fk_shipped_items
        FOREIGN KEY (item_id) REFERENCES orders_schema.items
);
