package DEV;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Estructuras.Grafo;
import Estructuras.Llave;
import Personajes.Personaje;
import Personajes.Stark;

public class Mapa {
	private int alto;
	private int ancho;
	private int id_salida;
	private Puerta puertaTrono;
	private Sala[][] mapaSalas;
	private Queue<Llave> listaLlaveMapa;
	private Grafo grafoMapa;
	
	Mapa(int alto, int ancho, int id, int prof){
		this.alto = alto;
		this.ancho = ancho;
		this.id_salida = id;
		puertaTrono = new Puerta(prof);
		listaLlaveMapa = new LinkedList<Llave>();
		
		//Inicializamos cada Sala con su id
		mapaSalas = new Sala[alto][ancho];
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				mapaSalas[i][j] = new Sala(j + ancho * i);
			}
		}
		buscarSala(id_salida).setPuertaSalida(true);
		grafoMapa = new Grafo(alto,ancho);
		grafoMapa.procesarParedes(ancho, 1987);
		for (int i = 0;i<30;i++)
		{
			listaLlaveMapa.add(new Llave(i));
			if (i%2 != 0)
				listaLlaveMapa.add(new Llave(i));
		}
	}
	
	public boolean simTurnoMapa(){
		boolean fin = false;
		for(int i = 0;i<alto && !fin;i++)
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
	
	public Integer[] SalasMasFrec()
	{
		List<LinkedList<Integer>> listaCam = new LinkedList<LinkedList<Integer>>();
		grafoMapa.encontrarListaCaminos(0, id_salida, listaCam, null);
		
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
			while (!camino.isEmpty())
			{
				est = camino.remove(0);
				frecuencias[est]++;
			}
		}
		Integer[] SalasConLlaves = new Integer[9];
		int max = 0;
		for (int i = 0; i<9;i++)
		{
			for (int j = 0;j<frecuencias.length;j++)
			{
				if (frecuencias[j] > max)
				{
					if (SalasConLlaves[i] != 0)
					{
						frecuencias[SalasConLlaves[i]] = max;
					}
					max = frecuencias[j];
					SalasConLlaves[i] = j;
					frecuencias[j] = 0;
				}
				
			}
		}
		return SalasConLlaves;
	}
	
	
	private String mapaAString(){
		String map = "";
		int y = 0;
		int x = 0;
		Boolean arcoBajo = true; // Para comprobar si hay un arco debajo del
									// nodo actual
		Boolean arcoDer = false; // Para comprobar si hay un arco a la derecha
									// del nodo actual
		map = map + '\n';
		map = map + " ";

		for (int i = 0; i < (ancho * 2) - 1; i++) {
			map = map + '_';
		}
		map = map + '\n';

		for (int i = 0; i < alto; i++) {
			map = map + '|';

			for (int j = 0; j < ancho; j++) {

				arcoBajo = true;

				if (mapaSalas[i][j].hayPersonajes() == true) {
					if (mapaSalas[i][j].cuantosPJ() > 1) {
						map = map + String.valueOf(mapaSalas[i][j].cuantosPJ());
						/* Si hay más de dos personajes en una estación,
						 * muestra cuantos hay 
						 */
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
					map = map + '_'; // Imprimir antes de comprobar arcos a la
									// der, por errores de espacio

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

		map = map + ' ';

		map = map + '\n';
		return map;
	}

	public Sala buscarSala(int id) {

		return mapaSalas[id / ancho][id % ancho];

	}
	
	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getId_salida() {
		return id_salida;
	}

	public void setId_salida(int id_salida) {
		this.id_salida = id_salida;
	}

	public Puerta getPuertaTrono() {
		return puertaTrono;
	}

	public void setPuertaTrono(Puerta puertaTrono) {
		this.puertaTrono = puertaTrono;
	}

	public Sala[][] getMapaSalas() {
		return mapaSalas;
	}

	public void setMapaSalas(Sala[][] mapaSalas) {
		this.mapaSalas = mapaSalas;
	}

	public Grafo getGrafoMapa() {
		return grafoMapa;
	}

	public void setGrafoMapa(Grafo grafoMapa) {
		this.grafoMapa = grafoMapa;
	}

	public boolean finJuego() {
		return puertaTrono.isEstado();
	}

	public String toString(){
		return this.mapaAString();
	}

	public static void main(String[] args)
	{
		Mapa Map = new Mapa(6,6,35,5);
		System.out.println(Map);
	}
	
}

