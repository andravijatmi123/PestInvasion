-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Okt 2024 pada 16.43
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `player`
--

CREATE TABLE `player` (
  `id` int(100) NOT NULL,
  `uname` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `player`
--

INSERT INTO `player` (`id`, `uname`, `password`) VALUES
(1, 'asep', 'abcd123'),
(2, 'nurdin', '123'),
(3, 'nurdin', '123'),
(4, 'tiara', 'basis'),
(5, 'reyvan', 'pulang jam 8'),
(6, 'reyvan', 'pulang jam 8'),
(7, 'reyvan', 'pulang jam 8'),
(8, 'reyvan', 'pulang jam 8'),
(9, 'reyvan', 'pulang jam 8'),
(10, 'reyvan', 'pulang jam 8'),
(11, 'reyvan', 'pulang jam 8'),
(12, 'reyvan', 'pulang jam 8'),
(13, 'pram', '456'),
(14, 'pramud', '456'),
(15, 'pramuda', '456'),
(16, 'pramuda', '456'),
(17, 'pramudassss', '69'),
(18, 'dajjal', 'janda '),
(19, 'andra', '123');

-- --------------------------------------------------------

--
-- Struktur dari tabel `record`
--

CREATE TABLE `record` (
  `id` int(250) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `idPlayer` int(100) NOT NULL,
  `score` bigint(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `record`
--

INSERT INTO `record` (`id`, `date`, `idPlayer`, `score`) VALUES
(8, '2024-10-18 10:35:41', 15, 2000),
(9, '2024-10-18 10:47:21', 17, 2000),
(10, '2024-10-18 10:52:00', 18, 2000),
(11, '2024-10-20 13:48:58', 19, 2000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `record`
--
ALTER TABLE `record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idPlayer` (`idPlayer`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `player`
--
ALTER TABLE `player`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT untuk tabel `record`
--
ALTER TABLE `record`
  MODIFY `id` int(250) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `record`
--
ALTER TABLE `record`
  ADD CONSTRAINT `record_ibfk_1` FOREIGN KEY (`idPlayer`) REFERENCES `player` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
