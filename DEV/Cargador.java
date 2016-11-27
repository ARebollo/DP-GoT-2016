package DEV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Estructuras.Grafo;
import Estructuras.Llave;
import Excepciones.ConfigNoValida;
import Excepciones.PersNoValido;
import Personajes.*;

public class Cargador {
	
	Mapa load (String fileName)
	{
		Mapa map; 
		System.out.println(" ");
		System.out.println("Intentando leer fichero :");
		try {
			BufferedReader flujo = new BufferedReader(new FileReader(fileName));

			String linea = flujo.readLine();

			List<String> vCampos = new ArrayList<String>();
			@SuppressWarnings("unused")
			int numCampos = 0; // Utilizado para la funcion trocearLineaInicial
			@SuppressWarnings("unused")
			char x; // Utilizado para extraer un char
			boolean mapaCreado = false; // Utilizado para evitar crear 2 galaxias
										// en
										// la misma ejecucion
			Personaje persAux; // Utilizado para introducir el camino a los
								// personajes
			if (flujo.ready() == false) {
				System.out.println("Problemas con el buffer (flujo)...");
			} else {

				while (linea != null) {

					if (linea.contains("--") == false) // Solo tomar en cuenta
														// lineas sin
														// comentarios
					{
						// Metemos la linea en vCampos
						vCampos.clear();
						numCampos = trocearLineaInicial(linea, vCampos);
						if (vCampos.isEmpty() == true) {
							try {
								throw new ConfigNoValida("LÍNEA NO VÁLIDA");
							} catch (ConfigNoValida e) {
								System.out.println(e.getMessage());
							}
						}
					}
					linea = flujo.readLine(); // Avanzar en el fichero

					if (vCampos.isEmpty() == false) {
						switch (vCampos.get(0)) { // Miramos de que tipo es el
													// campo
						case ("MAPA"):
							try {
								if (mapaCreado == false) {

									// Comprobamos que los parámetros sean
									// válidos
									// (ancho,alto mayores que 0, salida dentro
									// de
									// la galaxia)
									if (Integer.parseInt(vCampos.get(1)) <= 0 || Integer.parseInt(vCampos.get(2)) <= 0
											|| Integer.parseInt(vCampos.get(3)) < 0
											|| Integer.parseInt(vCampos.get(3)) > (Integer.parseInt(vCampos.get(1))
													* Integer.parseInt(vCampos.get(2)))
											|| Integer.parseInt(vCampos.get(4)) <= 0) {
										throw new ConfigNoValida("DATOS DEL MAPA INVÁLIDOS");
									}

									// Para que no se cree más de una galaxia al
									// cargar de fichero

									mostrarDatosInicial(vCampos);
									// Obtenemos alto, ancho y salida
									
									int alto = Integer.parseInt(vCampos.get(1));
									int ancho = Integer.parseInt(vCampos.get(2));
									int id_salida = Integer.parseInt(vCampos.get(3));
									int prof = Integer.parseInt(vCampos.get(4));
									
									//Llamamos al constructor de mapa
									map = new Mapa(alto, ancho, id_salida, prof);

									// Impedimos que se cree más de una galaxia, aplicando el Singleton
									mapaCreado = true;

									// Dibujamos el mapa de la galaxia al inicio
									// de
									// la simulacion (en fichero de log)
									//TODO Mover a simulacion
									datosAFichero(0, true);

								} else {
									throw new ConfigNoValida("ERROR, YA EXISTE UNA GALAXIA");
								}

							} catch (ConfigNoValida e) {
								System.out.println(e.getMessage());
							}
							break;

						case ("STARK"):
							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (mapaCreado == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Stark(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), 0, map);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {

							} catch (PersNoValido e) {
							}
							break;

						case ("TARGARYEN"):
							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (mapaCreado == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Targaryen(vCampos.get(1), vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), 0, map);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {
								throw e;
							} catch (PersNoValido e) {
							}
							break;

						case ("LANNISTER"):

							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (mapaCreado == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Lannister(vCampos.get(1), vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), (map.getAlto() * map.getAncho()) - 1, map);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {
								throw e;
							} catch (PersNoValido e) {
							}
							break;

						case ("CAMINANTE"):
							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (mapaCreado == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Caminante(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), (map.getAlto() * map.getAncho())
																					- map.getAncho(), map);

									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {
								throw e;
							} catch (PersNoValido e) {
							}
							break;
						default:
							break;
						} // FIN SWITCH

					} // Fin IF

				} // Fin WHILE

				flujo.close();
				//TODO: Repartir los midis en galaxia
				almacenarMidi();
				repartirMidi();
			} // Fin ELSE
		} catch (FileNotFoundException e) {
			throw e;
		}
	}
	
	private int trocearLineaInicial(String S, List<String> vCampos) {

		boolean eol = false;
		int pos = 0, posini = 0, numCampos = 0;

		while (!eol) {

			pos = S.indexOf("#");
			if (pos != -1) {
				vCampos.add(new String(S.substring(posini, pos)));
				S = S.substring(pos + 1, S.length());
				numCampos++;
			} else
				eol = true;
		}
		return numCampos;
	}
	
	private void mostrarDatosInicial(List<String> vCampos) {
		System.out.println(vCampos);
	}
}
