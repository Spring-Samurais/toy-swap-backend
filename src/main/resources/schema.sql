/*
 Enums are first loaded in the schema that Spring injects,
 otherwise the SQL trows an error. SO BE AWARE YOU FOOL.
 */
-- Enums
CREATE TYPE category_enum AS ENUM (
    'ACTION_FIGURES',
    'DOLLS',
    'CONSTRUCTION_TOYS',
    'VEHICLES',
    'EDUCATIONAL_TOYS',
    'ELECTRONIC_TOYS',
    'OTHER'
    );

CREATE TYPE status_enum AS ENUM (
    'AVAILABLE',
    'PENDING',
    'SWAPPED'
    );

-- User Table
CREATE TABLE MEMBER (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     TEXT        NOT NULL,
    nickname TEXT UNIQUE NOT NULL,
    location TEXT
);

-- Post Table
CREATE TABLE Listing
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    photo       BYTEA,
    listing_id   BIGINT,
    date_posted TIMESTAMP,
    category    category_enum,
    description TEXT NOT NULL,
    condition   TEXT,
    status      status_enum,
    FOREIGN KEY (listing_id) REFERENCES "MEMBER" (id)
);

-- Comment Table
CREATE TABLE Comment
(
    comment_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    text           TEXT NOT NULL,
    commenter_id   BIGINT,
    listing_id        BIGINT,
    date_commented TIMESTAMP,
    FOREIGN KEY (commenter_id) REFERENCES "MEMBER" (id),
    FOREIGN KEY (listing_id) REFERENCES Listing (id)
);

-- Image Table
CREATE TABLE Image
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_name TEXT  NOT NULL,
    listing_id    BIGINT,
    image      BYTEA NOT NULL,
    FOREIGN KEY (listing_id) REFERENCES Listing (id)
);
