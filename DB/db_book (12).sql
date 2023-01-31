-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 31, 2023 alle 07:40
-- Versione del server: 10.4.25-MariaDB
-- Versione PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_book`
--

DELIMITER $$
--
-- Procedure
--
CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getAllDocenti` ()   BEGIN
        select distinct (u.ID), Email, Password, Nome, Cognome, Ruolo, Attivo, PF, Stelle
        from lezione l join utente u on u.ID = l.Docente_ID
        where l.Stato='Libera' AND ((l.Data > CURRENT_DATE)OR ((l.Data = CURRENT_DATE)AND ((CAST(l.Ora AS TIME ))>CURRENT_TIME) ));
    END$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getCorsiByDoc` (IN `email` VARCHAR(50))   if(email='all') THEN
    BEGIN
        select distinct (c.nome) AS Corsi
        from utente u join lezione l on u.ID = l.Docente_ID join corso c on c.ID=l.Corso_ID
        where l.Stato='Libera' AND ((l.Data > CURRENT_DATE)OR ((l.Data = CURRENT_DATE)AND ((CAST(l.Ora AS TIME ))>CURRENT_TIME) ));
    END;
    ELSE
        BEGIN
        select distinct (c.nome)AS Corsi
        from utente u join lezione l on u.ID = l.Docente_ID join corso c on c.ID=l.Corso_ID
        where u.Email=email AND l.Stato='Libera' AND ((l.Data > CURRENT_DATE)OR ((l.Data = CURRENT_DATE)AND ((CAST(l.Ora AS TIME ))>CURRENT_TIME) ));
        END;
    END IF$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getDocByCorso` (IN `corso` VARCHAR(50))   BEGIN
        select  distinct(u.Email),u.ID, u.Password, u.Nome, u.Cognome, u.Ruolo, u.Attivo, u.PF, u.Stelle
        from corso c join lezione l on c.ID = l.Corso_ID join utente u on u.ID = l.Docente_ID
        where c.nome=corso and u.Attivo='true'and l.Stato='Libera'AND ((l.Data > CURRENT_DATE)OR ((l.Data = CURRENT_DATE)AND ((CAST(l.Ora AS TIME ))>CURRENT_TIME) ));
    END$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getLezioniLibereByDocente` (IN `email` VARCHAR(50))   BEGIN
        Select l.Data,l.Ora,l.prezzo,c.nome,u.Nome,u.Cognome,u.Email,u.PF
        From lezione l join utente u on u.ID = l.Docente_ID join corso c on c.ID = l.Corso_ID
        Where u.Email=email AND l.Stato = 'Libera' ;
    END$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getLezioniPrenotateByUt` (IN `Staus` VARCHAR(30), IN `email` VARCHAR(30))   IF (Staus = 'Prenotata' & email IS NOT NULL) THEN
        BEGIN
            select l.Data,l.Ora,l.Stato,c2.nome,u2.Nome,u2.Cognome,l.prezzo,l.Valutazione
            from utente u join lezione l on u.ID = l.Utente_ID join corso c2 on c2.ID = l.Corso_ID join utente u2 on u2.ID = l.Docente_ID
            where  l.Stato='Prenotata' AND u.Email=@email;
        END;
        ELSE IF(Staus = 'Conclusa' && email IS NOT NULL) THEN
              BEGIN
                select l.Data,l.Ora,l.Stato,c2.nome,u2.Nome,u2.Cognome,l.prezzo,l.Valutazione
                from utente u join lezione l on u.ID = l.Utente_ID join corso c2 on c2.ID = l.Corso_ID join utente u2 on u2.ID = l.Docente_ID
                where  l.Stato='Conclusa' AND u.Email=@email;
              END;


        ELSE
            BEGIN
            select l.Data,l.Ora,l.Stato,c2.nome,u2.Nome,u2.Cognome,l.prezzo,l.Valutazione
            from utente u join lezione l on u.ID = l.Utente_ID join corso c2 on c2.ID = l.Corso_ID join utente u2 on u2.ID = l.Docente_ID
            where  l.Stato='Libera';
        END;

        END IF;

        END IF$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getUtente` (IN `email` VARCHAR(50))   BEGIN
            select u.Nome,u.Cognome,u.PF,u.Ruolo
            from utente u
            where   u.Email=email;
        END$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `inserimentoDocenza` (IN `Doc_ID` INT, IN `Cor_ID` INT, IN `Data_start` VARCHAR(30), IN `Data_end` VARCHAR(30))   BEGIN
  DECLARE actdate,end_date DATE; 
    SET actdate = CAST(Data_start AS DATE );
    SET end_date = CAST(Data_end AS DATE );

    WHILE actdate<end_date DO
        IF WEEKDAY(actdate)<6 THEN
            INSERT INTO lezione (Data, Ora, Stato, Corso_ID, Docente_ID, prezzo, Utente_ID, Valutazione) VALUES (actdate, '15:00', 'Libera', Cor_ID, Doc_ID, '10', '0', '0');
            INSERT INTO lezione (Data, Ora, Stato, Corso_ID, Docente_ID, prezzo, Utente_ID, Valutazione) VALUES (actdate, '16:00', 'Libera', Cor_ID, Doc_ID, '10', '0', '0');
            INSERT INTO lezione (Data, Ora, Stato, Corso_ID, Docente_ID, prezzo, Utente_ID, Valutazione) VALUES (actdate, '17:00', 'Libera', Cor_ID, Doc_ID, '10', '0', '0');
            INSERT INTO lezione (Data, Ora, Stato, Corso_ID, Docente_ID, prezzo, Utente_ID, Valutazione) VALUES (actdate, '18:00', 'Libera', Cor_ID, Doc_ID, '10', '0', '0');
        end if;


        SET actdate = DATE_ADD(actdate,INTERVAL 1 DAY);
        END WHILE ;
        INSERT INTO docenza (Corso_ID, Docente_ID) VALUES (Cor_ID, Doc_ID);

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struttura della tabella `corso`
--

CREATE TABLE `corso` (
  `ID` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `Attivo` enum('true','false') NOT NULL DEFAULT 'true'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `corso`
--

INSERT INTO `corso` (`ID`, `nome`, `Attivo`) VALUES
(2, 'Fisica', 'true'),
(3, 'Latino', 'true'),
(4, 'Italiano', 'true'),
(7, 'Algoritmi', 'true'),
(8, 'Algebra', 'true');

-- --------------------------------------------------------

--
-- Struttura della tabella `docenza`
--

CREATE TABLE `docenza` (
  `Docente_ID` int(11) NOT NULL,
  `Corso_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `docenza`
