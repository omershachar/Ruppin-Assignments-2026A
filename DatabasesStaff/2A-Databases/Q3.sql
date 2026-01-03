-- Q3 Postgres solution:
SELECT 
    C.CreationName, 
    U.UserFirstName || ' ' || U.UserLastName AS fullName, -- Changed + to ||
    C.TechnicName,
    C.CreationIsPhysicalShow
FROM tblCreation C 
JOIN tblUser U ON C.ArtistUserID = U.UserID 
WHERE C.CreationStatus = 'Not Available' 
  AND C.CreationName IS NOT NULL 
  AND C.CreationName <> ''
ORDER BY fullName, C.CreationName;

-- Check data:
-- select
--   C.CreationName,
--   U.UserFirstName || ' ' || U.UserLastName as fullName,
--   C.TechnicName,
--   C.CreationIsPhysicalShow,
--   C.CreationStatus

-- from tblCreation C join tblUser U on C.artistuserid=U.userid;

-- SQL Server (T-SQL) - Sol
-- select C.CreationName, U.UserFirstName + ' ' + U.UserLastName as fullName, C.TechnicName,C.CreationIsPhysicalShow
-- from tblCreation C join tblUser U on C.ArtistUserID = U.UserID 
-- where c.CreationStatus = 'Not Available' and C.CreationName is not null and C.CreationName<> ''
-- order by fullName, C.CreationName