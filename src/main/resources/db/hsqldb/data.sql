-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner2','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner2','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner3','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner3','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner4','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner4','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner5','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner5','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner6','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner6','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner7','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner7','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','employee');


INSERT INTO employees VALUES (1, 'Marta', 'Carter','65847525H','679845125','vet1');
--INSERT INTO vets VALUES (2, 'Helen', 'Leary');
--INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
--INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
--INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
--INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO owners VALUES (1, 'David', 'Schroeder', '2749 Blackhawk Trail','6085559435', 'owner1');
INSERT INTO owners VALUES (2, 'Peter', 'McTavish', '2387 S. Fair Way',  '6085552765', 'owner2');
INSERT INTO owners VALUES (3, 'Jean', 'Coleman', '105 N. Lake St.', '6085552654', 'owner3');
INSERT INTO owners VALUES (4, 'Jeff', 'Black', '1450 Oak Blvd.', '6085555387', 'owner4');
INSERT INTO owners VALUES (5, 'Maria', 'Escobito', '345 Maple St.',  '6085557683', 'owner5');
INSERT INTO owners VALUES (6, 'David', 'Schroeder', '2749 Blackhawk Trail',  '6085559435', 'owner6');
INSERT INTO owners VALUES (7, 'Carlos', 'Estaban', '2335 Independence La.', '6085555487', 'owner7');

--INSERT INTO requests(id, date_req, employee_id, owner_id);
INSERT INTO requests(id, date_req,employee_id,owner_id) VALUES (1, '2030-08-01',1,1);
INSERT INTO requests(id, date_req,employee_id,owner_id) VALUES (2, '2021-02-01',1,4);
INSERT INTO requests(id, date_req,employee_id,owner_id) VALUES (3, '2022-05-01',1,7);
INSERT INTO requests(id, date_req,employee_id,owner_id) VALUES (4, '2020-08-07',1,2);
INSERT INTO requests(id, date_req,employee_id,owner_id) VALUES (5, '2031-04-11',1,1);
INSERT INTO requests(id, date_req,employee_id,owner_id) VALUES (6, '2026-09-10',1,4);


INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');


--INSERT INTO specialties VALUES (1, 'radiology');
--INSERT INTO specialties VALUES (2, 'surgery');
--INSERT INTO specialties VALUES (3, 'dentistry');


--INSERT INTO vet_specialties VALUES (2, 1);
--INSERT INTO vet_specialties VALUES (3, 2);
--INSERT INTO vet_specialties VALUES (3, 3);
--INSERT INTO vet_specialties VALUES (4, 2);
--INSERT INTO vet_specialties VALUES (5, 1);


