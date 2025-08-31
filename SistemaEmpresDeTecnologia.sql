
CREATE TABLE Usuario (
     id_usuario BIGINT PRIMARY KEY,
	 nombre VARCHAR(200) NOT NULL,
	 correo VARCHAR(200),
	 contraseña VARCHAR(200),
	 rol VARCHAR(100)
);

CREATE TABLE Cliente (
    id_cliente BIGINT PRIMARY KEY,
    nombre VARCHAR(250) NOT NULL,
    correo VARCHAR(250),
    telefono VARCHAR(25),
    direccion VARCHAR(300)
);

CREATE TABLE Producto (
    id_producto BIGINT PRIMARY KEY,
	nombre VARCHAR(200) NOT NULL,
	descripcion VARCHAR(250),
	cantidad_disponible INT,
	precio_unitario FLOAT
);

CREATE TABLE Factura (
    id_factura BIGINT NOT NULL PRIMARY KEY,
	id_cliente BIGINT NOT NULL,
	fecha DATETIME,
	total FLOAT,
	id_usuario BIGINT NOT NULL,
	CONSTRAINT FK_Factura_Cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    CONSTRAINT FK_Factura_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE DetalleFactura (
    id_detallefactura BIGINT PRIMARY KEY,
	id_factura BIGINT NOT NULL,
	id_producto BIGINT NOT NULL,
	cantidad INT NOT NULL,
	subtotal FLOAT NOT NULL,
	CONSTRAINT FK_DetalleFactura_Factura FOREIGN KEY (id_factura) REFERENCES Factura(id_factura),
    CONSTRAINT FK_DetalleFactura_Producto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) 
);


---USUARIO---
INSERT INTO Usuario (id_usuario, nombre, correo, contraseña, rol)
VALUES (1,'Pamela', 'pamela@gmail.com', 'Pame123', 'Admin');

INSERT INTO Usuario (id_usuario, nombre, correo, contraseña, rol)
VALUES (2, 'Allison', 'Allison@gmail.com', 'Alli321', 'Empleado');

INSERT INTO Usuario (id_usuario, nombre, correo, contraseña, rol)
VALUES (3, 'Diego', 'Diego@gmail.com', 'Diego543', 'Empleado');

INSERT INTO Usuario (id_usuario, nombre, correo, contraseña, rol)
VALUES (4, 'Pablo', 'Pablo@gmail.com', 'Pablo098', 'Admin');

INSERT INTO Usuario (id_usuario, nombre, correo, contraseña, rol)
VALUES (5, 'Javier', 'javier@gmail.com', 'Pablo654', 'Admin');


---CLIENTE---
INSERT INTO Cliente (id_cliente, nombre, correo, telefono, direccion)
VALUES (1, 'Fabiola', 'fabiola@gmail.com', '12345678', 'colonia las flores zona 3');

INSERT INTO Cliente (id_cliente, nombre, correo, telefono, direccion)
VALUES (2, 'Tania', ' tania@gmail.com', '12131415', 'Avenida Refomra 12-45');

INSERT INTO Cliente (id_cliente, nombre, correo, telefono, direccion)
VALUES (3, 'Brandon', 'brandon@gmail.com', '11223344', 'El Rosario Villa Nueva');

INSERT INTO Cliente (id_cliente, nombre, correo, telefono, direccion)
VALUES (4, 'Matias', 'matias@gmail.com', '44556677', 'Avenida las americas');

INSERT INTO Cliente (id_cliente, nombre, correo, telefono, direccion)
VALUES (5, 'Luis', 'luis@gmail.com', '27364536', 'Calle real 8-20');


---PRODUCTO---
INSERT INTO Producto (id_producto, nombre, descripcion, cantidad_disponible, precio_unitario)
VALUES (1, 'Laptop HP 15', 'Laptop HP 15.6 pulgadas, intel i5, 8GB RAM, 256GB SSD', '10', '5500'); 

INSERT INTO Producto (id_producto, nombre, descripcion, cantidad_disponible, precio_unitario)
VALUES (2, 'Mouse inalambrico', 'Mouse inalambrico con receptor USB, color negro', '50', '150');

INSERT INTO Producto (id_producto, nombre, descripcion, cantidad_disponible, precio_unitario)
VALUES (3, 'Auriculares gamer HyperX', 'audifonos con microfono, soido envolvente 7.1', '25', '700');

INSERT INTO Producto (id_producto, nombre, descripcion, cantidad_disponible, precio_unitario)
VALUES (4, 'tablet Samsung Galaxy A7', 'pantalla 10.4 pulgadas, 64GB, wi-fi', '7', '2200');

INSERT INTO Producto (id_producto, nombre, descripcion, cantidad_disponible, precio_unitario)
VALUES (5, 'smartphone Xiaomi Redmi Note 11', '6GB RAM, 128GB, camara 50MP', '15', '3200');


---FACTURA---
INSERT INTO Factura (id_factura, id_cliente, fecha, total, id_usuario)
VALUES (1, 1, '2025-08-20', '5500', 1);

INSERT INTO Factura (id_factura, id_cliente, fecha, total, id_usuario)
VALUES (2,2, '2025-08-15', '150', 2);

INSERT INTO Factura (id_factura, id_cliente, fecha, total, id_usuario)
VALUES (3,3, '2025-08-25', '700', 3);

INSERT INTO Factura (id_factura, id_cliente, fecha, total, id_usuario)
VALUES (4,4, '2025-08-4', '2200', 4);

INSERT INTO Factura (id_factura, id_cliente, fecha, total, id_usuario)
VALUES (5,5, '2025-08-28', '3200', 5);


---DETALLEFACTURA---
INSERT INTO DetalleFactura (id_detallefactura, id_factura, id_producto, cantidad, subtotal)
VALUES (1,1,1, '1', '5500');

INSERT INTO DetalleFactura (id_detallefactura, id_factura, id_producto, cantidad, subtotal)
VALUES (2,2,2, '1', '150');

I[dbo].[DetalleFactura]NSERT INTO DetalleFactura (id_detallefactura, id_factura, id_producto, cantidad, subtotal)
VALUES (3,3,3, '1', '700');

INSERT INTO DetalleFactura (id_detallefactura, id_factura, id_producto, cantidad, subtotal)
VALUES (4,4,4, '1', '2200');

INSERT INTO DetalleFactura (id_detallefactura, id_factura, id_producto, cantidad, subtotal)
VALUES (5,5,5, '1', '3200');


---Query---
use EmpresaTecnologia;
SELECT * FROM Cliente
WHERE nombre = 'Fabiola';


use EmpresaTecnologia;
SELECT f.id_factura, c.nombre AS Cliente, u.nombre AS Usuario, f.fecha, f.total
FROM Factura f
JOIN Cliente c ON f.id_cliente = c.id_cliente
JOIN Usuario u ON f.id_usuario = u.id_usuario;

USE EmpresaTecnologia;
GO