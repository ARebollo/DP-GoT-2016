package Personajes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;
import Estructuras.Llave;
import Personajes.Personaje.dir;

public class Lannister extends Personaje {
	
	private Queue<dir> CaminoL;
	
	 public Lannister(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idEstacion, map); 
	     	
	     	for (int i = 1; i <= 29; i = i + 2) {

				aniadirLlave(new Llave(i)); // Los imperiales tienen una pila de midiclorianos del 1 al 29 saltandose los pares
			}
	     	
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	protected void hallarCamino(Mapa map) {
		Grafo grafoAux = map.getGrafoMapa();
		dir Direc;
		CaminoL = new LinkedList<dir>();	// Para guardar una copia del camino del imperial (para usarlo en el reseteo)
		List<Integer> listaAux = new LinkedList<Integer>();
		
		
		int dirAnt, dirSig;

		listaAux = grafoAux.encontrarCaminoList(map.getAncho() * map.getAlto() - 1, map.getAncho() - 1, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(map.getAncho() - 1, 0, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(0, grafoAux.getNumNodos() - map.getAncho(), listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoL.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(grafoAux.getNumNodos() - map.getAncho(), grafoAux.getNumNodos() - 1, listaAux);
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
	protected void tocarPuerta(Puerta puertamap) {
		puertamap.cerrarPuerta();
		//TODO Resetear camino cuando toque
		
	}

	@Override
	protected void accionPersonaje(Sala sala) {
		if (getidSala() % 2 == 0 && tieneLlaves())
		{
		 getPilaLlave().removeLast();
		}
		
	}

	@Override
	protected void moverse(Mapa map) {
		if (camino.peek() == null)
		{
			hallarCamino(map);
		}
		map.buscarSala(dirACamino(camino.peek(), map.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
		setidSala(dirACamino(camino.remove(), map.getAncho()));
	}

}
