-- Tabla de Clientes
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birthday DATE NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    credit_card VARCHAR(20),
    password VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    premium BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Libros
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    edition VARCHAR(50),
    title VARCHAR(200) NOT NULL,
    writer VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Pedidos
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    order_date DATETIME NOT NULL,
    delivery_date DATETIME,
    status VARCHAR(20) DEFAULT 'CREATED',
    description TEXT,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE RESTRICT
);

-- Tabla de Detalles del Pedido
CREATE TABLE IF NOT EXISTS order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_ref VARCHAR(50) NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    amount INT NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Datos de ejemplo
INSERT INTO clients (name, birthday, address, phone, email, credit_card, password, code, premium) VALUES
('Juan Pérez García', '1990-05-15', 'Calle Mayor 1, Madrid', '123456789', 'juan@example.com', '4532-1111-2222-3333', 'pass123', 'CLI001', TRUE),
('María García López', '1985-08-20', 'Avenida Libertad 23, Barcelona', '987654321', 'maria@example.com', '5421-4444-5555-6666', 'pass456', 'CLI002', FALSE),
('Carlos Rodríguez Martín', '1988-03-10', 'Paseo del Prado 5, Valencia', '555666777', 'carlos@example.com', '3714-7777-8888-9999', 'pass789', 'CLI003', TRUE);

INSERT INTO books (edition, title, writer, description, type, price, stock, available) VALUES
('3ª edición', 'Clean Code', 'Robert C. Martin', 'Una novela verdaderamente novedosa sobre la forma de escribir código legible.', 'Programación', 45.99, 10, TRUE),
('1ª edición', 'Design Patterns', 'Gang of Four', 'Elementos de software orientado a objetos reutilizable.', 'Programación', 54.99, 5, TRUE),
('1ª edición', 'Spring in Action', 'Craig Walls', 'Guía completa sobre Spring Framework para desarrolladores Java.', 'Programación', 49.99, 8, TRUE),
('6ª edición', 'Effective Java', 'Joshua Bloch', 'Programación idiomática en Java con mejores prácticas.', 'Programación', 59.99, 6, TRUE);

INSERT INTO orders (client_id, order_date, delivery_date, status, description, total_amount) VALUES
(1, '2026-01-15 10:30:00', '2026-01-22 15:00:00', 'DELIVERED', 'Pedido de libros técnicos', 150.97),
(2, '2026-01-20 14:15:00', NULL, 'PENDING', 'Compra inicial de libros', 104.98),
(1, '2026-01-25 09:45:00', NULL, 'CREATED', 'Segundo pedido del cliente', 0.00);

INSERT INTO order_details (order_id, product_ref, product_name, unit_price, amount, discount) VALUES
(1, 'BOOK001', 'Clean Code', 45.99, 1, 0),
(1, 'BOOK002', 'Design Patterns', 54.99, 1, 10),
(1, 'BOOK003', 'Spring in Action', 49.99, 1, 0),
(2, 'BOOK001', 'Clean Code', 45.99, 2, 5),
(2, 'BOOK004', 'Effective Java', 59.99, 1, 0),
(3, 'BOOK002', 'Design Patterns', 54.99, 1, 15);