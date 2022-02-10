-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema progetto-ispw
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `progetto-ispw` ;

-- -----------------------------------------------------
-- Schema progetto-ispw
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `progetto-ispw` DEFAULT CHARACTER SET utf8 ;
USE `progetto-ispw` ;

-- -----------------------------------------------------
-- Table `progetto-ispw`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `progetto-ispw`.`users` ;

CREATE TABLE IF NOT EXISTS `progetto-ispw`.`users` (
  `enroll_number` INT NOT NULL,
  `full_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `type` ENUM('passenger', 'driver', 'verifier', 'admin') NOT NULL,
  PRIMARY KEY (`enroll_number`),
  UNIQUE INDEX `enroll_number_UNIQUE` (`enroll_number` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progetto-ispw`.`rides`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `progetto-ispw`.`rides` ;

CREATE TABLE IF NOT EXISTS `progetto-ispw`.`rides` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` VARCHAR(11) NOT NULL,
  `time` VARCHAR(5) NOT NULL,
  `start` VARCHAR(45) NOT NULL,
  `destination` VARCHAR(45) NOT NULL,
  `driver` INT NOT NULL,
  `number_of_seats` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `ride_driver_idx` (`driver` ASC) VISIBLE,
  CONSTRAINT `ride_driver`
    FOREIGN KEY (`driver`)
    REFERENCES `progetto-ispw`.`users` (`enroll_number`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progetto-ispw`.`seat_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `progetto-ispw`.`seat_request` ;

CREATE TABLE IF NOT EXISTS `progetto-ispw`.`seat_request` (
  `ride` INT NOT NULL,
  `passenger` INT NOT NULL,
  INDEX `seatrequest_ride_idx` (`ride` ASC) VISIBLE,
  INDEX `seatrequest_passenger_idx` (`passenger` ASC) VISIBLE,
  UNIQUE INDEX `unique_ride_requestant` (`ride` ASC, `passenger` ASC) VISIBLE,
  CONSTRAINT `seatrequest_ride`
    FOREIGN KEY (`ride`)
    REFERENCES `progetto-ispw`.`rides` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `seatrequest_passenger`
    FOREIGN KEY (`passenger`)
    REFERENCES `progetto-ispw`.`users` (`enroll_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progetto-ispw`.`seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `progetto-ispw`.`seat` ;

CREATE TABLE IF NOT EXISTS `progetto-ispw`.`seat` (
  `ride` INT NOT NULL,
  `passenger` INT NOT NULL,
  INDEX `seat_ride_idx` (`ride` ASC) VISIBLE,
  INDEX `seat_passenger_idx` (`passenger` ASC) VISIBLE,
  UNIQUE INDEX `unique_ride_passenger` (`ride` ASC, `passenger` ASC) VISIBLE,
  CONSTRAINT `seat_ride`
    FOREIGN KEY (`ride`)
    REFERENCES `progetto-ispw`.`rides` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `seat_passenger`
    FOREIGN KEY (`passenger`)
    REFERENCES `progetto-ispw`.`users` (`enroll_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progetto-ispw`.`license`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `progetto-ispw`.`license` ;

CREATE TABLE IF NOT EXISTS `progetto-ispw`.`license` (
  `code` VARCHAR(45) NOT NULL,
  `expiration` VARCHAR(11) NOT NULL,
  `owner` INT NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  INDEX `fk_license_owner_idx` (`owner` ASC) VISIBLE,
  CONSTRAINT `fk_license_owner`
    FOREIGN KEY (`owner`)
    REFERENCES `progetto-ispw`.`users` (`enroll_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progetto-ispw`.`license_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `progetto-ispw`.`license_request` ;

CREATE TABLE IF NOT EXISTS `progetto-ispw`.`license_request` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `license_code` VARCHAR(45) NOT NULL,
  `license_expiration` VARCHAR(11) NOT NULL,
  `request_date` VARCHAR(11) NOT NULL,
  `status` ENUM('pending', 'rejected', 'success') NOT NULL,
  `verifier` INT NULL,
  `verification_date` VARCHAR(11) NULL,
  PRIMARY KEY (`id`),
  INDEX `licenserequest_requestant_idx` (`user` ASC) VISIBLE,
  INDEX `licenserequest_verifier_idx` (`verifier` ASC) VISIBLE,
  CONSTRAINT `licenserequest_requestant`
    FOREIGN KEY (`user`)
    REFERENCES `progetto-ispw`.`users` (`enroll_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `licenserequest_verifier`
    FOREIGN KEY (`verifier`)
    REFERENCES `progetto-ispw`.`users` (`enroll_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `progetto-ispw`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `progetto-ispw`;
INSERT INTO `progetto-ispw`.`users` (`enroll_number`, `full_name`, `password`, `type`) VALUES (1, 'Mario Rossi', '4e85eddf886882ca7cb893ddd3f07051', '1');
INSERT INTO `progetto-ispw`.`users` (`enroll_number`, `full_name`, `password`, `type`) VALUES (2, 'Giuseppe Verdi', 'e2d45d57c7e2941b65c6ccd64af4223e', '2');
INSERT INTO `progetto-ispw`.`users` (`enroll_number`, `full_name`, `password`, `type`) VALUES (111, 'Nome Cognome', '21232f297a57a5a743894a0e4a801fc3', '4');
INSERT INTO `progetto-ispw`.`users` (`enroll_number`, `full_name`, `password`, `type`) VALUES (11, 'Tizio Verificatore', '0b5f8f06bafb3828f619f6f96fc6adb2', '3');
INSERT INTO `progetto-ispw`.`users` (`enroll_number`, `full_name`, `password`, `type`) VALUES (228612, 'Filippo Maria Briscese', '1a1dc91c907325c69271ddf0c944bc72', '1');

COMMIT;


-- -----------------------------------------------------
-- Data for table `progetto-ispw`.`rides`
-- -----------------------------------------------------
START TRANSACTION;
USE `progetto-ispw`;
INSERT INTO `progetto-ispw`.`rides` (`id`, `date`, `time`, `start`, `destination`, `driver`, `number_of_seats`) VALUES (1, '2022-03-01', '09:05', 'Via Tuscolana', 'Via del politecnico', 2, 3);
INSERT INTO `progetto-ispw`.`rides` (`id`, `date`, `time`, `start`, `destination`, `driver`, `number_of_seats`) VALUES (2, '2022-04-01', '16:30', 'Via del politecnico', 'Via Tuscolana', 2, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `progetto-ispw`.`seat_request`
-- -----------------------------------------------------
START TRANSACTION;
USE `progetto-ispw`;
INSERT INTO `progetto-ispw`.`seat_request` (`ride`, `passenger`) VALUES (1, 1);
INSERT INTO `progetto-ispw`.`seat_request` (`ride`, `passenger`) VALUES (1, 228612);

COMMIT;


-- -----------------------------------------------------
-- Data for table `progetto-ispw`.`seat`
-- -----------------------------------------------------
START TRANSACTION;
USE `progetto-ispw`;
INSERT INTO `progetto-ispw`.`seat` (`ride`, `passenger`) VALUES (2, 228612);

COMMIT;


-- -----------------------------------------------------
-- Data for table `progetto-ispw`.`license`
-- -----------------------------------------------------
START TRANSACTION;
USE `progetto-ispw`;
INSERT INTO `progetto-ispw`.`license` (`code`, `expiration`, `owner`) VALUES ('c0dic3Patent3', '2024-08-15', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `progetto-ispw`.`license_request`
-- -----------------------------------------------------
START TRANSACTION;
USE `progetto-ispw`;
INSERT INTO `progetto-ispw`.`license_request` (`id`, `user`, `license_code`, `license_expiration`, `request_date`, `status`, `verifier`, `verification_date`) VALUES (1, 1, 'patent3non4pprovata', '2024-03-23', '2021-12-15', 'rejected', 11, '2021-12-15');

COMMIT;

