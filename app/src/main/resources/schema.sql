-- Turns on support for UUID's in postgres
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- create table for product
CREATE TABLE product
(
  productid serial UNIQUE PRIMARY KEY,
  description character varying(10485760) NOT NULL,
  image character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  price double precision NOT NULL
);

ALTER TABLE product
  OWNER TO gordonuser;

-- create table for event

CREATE TABLE event
(
  eventId uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
  description character varying(10485760) NOT NULL,
  name character varying(255) NOT NULL
);

ALTER TABLE event
  OWNER TO gordonuser;

ALTER ROLE gordonuser CONNECTION LIMIT -1;

-- add product data
-- note: images are pulled from the public folder at junkstarter/app/react-app/public
INSERT INTO product (name, description, image, price) VALUES ('Unusable Security Delon', 'Unusuable security is not security', '/images/1.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Valentine''s Day', 'Love is meant to be shared', '/images/2.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Docker Tooling', 'Docker provides a whole suite of tools', '/images/3.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Docker Presents', 'Giving gifts every day', '/images/4.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Valentine''s Day', 'Love is in the air', '/images/5.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Docker Babies', 'For those with a cute little whale', '/images/6.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Experimental', 'Trying the latest', '/images/7.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('Docker for Developers', 'Escape the App Dependency Matrix', '/images/8.png', 25);
INSERT INTO product (name, description, image, price) VALUES ('DockerCon Copenhagen', 'DockerCon returns to Europe', '/images/9.png', 25);

INSERT INTO event (eventId, name, description) VALUES ('667fd724-2ac5-466b-ab85-948f42b0d372','Hot women day out', 'blah blah blah');
INSERT INTO event (eventId, name, description) VALUES ('2c7089fa-e1cb-4b0e-af36-a145d75a9046','Hot', 'blah blah blah');
INSERT INTO event (eventId, name, description) VALUES ('6654b8df-7ad8-4732-8f8a-11d9870404e9','day out', 'blah blah blah');
INSERT INTO event (eventId, name, description) VALUES ('fa4bdfd1-6eec-428b-8117-e4a09a71c6c2','out', 'blah blah blah');


