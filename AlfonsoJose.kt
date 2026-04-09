import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue

data class Torre(val fila: Int, val col: Int, val altura: Int): Comparable<Torre> {
	override fun compareTo(other: Torre): Int {
		return this.altura.compareTo(other.altura)
	}
}

fun crearMatriz(): Array<IntArray> {
	try {
		val reader = File("atlantis.txt").bufferedReader()
		var linea = reader.readLine()
		var matriz: MutableList<IntArray> = mutableListOf()

		while(linea != null) {
			val fila = linea.split(" ").map { it.toInt() }.toIntArray()
			matriz.add(fila)
			linea = reader.readLine()
		}
	
		return matriz.toTypedArray()
	} catch (e: java.io.FileNotFoundException) {
		error("El archivo 'atlantis.txt' no existe. Abortando")
	}
}

fun main() {
	val matriz = crearMatriz()

	val filas = matriz.size
	val cols = matriz[0].size
	val visitado = Array(filas) { BooleanArray(cols) }
	val colaPrioridad = PriorityQueue<Torre>()

	//Bordes de la ciudad, para evitar que la cola de prioridad salga de los límites
	for (i in 0 until filas) {
		colaPrioridad.add(Torre(i, 0, matriz[i][0]))
		visitado[i][0] = true
		colaPrioridad.add(Torre(i, cols-1, matriz[i][cols-1]))
		visitado[i][cols-1] = true
	}

	for (j in 1 until cols-1) {
		colaPrioridad.add(Torre(0, j, matriz[0][j]))
		visitado[0][j] = true
		colaPrioridad.add(Torre(filas-1, j, matriz[filas-1][j]))
		visitado[filas-1][j] = true
	}

	var totalAgua = 0

	//Variables para acceder a los valores adyacentes en una posición de la matriz (Arriba, abajo, izquierda, derecha)
	val movimientoFila = intArrayOf(0, 0, 1, -1)
	val movimientoCol = intArrayOf(1, -1, 0, 0)
	
	//Variación del algoritmo Dijkstra, con el fin de "alcanzar" la altura máxima posible a partir de los puntos mas bajos de cada torre
	while (colaPrioridad.isNotEmpty()) {
		val actual = colaPrioridad.poll()
		for (k in 0 until 4) {
			//ni y nj indican la posición donde se intentará colocar una columna de agua
			val ni = actual.fila + movimientoFila[k]
			val nj = actual.col + movimientoCol[k]

			if (ni >= 0 && ni < filas && nj >= 0 && nj < cols && !visitado[ni][nj]) {
				visitado[ni][nj] = true
				// El agua atrapada es el máximo entre 0 y la diferencia
				totalAgua += maxOf(0, actual.altura - matriz[ni][nj])
				// La nueva altura para la cola de prioridad es el máximo entre la altura original y el límite actual
				colaPrioridad.add(Torre(ni, nj, maxOf(matriz[ni][nj], actual.altura)))
			}
		}
	}
	
	//Salida
	println(totalAgua)
}
