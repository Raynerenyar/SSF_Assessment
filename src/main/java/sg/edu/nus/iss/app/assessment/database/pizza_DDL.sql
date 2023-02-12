-- 
-- Pizza DDL
-- 


CREATE DATABASE pizza;

USE pizza;

CREATE TABLE pizza_type (
	type VARCHAR(20) NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (type)
);

CREATE TABLE pizza_size (
	size CHAR(2) NOT NULL,
    multiplier DECIMAL(2,1),
    PRIMARY KEY (size)
);

CREATE TABLE pizza_selections (
    pizza_order_id VARCHAR(8) NOT NULL,
	pizza_size CHAR(2),
    type VARCHAR(20),
    quantity INT,
    CONSTRAINT fk_pizza_type
		FOREIGN KEY (type)
        REFERENCES pizza_type (type),
	CONSTRAINT fk_pizza_size
		FOREIGN KEY (pizza_size)
        REFERENCES pizza_size (size)
);

CREATE INDEX idx_selection ON pizza_selections(pizza_order_id);

CREATE TABLE pizza_order (
	id VARCHAR(8) NOT NULL,
    full_name VARCHAR(20) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone NUMERIC(8) NOT NULL,
    pizza_cost DOUBLE,
    rush BOOLEAN DEFAULT FALSE,
	total_cost DOUBLE,
    comments TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_pizza_selections
		FOREIGN KEY (id)
        REFERENCES pizza_selections (pizza_order_id)
);
