-- Insert members
INSERT INTO member (name, nickname, location) VALUES ('Juan Martinez', 'userOne', 'London');
INSERT INTO member (name, nickname, location) VALUES ('Willy Smith', 'userTwo', 'Black Pool');

-- Insert listings
INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Vintage Action Figure', (SELECT id FROM member WHERE nickname = 'userOne'), '2024-06-27', 'ACTION_FIGURES', 'A vintage action figure in good condition.', 'GOOD', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Classic Toy Car', (SELECT id FROM member WHERE nickname = 'userTwo'), '2024-07-01', 'VEHICLES', 'A classic toy car from the 80s.', 'LIKE_NEW', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Educational Board Game Set', (SELECT id FROM member WHERE nickname = 'userOne'), '2024-07-05', 'EDUCATIONAL_TOYS', 'A complete educational board game set with all pieces.', 'GOOD', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Electronic Stuffed Animal Collection', (SELECT id FROM member WHERE nickname = 'userTwo'), '2024-07-10', 'ELECTRONIC_TOYS', 'A collection of electronic stuffed animals in various sizes.', 'USED', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Lego Building Set', (SELECT id FROM member WHERE nickname = 'userOne'), '2024-07-15', 'CONSTRUCTION_TOYS', 'A large Lego building set with instructions.', 'LIKE_NEW', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Antique Doll', (SELECT id FROM member WHERE nickname = 'userTwo'), '2024-07-20', 'DOLLS', 'An antique doll in excellent condition.', 'LIKE_NEW', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Remote Control Car', (SELECT id FROM member WHERE nickname = 'userOne'), '2024-07-25', 'VEHICLES', 'A remote control car with all accessories.', 'GOOD', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Educational Puzzle Set', (SELECT id FROM member WHERE nickname = 'userTwo'), '2024-07-30', 'EDUCATIONAL_TOYS', 'A set of educational puzzles for kids.', 'USED', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Vintage Train Set', (SELECT id FROM member WHERE nickname = 'userOne'), '2024-08-05', 'OTHER', 'A vintage train set with tracks and accessories.', 'GOOD', 'AVAILABLE');

INSERT INTO listing (title, member_id, date_posted, category, description, condition, status_listing)
VALUES ('Electronic Learning Tablet', (SELECT id FROM member WHERE nickname = 'userTwo'), '2024-08-10', 'ELECTRONIC_TOYS', 'An electronic learning tablet for kids.', 'BRAND_NEW', 'AVAILABLE');


INSERT INTO image (image_name, url, listing_id) VALUES
                                                   ('Vintage Action Figure Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/action_figure.jpg', (SELECT id FROM listing WHERE title = 'Vintage Action Figure')),
                                                   ('Classic Toy Car Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Classic Toy Car')),
                                                   ('Educational Board Game Set Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Educational Board Game Set')),
                                                   ('Electronic Stuffed Animal Collection Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Electronic Stuffed Animal Collection')),
                                                   ('Lego Building Set Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Lego Building Set')),
                                                   ('Antique Doll Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Antique Doll')),
                                                   ('Remote Control Car Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Remote Control Car')),
                                                   ('Educational Puzzle Set Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Educational Puzzle Set')),
                                                   ('Vintage Train Set Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Vintage Train Set')),
                                                   ('Electronic Learning Tablet Image', 'https://elasticbeanstalk-eu-west-2-058264262755.s3.eu-west-2.amazonaws.com/images.jpeg', (SELECT id FROM listing WHERE title = 'Electronic Learning Tablet'));

-- UPDATE listing
-- SET photo = FILE_READ('src/main/resources/images/action_figure.jpg')
-- WHERE id = 1;
--
-- UPDATE listing
-- SET photo = FILE_READ('src/main/resources/images/classic_car_toy.jpeg')
-- WHERE id = 2;
--
-- UPDATE listing
-- SET photo = FILE_READ('src/main/resources/images/educational_board_game.jpeg')
-- WHERE id = 3;
--
-- UPDATE listing
-- SET photo = FILE_READ('src/main/resources/images/Electronic_Stuffed_Animal_Collection.jpg')
-- WHERE id = 4;
