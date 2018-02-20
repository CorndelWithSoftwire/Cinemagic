CREATE TABLE cinemas (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE screens (
  id int(11) NOT NULL,
  cinema_id int(11) NOT NULL,
  rows int(11) NOT NULL,
  row_width int(11) NOT NULL,
  PRIMARY KEY (id, cinema_id),
  FOREIGN KEY (cinema_id) REFERENCES cinemas (id)
);

CREATE TABLE films (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  length_minutes int(11) NOT NULL
);

CREATE TABLE showings (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  screen_id int(11) NOT NULL,
  screen_cinema_id int(11) NOT NULL,
  film_id int(11) NOT NULL,
  time datetime(6) NOT NULL,
  FOREIGN KEY (screen_id, screen_cinema_id) REFERENCES screens (id, cinema_id),
  FOREIGN KEY (film_id) REFERENCES films (id)
);

CREATE TABLE bookings (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  showing_id int(11) NOT NULL,
  reference varchar(10) NOT NULL,
  seat_row int(11) NOT NULL,
  seat_number int(11) NOT NULL,
  FOREIGN KEY (showing_id) REFERENCES showings (id)
);
