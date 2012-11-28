-- phpMyAdmin SQL Dump
-- version 3.3.7deb7
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Ноя 28 2012 г., 14:54
-- Версия сервера: 5.1.63
-- Версия PHP: 5.3.3-7+squeeze14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `checkers`
--

-- --------------------------------------------------------

--
-- Структура таблицы `PLAYER`
--

CREATE TABLE IF NOT EXISTS `PLAYER` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL,
  `password` char(80) NOT NULL,
  `won_games_count` int(11) NOT NULL,
  `lost_games_count` int(11) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Дамп данных таблицы `PLAYER`
--

INSERT INTO `PLAYER` (`id`, `name`, `password`, `won_games_count`, `lost_games_count`) VALUES
(3, 'altaos', '965f2572e5a8fb8a493e824ec9ed49b4', 0, 1),
(2, 'totzhe', '3f27b05da5588f6cef25579a4ee7a7', 0, 0),
(4, 'gamer', '7f99bef877271bf7dd4aee74c0629e32', 1, 3),
(6, 'somebody', '78b9d09661da64f0bc6c146c524bae4a', 2, 3),
(7, 'check', 'ba4439ee9a46d9d9f14c6f88f45f87', 4, 2);
