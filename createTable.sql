CREATE TABLE `evoting`.`candidate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `profileImg` BLOB NOT NULL,
  `partId` INT NOT NULL,
  `statesId` INT NOT NULL,
  PRIMARY KEY (`Id`));


CREATE TABLE `evoting`.`casting_vote` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL,
  `candidateId` INT NULL,
  `statesId` INT NULL,
  `isVoted` TINYINT NULL,
  `timestamp` DATETIME NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `evoting`.`final_result` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `statesId` INT NOT NULL,
  `candidateId` INT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  `isVictory` TINYINT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `evoting`.`party` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `logo` BLOB NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `slogan` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `evoting`.`states` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `evoting`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(200) NULL,
  `lastName` VARCHAR(200) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(150) NULL,
  `age` INT NULL,
  `gender` VARCHAR(45) NULL,
  `ic` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  `email` VARCHAR(100) NULL,
  `phone` VARCHAR(45) NULL,
  `statesId` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `evoting`.`voting_result` (
  `id` INT NOT NULL,
  `voteCount` INT NULL,
  `candidateId` INT NULL,
  `statesId` INT NULL,
  `timestamp` DATETIME NULL,
  PRIMARY KEY (`id`))