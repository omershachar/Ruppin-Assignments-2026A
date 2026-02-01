-- =============================================
-- 1. NUCLEAR CLEANUP
-- (Wipes all data so we start fresh and avoid ID mismatches)
-- =============================================
TRUNCATE tblUser, tblArtistUser, tblCriticUser, tblCuratorUser, 
         tblCity, tblTechnic, tblTag, tblGallery, tblPhysicalGallery, 
         tblDigitalGallery, tblCreation, tblArtistUserFollow, 
         tblTAssociatedC, tblExhibition, tblCVisitedE, tblReview, tblResponse 
         RESTART IDENTITY CASCADE;

-- =============================================
-- 2. INSERT USERS (With EXPLICIT IDs)
-- We force the IDs (10000, 10001...) so they match the other tables perfectly.
-- =============================================
INSERT INTO tblUser (UserID, UserEmail, UserPassword, UserFirstName, UserLastName, UserPhoneNumber, UserBirthDate)
VALUES 
-- Artists
(10000, 'pablo@art.com', 'Pabloo12', 'Pablo', 'Picasso', '0501234567', '1980-10-25'),      
(10001, 'vincent@art.com', 'Vincen89', 'Vincent', 'VanGogh', '0501234568', '2001-03-30'),  
(10002, 'claude@art.com', 'Claude99', 'Claude', 'Monet', '0501234569', '2002-11-14'),      
(10003, 'salvador@art.com', 'Salvad55', 'Salvador', 'Dali', '0501234570', '1995-05-11'),   

-- Critics
(10004, 'critic1@rev.com', 'Antonn75', 'Anton', 'Ego', '0521111111', '1975-08-10'),
(10005, 'critic2@rev.com', 'Ronaaa85', 'Rona', 'Ramon', '0522222222', '1985-05-05'),
(10006, 'critic3@rev.com', 'Yossii90', 'Yossi', 'Cohen', '0523333333', '1990-12-12'),

-- Curators
(10007, 'curator1@gal.com', 'Peggyy60', 'Peggy', 'Guggenheim', '0544444444', '1960-01-01'),
(10008, 'curator2@gal.com', 'Larryy65', 'Larry', 'Gagosian', '0545555555', '1965-02-02');

-- =============================================
-- 3. INSERT SUB-TYPES (Artists, Critics, Curators)
-- =============================================
INSERT INTO tblArtistUser (ArtistUserID, ArtistPicture) VALUES 
(10000, 'pablo.jpg'),
(10001, 'vincent.jpg'),
(10002, 'claude.jpg'),
(10003, 'salvador.jpg');

INSERT INTO tblCriticUser (CriticUserID) VALUES 
(10004), (10005), (10006);

INSERT INTO tblCuratorUser (CuratorUserID) VALUES 
(10007), (10008);

-- =============================================
-- 4. INSERT LOOKUP DATA
-- =============================================
INSERT INTO tblCity (CityName) VALUES ('Tel Aviv'), ('Jerusalem'), ('Haifa'), ('Paris');
INSERT INTO tblTechnic (TechnicName) VALUES ('Oil on Canvas'), ('Digital Art'), ('Sculpture'), ('Watercolor');
INSERT INTO tblTag (TagName) VALUES ('Modern'), ('Abstract'), ('Surrealism'), ('Classic');

-- =============================================
-- 5. INSERT CREATIONS
-- =============================================
INSERT INTO tblCreation (ArtistUserID, CreationID, CreationName, CreationDescription, CreationDate, TechnicName, CreationPrice, CreationStatus, CreationIsPhysicalShow) 
VALUES 
-- Pablo (10000)
(10000, 1, 'Guernica', 'War painting', '1937-01-01', 'Oil on Canvas', 1000000, 'Not Available', TRUE), 
(10000, 2, 'The Old Guitarist', 'Blue period', '1903-01-01', 'Oil on Canvas', 500000, 'Available', TRUE),

-- Vincent (10001)
(10001, 1, 'Starry Night', 'Swirling sky', '1889-06-01', 'Oil on Canvas', 200, 'Available', FALSE),
(10001, 2, 'Sunflowers', 'Yellow flowers', '1888-08-01', 'Watercolor', 150, 'Available', FALSE),

