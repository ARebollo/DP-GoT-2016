package Personajes;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;

public class Caminante extends Personaje{
	 
	public Caminante(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idEstacion, map); 
	     	
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
		
		if (sala.hayPersonajes())
		{
			//TODO Hay que guardarlos en algun sitio?
			sala.sacarPj();
		}
		
	}

}
