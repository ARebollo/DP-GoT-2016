package Personajes;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;
import Estructuras.Llave;

public class Lannister extends Personaje {
	
	 public Lannister(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idEstacion, map); 
	     	
	     	for (int i = 1; i <= 29; i = i + 2) {

				aniadirLlave(new Llave(i)); // Los imperiales tienen una pila de midiclorianos del 1 al 29 saltandose los pares
			}
	     	
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	protected void hallarCamino(Mapa map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void tocarPuerta(Puerta puertamap) {
		puertamap.cerrarPuerta();
		//TODO Resetear camino cuando toque
		
	}

	@Override
	protected void tocarLlave(Sala sala) {
		if (getidSala() % 2 == 0 && tieneLlaves())
		{
		 getPilaLlave().removeLast();
		}
		
	}

}
