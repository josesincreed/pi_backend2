# 🛍️ E-Commerce El Arte de Vivir – API Spring Boot
API REST desarrollada con **Spring Boot** y **Maven** que sirve como backend para una tienda de artesanías.  
El frontend se implementa en **React**, mientras que esta API gestiona el **CRUD** y las **validaciones** de:

- 🏷️ **Productos**  
- 📦 **Inventario**  
- 🛒 **Carrito de compras**  
- 🧩 **Items del carrito**  
- 🗂️ **Categorías**

La documentación interactiva de endpoints está disponible mediante **Swagger UI**:  
👉 [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## 🚀 Tecnologías
- **Java 17+**  
- **Spring Boot 3.x**  
- **Maven** como gestor de dependencias  
- **Spring Data JPA** para persistencia  
- **PostgreSQL** alojado en [Supabase](https://supabase.com/)  
- **Springdoc OpenAPI / Swagger** para documentación  

---

## ⚙️ Configuración del proyecto

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/<usuario>/<nombre-repositorio>.git
cd <nombre-repositorio>
```

### 2️⃣ Variables de entorno / Base de datos
Configura la conexión a Supabase PostgreSQL en `src/main/resources/application.properties` o en un archivo `.env`:
```properties
spring.datasource.url=jdbc:postgresql://<HOST>.supabase.co:5432/<DB_NAME>
spring.datasource.username=<DB_USER>
spring.datasource.password=<DB_PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
💡 En tu panel de Supabase: **Project → Settings → Database → Connection Info** encontrarás los datos de conexión (host, puerto, usuario, etc.).

---

### ▶️ Ejecución con Maven
**Modo desarrollo (hot reload):**
```bash
mvn spring-boot:run
```

**Construir y ejecutar JAR:**
```bash
mvn clean package
java -jar target/<nombre-del-jar>.jar
```
Por defecto la API quedará disponible en:  
[http://localhost:8080](http://localhost:8080)

---

## 🗂️ Endpoints principales
| Método | Endpoint             | Descripción                 |
|--------|----------------------|-----------------------------|
| GET    | `/api/products`      | Listar todos los productos  |
| POST   | `/api/products`      | Crear un nuevo producto     |
| GET    | `/api/products/{id}` | Obtener producto por ID     |
| PUT    | `/api/products/{id}` | Actualizar producto         |
| DELETE | `/api/products/{id}` | Eliminar producto           |
| GET    | `/api/categories`    | Listar categorías           |
| POST   | `/api/cart`          | Crear un carrito de compras |
| ...    | (Ver en Swagger)     | Documentación completa      |

---

## 🧩 Estructura del proyecto
```bash
src/
 ├─ main/
 │   ├─ java/com/example/pib2
 │   │    ├─ controller/    # Controladores REST
 │   │    ├─ entity/        # Entidades JPA
 │   │    ├─ repository/    # Repositorios Spring Data
 │   │    └─ service/       # Lógica de negocio
 │   └─ resources/
 │        ├─ application.properties
 │        └─ ...
 └─ test/                   # Pruebas unitarias
```

---

## 🛡️ Validaciones
- **Inventario**: control de stock antes de confirmar compras.  
- **Carrito**: evita duplicados y maneja incrementos de cantidad.  
- **Productos**: validación de campos obligatorios y rangos de precio.
