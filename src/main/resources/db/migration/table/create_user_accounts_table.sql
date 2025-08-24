--liquibase formatted sql
--changeset AVoronov:v1.0.0/user_accounts

CREATE TABLE IF NOT EXISTS user_accounts
(
    id uuid NOT NULL PRIMARY KEY,
    user_id uuid NOT NULL UNIQUE,
    balance numeric(19, 2) NOT NULL DEFAULT 0,
    created_at timestamp,
    updated_at timestamp,
    version bigint
);