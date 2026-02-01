select p1.CityName, p1.PhysicalGalleryAddress as address1, p2.PhysicalGalleryAddress as address2 , p3.PhysicalGalleryAddress as adress3
from tblPhysicalGallery p1 inner join tblPhysicalGallery p2 on p1.CityName= p2.CityName inner join tblPhysicalGallery p3 on p1.CityName = p3.CityName
where p1.PhysicalGalleryID < p2.PhysicalGalleryID and p2.PhysicalGalleryID < p3.PhysicalGalleryID
