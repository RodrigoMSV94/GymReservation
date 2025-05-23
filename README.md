ChatGPT dijo:
Perfecto, Rodrigo. Te dejo un ejemplo detallado de un archivo README.md para tu proyecto Java REST con Maven y Docker, siguiendo buenas pr�cticas y dejando listos los comandos que mencionaste para facilitar el uso del proyecto.

? README.md sugerido
markdown
Copiar
Editar
# Gym Reservation System ?????

Este es un sistema de reservas para un gimnasio, desarrollado con Java y Spring Boot siguiendo principios hexagonales. Utiliza Maven para la gesti�n del proyecto y pruebas, y MySQL como base de datos ejecutada en contenedor Docker.
Recordar que esta aplicaci�n tiene la opci�n de trabajar en memoria y apuntand a base de datos.
<br>
Solo se debe especificar como -Dpersistence=inmemory o -Dpersistence=mysql 

---

## ?? Requisitos previos

- Java 17 o superior (recomendado Java 21)
- Maven 3.8+
- Docker instalado y corriendo


---

## ? Comandos de uso

### ? Compilar el proyecto

```bash
    mvn clean compile
```

### ? Ejecutar pruebas del sistema

```bash
    mvn clean test
```

### ? Levantar base de datos MySQL en Docker

```bash
    docker run --name hexagon-mysql -d -p 3306:3306 \
  -e MYSQL_DATABASE=reservation \
  -e MYSQL_ROOT_PASSWORD=test \
  mysql:8.1