--

INSERT INTO `docenza` (`Docente_ID`, `Corso_ID`) VALUES
(5, 2),
(17, 7);

-- --------------------------------------------------------

--
-- Struttura della tabella `lezione`
--

CREATE TABLE `lezione` (
  `Data` date NOT NULL,
  `Ora` enum('15:00','16:00','17:00','18:00') NOT NULL,
  `Stato` enum('Libera','Conclusa','Prenotata') NOT NULL,
  `Corso_ID` int(11) NOT NULL,
  `Docente_ID` int(11) NOT NULL,
  `prezzo` double NOT NULL DEFAULT 10,
  `Utente_ID` int(11) NOT NULL DEFAULT 0,
  `Valutazione` enum('1','2','3','4','5','0') DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `lezione`
--

INSERT INTO `lezione` (`Data`, `Ora`, `Stato`, `Corso_ID`, `Docente_ID`, `prezzo`, `Utente_ID`, `Valutazione`) VALUES
('2023-01-31', '15:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-01-31', '16:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-01-31', '17:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-01-31', '18:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-01', '15:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-01', '16:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-01', '17:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-01', '18:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-02', '15:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-02', '16:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-02', '17:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-02', '18:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-03', '15:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-03', '16:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-03', '17:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-03', '18:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-04', '15:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-04', '16:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-04', '17:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-04', '18:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-06', '15:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-06', '16:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-06', '17:00', 'Libera', 7, 17, 10, 0, '0'),
('2023-02-06', '18:00', 'Libera', 7, 17, 10, 0, '0');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `ID` int(11) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Ruolo` enum('utente','docente','admin') NOT NULL,
  `Attivo` enum('true','false') NOT NULL DEFAULT 'true',
  `PF` varchar(100) NOT NULL DEFAULT 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAbBB_oglMX609dUtMkvQcL3nmKuqOQmVfR2VIj0he6Q&s',
  `Stelle` float(10,1) NOT NULL DEFAULT 0.0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`ID`, `Email`, `Password`, `Nome`, `Cognome`, `Ruolo`, `Attivo`, `PF`, `Stelle`) VALUES
(1, 'olly@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'oliviu', 'gratii', 'admin', 'true', 'C:/foto.jpeg', 0.0),
(2, 'matt@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'matteo', 'barone', 'admin', 'true', 'https://thispersondoesnotexist.com/image', 0.0),
(4, 'luigi@gmail.com', 'C4CA4238A0B923820DCC509A6F75849B', 'luigi', 'bianchi', 'utente', 'true', 'https://thispersondoesnotexist.com/image', 0.0),
(5, 'albero@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Alberto', 'Angela', 'docente', 'true', 'https://thispersondoesnotexist.com/image', 4.0),
(6, 'barbero@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Alessandro', 'Barbero', 'docente', 'true', 'https://www.pngmart.com/files/21/Admin-Profile-Vector-PNG-Clipart.png', 5.0),
(7, 'super_mario@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Mario', 'Super', 'utente', 'true', 'https://thispersondoesnotexist.com/image', 0.0),
(13, 'topolino@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Topolino', 'Disney', 'docente', 'true', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAbBB_oglMX609dUtMkvQcL3nmKuqOQmVfR2VIj0he6Q&s', 0.0),
(17, 'z', 'c4ca4238a0b923820dcc509a6f75849b', 'Olly', 'Gr', 'docente', 'true', 'https://upload.wikimedia.org/wikipedia/commons/2/23/Lil-Peep_PrettyPuke_Photoshoot.png', 5.0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `corso`
--
ALTER TABLE `corso`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Indici per le tabelle `docenza`
--
ALTER TABLE `docenza`
  ADD KEY `Corso_ID` (`Corso_ID`),
  ADD KEY `Docente_ID` (`Docente_ID`);

--
-- Indici per le tabelle `lezione`
--
ALTER TABLE `lezione`
  ADD PRIMARY KEY (`Data`,`Ora`,`Docente_ID`,`Corso_ID`) USING BTREE,
  ADD KEY `Docente_ID` (`Docente_ID`),
  ADD KEY `Corso_ID` (`Corso_ID`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `corso`
--
ALTER TABLE `corso`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `docenza`
--
ALTER TABLE `docenza`
  ADD CONSTRAINT `docenza_ibfk_1` FOREIGN KEY (`Corso_ID`) REFERENCES `corso` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `docenza_ibfk_2` FOREIGN KEY (`Docente_ID`) REFERENCES `utente` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Limiti per la tabella `lezione`
--
ALTER TABLE `lezione`
  ADD CONSTRAINT `lezione_ibfk_2` FOREIGN KEY (`Docente_ID`) REFERENCES `utente` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `lezione_ibfk_3` FOREIGN KEY (`Corso_ID`) REFERENCES `corso` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