-- Claude (10002)
(10002, 1, 'Water Lilies', 'Garden pond', '1919-01-01', 'Oil on Canvas', 300, 'Not Available', TRUE),

-- Salvador (10003)
(10003, 1, 'Persistence of Memory', 'Melting clocks', '1931-01-01', 'Oil on Canvas', 4000, 'Available', TRUE);

-- =============================================
-- 6. INSERT FOLLOWS
-- =============================================
INSERT INTO tblArtistUserFollow (FollowerID, FollowedID, ApprovalDate) VALUES 
(10002, 10000, '2023-01-01'), -- Claude follows Pablo
(10003, 10000, '2023-02-01'); -- Salvador follows Pablo

-- =============================================
-- 7. INSERT GALLERIES
-- =============================================
INSERT INTO tblGallery (CuratorUserID, GalleryName, GalleryEmail) VALUES 
(10007, 'Tel Aviv Museum', 'contact@tam.co.il'),  
(10007, 'Gordon Gallery', 'info@gordon.co.il'),   
(10008, 'Sommer Art', 'art@sommer.co.il'),        
(10008, 'Haifa Museum', 'haifa@museum.co.il'),    
(10008, 'Virtual Space', 'web@virtual.com');      

-- =============================================
-- 8. INSERT PHYSICAL GALLERIES
-- =============================================
INSERT INTO tblPhysicalGallery (PhysicalGalleryID, CityName, PhysicalGalleryAddress) VALUES 
(1, 'Tel Aviv', 'Shaul Hamelech 27'),
(2, 'Tel Aviv', 'Hazrem 5'),
(3, 'Tel Aviv', 'Rothschild 13'), 
(4, 'Haifa', 'Shabtai Levi 26');

-- =============================================
-- 9. INSERT DIGITAL GALLERIES
-- =============================================
INSERT INTO tblDigitalGallery (DigitalGalleryID, ArtistUserID1, CreationID1, ArtistUserID2, CreationID2, ArtistUserID3, CreationID3) VALUES 
(5, 10000, 1, 10000, 2, 10003, 1);

-- =============================================
-- 10. INSERT EXHIBITIONS
-- =============================================
INSERT INTO tblExhibition (ExhibitionID, PhysicalGalleryID, ExhibitionName, ExhibitionDateStart, ExhibitionDateEnd, ExhibitionSubject, ExhibitionStatus) VALUES 
(1, 1, 'Modern Masters', '2025-01-01', '2026-06-01', 'Modern Art', 'Active'),
(2, 2, 'History of Blue', '2020-01-01', '2020-02-01', 'Colors', 'Inactive'),
(3, 3, 'Pop Up Art', '2025-12-01', '2025-12-05', 'Street Art', 'Active');

-- =============================================
-- 11. INSERT VISITS
-- =============================================
INSERT INTO tblCVisitedE (CriticUserID, ExhibitionID, PhysicalGalleryID, TicketPrice, VisitDate) VALUES 
(10004, 1, 1, 50.00, '2025-02-01'), 
(10005, 1, 1, 50.00, '2025-02-02'), 
(10006, 1, 1, 50.00, '2025-02-03'), 
(10004, 2, 2, 30.00, '2020-01-15');

-- =============================================
-- 12. INSERT REVIEWS
-- =============================================
INSERT INTO tblReview (CriticUserID, ExhibitionID, PhysicalGalleryID, ReviewDegree, ReviewText) VALUES 
(10004, 1, 1, 5, 'Amazing exhibition!'),
(10005, 1, 1, 2, 'It was okay...'),
(10004, 2, 2, 5, 'Loved the blue!');

-- =============================================
-- 13. INSERT TAG ASSOCIATIONS
-- =============================================
INSERT INTO tblTAssociatedC (TagName, ArtistUserID, CreationID, MatchToCreation) VALUES 
('Modern', 10000, 1, 5),
('Surrealism', 10003, 1, 5),
('Abstract', 10001, 1, 4);