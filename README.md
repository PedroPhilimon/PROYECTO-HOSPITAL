# 🏥 Proyecto Hospital - Sistema de Microservicios

**Desarrollado por:**  
* Pablo
* Evan Marquez  
* Pedro Philimon

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

Cada microservicio utiliza una base de datos independiente para garantizar la separación de datos mediante persistencia dinámica.

| Microservicio | Puerto Local | Base de Datos | Funcionalidad |
|---------------|-------------|--------------|----------------|
| ms-pacientes | 8080 | db_pacientes | Gestión de pacientes |
| ms-medicos | 8081 | db_medicos | Gestión de médicos y turnos |
| ms-citamedica | 8082 | db_citamedica | Gestión de citas médicas |
| ms-inventario | 8083 | db_inventario | Control de insumos y stock |
| ms-agenda | 8084 | db_agendas_medicas | Gestión de agendas médicas |
| ms-facturacion | 8085 | db_facturacion | Facturación y pagos |
| ms-historial | 8086 | db_historial | Historial clínico |
| ms-consultas | 8087 | db_consultas | Atención médica y diagnósticos |
| ms-proveedores | 8088 | db_proveedores | Gestión de proveedores |
| ms-laboratorio | 8089 | db_laboratorio | Exámenes de laboratorio |

---

## 🛣️ Rutas Principales (API Gateway)

Todas las peticiones de clientes externos deben pasar por el Gateway (Puerto `9090`). Algunas de las rutas principales son:

*   **Pacientes:** `http://localhost:9090/api/pacientes/**`
*   **Proveedores:** `http://localhost:9090/api/proveedores/**`
*   **Órdenes de Compra:** `http://localhost:9090/api/ordenes-compra/**`
*   **Citas Médicas:** `http://localhost:9090/api/citas/**`

---

## 📖 Documentación de la API (Swagger)

Cada microservicio cuenta con su propia documentación generada dinámicamente con OpenAPI. Para acceder a la interfaz gráfica de Swagger, asegúrate de que el servicio esté corriendo y visita la ruta `/doc/swagger-ui.html`. 

Ejemplos de acceso directo local:
*   **Swagger Proveedores:** [http://localhost:8088/doc/swagger-ui.html](http://localhost:8088/doc/swagger-ui.html)
*   **Swagger Pacientes:** [http://localhost:8080/doc/swagger-ui.html](http://localhost:8080/doc/swagger-ui.html)

---

## ⚙️ Ejecución del Proyecto

### Requisitos Previos
*   Java Development Kit (JDK 21)
*   Apache Maven
*   Docker y Docker Desktop
*   **Nota de Hardware:** Desplegar simultáneamente 13 contenedores.

### Opción 1: Ejecución con Docker Compose (Recomendada)
Esta es la forma más rápida de levantar todo el ecosistema (Bases de datos + Microservicios).

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/PedroPhilimon/PROYECTO-HOSPITAL.git](https://github.com/PedroPhilimon/PROYECTO-HOSPITAL.git)
   cd PROYECTO-HOSPITAL