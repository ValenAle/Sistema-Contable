-- ============================================================
-- RESET COMPLETO - Sistema Contable (economia_db)
-- Borra todo lo existente y lo vuelve a crear desde cero,
-- ya con los tipos de datos correctos según la consigna.
-- ============================================================

DROP DATABASE IF EXISTS economia_db;
CREATE DATABASE economia_db;
USE economia_db;

-- ------------------------------------------------------------
-- GRUPOS (1er dígito del código)
-- ------------------------------------------------------------
CREATE TABLE grupos (
    id      INT PRIMARY KEY,
    nombre  VARCHAR(50) NOT NULL
);

-- ------------------------------------------------------------
-- RUBROS (3er y 4to dígito del código - 2 dígitos, ampliable)
-- tipo: 0 = no aplica corriente/no corriente
--       1 = corriente
--       2 = no corriente
-- ------------------------------------------------------------
CREATE TABLE rubros (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    grupo_id  INT NOT NULL,
    tipo      INT NOT NULL,
    codigo    VARCHAR(2) NOT NULL,
    nombre    VARCHAR(100) NOT NULL,
    FOREIGN KEY (grupo_id) REFERENCES grupos(id),
    UNIQUE KEY uq_rubro_codigo (codigo)
);

-- ------------------------------------------------------------
-- CUENTAS
-- codigo se genera automáticamente: grupo.tipo.rubro.numero
-- nombre: 40 caracteres | saldo: 9 enteros + 2 decimales | tipo_saldo: 1 carácter ('D'/'A')
-- ------------------------------------------------------------
CREATE TABLE cuentas (
    codigo         VARCHAR(12) PRIMARY KEY,
    grupo_id       INT NOT NULL,
    tipo           INT NOT NULL,
    rubro_id       INT NOT NULL,
    numero_cuenta  INT NOT NULL,
    nombre         VARCHAR(40)   NOT NULL,
    saldo          DECIMAL(11,2) NOT NULL DEFAULT 0,
    tipo_saldo     CHAR(1)       NOT NULL,
    FOREIGN KEY (grupo_id) REFERENCES grupos(id),
    FOREIGN KEY (rubro_id) REFERENCES rubros(id)
);

-- ------------------------------------------------------------
-- DATOS INICIALES: GRUPOS
-- ------------------------------------------------------------
INSERT INTO grupos (id, nombre) VALUES
(1, 'Activo'),
(2, 'Pasivo'),
(3, 'Patrimonio Neto'),
(4, 'Ingresos'),
(5, 'Egresos');

-- ------------------------------------------------------------
-- DATOS INICIALES: RUBROS (lista para el trabajo de desarrollo)
-- Podés agregar más filas cuando quieras ampliar el catálogo,
-- solo respetá grupo_id y tipo correctos.
-- ------------------------------------------------------------
INSERT INTO rubros (grupo_id, tipo, codigo, nombre) VALUES
(1, 1, '01', 'Caja y Bancos'),
(1, 1, '02', 'Créditos por Ventas'),
(1, 1, '03', 'Bienes de Cambio'),
(1, 2, '04', 'Bienes de Uso'),
(2, 1, '05', 'Deudas Comerciales'),
(2, 2, '06', 'Deudas a Largo Plazo'),
(3, 0, '07', 'Patrimonio Neto'),
(3, 0, '08', 'Resultados Acumulados'),
(4, 0, '09', 'Ventas'),
(5, 0, '10', 'Gastos');

-- ------------------------------------------------------------
-- VERIFICACIÓN
-- ------------------------------------------------------------
SHOW TABLES;
SELECT * FROM grupos;
SELECT * FROM rubros;
