-- Insert seed data into seat table
-- Runs automatically on application startup

Delete From seat;

INSERT INTO seat (movie_name, status, booked, version) VALUES
                                                           ('Inception',            'AVAILABLE', false, 0),
                                                           ('Inception',            'AVAILABLE', false, 0),
                                                           ('Inception',            'AVAILABLE', false, 0),
                                                           ('Oppenheimer',          'AVAILABLE',    false,  0);