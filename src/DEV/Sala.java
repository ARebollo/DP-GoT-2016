package DEV;

import java.util.List;

import Personajes.Personaje;

public class Sala {
	
	private List<Personaje> colaPers;
	
	
	public Personaje mirarPJ(){
		return colaPers.get(0);
	}
	
	public Integer cuantosPJ(){
		return colaPers.size();
	}

	public boolean hayPersonajes() {
		return !colaPers.isEmpty();
	}

	public void aniadirPj(Personaje personaje) {
		// TODO Auto-generated method stub
		
	}
}
