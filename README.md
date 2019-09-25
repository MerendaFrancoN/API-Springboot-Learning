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



