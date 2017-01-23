package DEV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import Estructuras.Llave;
import Excepciones.ConfigNoValida;
import Personajes.Personaje;

/**
* Declaracion de la clase Simulacion
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Simulacion {
	
	/** Nuestro Mapa, contiene el tablero de la simulacion */
	Mapa mapaGOT;
	
	/** Nuestro Cargador, carga los datos del fichero */
	Cargador loader;
	
	/** Entero que indica el turno actual de la simulacion */
	int turno;
	
	/**
	 * Constructor parametrizado de la clase Simulacion 
	 * 
	 * @param filename Nombre del fichero de configuracion
	 * 
	 * @throws IOException, ConfigNoValida                
	 * 
	 */
	Simulacion(String filename) throws IOException, ConfigNoValida
	{
		loader = new Cargador();
		mapaGOT = loader.load(filename);
		turno = 0;
	}
	
	/**
	 * Metodo para llevar a cabo la simulacion <br>
	 * Termina cuando se alcanza un limite de 50 turnos o cuando un Personaje consiga abrir la puerta
	 * 
	 */
	private void simular() throws IOException{
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("registro.log", true)));
		datosInicial(out);
		System.out.println("INICIANDO");
		boolean fin = false;
		
		while (turno < 100 && !fin)
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
	
	/**
	 * Metodo para mostrar los datos de un turno 
	 * 
	 */
	private void mostrarTurno() {

		System.out.println();
		System.out.println("(turno:" + turno + ")");
		System.out.println("(mapa:" + (mapaGOT.getAlto() * mapaGOT.getAncho() - 1) + ")");

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
		// Mostrar el mapa
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
		// Mostrar personajes en el mapa
		// ------------------------------------

		for (int i = 0; i < mapaGOT.getAlto(); i++) {

			for (int j = 0; j < mapaGOT.getAncho(); j++) {

				mapaGOT.buscarSala(i*mapaGOT.getAncho() + j).mostrarPersonajes();
			}
		}
	}

	/**
	 * Metodo para mostrar el dibujo del mapa de Salas
	 * 
	 */
	private void mostrarMapa() {
		System.out.print(mapaGOT);
	}

	/**
	 * Metodo para mostrar los datos del final de la simulacion
	 * 
	 */
	private void mostrarFin() {

		System.out.println("(miembrostrono)");

		// ----------------------
		// Mostrando ganadores
		// ----------------------
		System.out.println("(nuevorey:" + mapaGOT.getPuertaTrono().getPersTrono().get(0) + ")");
	}
	
	/**
	 * Metodo para almacenar los datos del comienzo de la simulacion en el archivo de registro
	 * 
	 * @param out PrintWriter, flujo de escritura
	 * 
	 */
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

	/**
	 * Metodo para almacenar los datos de un turno en el archivo de registro
	 * 
	 * @param out PrintWriter, flujo de escritura
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
				// Mostrar personajes en el mapa
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

	/**
	 * Metodo para almacenar los datos del final de la simulacion en el archivo de registro
	 * 
	 * @param out PrintWriter, flujo de escritura
	 * 
	 */
	private void datosFin(PrintWriter out) {

		out.println("(miembrostrono)");

		// ----------------------
		// Mostrando ganadores
		// ----------------------
		out.println("(nuevorey:" + mapaGOT.getPuertaTrono().getPersTrono().get(0));
		for(int i = 1;i<mapaGOT.getPuertaTrono().getPersTrono().size();i++)
			out.println(mapaGOT.getPuertaTrono().getPersTrono().get(i));
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
