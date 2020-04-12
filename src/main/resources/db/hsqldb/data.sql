-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
 INSERT INTO authorities VALUES ('owner1','owner');
-- 
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


-- EMPLOYEES USERS
 INSERT INTO users(username,password,enabled) VALUES ('emp1','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp1','employee');
 INSERT INTO users(username,password,enabled) VALUES ('emp2','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp2','employee');
 INSERT INTO users(username,password,enabled) VALUES ('emp3','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp3','employee');
 INSERT INTO users(username,password,enabled) VALUES ('emp4','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp4','employee');

--INSERT INTO owners VALUES (id, firstName, LastName, telephone, address, username);
 INSERT INTO owners VALUES (1, 'David', 'Schroeder','6085559435' , '2749 Blackhawk Trail', 'owner1');
 INSERT INTO owners VALUES (2, 'Peter', 'McTavish','6085552765' , '2387 S. Fair Way', 'owner2');
 INSERT INTO owners VALUES (3, 'Jean', 'Coleman', '6085552654' , '105 N. Lake St.', 'owner3');
 INSERT INTO owners VALUES (4, 'Jeff', 'Black','6085555387' , '1450 Oak Blvd.', 'owner4');
 INSERT INTO owners VALUES (5, 'Maria', 'Escobito', '6085557683' , '345 Maple St.', 'owner5');
 INSERT INTO owners VALUES (6, 'David', 'Schroeder','6085559435' ,'2749 Blackhawk Trail', 'owner6');
 INSERT INTO owners VALUES (7, 'Carlos', 'Estaban', '6085555487', '2335 Independence La.', 'owner7');

-- 
 INSERT INTO types VALUES (1, 'cat');
 INSERT INTO types VALUES (2, 'dog');
 INSERT INTO types VALUES (3, 'lizard');
 INSERT INTO types VALUES (4, 'snake');
 INSERT INTO types VALUES (5, 'bird');
 INSERT INTO types VALUES (6, 'hamster');
-- 

-- NSERT INTO specialties VALUES (1, 'radiology');
-- NSERT INTO specialties VALUES (2, 'surgery');
-- NSERT INTO specialties VALUES (3, 'dentistry');

-- 

--INSERT INTO vet_specialties VALUES (2, 1);
--INSERT INTO vet_specialties VALUES (3, 2);
--INSERT INTO vet_specialties VALUES (3, 3);
--INSERT INTO vet_specialties VALUES (4, 2);
--INSERT INTO vet_specialties VALUES (5, 1);




--INSERT INTO clinic_types VALUES (id, name);
 INSERT INTO clinic_types VALUES (1, 'Peluquería');
 INSERT INTO clinic_types VALUES (2, 'Operaciones');
 INSERT INTO clinic_types VALUES (3, 'Chalé');

--INSERT INTO pets VALUES (id, name, birth_date, owner_id, type_id)

 INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
 INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Sly', '2012-06-08', 1, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 4);



--INSERT INTO clinics(id, name, address, close, open, rating, description, max, price, type_id);
INSERT INTO clinics VALUES (1, 'Clinica 1','Elm Street s/n', '10:10', '12:00', 3, 'Description 1', 10, 2.5, 1);
INSERT INTO clinics VALUES (2, 'Clinica 2','Avenida de la reina mercedes 2', '10:10', '12:00', 4, 'Description 2', 20, 3.5, 2);
INSERT INTO clinics VALUES (3, 'Clinica 3', 'Calle Bami 3', '10:00', '13:00', 5, 'Description 3', 30, 4.5, 3);

--INSERT INTO residences(id, name, address, close, open, rating, description, max, price,days);
INSERT INTO residences VALUES (1, 'Residencia 1','Madison Square, 51-B', '10:10', '12:00', 3, 'Description 1', 10, 2.5, 2);
INSERT INTO residences VALUES (2, 'Residencia 2','Baker Street 221-B', '10:10', '12:00', 4, 'Description 2', 20, 3.5, 8);
INSERT INTO residences VALUES (3, 'Residencia 3','Avenida Doctor Fedriani, 3, 2ºC', '10:00:00', '13:00', 5, 'Description 3', 30, 4.5, 4);

--INSERT INTO employees(id, first_name, last_name, telephone, dni, username);
INSERT INTO employees VALUES (1, 'Marta', 'Carter', '679845125','65847525H','emp1');
INSERT INTO employees VALUES (2, 'Jeorge', 'Frank', '6115551023', '87654321A', 'emp2');
INSERT INTO employees VALUES (3, 'Hoss', 'Mad', '6115551023', '87654721A', 'emp3');
INSERT INTO employees VALUES (4, 'No Hose', 'Mad', '6145556023', '87654341A', 'emp4');


