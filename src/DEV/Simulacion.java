package DEV;

public class Simulacion {
	Mapa mapaGOT;
	Cargador loader;
	
	Simulacion(String filename)
	{
		loader = new Cargador();
		mapaGOT = loader.load(filename);
	}
	
	private void simular(){
		for (int i = 0;i < 50; i++)
			mapaGOT.simTurnoMapa();
	}
	
	
	public static void main (String[] args){
		Simulacion sim = new Simulacion(args[0]);
		sim.simular();
	}
	
	public void datosaFichero(){
		
	}
}
