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

### Video de presentacion
https://youtu.be/8vZuR8ChDqA

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

### Testing Manual
https://docs.google.com/spreadsheets/d/1ZXZ32c4EVCGl5GaKt0WRmgPtIZQKcdEl/edit?usp=sharing&ouid=113433791087033757583&rtpof=true&sd=true

### Testing Exploratorio
https://docs.google.com/spreadsheets/d/1CVAqSheq23vQ2jKCDrjxH-sBpVBzrfu-/edit?usp=sharing&ouid=113433791087033757583&rtpof=true&sd=true

### Testing KickOff
https://drive.google.com/file/d/1Ep6cm5LBT3DgZ-_9SzRfOiaI5tzNGkP3/view?usp=sharing

### QA Sign Off
https://drive.google.com/file/d/1ptS_YLeEIqcHfXARxK8idnglYGgI8NB6/view?usp=sharing