--INSERT INTO requests(id, date_req, date_ser, status, employee_id, owner_id, pet_id);
INSERT INTO requests VALUES (1, '2019-08-01 17:00', '2030-08-03 13:00', null, 1, 1, 1);
INSERT INTO requests VALUES (2, '2019-08-01 18:00', '2030-08-06 19:00', true, 2, 1, 3);
INSERT INTO requests VALUES (3, '2019-08-01 17:30', '2030-08-06 18:00', false, 2, 2, 2);
INSERT INTO requests VALUES (4, '2019-08-05 16:00', '2030-08-06 20:00', true, 1, 2, 1);
INSERT INTO requests VALUES (5, '2019-08-01 15:30', '2030-08-06 14:00', true, 3, 1, 3);
INSERT INTO requests VALUES (6, '2019-08-01 14:30', '2030-08-06 15:00', null, 3, 3, 2);
INSERT INTO requests VALUES (7, '2019-08-05 19:00', '2030-08-08 17:00', true, 1, 1, 2);

--INSERT INTO residences_requests VALUES (residence_id, request_id);
INSERT INTO residences_requests VALUES (1, 4);
INSERT INTO residences_requests VALUES (1, 5);
INSERT INTO residences_requests VALUES (3, 6);

--INSERT INTO clinics_requests VALUES (clinics_id, requests_id);
INSERT INTO clinics_requests VALUES (1, 1);
INSERT INTO clinics_requests VALUES (1, 2);
INSERT INTO clinics_requests VALUES (2, 3);



--INSERT INTO payments(id, credit_card, pay, date_pay, owner_id);
-- INSERT INTO payments VALUES (1, '1234 1234 1234 1234', 0.5, '2012-06-08', 1);
-- INSERT INTO payments VALUES (2, '4234 1234 1234 5234', 1, '2012-06-08', 1);
-- INSERT INTO payments VALUES (3, '3234 1234 1234 6234', 2, '2012-06-08', 1);

--INSERT INTO clinics_employees(clinic_id, employees_id);
  INSERT INTO clinics_employees VALUES (1, 1);
  INSERT INTO clinics_employees VALUES (2, 2);
  
  --INSERT INTO clinics_employees(clinic_id, employees_id);
  INSERT INTO residences_employees VALUES (1, 4);
  INSERT INTO residences_employees VALUES (2, 3);

--INSERT INTO clinics_payments VALUES (clinics_id, payments_id);
-- INSERT INTO clinics_payments VALUES (1, 1);
-- INSERT INTO clinics_payments VALUES (1, 2);
-- INSERT INTO clinics_payments VALUES (3, 3);

--INSERT INTO residences_employees VALUES (residence_id, employee_id);

--INSERT INTO residences_payments VALUES (residence_id, payments_id);

--UPDATE requests SET employee_id = 1  WHERE (id = 1); UPDATE requests SET owner_id = 1  WHERE (id = 1);
--UPDATE requests SET employee_id = 2  WHERE (id = 2); UPDATE requests SET owner_id = 1  WHERE (id = 2);
--UPDATE requests SET employee_id = 2  WHERE (id = 3); UPDATE requests SET owner_id = 3  WHERE (id = 3);


--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');

--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');*/

--INSERT INTO items (items_id, description, price, sale, stock)
  INSERT INTO items VALUES (1,'Collar','Description 1',10., 0.5, 3);
  INSERT INTO items VALUES (2,'Champú','Description 2',7., 0.5, 3);
  INSERT INTO items VALUES (3,'Pienso','Description 3',8., 0.5, 3);
  INSERT INTO items VALUES (4,'Gel Antiséptico','Description 4',4., 0.5, 3);
  INSERT INTO items VALUES (5,'Gold Terrine surtido','Description 5',10., 0.5, 3);
  INSERT INTO items VALUES (6,'Collar antiparasitario para perros ','Description 6',7., 0.5, 3);
  INSERT INTO items VALUES (7,'Vegetal Clean Papel','Description 7',8., 0.5, 3);
  INSERT INTO items VALUES (8,'Arena Absorbente Classic','Description 8',10., 0.5, 3);
  INSERT INTO items VALUES (9,'Sustrato Absorbente Natural','Description 9',9., 0.5, 3);
  INSERT INTO items VALUES (10,'Lagrinet Neo','Description 10',5., 0.5, 3);
  INSERT INTO items VALUES (11,'Toallitas higiénicas','Description 11',7., 0.5, 3);
  INSERT INTO items VALUES (12,'Champú en espuma seca','Description 12',6., 0.5, 3);
  INSERT INTO items VALUES (13,'Desodorante para gatos','Description 13',8., 0.5, 3);
  
--INSERT INTO clinics_items(clinic_id, items_id)
  INSERT INTO clinics_items VALUES (1,1);
  INSERT INTO clinics_items VALUES (1,3);
  INSERT INTO clinics_items VALUES (2,5);
  INSERT INTO clinics_items VALUES (2,7);
  INSERT INTO clinics_items VALUES (3,9);
  INSERT INTO clinics_items VALUES (3,11);
  
--INSERT INTO residences_items(residence_id, items_id)
  INSERT INTO residences_items VALUES (1,2);
  INSERT INTO residences_items VALUES (1,4);
  INSERT INTO residences_items VALUES (2,6);
  INSERT INTO residences_items VALUES (2,8);
  INSERT INTO residences_items VALUES (3,10);
  INSERT INTO residences_items VALUES (3,12);
  
  
  
  