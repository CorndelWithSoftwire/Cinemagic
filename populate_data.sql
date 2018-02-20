-- N.B. Relies on auto-incrementing id starting from 1!

-- Cinemas
INSERT INTO cinemas (name) VALUES ('Camden');
INSERT INTO cinemas (name) VALUES ('Kentish Town');

-- Screens
INSERT INTO screens (id, cinema_id, rows, row_width) VALUES (1, 1, 10, 20);
INSERT INTO screens (id, cinema_id, rows, row_width) VALUES (2, 1, 10, 30);
INSERT INTO screens (id, cinema_id, rows, row_width) VALUES (3, 1, 7, 10);

INSERT INTO screens (id, cinema_id, rows, row_width) VALUES (1, 2, 10, 15);
INSERT INTO screens (id, cinema_id, rows, row_width) VALUES (2, 2, 12, 20);

-- Films
INSERT INTO films (name, length_minutes) VALUES ('Black Panther', 135);
INSERT INTO films (name, length_minutes) VALUES ('Citizen Kane', 119);
INSERT INTO films (name, length_minutes) VALUES ('2001: A Space Odyssey', 161);

-- Showings
INSERT INTO showings (screen_id, screen_cinema_id, film_id, time) VALUES (1, 1, 1, '2018-02-20 15:45');
INSERT INTO showings (screen_id, screen_cinema_id, film_id, time) VALUES (1, 1, 1, '2018-02-20 19:45');
INSERT INTO showings (screen_id, screen_cinema_id, film_id, time) VALUES (2, 1, 1, '2018-02-20 16:45');
INSERT INTO showings (screen_id, screen_cinema_id, film_id, time) VALUES (1, 2, 1, '2018-02-20 20:00');
INSERT INTO showings (screen_id, screen_cinema_id, film_id, time) VALUES (2, 2, 1, '2018-02-20 19:30');

-- Bookings
INSERT INTO bookings (showing_id, reference, seat_row, seat_number) VALUES (1, 'ABCDE12345', 1, 1);
INSERT INTO bookings (showing_id, reference, seat_row, seat_number) VALUES (1, 'ABCDE12345', 1, 2);
INSERT INTO bookings (showing_id, reference, seat_row, seat_number) VALUES (1, 'ABCDE12345', 1, 3);
INSERT INTO bookings (showing_id, reference, seat_row, seat_number) VALUES (1, 'MORPsMORPs', 2, 7);
INSERT INTO bookings (showing_id, reference, seat_row, seat_number) VALUES (2, 'BOOKINGAAA', 2, 5);
