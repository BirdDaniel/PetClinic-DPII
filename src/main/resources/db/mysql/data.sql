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
  INSERT INTO users(username,password,enabled) VALUES ('emp5','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp5','employee');
  INSERT INTO users(username,password,enabled) VALUES ('emp6','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp6','employee');
  INSERT INTO users(username,password,enabled) VALUES ('emp7','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp7','employee');
  INSERT INTO users(username,password,enabled) VALUES ('emp8','3mpl0',TRUE);
 INSERT INTO authorities VALUES ('emp8','employee');

--INSERT INTO owners VALUES (id, firstName, LastName, telephone, address, username);
 INSERT INTO owners (id, first_name, last_name, telephone, address, username) VALUES (1, 'David', 'Seder','6085559435' , '2749 Blackhawk Trail', 'owner1');
 INSERT INTO owners (id, first_name, last_name, telephone, address, username)VALUES (2, 'Peter', 'McTavish','6085552765' , '2387 S. Fair Way', 'owner2');
 INSERT INTO owners (id, first_name, last_name, telephone, address, username)VALUES (3, 'Jean', 'Coleman', '6085552654' , '105 N. Lake St.', 'owner3');
 INSERT INTO owners (id, first_name, last_name, telephone, address, username)VALUES (4, 'Jeff', 'Black','6085555387' , '1450 Oak Blvd.', 'owner4');
 INSERT INTO owners (id, first_name, last_name, telephone, address, username)VALUES (5, 'Maria', 'Escobito', '6085557683' , '345 Maple St.', 'owner5');
 INSERT INTO owners (id, first_name, last_name, telephone, address, username)VALUES (6, 'David', 'Schroeder','6085559435' ,'2749 Blackhawk Trail', 'owner6');
 INSERT INTO owners (id, first_name, last_name, telephone, address, username)VALUES (7, 'Carlos', 'Estaban', '6085555487', '2335 Independence La.', 'owner7');

-- TYPES
 INSERT INTO types (id, name) VALUES (1, 'cat');
 INSERT INTO types (id, name) VALUES (2, 'dog');
 INSERT INTO types (id, name) VALUES (3, 'lizard');
 INSERT INTO types (id, name) VALUES (4, 'snake');
 INSERT INTO types (id, name) VALUES (5, 'bird');
 INSERT INTO types (id, name) VALUES (6, 'hamster');

INSERT INTO PARKS (id,name,address,owner_id) VALUES(1,'Parque del retiro', 'Plaza de la independencia, Madrid',1);
INSERT INTO PARKS (id,name,address,owner_id)VALUES(2,'Parque del Alamillo', 'Callejón Iglesia, Serranillos del Valle',2);
INSERT INTO PARKS (id,name,address,owner_id)VALUES(3,'Parque de la urba', 'Calle del Cerro, 36, 28979 Serranillos del Valle, Madrid',3);
INSERT INTO PARKS (id,name,address,owner_id)VALUES(4,'Parque Pardo de san Sebastián', '41004 Sevilla',2);
INSERT INTO PARKS (id,name,address,owner_id)VALUES(5,'Plaza de américa', 'Plaza América, 3, 41013 Sevilla',1);

INSERT INTO clinic_types (id, name) VALUES (1, 'The Clinic');
INSERT INTO clinic_types (id,name)VALUES (2, 'The Pet Surgery');
INSERT INTO clinic_types (id,name)VALUES (3, 'Vet Services');

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

INSERT INTO clinics (id, name, address, close, open, rating, description, max, price, type_id)VALUES (1, 'Clinica 1','Elm Street s/n', '10:10', '12:00', 3, 'Description 1', 10, 2.5, 1);
INSERT INTO clinics (id, name, address, close, open, rating, description, max, price, type_id)VALUES (2, 'Clinica 2','Avenida de la reina mercedes 2', '10:10', '12:00', 4, 'Description 2', 20, 3.5, 2);
INSERT INTO clinics (id, name, address, close, open, rating, description, max, price, type_id)VALUES (3, 'Clinica 3', 'Calle Bami 3', '10:00', '13:00', 5, 'Description 3', 30, 4.5, 3);

INSERT INTO residences (id, name, address, close, open, rating, description, max, price)VALUES (1, 'Residence "Happy Pet"','Madison Square, 51-B', '10:10', '12:00', 3, 'Description 1', 10, 2.5);
INSERT INTO residences (id, name, address, close, open, rating, description, max, price)VALUES (2, 'The Pet Residence ','Baker Street 221-B', '10:10', '12:00', 4, 'Description 2', 20, 3.5);
INSERT INTO residences (id, name, address, close, open, rating, description, max, price)VALUES (3, 'Pet Stay','Avenida Doctor Fedriani, 3, 2ºC', '10:00:00', '13:00', 5, 'Description 3', 30, 4.5);

INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (1, 'Marta', 'Carter', '679845125','65847525H','emp1');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (2, 'Jeorge', 'Frank', '6115551023', '87654321A', 'emp2');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (3, 'Hoss', 'Mad', '6115551023', '87654721A', 'emp3');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (4, 'No Hose', 'Mad', '6145556023', '87854341A', 'emp4');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (5, 'María', 'Martín', '6145456023', '87614341A', 'emp5');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (6, 'Pablo ', 'Dario', '6145256023', '87652341A', 'emp6');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (7, 'Manuel', 'Pérez', '6141556023', '876544341A', 'emp7');
INSERT INTO employees (id, first_name, last_name, telephone, dni, username)VALUES (8, 'Luisa', 'Paredes', '6147556023', '87654641A', 'emp8');



