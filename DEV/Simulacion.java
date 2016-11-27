package DEV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import Estructuras.Llave;
import Personajes.Personaje;

public class Simulacion {
	
	Mapa mapaGOT;
	Cargador loader;
	
	Simulacion(String filename)
	{
		loader = new Cargador();
		mapaGOT = loader.load(filename);
	}
	
	private void simular(){
		
		boolean fin = false;
		
		for (int i = 0;i < 50 && !fin; i++)
			fin = mapaGOT.simTurnoMapa();
	}
	
	
	public static void main (String[] args){
		
		Simulacion sim = new Simulacion(args[0]);
		sim.simular();
	}
	
	/**
	 * Metodo para almacenar los datos de un turno en el archivo de registro
	 * 
	 * @param turno
	 *            Numero del turno en el que se encuentra la simulacion
	 * @param mapaInicial
	 *            Booleano para indicar si solo hay que guardar el mapa de la
	 *            Galaxia o la informacion completa del turno
	 * 
	 * @throws IOException
	 * 
	 */
	private void datosAFichero(int turno, boolean mapaInicial) throws IOException {
		//TODO: No necesario en esta entrega
		/* Iniciar flujo (true añade datos al final) */
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("registro.log", true))); 
		Personaje persAux; // Para extraer datos de los personajes

		if (mapaInicial == false) {

			if (turno == 0) // Imprimir todos los caminos de los personajes al
							// comienzo de la simulacion
			{
				Iterator<Personaje> it = mapaGOT.buscarSala(0).getColaPers().iterator(); 

				while (it.hasNext() == true) {

					persAux = it.next();
					out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
				}

				it = mapaGOT.buscarSala(mapaGOT.getAlto() * mapaGOT.getAncho() - mapaGOT.getAncho()).getColaPers().iterator(); 

				while (it.hasNext() == true) {

					persAux = it.next();
					out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
				}

				it = mapaGOT.buscarSala(mapaGOT.getAlto() * mapaGOT.getAncho() - 1).getColaPers().iterator(); 

				while (it.hasNext() == true) {

					persAux = it.next();
					out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
				}

			}

			if (mapaGOT.finJuego() == false) {

				out.println("===============================================");
				out.println("(turno:" + turno + ")");
				out.println("(galaxia:" + (mapaGOT.getAlto() * mapaGOT.getAncho() - 1) + ")");

				out.print("(puerta:");
				if (mapaGOT.getPuertaTrono().isEstado() == true) {
					out.print("abierta:" + mapaGOT.getPuertaTrono().getProfundidad() + ":");
					out.print(mapaGOT.getPuertaTrono().getProbados().arbolAString());
					out.print(")");
				} else {
					out.print("cerrada:" + mapaGOT.getPuertaTrono().getProfundidad() + ":");
					out.print(mapaGOT.getPuertaTrono().getProbados().arbolAString());
					out.print(")");
				}

				// ------------------------------------
				// Mostrar el mapa de la Galaxia
				// ------------------------------------

				out.println(mapaGOT.toString());

				// ------------------------------------
				// Mostrar estaciones con midiclorianos
				// ------------------------------------

				out.println();

				for (int i = 0; i < mapaGOT.getAlto(); i++) {

					for (int j = 0; j < mapaGOT.getAncho(); j++) {

						Iterator<Midi> it = listaEstaciones[i][j].getListaMidiEst().iterator();

						if (it.hasNext() == true) {
							out.print("(estacion:" + listaEstaciones[i][j].getId() + ": ");

							while (it.hasNext() == true) {

								out.print(it.next().toString() + " ");
							}

							out.print(")");
							out.println();
						}
					}
				}

				// ------------------------------------
				// Mostrar personajes en la galaxia
				// ------------------------------------

				for (int i = 0; i < mapaGOT.getAlto(); i++) {

					for (int j = 0; j < mapaGOT.getAncho(); j++) {

						Iterator<Personaje> it = listaEstaciones[i][j].getColaPers().iterator();

						while (it.hasNext() == true) {

							out.println(it.next().toString());
						}
					}
				}

				out.close(); // Cerrar flujo
			} else // Si ha terminado la simulacion, imprimir lo siguiente
			{
				finAFichero(out);
				out.close(); // Cerrar flujo
			}

		} else {
			out.println(mapaGOT.toString());
			out.close();
		}

	}
}
