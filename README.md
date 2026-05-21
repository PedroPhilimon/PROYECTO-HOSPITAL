# Proyecto Hospital - Sistema de Microservicios

**Desarrollado por:**
- Pedro Philimon
- Evan Marquez

Este repositorio contiene la arquitectura backend del Proyecto Hospital, un sistema escalable diseñado para la gestión integral de un centro médico. El ecosistema está desarrollado completamente sobre Java con Spring Boot y estructurado bajo el patrón arquitectónico Controller-Service-Repository.

---

## Tecnologías y Herramientas Comunes

A nivel global, todos los módulos de la solución comparten el siguiente stack tecnológico:
* **Lenguaje:** Java 21.
* **Framework:** Spring Boot (Spring Web, Spring Data JPA, Lombok, Validation, OpenFeign, MySQL Driver).
* **Gestión de Base de Datos:** MySQL Server.
* **Control de Versiones de Base de Datos:** Liquibase (ejecutando migraciones estructuradas a través de db.changelog.sql).

---

## Catálogo Específico de Microservicios

El ecosistema se compone de los siguientes 10 microservicios individuales. Cada uno de ellos administra su propio esquema de base de datos aislado de manera independiente:

| Nombre del Microservicio | Puerto Local | Base de Datos Asociada (MySQL) | Propósito / Funcionalidad Principal |
| :--- | :---: | :--- | :--- |
| `ms-pacientes` | `8080` | `db_pacientes` | Registro, gestión de datos demográficos e información base de los pacientes. |
| `ms-medicos` | `8081` | `db_medicos` | Administración de personal médico, turnos y asignación de especialidades médicas. |
| `ms-citamedica` | `8082` | `db_citamedica` | Agendamiento, control de estados y flujos lógicos para las citas médicas presenciales. |
| `ms-inventario` | `8083` | `db_inventario` | Control de stock de insumos clínicos, fármacos y movimientos de bodega interna. |
| `ms-agenda` | `8084` | `db_agendas_medicas` | Gestión y bloqueos de calendarios de atención médica diaria y asignación de salas. |
| `ms-facturacion` | `8085` | `db_facturacion` | Emisión de comprobantes de pago, cálculo de aranceles y liquidaciones financieras. |
| `ms-historial` | `8086` | `db_historial` | Resguardo y auditoría del historial clínico unificado y registros de evolución médica. |
| `ms-consultas` | `8087` | `db_consultas` | Módulo de soporte para atenciones activas, recetas y diagnósticos de consultas médicas. |
| `ms-proveedores` | `8088` | `db_proveedores` | Administración de entidades externas proveedoras de insumos clínicos y compras. |
| `ms-laboratorio` | `8089` | `db_laboratorio` | Control, órdenes de exámenes clínicos y carga de resultados de laboratorio. |

---

## Requisitos Previos

Antes de proceder con el levantamiento del ecosistema de manera local, asegúrate de contar con los siguientes elementos instalados y configurados:
1. **Java Development Kit (JDK):** Versión 21, configurada en tus variables de entorno.
2. **Apache Maven:** Herramienta de construcción de proyectos (o el wrapper mvnw incluido en cada módulo).
3. **Servidor MySQL:** Corriendo de manera nativa o mediante contenedor en el puerto predeterminado 3306.

---

## Pasos para Ejecutar y Probar el Proyecto

### 1. Preparación de la Base de Datos
Asegúrate de tener tu servidor MySQL en ejecución. Antes de levantar las aplicaciones, debes crear de forma manual las 10 bases de datos (por ejemplo: `CREATE DATABASE db_pacientes;`, `CREATE DATABASE db_medicos;`, etc.).

### 2. Clonación del Repositorio
Abre tu terminal y clona el proyecto en tu entorno de desarrollo:
```bash
git clone https://github.com/PedroPhilimon/PROYECTO-HOSPITAL.git