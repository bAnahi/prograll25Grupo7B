
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
    precio_unitario FLOAT
);

CREATE TABLE Inventario (
    id_inventario BIGINT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    cantidad_disponible INT NOT NULL,
    CONSTRAINT FK_Inventario_Producto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

CREATE TABLE Venta (
    id_venta BIGINT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    fecha DATETIME,
    total DECIMAL(10,2),
    CONSTRAINT FK_Venta_Cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    CONSTRAINT FK_Venta_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Factura (
    id_factura BIGINT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    numero_factura VARCHAR(50),
    fecha_emision DATETIME,
    total DECIMAL(10,2),
    CONSTRAINT FK_Factura_Venta FOREIGN KEY (id_venta) REFERENCES Venta(id_venta)
);

CREATE TABLE DetalleVenta (
    id_detalle BIGINT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario FLOAT NOT NULL,
    subtotal FLOAT NOT NULL,
    CONSTRAINT FK_DetalleVenta_Venta FOREIGN KEY (id_venta) REFERENCES Venta(id_venta),
    CONSTRAINT FK_DetalleVenta_Producto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
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
INSERT INTO Producto (id_producto, nombre, descripcion, precio_unitario)
VALUES (1, 'Laptop HP 15', 'Laptop HP 15.6 pulgadas, intel i5, 8GB RAM, 256GB SSD', 5500); 

INSERT INTO Producto (id_producto, nombre, descripcion, precio_unitario)
VALUES (2, 'Mouse inalambrico', 'Mouse inalambrico con receptor USB, color negro', 150);

INSERT INTO Producto (id_producto, nombre, descripcion, precio_unitario)
VALUES (3, 'Auriculares gamer HyperX', 'Audífonos con micrófono, sonido envolvente 7.1', 700);

INSERT INTO Producto (id_producto, nombre, descripcion, precio_unitario)
VALUES (4, 'Tablet Samsung Galaxy A7', 'Pantalla 10.4 pulgadas, 64GB, Wi-Fi', 2200);

INSERT INTO Producto (id_producto, nombre, descripcion, precio_unitario)
VALUES (5, 'Smartphone Xiaomi Redmi Note 11', '6GB RAM, 128GB, cámara 50MP', 3200);


---INVENTARIO---
INSERT INTO Inventario (id_inventario, id_producto, cantidad_disponible)
VALUES (1, 1, 10);

INSERT INTO Inventario (id_inventario, id_producto, cantidad_disponible)
VALUES (2, 2, 50);

INSERT INTO Inventario (id_inventario, id_producto, cantidad_disponible)
VALUES (3, 3, 25);

INSERT INTO Inventario (id_inventario, id_producto, cantidad_disponible)
VALUES (4, 4, 7);

INSERT INTO Inventario (id_inventario, id_producto, cantidad_disponible)
VALUES (5, 5, 15);


---VENTA---
INSERT INTO Venta (id_venta, id_cliente, id_usuario, fecha, total)
VALUES (1, 1, 1, '2025-08-20', 5500);

INSERT INTO Venta (id_venta, id_cliente, id_usuario, fecha, total)
VALUES (2, 2, 2, '2025-08-15', 150);

INSERT INTO Venta (id_venta, id_cliente, id_usuario, fecha, total)
VALUES (3, 3, 3, '2025-08-25', 700);

INSERT INTO Venta (id_venta, id_cliente, id_usuario, fecha, total)
VALUES (4, 4, 4, '2025-08-04', 2200);

INSERT INTO Venta (id_venta, id_cliente, id_usuario, fecha, total)
VALUES (5, 5, 5, '2025-08-28', 3200);


---FACTURA---
INSERT INTO Factura (id_factura, id_venta, numero_factura, fecha_emision, total)
VALUES (1, 1, 'F001', '2025-08-20', 5500);

INSERT INTO Factura (id_factura, id_venta, numero_factura, fecha_emision, total)
VALUES (2, 2, 'F002', '2025-08-15', 150);

INSERT INTO Factura (id_factura, id_venta, numero_factura, fecha_emision, total)
VALUES (3, 3, 'F003', '2025-08-25', 700);

INSERT INTO Factura (id_factura, id_venta, numero_factura, fecha_emision, total)
VALUES (4, 4, 'F004', '2025-08-04', 2200);

INSERT INTO Factura (id_factura, id_venta, numero_factura, fecha_emision, total)
VALUES (5, 5, 'F005', '2025-08-28', 3200);


---DETALLEVENTA---
INSERT INTO DetalleVenta (id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (1, 1, 1, 1, 5500, 5500);

INSERT INTO DetalleVenta (id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (2, 2, 2, 1, 150, 150);

INSERT INTO DetalleVenta (id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (3, 3, 3, 1, 700, 700);

INSERT INTO DetalleVenta (id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (4, 4, 4, 1, 2200, 2200);

INSERT INTO DetalleVenta (id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (5, 5, 5, 1, 3200, 3200);



---Query---
use EmpresaTecnologia;
SELECT * FROM Inventario;
WHERE nombre = 'Pamela';

USE EmpresaTecnologia;
GO