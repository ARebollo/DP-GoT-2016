package Personajes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;

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
	protected void tocarPuerta(Puerta puertamap) {
		puertamap.cerrarPuerta();	
		
	}

	@Override
	protected void accionPersonaje(Sala sala) {
		if (sala.hayPersonajes() && sala.getColaPers().get(0).getClass()!=this.getClass())
		{
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
	
	@Override
	public String toString() {
		
		String Comidos = "";
		Iterator<Character> it = ListaMarcas.iterator();
		
		while(it.hasNext() == true){
			 
			Comidos = Comidos + " " + it.next().toString();
		}
	
	  return getClass().getSimpleName() + ":" + marcaId + ":" + idSala + ":" + getTurnoMasAlto() + ":" + Comidos + ")";
	}

}
