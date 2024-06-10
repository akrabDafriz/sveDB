INSERT INTO card_database (release_set, 
release_code, card_name, class, type, cost, 
stats, trait, rarity, effects)
VALUES ('SD01', 'SD01-LD01', 'Arisa', 'Forestcraft', 'Leader', 0, '-', '-', 'L', '-'),
        ('SD01', 'SD01-LD01', 'Aria, Fairy Princess', 'Forestcraft', 
        'Follower', 6, '5/5', 'Pixie / Princess', 'L', 
        E'Ward. \nFanfare Put up to 9 Fairy tokens onto your field or into your EX area. \nWhile this card is on your field, your other Pixie followers have Rush');
        
COPY card_database(release_set, release_code, card_name, class, type, cost, stats, trait, rarity, effects)
FROM 'D:\Benda_benda_kuliah\BELAJAR\Tugas\Semester4\SBD\svebp03.csv'
DELIMITER ','
CSV HEADER;