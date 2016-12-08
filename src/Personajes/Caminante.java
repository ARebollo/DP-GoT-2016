package Personajes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;
import Personajes.Personaje.dir;

public class Caminante extends Personaje{

	private Queue<dir> CaminoC;
	private List<Character> ListaMarcas;
	
	public Caminante(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idEstacion, map); 
	     	ListaMarcas = new LinkedList<Character>();
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	protected void hallarCamino(Mapa map) {
		Grafo grafoAux = map.getGrafoMapa();
		dir Direc;
		CaminoC = new LinkedList<dir>();	// Para guardar una copia del camino del imperial (para usarlo en el reseteo)
		List<Integer> listaAux = new LinkedList<Integer>();
		
		
		int dirAnt, dirSig;
		
		listaAux = grafoAux.encontrarCaminoList(grafoAux.getNumNodos() - map.getAncho(), 0, listaAux);
		//listaAux = grafoAux.encontrarCaminoList(, map.getAncho() - 1, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(0, map.getAncho() - 1, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(map.getAncho() - 1, map.getAncho() * map.getAlto() - 1, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			Direc = interpretarCamino(dirAnt,dirSig);
			aniadirCamino(Direc);
			CaminoC.add(Direc);
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(map.getAncho() * map.getAlto() - 1,grafoAux.getNumNodos() - map.getAncho(), listaAux);
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
	protected void tocarPuerta(Puerta puertamap) {
		puertamap.cerrarPuerta();
		//TODO Resetear camino cuando toque
		
		
	}

	@Override
	protected void accionPersonaje(Sala sala) {
		if (sala.hayPersonajes())
		{
			//TODO Hay que guardarlos en algun sitio?
			ListaMarcas.add(sala.sacarPj().getMarcaId());
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
