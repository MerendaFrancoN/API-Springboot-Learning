# API-Springboot-Learning
Learning to work with Springboot to develop API's with Java.

# Ejercicio
Considere la siguiente realidad:

Se solicita realizar el análisis, diseño e implementación de una API REST para la aplicación web de un banco digital. Los actores de interés involucrados en el funcionamiento del banco son sus clientes (usuarios), las cuentas bancarias (por simplicidad trabajaremos sólo con cuentas de nuestro banco) y las transferencias que se realizan entre cuentas de los clientes 
del banco. Los datos de interés a almacenar para los usuarios son el número de cliente, dni, apellido, nombre y estado del cliente.
<br /> 
En el caso de las cuentas bancarias se debe registrar quién es el titular de la cuenta, el saldo actual, el tipo de moneda y el estado. Para las transferencias es necesario guardar el número de transferencia, cuenta origen del pago, cuenta destino del
pago, fecha-hora del movimiento y estado de la operación.
<br /> 
Entre los requerimientos del banco encontramos las siguientes operaciones:<br /> 
<br /> 
➔ Registrar nuevos usuarios, solicitando apellido, nombre y dni. Al cargarlos en el sistema,
el usuario quedará automáticamente en estado ACTIVO y se le asignará un número de
cliente (único).<br /> <br /> 
➔ Abrir una cuenta bancaria. Cada usuario cliente podrá abrir hasta 3 cuentas, en las cuales quedará como titular. Al momento de abrir una nueva cuenta, se deberá informar la moneda en la que trabajará la misma (PESO_AR, DOLAR, EURO). Todas las cuentas nuevas tendrán un saldo inicial de 0 y un estado ACTIVO.<br /> <br /> 
Por restricciones del banco, solo se puede tener una cuenta en cada tipo de moneda (hasta 3 cuentas en total por cada persona).<br /> <br /> 
➔ Realizar transferencias de dinero entre las cuentas de los usuarios. A continuación se detalla cómo es que se realiza este procedimiento:<br /> <br /> 
a. El usuario emisor de la transferencia elige una de sus cuentas, desde la cual desea realizar la operación.<br /> 
b. Ingresa el número de cuenta a quién desea realizar la transferencia.<br /> 
c. Ingresa el monto a transferir. Debe ser un importe mayor a 0.<br /> 
d. Si todos los datos son confirmados, se realiza la operación, descontando el importe de la cuenta origen y acreditandolo en la cuenta destino. El estado de la transferencia será PENDIENTE.<br /> <br /> 
**Restricciones:** <br /> <br /> 
No es posible realizar transferencias a uno mismo.<br /> 
Tanto la cuenta origen como la cuenta destino deben existir, y la moneda asociada a cada una de ellas tiene que ser la misma.<br /> 
La cuenta origen debe tener dinero suficiente para la transferencia.<br /> 
➔ Consultar los datos personales para un determinado usuario, conociendo su número decliente, o bien su número de dni.<br /> 
➔ Actualizar los datos personales de un cliente que se encuentra cargado en el sistema. Los datos que se podrán cambiar son apellido y nombre.<br /> 
➔ Dar de baja a un usuario. Todas sus cuentas asociadas deben quedar también en estado BAJA.<br /> 
➔ Obtener un listado con detalle de las cuentas bancarias, dado un número de cliente.<br /> 
➔ Dado un número de transferencia, se debe poder consultar quienes fueron los usuarios que realizaron la operación, el importe y el estado del movimiento.<br /> <br /> 
**Operaciones Opcionales:**<br /> <br /> 
➔ Al realizarse transferencias, el importe solo es descontado de la cuenta de origen, pero no es depositado inmediatamente en la cuenta destino. Luego, el banco realiza el procesamiento de la operación (no depende de nuestra API) y se debe poder cambiar el
estado de la operación a PROCESADA o CANCELADA. Una transferencia procesada, suma el importe de la operación al saldo de la cuenta destino, mientras que si el nuevo estado es CANCELADA, el importe de la transferencia es devuelto a la cuenta origen.<br /> <br /> 
➔ Como beneficio para sus clientes, la primer cuenta que los usuarios abran en moneda PESO_AR, tendrá un bono de regalo de $500, acreditado inmediatamente al darse de alta.<br /> <br /> 
➔ Consultar información sobre las cuentas de sus clientes. Dado un número de cuenta, se puede obtener los datos del titular (apellido, nombre, dni).<br /> <br /> <br /> <br /> 
➔ Obtener el registro de movimientos realizados en una determinada cuenta en un rango de fechas o bien los últimos 5 movimientos.


