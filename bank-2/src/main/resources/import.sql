REPLACE INTO `Bank` (`bankId`,`bankName`) VALUES ("999","Bank of Ireland");
REPLACE INTO `Account` (`accountId`, `bankId`,`ownerFirstName`, `ownerLastName`, `email`, `password`, `balance`) VALUES (1,999,'MR.Ireland','Ireland', 'test', 'test', 1000);
