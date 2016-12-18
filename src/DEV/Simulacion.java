package DEV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import Estructuras.Llave;
import Excepciones.ConfigNoValida;
import Personajes.Personaje;

public class Simulacion {
	Mapa mapaGOT;
	Cargador loader;
	int turno;
	
	Simulacion(String filename) throws IOException, ConfigNoValida
	{
		loader = new Cargador();
		mapaGOT = loader.load(filename);
		turno = 0;
	}
	
	private void simular() throws IOException{
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("registro.log", true)));
		datosInicial(out);
		System.out.println("INICIANDO");
		boolean fin = false;
		
		while (turno < 50 && !fin)
		{
			fin = mapaGOT.simTurnoMapa();
			if (!fin)
			{
				datosAFichero(out);
				mostrarTurno();
			}
			else
			{
				datosAFichero(out);
				mostrarTurno();
				datosFin(out);
				mostrarFin();	
			}
			turno++;
		}
		out.close();
	}
	
	
	private void datosInicial(PrintWriter out) {
		Personaje persAux;
		out.print(mapaGOT.getMapaSinAtajos());
		Iterator<Personaje> it = mapaGOT.buscarSala(0).getColaPers().iterator(); 
		/* Para los Stark */

		while (it.hasNext() == true) {

			persAux = it.next();
			out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
		}

		it = mapaGOT.buscarSala(mapaGOT.getAlto() * mapaGOT.getAncho() - mapaGOT.getAncho()).getColaPers().iterator(); 
		/* Para los Targaryen */
		while (it.hasNext() == true) {

			persAux = it.next();
			out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
		}

		it = mapaGOT.buscarSala(mapaGOT.getId_salida()).getColaPers().iterator(); 
		/* Para los Lannister */


		while (it.hasNext() == true) {

			persAux = it.next();
			out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
		}
		
		it = mapaGOT.buscarSala(mapaGOT.getAncho()).getColaPers().iterator(); 
		/* Para los Caminantes */


		while (it.hasNext() == true) {

			persAux = it.next();
			out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
		}
	}

	private void mostrarTurno() {

		System.out.println();
		System.out.println("(turno:" + turno + ")");
		System.out.println("(galaxia:" + (mapaGOT.getAlto() * mapaGOT.getAncho() - 1) + ")");

		System.out.print("(puerta:");
		if (mapaGOT.finJuego() == true) {
			System.out.print("abierta:" + mapaGOT.getPuertaTrono().getProfundidad() + ":");
			mapaGOT.getPuertaTrono().getProbados().inOrder();
			System.out.print(")");
		} else {
			System.out.print("cerrada:" + mapaGOT.getPuertaTrono().getProfundidad() + ":");
			mapaGOT.getPuertaTrono().getProbados().inOrder();
			System.out.print(")");
		}

		// ------------------------------------
		// Mostrar el mapa de la Galaxia
		// ------------------------------------

		System.out.println(mapaGOT);
		mostrarMapa();

		// ------------------------------------
		// Mostrar estaciones con midiclorianos
		// ------------------------------------

		System.out.println();

		for (int i = 0; i < mapaGOT.getAlto(); i++) {

			for (int j = 0; j < mapaGOT.getAncho(); j++) {

				if (mapaGOT.buscarSala(i*mapaGOT.getAncho() + j).hayLlaves() == true) {
					System.out.print("(estacion:" + (i*mapaGOT.getAncho() + j) + ": ");
					mapaGOT.buscarSala(i*mapaGOT.getAncho() + j).mostrarLista();
					System.out.print(")");

					System.out.println();
				}
			}
		}

		// ------------------------------------
		// Mostrar personajes en la galaxia
		// ------------------------------------

		for (int i = 0; i < mapaGOT.getAlto(); i++) {

			for (int j = 0; j < mapaGOT.getAncho(); j++) {

				mapaGOT.buscarSala(i*mapaGOT.getAncho() + j).mostrarPersonajes();
			}
		}
	}

	private void mostrarMapa() {
		System.out.print(mapaGOT);
	}

	private void mostrarFin() {

		System.out.println("(miembrostrono)");

		// ----------------------
		// Mostrando ganadores
		// ----------------------
		System.out.println("(nuevorey:" + mapaGOT.getPuertaTrono().getPersTrono().get(0));
	}
	
	private void datosFin(PrintWriter out) {

		out.println("(miembrostrono)");

		// ----------------------
		// Mostrando ganadores
		// ----------------------
		out.println("(nuevorey:" + mapaGOT.getPuertaTrono().getPersTrono().get(0));
		for(int i = 1;i<mapaGOT.getPuertaTrono().getPersTrono().size();i++)
			out.println(mapaGOT.getPuertaTrono().getPersTrono().get(i));
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
	private void datosAFichero(PrintWriter out) throws IOException {
		
				out.println("(turno:" + turno + ")");
				out.println("(mapa:" + (mapaGOT.getAlto() * mapaGOT.getAncho() - 1) + ")");

				out.print("(puerta:");
				if (mapaGOT.finJuego() == true) {
					out.print("abierta:" + mapaGOT.getPuertaTrono().getProfundidad() + ":");
					out.print(mapaGOT.getPuertaTrono().getCombinacionString());
					out.print(mapaGOT.getPuertaTrono().getProbadosString());
					out.println(":)");
				} else {
					out.print("cerrada:" + mapaGOT.getPuertaTrono().getProfundidad() + ":");
					out.print(mapaGOT.getPuertaTrono().getCombinacionString());
					out.print(mapaGOT.getPuertaTrono().getProbadosString());
					out.println(":)");
				}

				// ------------------------------------
				// Mostrar el Mapa
				// ------------------------------------

				out.print(mapaGOT);

				// ------------------------------------
				// Mostrar salas con llaves
				// ------------------------------------

				for (int i = 0; i < mapaGOT.getAlto(); i++) {

					for (int j = 0; j < mapaGOT.getAncho(); j++) {

						Iterator<Llave> it = mapaGOT.buscarSala(i*mapaGOT.getAncho() + j).getlistaLlaveSala().iterator();

						if (it.hasNext() == true) {
							out.print("(sala:" + (i*mapaGOT.getAncho() + j) + ":");

							while (it.hasNext() == true) {

								out.print(" " + it.next().toString());
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

						Iterator<Personaje> it = mapaGOT.buscarSala(i*mapaGOT.getAncho() + j).getColaPers().iterator();

						while (it.hasNext() == true) {

							out.println("(" + it.next().toString());
						}
					}
				}
	}
	
	public static void main (String[] args){
		Simulacion sim;
		try {
			sim = new Simulacion(args[0]);
			sim.simular();
		} catch (IOException e) {
			System.out.println("Error de archivo");
			e.printStackTrace();
		} catch (ConfigNoValida e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
