# Mutant Detector API – Magneto Challenge

**Examen MercadoLibre – Proyecto Final**  
**Deployment en Render:**  
👉 https://mutantesgallardo.onrender.com

Este proyecto implementa la API solicitada por Magneto para identificar mutantes a partir de una secuencia de ADN.  
Incluye detección, persistencia en H2, estadísticas y pruebas automáticas.

---

# 🎯 Objetivo del Proyecto

Según las consignas del examen (PDF MercadoLibre, pág. 1–3):

> “Un humano es mutante si su ADN contiene al menos **dos secuencias** de cuatro letras iguales consecutivas, en dirección horizontal, vertical o diagonal.”

---

# 🧬 1. Algoritmo `isMutant(String[] dna)`

El ADN se recibe como un arreglo de Strings que representa una matriz **NxN**.  
Los caracteres permitidos son: **A, T, C y G**.

### ✔ Condiciones (PDF pág. 1–2)

- La matriz debe ser cuadrada (N x N).  
- Solo puede contener A, T, C, G.  
- Se detectan secuencias consecutivas de 4 caracteres iguales en:
  - Horizontal →
  - Vertical ↓
  - Diagonal ↘
  - Diagonal inversa ↙
- El ADN es mutante si se encuentran **2 o más** secuencias válidas.

Ejemplo del PDF (mutante):

```json
["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
```

---

# 🧪 2. API REST (Nivel 2)

## POST `/mutant`

### Request
```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

### Respuestas
| Caso       | Código              | Descripción                       |
|-----------|---------------------|-----------------------------------|
| Mutante   | **200 OK**          | ADN válido & detectado como mutante |
| Humano    | **403 Forbidden**   | ADN válido pero NO mutante |
| Inválido  | **400 Bad Request** | Formato ADN incorrecto |
| Error     | **500 Internal Server Error** | Fallo inesperado |

---

# 📊 3. Base de Datos + Estadísticas (Nivel 3)

La API almacena cada ADN en **H2 en memoria**.  
Para evitar duplicados se genera un **hash SHA-256**.

## GET `/stats`
Retorna:

```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

# 🏗️ Arquitectura del Proyecto

Estructura definida según el PDF (pág. 3–7):

```
src/
 └── main/
     ├── java/
     │   └── org.example.mutantes
     │       ├── controller/  → Endpoints REST
     │       ├── service/     → Lógica de negocio
     │       ├── entity/      → Entidad JPA
     │       ├── repository/  → DAO / H2
     │       └── exception/   → GlobalExceptionHandler
     └── resources/
         ├── application.properties
         └── schema.sql (opcional)
```

### Componentes Principales

#### **MutantesApplication**
Punto de entrada de Spring Boot.

#### **MutantService**
- Valida ADN  
- Ejecuta `isMutant`  
- Guarda en BD  
- Evita duplicados con hash  

#### **MutantDetector**
Algoritmo de análisis del ADN (horizontal, vertical, diagonales).

#### **StatsService**
Genera:
- `count_mutant_dna`
- `count_human_dna`
- `ratio`

#### **GlobalExceptionHandler**
Maneja:
- 400 → entrada inválida  
- 500 → errores internos  

---

# 🚀 Ejecución Local

## 1. Clonar el repositorio
```bash
git clone https://github.com/12juangallardo-wq/mutantesGallardo
cd mutantesGallardo
```

## 2. Ejecutar el servidor
```bash
./gradlew bootRun
```

## 3. Abrir Swagger
```
http://localhost:8080/swagger-ui.html
```

## 4. Consola H2 (opcional)
```
http://localhost:8080/h2-console
```

Credenciales correctas (según el proyecto):
```
JDBC URL: jdbc:h2:mem:mutantesdb
User: sa
Password: 
```

---

# 🌐 Producción (Render)

API en la nube:
👉 https://mutantesgallardo.onrender.com

Endpoints:
- **POST** `/mutant`
- **GET**  `/stats`

---

# ✔ Estado del Proyecto

- [x] Algoritmo isMutant implementado  
- [x] API REST completada  
- [x] Persistencia en H2  
- [x] Estadísticas `/stats`  
- [x] Swagger OpenAPI  
- [x] Excepciones validadas  
- [x] Pruebas unitarias + cobertura  
- [x] Deploy en Render  

---

# 🏁 Conclusión

Proyecto completamente funcional, alineado al examen MercadoLibre, con entregables solicitados y despliegue operativo.
