INSERT INTO hotels (id, name, description, brand)
VALUES (-1, 'test1', 'testS1', 'testB1'),
       (-2, 'test2', 'testS2', 'testB2'),
       (-3, 'test3', 'testS3', 'testB3'),
       (-4, 'test4', 'testS4', 'testB4');
INSERT INTO addresses (id, city, street, country, house_number, postcode, hotel_id)
VALUES (-1, 'test_city1', 'testS1', 'testB1',11, 'code',-1),
       (-2, 'test_city1', 'testS2', 'testB2',12, 'code',-2),
       (-3, 'test3_city3', 'testS3', 'testB3',23, 'code',-3),
       (-4, 'test_city14', 'testS4', 'testB3',14, 'code',-4);
INSERT INTO contacts (id, type, contact_value, hotel_id)
VALUES (-1, 'PHONE', '+234584356',-1),
       (-2, 'PHONE', '+234584357',-2),
       (-3, 'PHONE', '+234584358',-3),
       (-4, 'PHONE', '+234584359',-4),
        (-5, 'EMAIL', 'test1@mail.com',-1),
       (-6, 'EMAIL', 'test2@mail.com',-2),
       (-7, 'EMAIL', 'test3@mail.com',-3),
       (-8, 'EMAIL', 'test4@mail.com',-4);