-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 14, 2021 at 03:34 AM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 7.3.29

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `slatedbs`
--

-- --------------------------------------------------------

--
-- Table structure for table `sub-teach`
--

CREATE TABLE `sub-teach` (
  `SubID` varchar(15) NOT NULL,
  `TeachID` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- RELATIONSHIPS FOR TABLE `sub-teach`:
--   `SubID`
--       `subjects` -> `SubID`
--   `TeachID`
--       `teachers` -> `TeachID`
--

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `SubName` varchar(20) NOT NULL,
  `SubID` varchar(15) NOT NULL,
  `HoursPerWeek` smallint(6) NOT NULL,
  `Department` varchar(4) NOT NULL,
  `Semester` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- RELATIONSHIPS FOR TABLE `subjects`:
--

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `TeachName` varchar(25) NOT NULL,
  `TeachID` varchar(20) NOT NULL,
  `Department` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- RELATIONSHIPS FOR TABLE `teachers`:
--

-- --------------------------------------------------------

--
-- Table structure for table `timeslots`
--

CREATE TABLE `timeslots` (
  `TeachID` varchar(20) NOT NULL,
  `SubID` varchar(15) NOT NULL,
  `Hour` int(11) NOT NULL,
  `Day` int(11) NOT NULL,
  `Department` varchar(4) NOT NULL,
  `Semester` int(11) NOT NULL,
  `Section` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- RELATIONSHIPS FOR TABLE `timeslots`:
--   `SubID`
--       `subjects` -> `SubID`
--   `TeachID`
--       `teachers` -> `TeachID`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sub-teach`
--
ALTER TABLE `sub-teach`
  ADD KEY `SubID` (`SubID`),
  ADD KEY `TeachID` (`TeachID`);

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`SubID`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`TeachID`);

--
-- Indexes for table `timeslots`
--
ALTER TABLE `timeslots`
  ADD KEY `SubID` (`SubID`),
  ADD KEY `TeachID` (`TeachID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sub-teach`
--
ALTER TABLE `sub-teach`
  ADD CONSTRAINT `sub-teach_ibfk_1` FOREIGN KEY (`SubID`) REFERENCES `subjects` (`SubID`),
  ADD CONSTRAINT `sub-teach_ibfk_2` FOREIGN KEY (`TeachID`) REFERENCES `teachers` (`TeachID`);

--
-- Constraints for table `timeslots`
--
ALTER TABLE `timeslots`
  ADD CONSTRAINT `timeslots_ibfk_1` FOREIGN KEY (`SubID`) REFERENCES `subjects` (`SubID`),
  ADD CONSTRAINT `timeslots_ibfk_2` FOREIGN KEY (`TeachID`) REFERENCES `teachers` (`TeachID`);
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
