CREATE TABLE `categorias` (
  `id` varchar(45) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `descrip` varchar(400) DEFAULT NULL,
  `precio` varchar(45) DEFAULT NULL,
  `aplicaciones` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `marca_parte` (
  `id` varchar(45) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `descrip` varchar(400) DEFAULT NULL,
  `precio` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERTS;