INSERT INTO requests (id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id)VALUES (4, '2020-12-12 12:00', '2020-01-01 13:00','2020-02-02 17:00', true, 1, 1, 1);
INSERT INTO requests (id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id)VALUES (2, '2030-08-12 12:00', '2019-08-06 19:00','2021-08-01 18:00', true, 2, 1, 3);
INSERT INTO requests(id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id) VALUES (3, '2030-08-12 12:00', '2019-08-06 18:00','2023-08-01 17:30', false, 2, 2, 2);
INSERT INTO requests (id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id)VALUES (1, '2030-08-12 12:00', '2019-08-06 20:00','2021-08-05 16:00', true, 1, 2, 1);
INSERT INTO requests (id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id)VALUES (5, '2030-08-12 12:00', '2019-08-06 14:00','2024-08-01 15:30', true, 3, 3, 3);
INSERT INTO requests (id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id)VALUES (6, '2030-08-12 12:00', '2019-08-06 15:00','2022-08-01 14:30', null, 3, 3, 2);
INSERT INTO requests (id,date_finish, date_req, date_ser, status, employee_id, owner_id, pet_id)VALUES (7, '2030-08-12 12:00', '2019-08-08 17:00','2022-08-05 19:00', true, 1, 2, 2);


INSERT INTO residences_requests (residence_id, requests_id)VALUES (1, 4);
INSERT INTO residences_requests (residence_id, requests_id)VALUES (1, 5);
INSERT INTO residences_requests (residence_id, requests_id)VALUES (3, 6);

INSERT INTO clinics_requests (clinic_id, requests_id)VALUES (1, 1);
INSERT INTO clinics_requests (clinic_id, requests_id)VALUES (1, 2);
INSERT INTO clinics_requests (clinic_id, requests_id) VALUES (2, 3);


INSERT INTO payments (id, credit_card, paid, date_pay, owner_id) VALUES (1, '1234 1234 1234 1234', 0.5, '2012-06-08', 1);
INSERT INTO payments (id, credit_card, paid, date_pay, owner_id) VALUES (2, '4234 1234 1234 5234', 1, '2012-06-08', 1);
INSERT INTO payments (id, credit_card, paid, date_pay, owner_id) VALUES (3, '3234 1234 1234 6234', 2, '2012-06-08', 1);



  INSERT INTO clinics_employees (clinic_id, employees_id)VALUES (1, 1);
  INSERT INTO clinics_employees (clinic_id, employees_id)VALUES (2, 2);
  INSERT INTO clinics_employees (clinic_id, employees_id)VALUES (2, 3);
  INSERT INTO clinics_employees (clinic_id, employees_id)VALUES (3, 4);


  INSERT INTO residences_employees (residence_id, employees_id)VALUES (1, 5);
  INSERT INTO residences_employees (residence_id, employees_id)VALUES (1, 6);
  INSERT INTO residences_employees (residence_id, employees_id)VALUES (2, 7);
  INSERT INTO residences_employees (residence_id, employees_id)VALUES (3, 8);


INSERT INTO clinics_payments (clinic_id, payments_id)VALUES (1, 1);
INSERT INTO clinics_payments (clinic_id, payments_id)VALUES (1, 2);
INSERT INTO clinics_payments (clinic_id, payments_id)VALUES (3, 3);

--INSERT INTO items (items_id, description, price, sale, stock)
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (1,'Collar','Description 1',10., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (2,'Champú','Description 2',7., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (3,'Pienso','Description 3',8., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (4,'Gel Antiséptico','Description 4',4., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (5,'Gold Terrine surtido','Description 5',10., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (6,'Collar antiparasitario para perros ','Description 6',7., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (7,'Vegetal Clean Papel','Description 7',8., 0.5, 3);
  INSERT INTO items(id,name, description, price, sale, stock) VALUES (8,'Arena Absorbente Classic','Description 8',10., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (9,'Sustrato Absorbente Natural','Description 9',9., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (10,'Lagrinet Neo','Description 10',5., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (11,'Toallitas higiénicas','Description 11',7., 0.5, 3);
  INSERT INTO items (id,name, description, price, sale, stock)VALUES (12,'Champú en espuma seca','Description 12',6., 0.5, 3);
  INSERT INTO items(id,name, description, price, sale, stock) VALUES (13,'Desodorante para gatos','Description 13',8., 0.5, 3);
  
--INSERT INTO clinics_items(clinic_id, items_id)
  INSERT INTO clinics_items (clinic_id, items_id)VALUES (1,1);
  INSERT INTO clinics_items (clinic_id, items_id)VALUES (1,3);
  INSERT INTO clinics_items(clinic_id, items_id) VALUES (2,5);
  INSERT INTO clinics_items (clinic_id, items_id)VALUES (2,7);
  INSERT INTO clinics_items (clinic_id, items_id)VALUES (3,9);
  INSERT INTO clinics_items (clinic_id, items_id)VALUES (3,11);
  
--INSERT INTO residences_items(residence_id, items_id)
  INSERT INTO residences_items (residence_id, items_id)VALUES (1,2);
  INSERT INTO residences_items (residence_id, items_id)VALUES (1,4);
  INSERT INTO residences_items (residence_id, items_id)VALUES (2,6);
  INSERT INTO residences_items (residence_id, items_id)VALUES (2,8);
  INSERT INTO residences_items (residence_id, items_id)VALUES (3,10);
  INSERT INTO residences_items (residence_id, items_id)VALUES (3,12);  