/*

--INSERT INTO clinic_types VALUES (id, name);
INSERT INTO clinic_types VALUES (1, 'Peluquería');
INSERT INTO clinic_types VALUES (2, 'Operaciones');
INSERT INTO clinic_types VALUES (3, 'Chalé');
    
--INSERT INTO owners VALUES (id, firstName, LastName, telephone, address, username);
INSERT INTO owners VALUES (1, 'George', 'Franklin', '6085551023', '110 W. Liberty St., Madison', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '6085551749', '638 Cardinal Ave., Sun Prairie', 'admin1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '6085558763', '2693 Commerce St., McFarland', 'owner1');
--INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
--INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
--INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
--INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
--INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
--INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
--INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

--INSERT INTO pets VALUES (id, name, birth_date, owner_id, type_id)
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);


--INSERT INTO clinics(id, address, close, open, rating, description, max, price, type_id);
--INSERT INTO clinics VALUES (1, 'New Address', '2012-06-08 10:10:10', '2022-06-08 12:00', 3, 'Description 1', 10, 2.5, 1);
--INSERT INTO clinics VALUES (2, 'New Address 2', '2012-06-08 10:10:10', '2022-06-08 12:00', 4, 'Description 2', 20, 3.5, 2);
--INSERT INTO clinics VALUES (3, 'New Address 3', '2012-01-01 10:00:00', '2022-06-08 13:00', 5, 'Description 3', 30, 4.5, 3);

--INSERT INTO residences(id, address, close, open, rating, description, max, price, type_id);
--INSERT INTO residences VALUES (1, 'New Address', '2012-06-08 10:10:10', '2022-06-08 12:00', 3, 'Description 1', 10, 2.5, 7);
--INSERT INTO residences VALUES (2, 'New Address 2', '2012-06-08 10:10:10', '2022-06-08 12:00', 4, 'Description 2', 20, 3.5, 8);
--INSERT INTO residences VALUES (3, 'New Address 3', '2012-01-01 10:00:00', '2022-06-08 13:00', 5, 'Description 3', 30, 4.5, 4);

--INSERT INTO requests(id, date_req, employee_id, owner_id);
INSERT INTO requests(id, date_req) VALUES (2, '2031-08-01');
INSERT INTO requests(id, date_req) VALUES (3, '2032-08-01');


--INSERT INTO services(id, address, close, max, open, price, reception, room, request_id);
--INSERT INTO services VALUES (1, 'New Address', '2012-06-08 10:10:10', 10, '2022-06-08 12:00', 2.5, 3, 'A1-23', 1);
--INSERT INTO services VALUES (2, 'New Address 2', '2112-06-08 13:16', 4, '2015-06-08 10:15', 5.5, 3, 'C1-23', 1);
--INSERT INTO services VALUES (3, 'Hello 2', '2212-06-08 13:10', 5, '2052-06-08 16:10', 3, 2, 'B1-23', 2);

--INSERT INTO employees(id, first_name, last_name, telephone, dni, username);
INSERT INTO employees VALUES (1, 'Jeorge', 'Frank', '6115551023', '87654321A', 'vet1');
INSERT INTO employees VALUES (2, 'Hoes', 'Mad', '6115551023', '87654721A', 'vet1');
INSERT INTO employees VALUES (3, 'No Hoes', 'Mad', '6145556023', '87654341A', 'vet1');

--INSERT INTO requests(id, date_req, status, employee_id, owner_id);
INSERT INTO requests VALUES (1, '2030-08-01', null, 1, 1);
INSERT INTO requests VALUES (2, '2031-08-01', true, 2, 1);
INSERT INTO requests VALUES (3, '2032-08-01', false, 2, 2);
INSERT INTO requests VALUES (4, '2035-08-05', null, 1, 1);
INSERT INTO requests VALUES (5, '2031-08-01', true, 3, 3);
INSERT INTO requests VALUES (6, '2032-08-01', false, 3, 3);

--INSERT INTO payments(id, credit_card, pay, date_pay, owner_id);
INSERT INTO payments VALUES (1, '1234 1234 1234 1234', 0.5, '2012-06-08', 1);
INSERT INTO payments VALUES (2, '4234 1234 1234 5234', 1, '2012-06-08', 1);
INSERT INTO payments VALUES (3, '3234 1234 1234 6234', 2, '2012-06-08', 1);

--INSERT INTO clinics_employees(clinic_id, employees_id);
INSERT INTO clinics_employees VALUES (1, 1);
INSERT INTO clinics_employees VALUES (2, 2);
INSERT INTO clinics_employees VALUES (2, 3);

--INSERT INTO clinics_employees VALUES (clinics_id, employees_id);

--INSERT INTO clinics_payments VALUES (clinics_id, payments_id);
INSERT INTO clinics_payments VALUES (1, 1);
INSERT INTO clinics_payments VALUES (1, 2);
INSERT INTO clinics_payments VALUES (3, 3);

--INSERT INTO clinics_requests VALUES (clinics_id, requests_id);
INSERT INTO clinics_requests VALUES (1, 1);
INSERT INTO clinics_requests VALUES (1, 2);
INSERT INTO clinics_requests VALUES (2, 3);

--INSERT INTO residences_employees VALUES (residence_id, employee_id);

--INSERT INTO residences_payments VALUES (residence_id, payments_id);

--INSERT INTO residences_requests VALUES (residence_id, request_id);
INSERT INTO residences_requests VALUES (1, 4);
INSERT INTO residences_requests VALUES (1, 5);
INSERT INTO residences_requests VALUES (3, 6);

--UPDATE requests SET employee_id = 1  WHERE (id = 1); UPDATE requests SET owner_id = 1  WHERE (id = 1);
--UPDATE requests SET employee_id = 2  WHERE (id = 2); UPDATE requests SET owner_id = 1  WHERE (id = 2);
--UPDATE requests SET employee_id = 2  WHERE (id = 3); UPDATE requests SET owner_id = 3  WHERE (id = 3);

--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');*/