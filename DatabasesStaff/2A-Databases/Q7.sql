-- Q7
-- SELECT DISTINCT 
--     E.ExhibitionID, 
--     E.ExhibitionName,
--     E.ExhibitionDateStart, 
--     E.ExhibitionDateEnd
-- FROM tblCVisitedE C1 
-- JOIN tblCVisitedE C2 ON (C1.ExhibitionID = C2.ExhibitionID AND C1.PhysicalGalleryID = C2.PhysicalGalleryID)
-- JOIN tblCVisitedE C3 ON (C1.ExhibitionID = C3.ExhibitionID AND C1.PhysicalGalleryID = C3.PhysicalGalleryID)
-- JOIN tblExhibition E ON (C1.ExhibitionID = E.ExhibitionID AND C1.PhysicalGalleryID = E.PhysicalGalleryID)
-- WHERE E.ExhibitionStatus = 'Active' 
--   AND (E.ExhibitionDateEnd - E.ExhibitionDateStart) > 10
--   AND (C1.CriticUserID < C2.CriticUserID) 
--   AND (C2.CriticUserID < C3.CriticUserID);

-- Check data:
SELECT DISTINCT 
    E.ExhibitionID || ' - ' || E.ExhibitionName as Exhibition_ID_Name,
    E.ExhibitionDateStart || ' -> ' || E.ExhibitionDateEnd as Exhibition_Date_Range, 
    V.CriticUserID
from tblExhibition E
join tblCVisitedE V on (E.ExhibitionID = V.ExhibitionID and E.PhysicalGalleryID = V.PhysicalGalleryID);

-- Op2 - Q7:
SELECT DISTINCT 
    E.ExhibitionID, 
    E.ExhibitionName,
    E.ExhibitionDateStart, 
    E.ExhibitionDateEnd
from tblExhibition E
where E.ExhibitionStatus = 'Active' 
  and (E.ExhibitionDateEnd - E.ExhibitionDateStart) > 10
  and (
    select count(distinct V.CriticUserID)
    from tblCVisitedE V
    where V.ExhibitionID = E.ExhibitionID and V.PhysicalGalleryID = E.PhysicalGalleryID
  ) >= 3;

-- SQL Server (T-SQL) - Sol:

-- select distinct
-- E.ExhibitionID,
-- E.ExhibitionName,
-- E.ExhibitionDateStart,
-- E.ExhibitionDateEnd
-- from tblCVisitedE C1
-- join tblCVisitedE C2 on (C1.ExhibitionID = C2.ExhibitionID and C1.PhysicalGalleryID = C2.PhysicalGalleryID)
--  join tblCVisitedE C3 on (C1.ExhibitionID = C3.ExhibitionID and C1.PhysicalGalleryID = C3.PhysicalGalleryID)
--  join tblExhibition E on (C1.ExhibitionID = E.ExhibitionID and C1.PhysicalGalleryID = E.PhysicalGalleryID)
-- where E.ExhibitionStatus = N'Active'
-- and DATEDIFF(day,E.ExhibitionDateStart,E.ExhibitionDateEnd) > 10
-- and (C1.CriticUserID < C2.CriticUserID)
-- and (C2.CriticUserID <C3.CriticUserID)
