CREATE DATABASE ticket_service_db;

CREATE TYPE ticket_type AS ENUM ('DAY', 'WEEK', 'MONTH', 'YEAR');

CREATE TABLE users (
    id UUID PRIMARY KEY NOT NULL,
    name TEXT,
    creation_time TIMESTAMPTZ
);

CREATE TABLE tickets (
    id UUID PRIMARY KEY NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    ticket_type ticket_type,
    creation_time TIMESTAMPTZ
);