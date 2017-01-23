package Personajes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;
import Estructuras.Llave;

/**
* Declaracion de la clase Lannister
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Lannister extends Personaje {
	
	/** Cola de DIR que almacena el camino del Lannister */
	private Queue<dir> CaminoL;
	
	/**
 	* Constructor parametrizado de la clase Lannister
	* 
	* @param nombre Nombre del personaje
	* @param marcaId Marca del personaje
	* @param turno Turno en el que comienza a mover el personaje
	* @param idSala Sala en la que se encuentra el personaje
	* @param map Nuestro Mapa
	* 
	*/
	 public Lannister(String nombre, char marcaId, int turno, int idSala, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idSala, map); 
	     	
	     	for (int i = 1; i <= 29; i = i + 2) {

				aniadirLlave(new Llave(i)); // Los imperiales tienen una pila de midiclorianos del 1 al 29 saltandose los pares
			}
	     	
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	/**
   	 * Metodo para hallar y almacenar el camino que el Lannister ha de seguir en el Mapa
   	 * 
   	 * @param map Nuestro Mapa
   	 * 
   	 */
	protected void hallarCamino(Mapa map) {
		Grafo grafoAux = map.getGrafoMapa();
		dir Direc;
		CaminoL = new LinkedList<dir>();	// Para guardar una copia del camino del imperial (para usarlo en el reseteo)
		List<Integer> listaAux = new LinkedList<Integer>();
		
		
		int dirAnt, dirSig;

		listaAux = grafoAux.encontrarMasCorto(map.getAncho() * map.getAlto() - 1, map.getAncho() - 1);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarMasCorto(map.getAncho() - 1, 0);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarMasCorto(0, grafoAux.getNumNodos() - map.getAncho());
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarMasCorto(grafoAux.getNumNodos() - map.getAncho(), grafoAux.getNumNodos() - 1);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
	}

	@Override
	/**
   	 * Metodo para cerrar la Puerta del trono
   	 * 
   	 * @param puertamap Objeto de la clase Puerta
   	 * 
   	 */
	protected void tocarPuerta(Puerta puertamap) {
		puertamap.cerrarPuerta();
		
	}

	@Override
	/**
   	 * Metodo para perder una LLave que posee el Lannister en las Sala con identificador par
   	 * 
   	 * @param sala Objeto de la clase Sala
   	 * 
   	 */
	protected void accionPersonaje(Sala sala) {
		if (getidSala() % 2 == 0 && tieneLlaves())
		{
		 sala.aniadirLlave(getPilaLlave().removeFirst());
		}
		
	}

	@Override
	/**
   	 * Metodo para mover al Lannister <br>
   	 * Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
   	 * 
   	 * @param map Nuestro Mapa
   	 * 
   	 */
	protected void moverse(Mapa map) {
		if (camino.peek() == null)
		{
			hallarCamino(map);
		}
		map.buscarSala(dirACamino(camino.peek(), map.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
		setidSala(dirACamino(camino.remove(), map.getAncho()));
	}

}
