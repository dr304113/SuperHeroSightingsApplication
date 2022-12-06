DROP DATABASE IF EXISTS SuperHeroSightingsTestDB;

CREATE DATABASE SuperHeroSightingsTestDB;

USE SuperHeroSightingsTestDB;

CREATE TABLE hero (
heroId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
description VARCHAR(200) NOT NULL
);
CREATE TABLE superpower (
superpowerId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
description VARCHAR(200) NOT NULL
);
CREATE TABLE heroSuperpower (
heroId INT NOT NULL,
superpowerId INT NOT NULL,
PRIMARY KEY (heroId, superpowerId),
FOREIGN KEY (heroId)
REFERENCES hero (heroId),
FOREIGN KEY (superpowerId)
REFERENCES superpower (superpowerId)
);
CREATE TABLE location (
locationId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
address VARCHAR(50) NOT NULL,
city VARCHAR(30) NOT NULL,
state VARCHAR(30) NOT NULL,
zip CHAR(5) NOT NULL,
description VARCHAR(200) NOT NULL,
latitude VARCHAR(30) NOT NULL,
longitude VARCHAR(30) NOT NULL
);
CREATE TABLE sighting (
sightingId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
date DATE NOT NULL,
heroId INT NOT NULL,
locationId INT NOT NULL,
FOREIGN KEY (heroId)
REFERENCES hero (heroId),
FOREIGN KEY (locationId)
REFERENCES location (locationId)
);

CREATE TABLE organization (
organizationId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
description VARCHAR(200) NOT NULL,
address VARCHAR(50) NOT NULL,
city VARCHAR(30) NOT NULL,
state VARCHAR(30) NOT NULL,
zip CHAR(5) NOT NULL,
phone CHAR(10) NOT NULL
);
CREATE TABLE heroOrganization (
heroId INT NOT NULL,
organizationId INT NOT NULL,
PRIMARY KEY (heroId, organizationId),
FOREIGN KEY (heroId)
REFERENCES hero (heroId),
FOREIGN KEY (organizationId)
REFERENCES organization (organizationId)
);