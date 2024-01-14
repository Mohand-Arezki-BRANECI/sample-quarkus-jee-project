REPLACE INTO `Bank` (`bankId`,`bankName`) VALUES ("1000","Bank of America");
REPLACE INTO `Account` (`accountId`, `bankId`,`ownerFirstName`, `ownerLastName`, `email`, `password`, `balance`) VALUES (1,1000,'MR. America','America', 'test', 'test', 1000);
REPLACE INTO `Account` (`accountId`, `bankId`,`ownerFirstName`, `ownerLastName`, `email`, `password`, `balance`) VALUES (2,1000,'Booking','Account', 'booking@booking.com', 'test', 0);
