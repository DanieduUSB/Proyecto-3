# Proyecto 3 - 2110447 - 2010518
+ Nombres: Daniel Quijada y Brian Orta
+ Carnets: 2010518, 2110447
+ Universidad Simón Bolívar

# Descripción del Proyecto: Mundo Cubo
Para este proyecto nos describieron la situación de Alfonso José, un ávido jugador del popular juego, **Mundo Cubo**, en este juego entre
muchas cosas se pueden costruir estructuras con cubos completamente sólidos que se adhieren entre si. En este juego tambén existe agua
cúbica que fluye como agua del mundo real.

Alfonso José desea construir una estructura en este juego sobre una superficie plana de tamaño nxm, donde en cada espacio de esta
superficie construirá una torre de una altura determinada. Por caprichos de Alfonso José, esta estructura luego será sumergida bajo el agua
y Alfonso José desea saber cuantos bloques de agua quearán empozados dentro de su estructura. La finaliad de este proyecto es ayudar a
nuestro querido Alfonso a calcular esto mediante un programa de Kotlin que utilice estrategias relacionadas a grafos. El programa debe
recibir como entrada una matriz de enteros que muestra los "planos" de la atura de cada torre y debe retornar un único entero; la cantidad
de bloques de agua empozados.

# Instrucciones de uso
Coloque todos los archivos encontrados en este repositorio en un mismo directorio y siga los siguientes pasos (debes tener
kotlin instalado):

1. Utilize el comando `make` para compilar y preparar el pograma.
2. En el archivo atlantis.txt aportado o alguno creado por usted escriba la información correspodiente a la matriz de entrada.
3. Use el siguiente comando para ejecutar el programa:
```
./run.sh
```

# Lógica del Programa
Al leer el archvo atlantis.txt el programa lee las líneas que representan la matriz de entrada y los convierte en una matriz nxm de
enteros mediante la función `crearMatrtiz()`, una vez creada la matriz la función `main()` calcula el n y el m de la matriz y luego incia
el algoritmo de solución. Este algoritmo esta basado en la lógica del algoritmo para grafos "Dijkstra", en este caso particular la matriz
se trata implicitamente como un grafo, donde cada celda de la matriz es un vértice y dos vértices estan conectados si son celas adyacentes
en la matriz, adyacente se refiere a que esten una al lado de la otra horizontal o verticalmente. Habiendo definido esto, podemos explicar
el algoritmo, primero, el algoritmo genera una cola de prioridad en la que se guardaran elementos del tipo "celda", el tipo celda es una 
estructura de datos sencilla que solo facilita buscar los indices y el valor de cada celda de la matriz sin necesidad de hacer operaciones
adicionales con la propia matriz. También genera una matriz booleana nxm llamada "visitados" que lleva registro de que celdas han sido
visitadas ya. Una vez terminadas las preparaciones, el algoritmo comienza introduciendo en la cola todas las celdas que bordean la matriz
y como Dijkstra, elige la que este al inicio de la cola, en este caso, la que tenga menor altura, si hay varias con esa menor altura,
elige cualquiera, es indiferente cual use. Una vez elegida una celda, empieza a buscar que celdas no visitadas tiene al sus alrededores y
las añade a la cola de prioridad, creando una especie de efecto de propagación donde revisa todos los espacios adyacentes de la misma
altura debido a las propiedades de la cola de prioridad, adicionalmente, cada vez que encuentra un adyacente sin visitar, revisa si es más
bajo que el bloque actual para comprobar si puede ser llenado con agua, si en efecto se puede, la añade. Este proceso se realiza hasta que
la cola se vacíe, es decir, ya se hayan revidado todas las celdas.

Este algoritmo funciona debido a dos propiedades, el uso de una cola de prioridad, y la buqueda de afuera hacia adentro (iniciando por los
bordes). Gracias a que la busqueda inicia desde afuera y propaga a celdas adyacentes de la misma altura, el algoritmo descarta al inicio
cualquier espacio de bloques que eventualmente lleve a un borde (a que el agua se desborde, es decir, espacios donde no se puede colocar
agua), el hecho de que al incio se descartan las celdas de altura baja que llevan a desbordes significa que más adelante, si encuentras
alguna celda que todavía no ha sido visitada que posee una altura más baja a la actual, puedes decir con seguridad que se puede empozar
agua en esa celda, esta busqueda donde primero se desscartan los desagues de un piso y luego se consiguen los posos permite una busqueda
eficiente de la matriz. El agua encontrada en cada celda poso se va sumando a un total llamado "totalAgua" y al final se imrpime dicho
total.


# Análisis de Complejidad
Con observar la función `crearMatrtiz()` es evidente que su complejidad es O(nxm), el peso temporar más grande viene de la propia busqueda;
podemos ver que el algoritmo termina visitando cada celda una vez y también añade cada celda a la cola de prioridad una sola vez, por lo
tanto en conjunto esto genera una complejidad de O(nxm*log(nxm)), esto da una complejidad total de O(nxm) + O(nxmlog(nxm)) que en total es
acotado por O(nxmlog(nxm)).

# Decisiones de Implementación
+ Considerando el requerimiento del enunciado que exigía utilizar conocimientos de grafos para resolver este problma, decidimos buscar
  entre todas las herramientas que teníamos disponibles para resolver un problema tan complejo, eventualmente evaluamos Dijkstra y
  decidimos que era el más cercano a lo que queríamos resolver debido a su uso de cola de prioridad para encontrar cosotos mínimos (o en
  nuestro caso, alturas bajas).
+ Para facilitar la busqueda de celdas adyacentes se hizo uso de dos arrays "guías" de tamaño 4 que contienen los cambios de coordenadas
  necesarios para revisar las celdas adyacente a alguna elegida.
+ Iniciar la busqueda desde los bordes y desde la altura más baja es clave para eliminar los caudales que de otra manera podrían generar
  "falsos positivos".
+ Se hizo uso de una data class simple "torres" para facilitar el proceso de introducir las celdas dentro de la cola de prioridad.
