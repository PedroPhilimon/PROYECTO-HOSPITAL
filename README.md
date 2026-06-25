# 🏥 Proyecto Hospital - Sistema de Microservicios

**Desarrollado por:**  
Pedro Philimon  
Evan Marquez  

---

## Descripción

Este repositorio contiene la arquitectura backend del **Proyecto Hospital**, un sistema escalable diseñado para la gestión integral de un centro médico.

El ecosistema está desarrollado completamente en **Java con Spring Boot** y estructurado bajo el patrón arquitectónico **Controller - Service - Repository**.

---

## Tecnologías y Herramientas Comunes

A nivel global, todos los módulos de la solución comparten el siguiente stack tecnológico:

- **Lenguaje:** Java 21  
- **Framework:** Spring Boot (Spring Web, Spring Data JPA, Lombok, Validation, OpenFeign)  
- **Infraestructura Cloud:** Spring Cloud Netflix Eureka (Service Discovery) y Spring Cloud Gateway  
- **Seguridad:** Spring Security con JSON Web Tokens (JWT) para la protección de endpoints  
- **Gestión de Base de Datos:** MySQL Server  
- **Migraciones de Base de Datos:** Liquibase (ejecutando migraciones estructuradas a través de `db.changelog.sql`)  
- **Despliegue y Orquestación:** Docker y Docker Compose  
- **Documentación:** SpringDoc OpenAPI (Swagger UI)  

---

## Arquitectura Base e Infraestructura

El sistema centraliza su enrutamiento, seguridad y descubrimiento en los siguientes servicios core:

| Microservicio | Puerto Local | Puerto Docker | Propósito |
|----------------|-------------|---------------|------------|
| ms-eureka | 8761 | 8761 | Servidor de descubrimiento. Mantiene el registro de instancias activas |
| ms-gateway | 8090 | 8090 | Punto de entrada único (API Gateway). Enruta peticiones externas |
| ms-auth | 8091 | 8081 | Gestión de usuarios, roles y emisión de JWT |

---

## Catálogo de Microservicios de Negocio

Cada microservicio utiliza una base de datos independiente para garantizar la separación de datos.

| Microservicio | Puerto Local | Puerto Docker | Base de Datos | Funcionalidad |
|---------------|-------------|---------------|--------------|----------------|
| ms-pacientes | 8080 | 8080 | db_pacientes | Gestión de pacientes |
| ms-medicos | 8081 | 8083 | db_medicos | Gestión de médicos y turnos |
| ms-citamedica | 8082 | 8084 | db_citamedica | Gestión de citas médicas |
| ms-inventario | 8083 | 8085 | db_inventario | Control de insumos y stock |
| ms-agenda | 8084 | 8086 | db_agendas_medicas | Gestión de agendas médicas |
| ms-facturacion | 8085 | 8087 | db_facturacion | Facturación y pagos |
| ms-historial | 8086 | 8088 | db_historial | Historial clínico |
| ms-consultas | 8087 | 8089 | db_consultas | Atención médica y diagnósticos |
| ms-proveedores | 8088 | 8090 | db_proveedores | Gestión de proveedores |
| ms-laboratorio | 8089 | 8091 | db_laboratorio | Exámenes de laboratorio |

---

## Requisitos Previos

Antes de levantar el ecosistema, asegúrate de tener instalado:

- Java Development Kit (JDK 21)
- Apache Maven (o wrappers `mvnw`)
- Docker y Docker Compose (recomendado)
- MySQL Server (si se ejecuta sin Docker)

---

## Ejecución del Proyecto

### Opción 1: Docker Compose (Recomendada)

Esta es la forma más rápida de levantar todo el sistema.

```bash
git clone https://github.com/PedroPhilimon/PROYECTO-HOSPITAL.git