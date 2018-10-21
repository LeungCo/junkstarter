-- create table for event
DROP TABLE IF EXISTS event;

CREATE TABLE event
(
  eventId uuid PRIMARY KEY,
  description character varying(10485760) NOT NULL,
  name character varying(255) NOT NULL
);


