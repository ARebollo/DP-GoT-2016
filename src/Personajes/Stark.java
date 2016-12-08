package Personajes;

import java.util.LinkedList;
import java.util.List;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Grafo;

public class Stark extends Personaje{
	
	 public Stark(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
     	
     	super(nombre, marcaId, turno, idEstacion, map); 
     	
         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
     }

	@Override
	protected void hallarCamino(Mapa map) {
		List<Integer> listaAux = new LinkedList<Integer>();
		Grafo grafoAux = map.getGrafoMapa();
		
		listaAux = grafoAux.encontrarCaminoList(0, map.getId_salida(), listaAux);
		
		int dirAnt = listaAux.remove(0);
		int dirSig;
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
	}

	@Override
	protected void tocarPuerta(Puerta puertamap) {
		if (tieneLlaves())
		{
		 puertamap.probarLlave(sacarLlave());	
		}
	}
	
	@Override
	protected void moverse(Mapa map)
	{
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

	@Override
	protected void accionPersonaje(Sala sala) {
		if (sala.hayLlaves())
		{
		 aniadirLlave(sala.sacarLlave());
		}
		
	}
}
