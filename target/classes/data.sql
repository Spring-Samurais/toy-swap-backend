INSERT INTO MEMBER (name, nickname, location) VALUES ('John Doe', 'johndoe', 'New York');
INSERT INTO MEMBER (name, nickname, location) VALUES  ('Willy Smith', 'wsmithy', 'Black Pool');

--------------------------------------------------------------------------------------------------------------------


INSERT INTO Listing (photo, poster_id, date_posted, category, description, condition, status)
VALUES (NULL, 1, CURRENT_TIMESTAMP, 'ACTION_FIGURES', 'John\''s action figure', 'New', 'AVAILABLE');

INSERT INTO Listing (photo, poster_id, date_posted, category, description, condition, status)
VALUES ('', 2, CURRENT_TIMESTAMP, 'DOLLS', 'Jane''s doll', 'Used', 'AVAILABLE');