-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 14, 2021 at 09:00 PM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 7.3.29

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
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `SubName` varchar(50) NOT NULL,
  `SubID` varchar(15) NOT NULL,
  `HoursPerWeek` smallint(6) NOT NULL,
  `Department` varchar(4) NOT NULL,
  `Semester` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`SubName`, `SubID`, `HoursPerWeek`, `Department`, `Semester`) VALUES
('Data Base Management Systems', '18CS43', 3, 'CSE', 4);

-- --------------------------------------------------------

--
-- Table structure for table `subteach`
--

CREATE TABLE `subteach` (
  `SubID` varchar(15) NOT NULL,
  `TeachID` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subteach`
--

INSERT INTO `subteach` (`SubID`, `TeachID`) VALUES
('18CS43', 'CS001');

-- --------------------------------------------------------

--
-- Table structure for table `teacherallo`
--

CREATE TABLE `teacherallo` (
  `TeachID` varchar(20) NOT NULL,
  `SubID` varchar(15) NOT NULL,
  `Department` varchar(4) NOT NULL,
  `Sem` int(11) NOT NULL,
  `Section` varchar(1) NOT NULL,
  `Batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`TeachName`, `TeachID`, `Department`) VALUES
('Mamatha Bai G', 'CS001', 'CSE');

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
  `Section` varchar(1) NOT NULL,
  `Batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`SubID`);

--
-- Indexes for table `subteach`
--
ALTER TABLE `subteach`
  ADD KEY `SubID` (`SubID`),
  ADD KEY `TeachID` (`TeachID`);

--
-- Indexes for table `teacherallo`
--
ALTER TABLE `teacherallo`
  ADD KEY `SubID` (`SubID`),
  ADD KEY `TeachID` (`TeachID`);

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
-- Constraints for table `subteach`
--
ALTER TABLE `subteach`
  ADD CONSTRAINT `subteach_ibfk_1` FOREIGN KEY (`SubID`) REFERENCES `subjects` (`SubID`),
  ADD CONSTRAINT `subteach_ibfk_2` FOREIGN KEY (`TeachID`) REFERENCES `teachers` (`TeachID`);

--
-- Constraints for table `teacherallo`
--
ALTER TABLE `teacherallo`
  ADD CONSTRAINT `teacherallo_ibfk_1` FOREIGN KEY (`SubID`) REFERENCES `subjects` (`SubID`),
  ADD CONSTRAINT `teacherallo_ibfk_2` FOREIGN KEY (`TeachID`) REFERENCES `teachers` (`TeachID`);

--
-- Constraints for table `timeslots`
--
ALTER TABLE `timeslots`
  ADD CONSTRAINT `timeslots_ibfk_1` FOREIGN KEY (`SubID`) REFERENCES `subjects` (`SubID`),
  ADD CONSTRAINT `timeslots_ibfk_2` FOREIGN KEY (`TeachID`) REFERENCES `teachers` (`TeachID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
