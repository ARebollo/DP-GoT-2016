package DEV;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import Estructuras.Grafo;
import Estructuras.Llave;
import Estructuras.Pair;

/**
* Declaracion de la clase Mapa
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Mapa {
	
	/** Altura de la matriz de salas */
	private int alto;
	
	/** Anchura de la matriz de salas */
	private int ancho;
	
	/** Numero de la Sala que contiene la Puerta del trono */
	private int id_salida;
	
	/** La Puerta del trono */
	private Puerta puertaTrono;
	
	/** Matriz que contiene las Salas del Mapa */
	private Sala[][] mapaSalas;
	
	/** Cola que almacena las LLave a repartir entre las Salas del Mapa */
	private Queue<Llave> listaLlaveMapa;
	
	/** Acceso a la clase Grafo para los metodos relacionados con las Salas del Mapa */
	private Grafo grafoMapa;
	
	/** String que contiene el mapa sin ningun atajo */
	private String mapaSinAtajos;
	
	/**
	 * Constructor parametrizado de la clase Mapa 
	 * 
	 * @param alto Altura de la matriz de salas
	 * @param ancho Anchura de la matriz de salas
	 * @param id Numero de la Sala que contiene la Puerta del trono
	 * @param prof Profundidad de la cerradura de la Puerta del trono                   
	 * 
	 */
	Mapa(int alto, int ancho, int id, int prof){
		this.alto = alto;
		this.ancho = ancho;
		this.id_salida = id;
		puertaTrono = new Puerta(prof);
		listaLlaveMapa = new LinkedList<Llave>();
		
		/* Inicializamos cada Sala con su id */	
		mapaSalas = new Sala[alto][ancho];
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				mapaSalas[i][j] = new Sala(j + ancho * i);
			}
		}
		
		buscarSala(id_salida).setPuertaSalida(true);
		
		// Crear el grafo del Mapa
		Random randomGenerator = new Random(1987);  
   	  		
		grafoMapa = new Grafo(alto,ancho);
		grafoMapa.procesarParedes(ancho, randomGenerator);
		
		/* Imprimir el mapa antes de generar los atajos */
		mapaSinAtajos = mapaAStringSinPJ();
		
		// Tirar los atajos
		grafoMapa.tirarParedesAtajo(ancho, randomGenerator);
		
		// Almacenar las llaves
		for (int i = 0;i<30;i++)
		{		
			listaLlaveMapa.add(new Llave(i));
			if (i%2 != 0)
				listaLlaveMapa.add(new Llave(i));
		}
		
		// Repartir llaves
		Integer[] repartirLlaves = SalasMasFrec(); 
		
		// Recorre el vector de las mas frecuentadas
		for (int i = 0; i<9; i++){
			
			// Mete 5 llaves en cada una
			for (int j = 0; j<5; j++){
				buscarSala(repartirLlaves[i]).aniadirLlave(listaLlaveMapa.poll());
			}			
		}
	}
	
	/**
	 * Metodo para llamar a los Personaje en el Mapa para que realizen sus turnos
	 * 
	 * @return True : si el turno se ha hecho correctamente <br> False : si el turno no se ha llevado a cabo correctamente
	 * 
	 */
	public boolean simTurnoMapa(){
		
		boolean fin = false;
		for(int i = 0;i<alto;i++)
		{
			for(int j = 0;j<ancho && !fin;j++)
				fin = mapaSalas[i][j].activarPJ(this);
		}
		for(int i = 0;i<alto && !fin;i++)
		{
			for(int j = 0;j<ancho;j++)
				mapaSalas[i][j].reiniciarTurnoPj();
		}
		return fin;
	}
	
	/**
	 * Metodo para obtener un vector de enteros, ordenadas de mayor a menor, de las Sala mas frecuentadas en el Mapa
	 * 
	 * @return Vector de enteros con la id de las salas mas frecuentadas, ordenadas de mayor a menor
	 * 
	 */
	public Integer[] SalasMasFrec(){
		
		List<LinkedList<Integer>> listaCam = new LinkedList<LinkedList<Integer>>();
		grafoMapa.encontrarListaCaminos(0, id_salida, listaCam, new LinkedList<Integer>());
		
		//Creamos el vector de frecuencias y lo iniciamos a 0
		int[] frecuencias = new int[alto*ancho];
		for(int i = 0;i<frecuencias.length;i++)
		{
			frecuencias[i] = 0;
		}
		
		List<Integer> camino;
		int est;
		while(!listaCam.isEmpty())
		{
			camino = listaCam.remove(0);
			System.out.println(camino);
			while (!camino.isEmpty())
			{
				est = camino.remove(0);
				frecuencias[est]++;
			}
		}

		/*En el primer elemento se guarda el número de sala, en el segundo la frecuencia */
		List<Pair<Integer, Integer>> SalasLlaves = new LinkedList<Pair<Integer, Integer>>();
		
		boolean auxInt = false; // Si ya se ha insertado
		
		/*
		 * Recorre los valores del vector de frecuencias que sean mayores 
		 * que 1 y los inserta si son mayores que alguno de los que hay (sustituyéndolo)
		 * o si, en caso de no serlo, hay menos de 9, en cuyo caso se inserta al final
		 *  */
		for (int i = 0; i<frecuencias.length; i++)
		{
			auxInt = false;
			if (frecuencias[i] > 0)
			{
				for (int j = 0;j<SalasLlaves.size() && !auxInt;j++)
				{
					if (frecuencias[i] > SalasLlaves.get(j).getSecond())
					{
						SalasLlaves.set(j, new Pair<Integer,Integer>(i, frecuencias[i]));
						auxInt = true;
					}
				}
				if (auxInt == false && SalasLlaves.size() < 9)
					SalasLlaves.add(SalasLlaves.size(), new Pair<Integer,Integer>(i, frecuencias[i]));
			}
		}
		
		/* Si hay menos de 9 salas con frecuencia 1 o más, se insertan por orden de id hasta llegar a 9 */
		for (int i = 0;i<frecuencias.length && SalasLlaves.size()<9;i++) 
		{
			if (frecuencias[i] == 0)
			{
				SalasLlaves.add(SalasLlaves.size(), new Pair<Integer,Integer>(i, frecuencias[i]));
			}
		}
		Integer[] SalasConLlaves = new Integer[9];
		
		for (int i = 0;i<9;i++)
		{
			SalasConLlaves[i] = SalasLlaves.get(i).getFirst();
		}
		return SalasConLlaves;
	}
	
	/**
	 * Metodo para dibujar el Mapa con sus Salas y respectivos personajes
	 * 
	 * @return String con el dibujo de las Salas del Mapa con sus personajes
	 * 
	 */
	private String mapaAString(){
		
		String map = "";
		int y = 0;
		int x = 0;
		/* Para comprobar si hay un arco debajo del nodo actual */
		Boolean arcoBajo = true; 
		/*Para comprobar si hay un a la derecha del nodo actual */
		Boolean arcoDer = false;
		
		map = map + " ";
		for (int i = 0; i < (ancho); i++) {
			map = map + "_ ";
		}
		map = map + '\n';

		for (int i = 0; i < alto; i++) {
			map = map + '|';

			for (int j = 0; j < ancho; j++) {

				arcoBajo = true;

				if (mapaSalas[i][j].hayPersonajes() == true) {
					if (mapaSalas[i][j].cuantosPJ() > 1) {
						map = map + String.valueOf(mapaSalas[i][j].cuantosPJ());
						/* Si hay más de dos personajes en una sala,
						 * muestra cuantos hay */
					} else {
						map = map + mapaSalas[i][j].mirarPJ().getMarcaId();
					}
				} else {
					y = 0;
					arcoBajo = false;
					/* Comprobamos si hay arco con el nodo inferior */
					while (y < grafoMapa.devolverArcos().size() && arcoBajo == false) { 
						
						/* Si el nodo de la lista coincide con el actual continuamos */
						if (grafoMapa.devolverArcos().get(y).getFirst() == i * ancho + j) 
						{
							if (grafoMapa.devolverArcos().get(y).getFirst() + ancho == 
									grafoMapa.devolverArcos().get(y).getSecond()) {
								map = map + ' ';
								arcoBajo = true;
							}
						}
						y = y + 1;
					} // Fin while arcoBajo
				}

				if (arcoBajo == false)
					map = map + '_'; 
					/* Imprimir antes de comprobar arcos a la
					 * derecha, por errores de espacio */

				x = 0;
				arcoDer = false;
				/* Comprobamos si hay un arco con el nodo a la derecha */
				while (x < grafoMapa.devolverArcos().size() && arcoDer == false) { 
					/* Si el nodo de la lista coincide con el actual continuamos */
					if (grafoMapa.devolverArcos().get(x).getFirst() == i * ancho + j) 
					{
						if (grafoMapa.devolverArcos().get(x).getFirst() + 1 == 
								grafoMapa.devolverArcos().get(x).getSecond()) {
							map = map + ' ';
							arcoDer = true;
						}
					}
					x = x + 1;
				} // Fin while arcoDer

				if (arcoDer == false)
					map = map + '|';

			} // Fin for j

			map = map + '\n';

		} // Fin for i

		return map;
	}
	
	/**
	 * Metodo para dibujar el Mapa con sus Salas, pero sin personajes
	 * 
	 * @return String con el dibujo de las Salas del Mapa sin personajes
	 * 
	 */
	private String mapaAStringSinPJ(){
		String map = "";
		int y = 0;
		int x = 0;
		/* Para comprobar si hay un arco debajo del nodo actual */
		Boolean arcoBajo = true; 
		/*Para comprobar si hay un a la derecha del nodo actual */
		Boolean arcoDer = false;
		
		map = map + " ";

		for (int i = 0; i < (ancho); i++) {
			map = map + "_ ";
		}
		map = map + '\n';

		for (int i = 0; i < alto; i++) {
			map = map + '|';

			for (int j = 0; j < ancho; j++) {

				arcoBajo = true;

					y = 0;
					arcoBajo = false;
					/* Comprobamos si hay arco con el nodo inferior */
					while (y < grafoMapa.devolverArcos().size() && arcoBajo == false) { 
						
						/* Si el nodo de la lista coincide con el actual continuamos */
						if (grafoMapa.devolverArcos().get(y).getFirst() == i * ancho + j) 
						{
							if (grafoMapa.devolverArcos().get(y).getFirst() + ancho == 
									grafoMapa.devolverArcos().get(y).getSecond()) {
								map = map + ' ';
								arcoBajo = true;
							}
						}
						y = y + 1;
					} // Fin while arcoBajo

				if (arcoBajo == false)
					map = map + '_'; 
					/* Imprimir antes de comprobar arcos a la
					 * derecha, por errores de espacio */

				x = 0;
				arcoDer = false;
				/* Comprobamos si hay un arco con el nodo a la derecha */
				while (x < grafoMapa.devolverArcos().size() && arcoDer == false) { 
					/* Si el nodo de la lista coincide con el actual continuamos */
					if (grafoMapa.devolverArcos().get(x).getFirst() == i * ancho + j) 
					{
						if (grafoMapa.devolverArcos().get(x).getFirst() + 1 == 
								grafoMapa.devolverArcos().get(x).getSecond()) {
							map = map + ' ';
							arcoDer = true;
						}
					}
					x = x + 1;
				} // Fin while arcoDer

				if (arcoDer == false)
					map = map + '|';

			} // Fin for j

			map = map + '\n';

		} // Fin for i
		
		return map;
	}
	
	/**
	 * Metodo para buscar una Sala en el Mapa
	 * 
	 * @param id Id de la Sala a buscar
	 * 
	 * @return Objeto de la clase Sala
	 * 
	 */
	public Sala buscarSala(int id) {

		return mapaSalas[id / ancho][id % ancho];

	}
	
	/**
	 * Obtiene el atributo alto de la clase Mapa
	 * 
	 * @return Entero con la altura de la Mapa
	 * 
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Cambia el valor del atributo alto de la clase Mapa
	 * 
	 * @param alto Nuevo valor entero
	 * 
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}
	
	/**
	 * Obtiene el atributo ancho de la clase Mapa
	 * 
	 * @return Entero con la anchura de la Mapa
	 * 
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Cambia el valor del atributo ancho de la clase Mapa
	 * 
	 * @param ancho Nuevo valor entero
	 * 
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * Obtiene el atributo id_salida de la clase Mapa
	 * 
	 * @return Entero con la id de la Sala que contiene la Puerta del trono
	 * 
	 */
	public int getId_salida() {
		return id_salida;
	}

	/**
	 * Cambia el valor del atributo id_salida de la clase Mapa
	 * 
	 * @param id_salida Nuevo valor entero
	 * 
	 */
	public void setId_salida(int id_salida) {
		this.id_salida = id_salida;
	}
	
	/**
	 * Obtiene el atributo puertaTrono de la clase Mapa
	 * 
	 * @return Objeto de la clase Puerta
	 * 
	 */
	public Puerta getPuertaTrono() {
		return puertaTrono;
	}

	/**
	 * Cambia el atributo puertaTrono de la clase Mapa
	 * 
	 * @param puertaTrono Nuevo objeto de la clase Puerta
	 * 
	 */
	public void setPuertaTrono(Puerta puertaTrono) {
		this.puertaTrono = puertaTrono;
	}

	/**
	 * Obtiene la matriz de las Salas que componen el Mapa
	 * 
	 * @return Matriz de tipo Sala que componen el Mapa
	 * 
	 */
	public Sala[][] getMapaSalas() {
		return mapaSalas;
	}

	/**
	 * Cambia la matriz de las Salas que componen el Mapa
	 * 
	 * @param mapaSalas Nueva matriz de tipo Sala
	 * 
	 */
	public void setMapaSalas(Sala[][] mapaSalas) {
		this.mapaSalas = mapaSalas;
	}

	/**
	 * Obtiene el atributo grafoMapa de la clase Mapa
	 * 
	 * @return Objeto de la clase Grafo
	 * 
	 */
	public Grafo getGrafoMapa() {
		return grafoMapa;
	}

	/**
	 * Cambia el atributo grafoMapa de la clase Mapa
	 * 
	 * @param grafoMapa Nuevo objeto de la clase Grafo
	 * 
	 */
	public void setGrafoMapa(Grafo grafoMapa) {
		this.grafoMapa = grafoMapa;
	}
	
	/**
	 * Obtiene el atributo mapaSinAtajos de la clase Mapa
	 * 
	 * @return String con el mapa de salas, sin atajos
	 * 
	 */
	public String getMapaSinAtajos() {
		return mapaSinAtajos;
	}
	
	/**
	 * Cambia el atributo mapaSinAtajos de la clase Mapa
	 * 
	 * @param mapaSinAtajos Nuevo string de un mapa
	 * 
	 */
	public void setMapaSinAtajos(String mapaSinAtajos) {
		this.mapaSinAtajos = mapaSinAtajos;
	}

	/**
	 * Indica si la Puerta del trono esta abierta
	 * 
	 * @return True : si la Puerta esta abierta <br> False : si la Puerta esta cerrada
	 * 
	 */
	public boolean finJuego() {
		return puertaTrono.isEstado();
	}
	
	/**
	 * Obtiene el dibujo con las Sala que forman el Mapa
	 * 
	 * @return String con el mapa de salas
	 * 
	 */
	public String toString(){
		return this.mapaAString();
	}
}

