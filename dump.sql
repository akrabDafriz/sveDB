CREATE DATABASE sveDB;

CREATE TYPE sveClass AS ENUM (
    'Forestcraft',
    'Swordcraft',
    'Dragoncraft',
    'Runecraft',
    'Abysscraft',
    'Havencraft',
    'Neutral',
    '-'
);

CREATE TYPE sveType AS ENUM (
    'Leader',
    'Follower',
    'Spell',
    'Amulet',
    'Evolved Follower',
    'Follower Token',
    'Spell Token',
    'Amulet Token',
    '-'
);

CREATE TYPE sveRarity AS ENUM(
    'L',
    'G',
    'S',
    'B',
    'SP',
    'U',
    'SL',
    'PR',
    '-'
);

CREATE TABLE a_user(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    username varchar(30) NOT NULL UNIQUE,
    email varchar(320) NOT NULL UNIQUE, 
    password text NOT NULL
);

CREATE TABLE card_database (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(), 
    release_set text NOT NULL, 
    release_code text NOT NULL, 
    card_name text NOT NULL, 
    class sveClass NOT NULL, 
    Type sveType NOT NULL, 
    Cost int NOT NULL, 
    Stats varchar(6) NOT NULL, 
    Trait text NOT NULL, 
    Rarity sveRarity NOT NULL, 
    Effects text NOT NULL
);

CREATE TABLE deck_list (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(), 
    user_id uuid NOT NULL, 
    deck_name varchar(20) NOT NULL
);

CREATE TABLE deck_cards (
    deck_id uuid NOT NULL, 
    card_id uuid NOT NULL, 
    amount int NOT NULL
);

CREATE TABLE list_list (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(), 
    user_id uuid NOT NULL, 
    list_name varchar(20) NOT NULL
);

CREATE TABLE list_cards (
    list_id uuid NOT NULL, 
    card_id uuid NOT NULL, 
    amount int NOT NULL
);

ALTER TABLE deck_cards ADD FOREIGN KEY (deck_id) REFERENCES "deck_list" (id);
ALTER TABLE deck_cards ADD FOREIGN KEY (card_id) REFERENCES "card_database" (id);
ALTER TABLE list_cards ADD FOREIGN KEY (list_id) REFERENCES "list_list" (id);
ALTER TABLE list_cards ADD FOREIGN KEY (card_id) REFERENCES "card_database" (id);