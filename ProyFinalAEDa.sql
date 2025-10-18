use master
-- drop database ProyFinalAEDa
create database ProyFinalAEDa
use ProyFinalAEDa
  
--Administrador
CREATE TABLE Administrador (
    username NVARCHAR(50) PRIMARY KEY,
    password NVARCHAR(50)
);

--Empleado
CREATE TABLE Empleado (
    id NVARCHAR(50) PRIMARY KEY,
    nombre NVARCHAR(100),
    puesto NVARCHAR(50),
    salario DECIMAL(10, 2),
    fechaContratacion DATE,
    admin_username NVARCHAR(50),
    FOREIGN KEY (admin_username) REFERENCES Administrador(username)
);

--Categoria
CREATE TABLE Categoria (
    id NVARCHAR(50) PRIMARY KEY,
    nombre NVARCHAR(100),
    descripcion NVARCHAR(255)
);

--Producto
CREATE TABLE Producto (
    id NVARCHAR(50) PRIMARY KEY,
    nombre NVARCHAR(100),
    descripcion NVARCHAR(255),
    precio DECIMAL(10, 2),
    stock INT,
    tipo NVARCHAR(50),
    admin_username NVARCHAR(50),
    categoria_id NVARCHAR(50),
    promocion_id NVARCHAR(50), -- Relación con Promoción
    FOREIGN KEY (admin_username) REFERENCES Administrador(username),
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id),
    FOREIGN KEY (promocion_id) REFERENCES Promocion(id) -- Llave foránea con Promoción
);

--Promocion
CREATE TABLE Promocion (
    id NVARCHAR(50) PRIMARY KEY,
    descripcion NVARCHAR(255),
    descuento DECIMAL(5, 2)
);


--Inventario
CREATE TABLE Inventario (
    id NVARCHAR(50) PRIMARY KEY,
    id_producto NVARCHAR(50),
    nombre NVARCHAR(100),
    precio_unitario DECIMAL(10, 2),
    stock INT,
    total_productos AS (precio_unitario * stock),
    FOREIGN KEY (id_producto) REFERENCES Producto(id)
);

--InventarioProducto
CREATE TABLE InventarioProducto (
    inventario_id NVARCHAR(50),
    producto_id NVARCHAR(50),
    PRIMARY KEY (inventario_id, producto_id),
    FOREIGN KEY (inventario_id) REFERENCES Inventario(id),
    FOREIGN KEY (producto_id) REFERENCES Producto(id)
);

--Venta
CREATE TABLE Venta (
    idVenta NVARCHAR(50) PRIMARY KEY,
    nombreEmpleado NVARCHAR(50),
    idProducto NVARCHAR(50),
    nombreProducto NVARCHAR(50),
    descripProdcuto NVARCHAR(50),
    precioProducto DECIMAL(10, 2),
    tipoProducto NVARCHAR(50),
    categProducto NVARCHAR(50),
    descuentoProducto DECIMAL(5, 2),
    cantidadProducto INT,
    subtotalProducto DECIMAL(5, 2),
    preciototalProducto DECIMAL(5, 2),
    fechaventa DATE
);

--Administrador
INSERT INTO Administrador (username, password) VALUES ('admin1', 'pass123');
INSERT INTO Administrador (username, password) VALUES ('admin2', 'pass123');

INSERT INTO Empleado (id, nombre, puesto, salario, fechaContratacion, admin_username) 
VALUES ('EMP001', 'Juan Pérez', 'Gerente', 5500.00, '2020-03-15', 'admin1'),
       ('EMP002', 'Ana García', 'Cajero', 1800.00, '2021-06-01', 'admin2'),
       ('EMP003', 'Luis Torres', 'Repartidor', 1500.00, '2022-01-10', 'admin1'),
       ('EMP004', 'María López', 'Supervisor', 3200.00, '2019-11-22', 'admin2'),
       ('EMP005', 'Carlos Gómez', 'Auxiliar', 1200.00, '2023-04-05', 'admin1'),
       ('EMP006', 'Rosa Sánchez', 'Vendedor', 2100.00, '2021-08-18', 'admin2');

INSERT INTO Categoria (id, nombre, descripcion)
VALUES ('CAT001', 'Bebidas', 'Bebidas frías y calientes'),
       ('CAT002', 'Snacks', 'Snacks y bocadillos variados'),
       ('CAT003', 'Lácteos', 'Productos lácteos y derivados'),
       ('CAT004', 'Carnes', 'Carnes frescas y congeladas'),
       ('CAT005', 'Panadería', 'Productos horneados y panadería'),
       ('CAT006', 'Verduras', 'Verduras frescas y orgánicas');

