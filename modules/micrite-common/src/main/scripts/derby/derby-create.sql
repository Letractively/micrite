
CREATE TABLE T_ROLE (
		ID INTEGER NOT NULL generated by default as identity,
		DESCRIPTION VARCHAR(255),
		NAME VARCHAR(255)
	);

CREATE TABLE T_USER (
		ID INTEGER NOT NULL generated by default as identity,
		DISABLED SMALLINT NOT NULL,
		EMAIL VARCHAR(255),
		NAME VARCHAR(255),
		PASSWORD VARCHAR(255),
		USERNAME VARCHAR(255)
	);

CREATE TABLE T_RESOURCE (
		ID INTEGER NOT NULL generated by default as identity,
		TYPE VARCHAR(255),
		VALUE VARCHAR(255)
	);

CREATE TABLE T_CUSTOMER (
		ID INTEGER NOT NULL generated by default as identity,
		NAME VARCHAR(255)
	);

CREATE TABLE T_MEMBERS (
		ID INTEGER NOT NULL generated by default as identity,
		NAME VARCHAR(255),
		TELEPHONE VARCHAR(255),
		CUSTOMER_ID INTEGER
	);

CREATE TABLE T_ROLE_RESOURCE (
		ROLE_ID INTEGER NOT NULL,
		RESOURCE_ID INTEGER NOT NULL
	);

CREATE TABLE T_USER_ROLE (
		USER_ID INTEGER NOT NULL,
		ROLE_ID INTEGER NOT NULL
	);

CREATE INDEX SQL090306120608670 ON T_ROLE_RESOURCE (RESOURCE_ID ASC);

CREATE UNIQUE INDEX SQL090306120607120 ON T_USER_ROLE (USER_ID ASC, ROLE_ID ASC);

CREATE UNIQUE INDEX SQL090306120608850 ON T_MEMBERS (ID ASC);

CREATE INDEX SQL090306120608290 ON T_USER_ROLE (ROLE_ID ASC);

CREATE INDEX SQL090306120608560 ON T_ROLE_RESOURCE (ROLE_ID ASC);

CREATE UNIQUE INDEX SQL090306120607980 ON T_USER (ID ASC);

CREATE UNIQUE INDEX SQL090306120607840 ON T_RESOURCE (ID ASC);

CREATE UNIQUE INDEX SQL090306120607310 ON T_ROLE (ID ASC);

CREATE UNIQUE INDEX SQL090306122730510 ON T_CUSTOMER (ID ASC);

CREATE UNIQUE INDEX SQL090306120607750 ON T_ROLE_RESOURCE (ROLE_ID ASC, RESOURCE_ID ASC);

CREATE INDEX SQL090306122730710 ON T_MEMBERS (CUSTOMER_ID ASC);

CREATE INDEX SQL090306120608120 ON T_USER_ROLE (USER_ID ASC);

ALTER TABLE T_CUSTOMER ADD CONSTRAINT SQL090306122730510 PRIMARY KEY (ID);

ALTER TABLE T_ROLE ADD CONSTRAINT SQL090223195851290 PRIMARY KEY (ID);

ALTER TABLE T_RESOURCE ADD CONSTRAINT SQL090223195851070 PRIMARY KEY (ID);

ALTER TABLE T_MEMBERS ADD CONSTRAINT SQL090223195851670 PRIMARY KEY (ID);

ALTER TABLE T_ROLE_RESOURCE ADD CONSTRAINT SQL090223195851760 PRIMARY KEY (ROLE_ID, RESOURCE_ID);

ALTER TABLE T_USER_ROLE ADD CONSTRAINT SQL090223195852140 PRIMARY KEY (USER_ID, ROLE_ID);

ALTER TABLE T_USER ADD CONSTRAINT SQL090223195851960 PRIMARY KEY (ID);

ALTER TABLE T_USER_ROLE ADD CONSTRAINT FK331DEE5F97972F83 FOREIGN KEY (USER_ID)
	REFERENCES T_USER (ID);

ALTER TABLE T_ROLE_RESOURCE ADD CONSTRAINT FK379F912CF201A823 FOREIGN KEY (RESOURCE_ID)
	REFERENCES T_RESOURCE (ID);

ALTER TABLE T_MEMBERS ADD CONSTRAINT FKF420BC4E77425623 FOREIGN KEY (CUSTOMER_ID)
	REFERENCES T_CUSTOMER (ID);

ALTER TABLE T_USER_ROLE ADD CONSTRAINT FK331DEE5FF26C6BA3 FOREIGN KEY (ROLE_ID)
	REFERENCES T_ROLE (ID);

ALTER TABLE T_ROLE_RESOURCE ADD CONSTRAINT FK379F912CF26C6BA3 FOREIGN KEY (ROLE_ID)
	REFERENCES T_ROLE (ID);

