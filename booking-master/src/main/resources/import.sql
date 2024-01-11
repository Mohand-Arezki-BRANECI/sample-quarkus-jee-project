REPLACE INTO `HotelLocation` ( `locationName`, `latitude`, `longitude`) VALUES ('California','0','0'), ('Paris','1','1');
REPLACE INTO `Hotel` (`locationId`,`hotelName`) VALUES (2,'Hotel Paradiso');
REPLACE INTO `Hotel` (`locationId`,`hotelName`) VALUES (1,'Hotel California');
REPLACE INTO `User` (`firstName`,`lastName`,`email`) VALUES ('Jean','Dupont','jeandupont@gmail.com');
REPLACE INTO `HotelOption` (`optionName`) VALUES ('Spa');
REPLACE INTO `HotelOption` (`optionName`) VALUES ('Pool');
REPLACE INTO `HotelOption` (`optionName`) VALUES ('Parking');
REPLACE INTO `HotelOptions` (`hotels_hotelId`,`options_optionId`) VALUES (1,1);
REPLACE INTO `HotelOptions` (`hotels_hotelId`,`options_optionId`) VALUES (1,2);
REPLACE INTO `HotelOptions` (`hotels_hotelId`,`options_optionId`) VALUES (2,2);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (1,5,'2024-12-12',2);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (1,3,'2023-12-13',3);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (1,7,'2024-12-13',2);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (2,10,'2023-12-31',2);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (2,2,'2023-12-31',3);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (2,2,'2024-01-12',6);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (2,2,'2024-01-13',3);
REPLACE INTO `Availability` (`hotelId`,`numberFreeRooms`,`date`,`bedsNumber`) VALUES (2,2,'2024-01-14',2);