INSERT INTO Producto (id, nombre, descripcion, precio, stock, tipo, admin_username, categoria_id, promocion_id)
VALUES ('PROD001', 'Coca-Cola', 'Bebida gaseosa', 5.50, 100, 'Bebida', 'admin1', 'CAT001', 'PROMO01'),
       ('PROD002', 'Doritos', 'Snack de maíz', 3.20, 50, 'Snack', 'admin2', 'CAT002', 'PROMO02'),
       ('PROD003', 'Leche Gloria', 'Leche evaporada', 4.00, 200, 'Lácteo', 'admin1', 'CAT003', NULL),
       ('PROD004', 'Carne de Res', 'Carne fresca de res', 25.00, 30, 'Carne', 'admin2', 'CAT004', NULL),
       ('PROD005', 'Pan Integral', 'Pan saludable', 2.50, 40, 'Panadería', 'admin1', 'CAT005', 'PROMO03'),
       ('PROD006', 'Lechuga', 'Verdura fresca', 1.80, 70, 'Verdura', 'admin2', 'CAT006', NULL);

INSERT INTO Producto (id, nombre, descripcion, precio, stock, tipo, admin_username, categoria_id, promocion_id)
VALUES('PROD007', 'Fanta', 'Bebida gaseosa', 5.00, 150, 'Bebida', 'admin1', 'CAT001', 'PROMO01');

-- Insertar el nuevo producto PROD008
INSERT INTO Producto (id, nombre, descripcion, precio, stock, tipo, admin_username, categoria_id, promocion_id)
VALUES('PROD008', 'Sprite', 'Bebida gaseosa', 4.50, 120, 'Bebida', 'admin1', 'CAT001', 'PROMO01');

INSERT INTO Producto (id, nombre, descripcion, precio, stock, tipo, admin_username, categoria_id, promocion_id)
VALUES ('PROD009', 'Agua Mineral', 'Botella de agua mineral', 2.00, 150, 'Bebida', 'admin1', 'CAT001', 'PROMO01');

INSERT INTO Promocion (id, descripcion, descuento)
VALUES ('PROMO01', 'Descuento del 10% en bebidas gaseosas', 10.00),
       ('PROMO02', 'Descuento del 15% en snacks', 15.00),
       ('PROMO03', 'Promoción en panes integrales', 5.00),
       ('PROMO04', 'Descuento por temporada', 20.00),
       ('PROMO05', 'Oferta de 2x1 en algunos productos', 50.00),
       ('PROMO06', 'Descuento en productos lácteos', 7.50);

INSERT INTO Inventario (id, id_producto, nombre, precio_unitario, stock)
VALUES ('INV001', 'PROD001', 'Coca-Cola', 5.50, 100),
       ('INV002', 'PROD002', 'Doritos', 3.20, 50),
       ('INV003', 'PROD003', 'Leche Gloria', 4.00, 200),
       ('INV004', 'PROD004', 'Carne de Res', 25.00, 30),
       ('INV005', 'PROD005', 'Pan Integral', 2.50, 40),
       ('INV006', 'PROD006', 'Lechuga', 1.80, 70);

INSERT INTO InventarioProducto (inventario_id, producto_id)
VALUES ('INV001', 'PROD001'),
       ('INV002', 'PROD002'),
       ('INV003', 'PROD003'),
       ('INV004', 'PROD004'),
       ('INV005', 'PROD005'),
       ('INV006', 'PROD006');



SELECT * FROM Administrador;
SELECT * FROM Empleado;
SELECT * FROM Categoria;
SELECT * FROM Producto;
SELECT * FROM Promocion;
SELECT * FROM Inventario;
SELECT * FROM InventarioProducto;

-- 1. Agregar la nueva columna porcentaje_descuento
ALTER TABLE Producto ADD porcentaje_descuento DECIMAL(5, 2);

-- 2. Actualizar la columna con los valores correspondientes de la tabla Promocion
UPDATE Producto
SET porcentaje_descuento = p.descuento
FROM Producto pr
INNER JOIN Promocion p ON pr.promocion_id = p.id;

-- 3. Verificar los resultados
SELECT * FROM Producto;

--Facilitamos el descuento
CREATE TRIGGER tr_asignar_descuento
ON Producto
AFTER INSERT
AS
BEGIN
    -- Variables para almacenar el descuento y el código de promoción
    DECLARE @descuento DECIMAL(5,2), @promocion_id VARCHAR(50);

    -- Seleccionar el código de promoción y descuento desde la tabla Promocion
    SELECT @promocion_id = i.promocion_id, @descuento = p.descuento
    FROM INSERTED i
    INNER JOIN Promocion p ON i.promocion_id = p.id
    WHERE i.promocion_id = p.id;

    -- Si el descuento es encontrado, actualizar el valor de porcentaje_descuento en la tabla Producto
    IF @descuento IS NOT NULL
    BEGIN
        UPDATE Producto
        SET porcentaje_descuento = @descuento
        FROM Producto pr
        INNER JOIN INSERTED i ON pr.id = i.id
        WHERE pr.id = i.id;
    END
END;
