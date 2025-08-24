--liquibase formatted sql
--changeset AVoronov:v1.0.0/payments

CREATE TABLE IF NOT EXISTS payments
(
    id uuid NOT NULL PRIMARY KEY,
    external_id uuid NOT NULL,
    amount numeric(19, 2) NOT NULL DEFAULT 0,
    currency varchar(4) NOT NULL,
    status varchar NOT NULL,
    user_account_id uuid NOT NULL,
    order_id uuid NOT NULL,
    description text,
    error_message text,
    created_at timestamp,
    updated_at timestamp
);