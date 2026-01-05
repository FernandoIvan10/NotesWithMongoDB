# Notes

## Descripción

Aplicación de consola en Java para la gestión de notas.
Permite crear, listar, modificar y eliminar notas, persistiendo la información en una base de datos MongoDB.

El proyecto está estructurado siguiendo una arquitectura por capas (dominio, servicio y persistencia), aplicando el Repository Pattern para desacoplar la lógica de negocio del acceso a datos.

## Tecnologías

- **Lenguaje**: Java
- **Base de Datos**: MongoDB
- **Driver**: MongoDB Java Driver
- **Entorno**: Variables de sistema (System.getProperty)

## Arquitectura

El proyecto está organizado en las siguientes capas:
- **Domain**
  - Note: entidad de dominio
- **Repository**
  - NoteRepository: contrato (interface)
  - MongoNoteRepository: implementación MongoDB
- **Service**
  - NoteService: lógica de negocio
- **Infrastructure**
  - MongoDBConnection: manejo de la conexión
- **Application**
  - App: punto de entrada y capa de interacción por consola
 
## Requisitos

- Java 17+
- MongoDB en ejecución (local o remoto)

## Configuración

1. La aplicación usa propiedades del sistema para la configuración. Definelas desde el IDE o desde la línea de comandos.
   Propiedades necesarias:

   ```bash
   MONGO_URI=<tu_mongo_uri>
   MONGO_DB=<tu_base_de_datos>
   ```

## Funcionalidades

- Crear notas
- Listar notas
- Modificar notas existentes
- Eliminar notas
- Persistencia automática en MongoDB
- Manejo básico de errores a nivel de servicio

## Consideraciones

- La aplicación es de consola, por lo que la interacción se realiza mediante texto.
- El manejo de errores se propaga desde el repositorio hacia el servicio y finalmente al borde (consola).
- No se mantiene estado en memoria: las notas se obtienen siempre desde la base de datos.
- Pensada como proyecto de aprendizaje de arquitectura y buenas prácticas, no como producto final.
