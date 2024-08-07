# Proyecto-Christian-Marquez

Login: 
El login pide al usuario ingresar el usuario y la contraseña
Existen dos tipos de Usuarios, el usuario del tipo "Cajero" y el usuario del tipo "Administrador". Según el tipo de usuario que ingrese, la pantalla del menú será distintaa

![image](https://github.com/user-attachments/assets/a7c85919-3f89-44d2-b97f-b3397e02fca4)

Si las credenciales son incorrectas o no se llena el campo de texto nos saldrá lo siguiente:

![image](https://github.com/user-attachments/assets/45e7439d-0602-46b6-ab73-18d1c606274c)

MENÚ ADMINISTRADOR

![image](https://github.com/user-attachments/assets/fe2fd681-3ea2-40ef-b65d-940797d69ed2)

1.- Ver Productos
Aquí se mostraran todos los productos de la base de datos en una tabla, se puede ver la ID, Nombre, Precio, Stock, Marca, y Modelo. Además tiene un botón para actualizar

![image](https://github.com/user-attachments/assets/20d4ac59-f931-49a4-bc4d-404b1ec4e680)

2.- Agregar producto
Aqui se puede añadir un nuevo producto, para ello es necesario ingresar: Nombre, Precio, Stock, Marca (unicamente marcas guardadas), Modelo, e imagen.

![image](https://github.com/user-attachments/assets/60352ffa-96fd-4a96-8492-330327268e74)

3.- Ver Ventas
Aqui se pueden ver las ventas realizadas por todos los usuarios, se puede ver la ID, el nombre del cliente, el total pagado, la fecha y hora, y el usuario que realizó la venta. Además tiene un boton para actualizar la tabla

![image](https://github.com/user-attachments/assets/55dd5da2-59cf-49b3-a713-d32af772998c)

4.- Reportes Administrativos
Aqui se cargan todos los reportes creados, de todos los usuarios, indica la ID, el nombre, dirección, telefono, email, ci, producto, total, fecha y usuario que realizo el reporte. Además tiene un boton para cargar los reportes y un boton para guardar los reportes
![image](https://github.com/user-attachments/assets/de46b55f-a441-4af4-8a47-10676a6f8a59)

5.- Agregar Usuario
Aqui se puede agregar un nuevo usuario, se puede poner el nombre y la contraseña, además se puede escoger el rol: "Administrador o Usuario"

![image](https://github.com/user-attachments/assets/4f84379f-0db0-473d-b939-e6840197b114)
![image](https://github.com/user-attachments/assets/a2786948-8794-46a5-a356-23b01136b7bb)


6.-Eliminar Usuario
En este apartado se muestran todos los usuarios, se los puede escoger y se puede seleccionar y se lo desea eliminar

![image](https://github.com/user-attachments/assets/7c5da47f-b3a5-49cc-8d7a-cfe7ed5105d4)
![image](https://github.com/user-attachments/assets/48c25d38-8c0b-4bbe-a3d0-cbbb1ebfe46b)
![image](https://github.com/user-attachments/assets/6352ca6a-38ab-42ad-bcb4-2345982dc167)

7.- Configuracion
Este es un apartado incompleto. Que deberia implementar funcionalidades a futuro, como poner un mnodo oscuro, , cambiar el tema, activar notificaciones.

![image](https://github.com/user-attachments/assets/4027013f-b5c9-4cef-9518-3fc8be283309)

8.-Salir
Cierra la aplicación por completo

MENÚ CAJERO
Este menú posee menos cantidad de botones, ya que es lo justo para el cajero
1.-Productos
Aqui apareceran todos los productos cargados de la base de datos. Tiene dos menús desplegables, el menú de marca y de Modelo

![image](https://github.com/user-attachments/assets/df8ede70-cd98-448f-ad96-8d1e27978b1c)

El menú de modelo cambia según el menú de marca

![image](https://github.com/user-attachments/assets/92c333a7-b27a-4066-9743-181885102cb3)
![image](https://github.com/user-attachments/assets/e6931098-934f-4418-b00c-bc83932d014a)

En este Apartado cargan los productos mostrando: Nombre, Modelo, Precio, Stock y tiene un botón que permite añadir al carrito
Si no se selecciona ningun producto saldrá un aviso de que se debe seleccionar un producto

![image](https://github.com/user-attachments/assets/97cf3317-fcbd-4a92-a446-323283badfa3)

2.-CARRITO
Aqui se muestran los productos del carrito
Muestra: Producto, Modelo, Cantidad, Precio, Total a pagar, y eliminar el producto
Además en la parte inferior muestra el total a pagar y un boton para PAGAR

![image](https://github.com/user-attachments/assets/d9cc7aaf-c03e-4444-b52f-f1d40ee4dfe7)

Al dar clic en PAGAR se abre otro apartado para generar la factura, aqui es necesario llenar los campos: Nombre del cliente, Dirección, Teléfono, Email, ci.
Apareceran los detalles de la factura que incluyen: Producto, precio y cantidad
Además tiene el boton de Generar Factura y Cancelar
![image](https://github.com/user-attachments/assets/da917398-ef6e-48d8-9a30-be8ddab4e5e9)

Al pulsar en generar Factura se abrirá un menú que nos permitirá seleccionar en donde deseamos guardar la factura, automaticamente se le asigna un nombre y un numero a la factura

![image](https://github.com/user-attachments/assets/8cb69774-6d40-4274-9695-11e31537f444)
![image](https://github.com/user-attachments/assets/01d88998-5689-410b-ade1-90477fd57614)

Y así queda el comprobante de venta

![image](https://github.com/user-attachments/assets/77ae18ca-117e-4acc-912e-79faaf212c7e)

3.- Configuracion
Este es un apartado incompleto. Que deberia implementar funcionalidades a futuro, como poner un mnodo oscuro, , cambiar el tema, activar notificaciones.

![image](https://github.com/user-attachments/assets/4027013f-b5c9-4cef-9518-3fc8be283309)

4.- REPORTES

Aqui se cargan todos los reportes creados, de todos los usuarios, indica la ID, el nombre, dirección, telefono, email, ci, producto, total, fecha y usuario que realizo el reporte. Además tiene un boton para cargar los reportes y un boton para guardar los reportes

![image](https://github.com/user-attachments/assets/f5a3c1a8-1cf9-4dee-b694-2c434075b62c)

Bases de Datos:

![image](https://github.com/user-attachments/assets/4aa07362-566f-4dca-920c-2fa037e01746)
![image](https://github.com/user-attachments/assets/7424bedb-4edf-4b97-8f76-ab983b5a5294)
![image](https://github.com/user-attachments/assets/636b78f2-babb-41b7-bfda-2fd752988c86)
![image](https://github.com/user-attachments/assets/73bca882-aa2e-489d-8eaf-7d91f9fd53ff)
![image](https://github.com/user-attachments/assets/f7db252e-ed1d-407c-b79c-4b07810a5c5b)
![image](https://github.com/user-attachments/assets/107b1472-d370-44ce-a7d5-3e52cd709ef0)
![image](https://github.com/user-attachments/assets/bd6375d7-b627-497f-bb0d-18c68f3c816e)




Link del Video: 























