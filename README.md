# Digital Money House
## Desafio Profesional Backend - Digital House

> [!NOTE]
> ### ¿Que es?

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
  

> [!NOTE]
> ### Video de presentacion

[![Alt text](https://img.youtube.com/vi/8vZuR8ChDqA/0.jpg)](https://www.youtube.com/watch?v=8vZuR8ChDqA)


> [!NOTE]
> ### Microservicios

- eureka-server: servicio de registro y descubrimiento de servicios.
- config-server: servicio de configuraciones de los microservicios alojado en github.
- gateway: punto de entrada único para clientes externos que desean acceder a los diferentes servicios.
- users-service: Registro, logueo, actualizacion e informacion de usuario.
- accounts-service: servicios relacionados a toda la operacion de la billetera virtual.
- cards-service: CRUD de tarjetas gestionado mediante accounts-service con feign.
- transactions-service: registro de transacciones gentionado mediante accounts-service con feign.
  

> [!NOTE]
> ### Informacion Adicional referido al desarrollo

- Base de Datos: MySQL
- Autenticación, autorización y gestión de usuarios: Keycloak
- Deploy: Docker


> [!NOTE]
> ### Documentacion

https://documenter.getpostman.com/view/27251703/2sA3dsnZcX


> [!NOTE]
> ### Testing Manual

https://docs.google.com/spreadsheets/d/1ZXZ32c4EVCGl5GaKt0WRmgPtIZQKcdEl/edit?usp=sharing&ouid=113433791087033757583&rtpof=true&sd=true


> [!NOTE]
> ### Testing Exploratorio

https://docs.google.com/spreadsheets/d/1CVAqSheq23vQ2jKCDrjxH-sBpVBzrfu-/edit?usp=sharing&ouid=113433791087033757583&rtpof=true&sd=true


> [!NOTE]
> ### Testing KickOff

https://drive.google.com/file/d/1Ep6cm5LBT3DgZ-_9SzRfOiaI5tzNGkP3/view?usp=sharing


> [!NOTE]
> ### QA Sign Off

https://drive.google.com/file/d/1ptS_YLeEIqcHfXARxK8idnglYGgI8NB6/view?usp=sharing


> [!NOTE]
> ### Diagrama Infraestructura

![infraestructura](https://github.com/PabloNMerino/DigitalMoneyHouse/assets/44982651/333a1538-fa06-477c-b11c-5abdf08a2024)


> [!NOTE]
> ### Estructura del Proyecto

![digital Money House Estructura](https://github.com/PabloNMerino/DigitalMoneyHouse/assets/44982651/cec828b9-f6de-446d-bd0c-5d2cc4036bf3)


> [!IMPORTANT]
> # Pasos para ejecutar la aplicación
> 1. Clonar repositorio del frontend -> git clone https://github.com/PabloNMerino/FrontEnd-DMH.git
> 2. abrir terminal ubicado en la carpeta del frontend -> /FrontEnd-DMH
> 3. ejecutar el comando -> npm i
> 4. ejecutar el comando -> npm run dev
> 5. abrir enlace de localhost y acceder al frontend para empezar a usar la aplicación

> [!WARNING]
> Las transferencias solamente son posibles entre usuarios de Digital Money House, no se admiten transferencias con usuarios de otras aplicaciones.

## Usuarios actualmente registrados

> [!TIP]
> ### Usuario 1
 - Email: pablonicolasm@hotmail.com
 - Password: admin123
 - Alias: FALLO.IDEAS.YATES
 - CVU: 7031990840748232663690


> [!TIP]
> ### Usuario 2
 - Email: no.reply.dmh@gmail.com
 - Password: admin123
 - Alias: TACOS.VINOS.EMULO
 - CVU: 7031990705570570784025

> [!TIP]
> ### Usuario 3
 - Email: sandraob@gmail.com
 - Password: admin12
 - Alias: HALOS.TACOS.NACE
 - CVU: 7031990517727084673085











