REPLACE INTO `HotelLocation` ( `locationName`, `latitude`, `longitude`) VALUES ('California','0','0'), ('Paris','1','1');
REPLACE INTO `Hotel` (`locationId`,`hotelName`) VALUES (2,'Hotel Paradiso');
REPLACE INTO `Hotel` (`locationId`,`hotelName`) VALUES (1,'Hotel California');

REPLACE INTO `User` (`firstName`,`lastName`,`email`,`password`) VALUES ('Jean','Dupont','admin','admin');

REPLACE INTO `HotelOption` (`optionName`,`optionPrice`,`hotelId`) VALUES ('Spa',50,1);
REPLACE INTO `HotelOption` (`optionName`,`optionPrice`,`hotelId`) VALUES ('Pool',40,1);
REPLACE INTO `HotelOption` (`optionName`,`optionPrice`,`hotelId`) VALUES ('Parking',15.6,2);

REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (1,5,'2024-12-12',2,45.5);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (1,3,'2023-12-13',3,34);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (1,7,'2024-12-13',2,100.5);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (2,10,'2023-12-31',2,78);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (2,2,'2023-12-31',3,90);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (2,2,'2024-01-12',6,67);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (2,2,'2024-01-13',3,123);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`,`roomPrice`) VALUES (2,2,'2024-01-14',2,89);


REPLACE INTO `Reservation` (`idReservation`, `hotelId`, `userId`, `reservationNumber`, `status`, `startDate`, `endDate`, `bedsNumber`, `bookingDate`)
VALUES (1,1,1,587152403,PENDING,2024-12-12 00:00:00.000000,2024-12-13 00:00:00.000000,2,null);

