-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2021-01-05 20:40:15
-- 伺服器版本： 10.4.13-MariaDB
-- PHP 版本： 7.3.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `itp4511_db`
--

-- --------------------------------------------------------

--
-- 資料表結構 `disaequip`
--

CREATE TABLE `disaequip` (
  `equipID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `disaequip`
--

INSERT INTO `disaequip` (`equipID`) VALUES
(1);

-- --------------------------------------------------------

--
-- 資料表結構 `equipinfo`
--

CREATE TABLE `equipinfo` (
  `equipID` int(10) NOT NULL,
  `equipName` varchar(30) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `equipinfo`
--

INSERT INTO `equipinfo` (`equipID`, `equipName`, `description`) VALUES
(1, 'dell cool', '000'),
(2, 'dell pc321321321', ' '),
(3, '321', 'qqq'),
(4, 'ool', '');

-- --------------------------------------------------------

--
-- 資料表結構 `inventory`
--

CREATE TABLE `inventory` (
  `equipID` int(10) NOT NULL,
  `quantity` int(5) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `inventory`
--

INSERT INTO `inventory` (`equipID`, `quantity`) VALUES
(1, 22),
(2, 33),
(3, 1),
(4, 0);

-- --------------------------------------------------------

--
-- 資料表結構 `record`
--

CREATE TABLE `record` (
  `recordID` int(10) NOT NULL,
  `recordItemID` int(5) NOT NULL,
  `borrowDate` date DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `status` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  `userID` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `quantity` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- 傾印資料表的資料 `record`
--

INSERT INTO `record` (`recordID`, `recordItemID`, `borrowDate`, `returnDate`, `status`, `userID`, `quantity`) VALUES
(1, 1, NULL, NULL, 'ready', '1', 1),
(2, 1, NULL, NULL, 'ready', '1', 1),
(3, 1, NULL, NULL, 'ready', '1', 1),
(4, 1, NULL, NULL, 'reserved', '1', 1),
(5, 1, NULL, NULL, 'reserved', '1', 1),
(6, 3, '2021-01-03', NULL, 'ready', '1', 10),
(7, 2, NULL, NULL, 'reserved', '1', 1),
(8, 1, NULL, NULL, 'reserved', '1', 1),
(9, 1, NULL, NULL, 'reserved', '1', 1),
(10, 3, NULL, NULL, 'reserved', '1', 10),
(11, 2, NULL, NULL, 'reserved', '1', 1),
(12, 2, NULL, NULL, 'reserved', '1', 1),
(13, 1, NULL, NULL, 'reserved', '1', 1),
(14, 1, NULL, NULL, 'reserved', '1', 1),
(15, 2, NULL, NULL, 'reserved', '2', 1),
(16, 3, NULL, NULL, 'reserved', '1', 1),
(17, 3, '2021-01-02', '2021-01-04', 'ready', '2', 3),
(18, 1, NULL, NULL, 'reserved', '1', 1),
(19, 2, NULL, NULL, 'reserved', '1', 2),
(20, 1, NULL, NULL, 'reserved', '2', 1),
(21, 2, NULL, NULL, 'overdue', '2', 2),
(22, 2, NULL, NULL, 'reserved', '2', 6),
(23, 3, NULL, NULL, 'reserved', '2', 1),
(24, 1, NULL, NULL, 'reserved', '2', 1),
(25, 2, NULL, NULL, 'reserved', '2', 4),
(26, 1, NULL, NULL, 'reserved', '2', 3),
(27, 1, NULL, NULL, 'reserved', '2', 1),
(28, 1, NULL, NULL, 'reserved', '111', 1),
(29, 4, NULL, NULL, 'reserved', '111', 1);

-- --------------------------------------------------------

--
-- 資料表結構 `userinfo`
--

CREATE TABLE `userinfo` (
  `userId` int(10) NOT NULL,
  `firstName` varchar(30) DEFAULT NULL,
  `lastName` varchar(30) DEFAULT NULL,
  `password` varchar(25) NOT NULL,
  `email` varchar(30) NOT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `regDate` date DEFAULT NULL,
  `disabled` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `userinfo`
--

INSERT INTO `userinfo` (`userId`, `firstName`, `lastName`, `password`, `email`, `tel`, `type`, `regDate`, `disabled`) VALUES
(111, '', '', '123', 'erd@g.com', '', 'Senior Technician', '2021-01-03', 0),
(123, '', '', '123', 'r@c.com', '', 'Technician', '2021-01-06', 1),
(222, '', '', '', 'r@g.com', '', 'Technician', '2021-01-06', 0),
(12, 'Rocky', 'Hui', '', 'rockyhuiop@gmail.com', '', 'Student', '2021-01-06', 0),
(333, '', '', '', 'yeah', '', 'Student', '2021-01-04', 0);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `disaequip`
--
ALTER TABLE `disaequip`
  ADD PRIMARY KEY (`equipID`);

--
-- 資料表索引 `equipinfo`
--
ALTER TABLE `equipinfo`
  ADD PRIMARY KEY (`equipID`);

--
-- 資料表索引 `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`equipID`);

--
-- 資料表索引 `record`
--
ALTER TABLE `record`
  ADD PRIMARY KEY (`recordID`),
  ADD KEY `userID` (`userID`),
  ADD KEY `recordItemID` (`recordItemID`);

--
-- 資料表索引 `userinfo`
--
ALTER TABLE `userinfo`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `userId` (`userId`);

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `disaequip`
--
ALTER TABLE `disaequip`
  ADD CONSTRAINT `fk2_equipID` FOREIGN KEY (`equipID`) REFERENCES `equipinfo` (`equipID`);

--
-- 資料表的限制式 `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `fk_equipID` FOREIGN KEY (`equipID`) REFERENCES `equipinfo` (`equipID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
