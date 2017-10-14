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


(
SELECT cat.id, replace(cat.precio,',',''), cat.url, cat.descrip, cat.aplicaciones
FROM repuestos.categorias as cat
)
UNION
(
SELECT mar.id, replace(cat.precio,',',''), mar.url, mar.descrip, cat.aplicaciones
FROM repuestos.marca_parte as mar left join repuestos.categorias as cat on mar.id = cat.id
)

Exportar a csv
Poner autofitros y tomar los que no tienen precios. 
Pasarlos de una columna a una fila separada por espacios con https://delim.co/#
Modificar el script del paso 7 pegando esos valores.