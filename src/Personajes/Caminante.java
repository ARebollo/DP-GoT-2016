package Personajes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;

/**
* Declaracion de la clase Caminante
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Caminante extends Personaje{
	
	/** Cola de DIR que almacena el camino del Caminante */
	private Queue<dir> CaminoC;
	
	/** Lista de Char que almacena los personajes eliminados por el Caminante */
	private List<Character> ListaMarcas;
	
	/**
 	* Constructor parametrizado de la clase Caminante
	* 
	* @param nombre Nombre del personaje
	* @param marcaId Marca del personaje
	* @param turno Turno en el que comienza a mover el personaje
	* @param idSala Sala en la que se encuentra el personaje
	* @param map Nuestro Mapa
	* 
	*/
	public Caminante(String nombre, char marcaId, int turno, int idSala, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idSala, map); 
	     	ListaMarcas = new LinkedList<Character>();
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	/**
   	 * Metodo para hallar y almacenar el camino que el Caminante ha de seguir en el Mapa
   	 * 
   	 * @param map Nuestro Mapa
   	 * 
   	 */
	protected void hallarCamino(Mapa map) {
		Grafo grafoAux = map.getGrafoMapa();
		dir Direc;
		CaminoC = new LinkedList<dir>();	// Para guardar una copia del camino del imperial (para usarlo en el reseteo)
		List<Integer> listaAux = new LinkedList<Integer>();
		
		
		int dirAnt, dirSig;
		
		listaAux = grafoAux.encontrarMasCorto(grafoAux.getNumNodos() - map.getAncho(), 0);
		
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarMasCorto(0, map.getAncho() - 1);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarMasCorto(map.getAncho() - 1, map.getAncho() * map.getAlto() - 1);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarMasCorto(map.getAncho() * map.getAlto() - 1,grafoAux.getNumNodos() - map.getAncho());
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
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
   	 * Metodo para eliminar un Personaje de la simulacion y aniadir su marca a la lista del Caminante
   	 * 
   	 * @param sala Objeto de la clase Sala
   	 * 
   	 */
	protected void accionPersonaje(Sala sala) {
		
		if (sala.hayPersonajes() && sala.getColaPers().get(0).getClass()!=this.getClass())
		{
			ListaMarcas.add(sala.sacarPj().getMarcaId());
		}		
	}

	@Override
	/**
   	 * Metodo para mover al Caminante <br>
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
	
	@Override
	/**
	 * Muestra la informacion de la clase Caminante
	 * 
	 */
	public String toString() {
		
		String Comidos = "";
		Iterator<Character> it = ListaMarcas.iterator();
		
		while(it.hasNext() == true){
			 
			Comidos = Comidos + " " + it.next().toString();
		}
	
	  return getClass().getSimpleName().toLowerCase() + ":" + marcaId + ":" + idSala + ":" + getTurnoMasAlto() + ":" + Comidos + ")";
	}

}
