REPLACE INTO `HotelLocation` (`idLocation`, `locationName`, `latitude`, `longitude`) VALUES (1,'Bordeaux Akea Arena','0','0'), (2,'Paris','1','1');
REPLACE INTO `Bank` (`bankId`,`bankName`) VALUES ("1","UBS"), ("2","BNP");
REPLACE INTO `Account` (`accountId`, `bankId`,`ownerFirstName`, `ownerLastName`, `email`, `password`, `balance`) VALUES (1,1,'Etienne','Baumgartner', 'baume96@gmail.com', 'test', 1000),(2,2,'Test','Tester', 't@t.com', 'test', 100);
