# Digital Money House
## Desafio Profesional Backend - Digital House

### ¿Que es?
Digital Money House es el backend de una billetera virtual, desarrollado en el lenguaje Java, con una arquitectura de microservicios.
La misma cuenta con funcionalidades de:
- Registro
- Login
- Actualizacion de datos de usuario, contraseña y alias.
- Envio de dinero
- Agregar dinero a partir de una tarjeta
- Agregar tarjeta
- Eliminar tarjeta

Tambien se puede observar:
- Datos de usuario
- Alias y CVU
- Cantidad de dinero disponible
- Tarjetas registradas
- transferencias realizadas o recibidas (con detalle de transferencia)

### Microservicios
- eureka-server: servicio de registro y descubrimiento de servicios.
- config-server: servicio de configuraciones de los microservicios alojado en github.
- gateway: punto de entrada único para clientes externos que desean acceder a los diferentes servicios.
- users-service: Registro, logueo, actualizacion e informacion de usuario.
- accounts-service: servicios relacionados a toda la operacion de la billetera virtual.
- cards-service: CRUD de tarjetas gestionado mediante accounts-service con feign.
- transactions-service: registro de transacciones gentionado mediante accounts-service con feign.

### Informacion Adicional referido al desarrollo
- Base de Datos: MySQL
- Autenticación, autorización y gestión de usuarios: Keycloak
- Deploy: Docker

### Documentacion
https://documenter.getpostman.com/view/27251703/2sA3dsnZcX
