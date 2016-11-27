package Personajes;

import DEV.Mapa;
import DEV.Puerta;
import DEV.Sala;

public class Targaryen extends Personaje{
	
	 public Targaryen(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
	     	
	     	super(nombre, marcaId, turno, idEstacion, map); 
	     	
	         System.out.println("Personaje " + getNombrePersonaje() +" creado.");
	     }

	@Override
	protected void hallarCamino(Mapa map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void tocarPuerta(Puerta puertamap) {
		
		if (tieneLlaves())
		{
		 puertamap.probarLlave(sacarLlave());	
		}
	}

	@Override
	protected void tocarLlave(Sala sala) {
		
		if (sala.hayLlaves())
		{
		 aniadirLlave(sala.sacarLlave());
		}
	}

}
