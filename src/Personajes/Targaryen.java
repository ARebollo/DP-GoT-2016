package Personajes;

import java.util.LinkedList;
import java.util.List;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;

/**
* Declaracion de la clase Targayren
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Targaryen extends Personaje{
	
	/**
 	* Constructor parametrizado de la clase Targaryen
	* 
	* @param nombre Nombre del personaje
	* @param marcaId Marca del personaje
	* @param turno Turno en el que comienza a mover el personaje
	* @param idSala Sala en la que se encuentra el personaje
	* @param map Nuestro Mapa
	* 
	*/
	 public Targaryen(String nombre, char marcaId, int turno, int idSala, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idSala, map); 
	     	
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	
	/**
   	 * Metodo para hallar y almacenar el camino que el Targayren ha de seguir en el Mapa
   	 * 
   	 * @param map Nuestro Mapa
   	 * 
   	 */
	protected void hallarCamino(Mapa map) {
		List<Integer> listaAux = new LinkedList<Integer>();
		Grafo grafoAux = map.getGrafoMapa();
		
		listaAux= grafoAux.manoDerecha(0, map.getId_salida(), map.getAncho());
		int dirAnt = listaAux.remove(0);
		int dirSig;
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
	}

	@Override
	/**
   	 * Metodo para probar una de las LLave recogidas por el Targayren en la Puerta del trono
   	 * 
   	 * @param puertamap Objeto de la clase Puerta
   	 * 
   	 */
	protected void tocarPuerta(Puerta puertamap) {
		if (tieneLlaves())
		{
		 puertamap.probarLlave(sacarLlave());	
		}
	}

	@Override
	/**
   	 * Metodo para recoger una LLave de la Sala en la que se encuentra el Stark
   	 * 
   	 * @param sala Objeto de la clase Sala
   	 * 
   	 */
	protected void accionPersonaje(Sala sala) {
		if (sala.hayLlaves())
		{
		 aniadirLlave(sala.sacarLlave());
		}
	}

	@Override
	/**
   	 * Metodo para mover al Targayren <br>
   	 * Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
   	 * 
   	 * @param map Nuestro Mapa
   	 * 
   	 */
	protected void moverse(Mapa map) {
		if (camino.peek() == null)
		{
			map.buscarSala(idSala).aniadirPj(this);
		}
		else 
		{
			map.buscarSala(dirACamino(camino.peek(), map.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
			setidSala(dirACamino(camino.remove(), map.getAncho()));
		}
	}

}
