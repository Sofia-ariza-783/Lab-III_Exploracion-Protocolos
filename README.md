# ╰┈➤ -【🌐】 | Lab III: Exploración Protocolos ┆⤿⌗

---

Nombres:
* Sofia Nicolle Ariza Goenaga
* Daniel Palacios Moreno
* Marlio Jose Charry Espitia

---

## Introducion

Los ejericio documentados acontinuación tienen la intencion de presentar la aproximacion de los estudiantes a los protocolos, sus diferentes implementaciones en java 21 con el fin de poder implementarlo en proyectos futuros.

# Ejercicio 3.1

para este ejercicio teniamos que  usar los metodos de get que  tiene la clase URL los cuales son los siguientes: getProtocol, getAuthority, getHost, getPort, getPath, getQuery, getFile, getRef. Y ya con esto simplemente imprimimos la informacion en la terminal

# Ejercicio 3.2

Para este ejercicio teniamos que generar una GUI basica que permitiera al usuario escribir una URL y si esta era valida mostrara la informacion que se conseguia en el anterior ejercicio por lo que se realizo la GUI basica y se le realizaron unos pequeños cambios al codigo del ejercicio anterior

# Ejercicio 4.3.1

Para este ejercicio teniamos que usar un ServerSockket y un cliente que se pudieran comunicar entre ellos y el cliente pudiera mandar un numero al  servidor y el servidor devolviera el cubo del numero que hyaa recibido por parte del cliente por lo que se modifico el codigo dado en la guia  tanto de la Figura 2 como la Figura 3 para terminar de  hacerlo 

# Ejercicio 4.3.2

Para este ejercicio teniamos que realizar algo similar que el ejercicio pasado pero en este caso que el usuario recibiera la solucion de una de 3 operaciones trigonometricas ya fuera coseno, seno o tangente, escribiendo el numero en este caso el numero de radianes desde el cliente y por defecto la funcion que funciona es coseno pero si el cliente escribe fun:sin por ejemplo el servidor pasara a resolver los numeros que le lleguen con la operacion de seno

# Ejercicio 4.4

en este ejercicio unicamente teniamos que implementar el codigo que se nos da en la guia en la figura 4 y posteriormente correr el servidor e intentarnos conectar desde localhost:35000 desde nuestro navegador donde nos aparecera simplemente una pagina que diga My Web Site confirmando la conexion al servidor pero el servidor una vez manda la respuesta del request se apaga

# Ejercicio 4.5.1

para este ejercicio teniamos que hacer un servidor el cual nos permitiera realizar multiples requests al mismo tiempo sin ser de manera concurrente permitiendo al cliente en este caso al navegador pedir el index.html o pedir las imagenes tanto para mostrar como para descargarlas y lo que hace el servidor por dentro es lo siguiente: 

1. Lo que se realizo en este ejercicio fue crear un servidor con un ServerSocket en el puerto 35000 el cual permite la conexion una vez el servidor esta corriendo si se conecta a localhost:35000
2. entra en un bucle infinito el cual permite  aceptar un cliente y leer peticiones HTTP 
3. verifica que la request contenga en su header un GET para confirmar que es una peticion tipo GET
4. convierte el "/" en "/index.html" lo cual le permite al cliente recibir el html que en este caso permite mostrar el html de manera visual en el navegador
5. resuelve el WEB_ROOT del servidor para poder despues ubicar las imagenes que necesita
6. despues si la persona necesita la imagen para ser descargada o vista en una pestaña aparte lo lee como bytes y se los manda al cliente 
7. si no existe la imagen o la persona intenta crear una request indebida muestara un 404 not found

# Ejercicio 6.4.1

En este ejercicio se plantea realizar un chat utilizando RMI, donde escribiremos un aplicativo que pueda conectarse a
otro aplicativo del mismo tipo en un servidor remoto para comenzar una conversación. El aplicativo debe solicitar una
dirección IP y un puerto antes de conectarse con el cliente que se desea. Igualmente, debe solicitar un puerto antes de
iniciar para que publique el objeto que recibe los llamados remotos en dicho puerto.

![img_2.png](img/img_2.png)

![img_3.png](img/img_3.png)

# Ejercicio 6.5

En este ejercicio utilizamos el código dado en la guía, y lo ejecutamos de acuerdo a las instrucciones con dos Peers, A
y B. Como se indicó, enviamos un mensaje de A a B con el comando especificado, obteniendo los siguientes resultados:

* Peer A
  ![img.png](img/img.png)
* Peer B
  ![img_1.png](img/img_1.png)

El código implementa dos sistemas de comunicación en red: un mecanismo RPC para una calculadora, donde un cliente envía
solicitudes a un servidor que ejecuta operaciones aritméticas y retorna los resultados; y una red peer-to-peer con un
tracker central que permite el descubrimiento de peers, los cuales se conectan directamente entre sí mediante TCP para
intercambiar mensajes de texto. Ambos ejemplos utilizan sockets y protocolos basados en texto para la comunicación.