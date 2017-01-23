package DEV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Excepciones.ConfigNoValida;
import Excepciones.PersNoValido;
import Personajes.*;

/**
* Declaracion de la clase Cargador
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Cargador {
	
	/**
	 * Metodo para cargar todos los datos del archivo y crear el mapa y los personajes basados en esos datos
	 *  
	 * @param fileName El nombre del archivo a cargar
	 * 
	 */
	Mapa load (String fileName) throws IOException, ConfigNoValida
	{
		Mapa map = null;
		System.out.println(" ");
		System.out.println("Intentando leer fichero :");
			BufferedReader flujo = new BufferedReader(new FileReader(fileName));

			String linea = flujo.readLine();

			List<String> vCampos = new ArrayList<String>();
			@SuppressWarnings("unused")
			int numCampos = 0; // Utilizado para la funcion trocearLineaInicial
			@SuppressWarnings("unused")
			char x; // Utilizado para extraer un char
			if (flujo.ready() == false) {
				System.out.println("Problemas con el buffer (flujo)...");
			} else {

				while (linea != null) {
					/* Solo tener en cuenta lineas sin comentarios */
					if (linea.contains("--") == false) 
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
					// Avanzar en el fichero
					linea = flujo.readLine(); 

					if (vCampos.isEmpty() == false) {
						/* Miramos que tipo de campo es */
						switch (vCampos.get(0)) {
						case ("MAPA"):
							try {
								/* Para no crear más de un mapa */
								if (map == null) {
									/* Comprobamos que los parámetros del mapa sean válidos */
									if (Integer.parseInt(vCampos.get(1)) <= 0 || Integer.parseInt(vCampos.get(2)) <= 0
											|| Integer.parseInt(vCampos.get(3)) < 0
											|| Integer.parseInt(vCampos.get(3)) > (Integer.parseInt(vCampos.get(1))
													* Integer.parseInt(vCampos.get(2)))
											|| Integer.parseInt(vCampos.get(4)) <= 0) {
										throw new ConfigNoValida("Error en los datos del Mapa");
									}

									mostrarDatosInicial(vCampos);
									/* Obtenemos alto, ancho y salida */
									int ancho = Integer.parseInt(vCampos.get(1));
									int alto = Integer.parseInt(vCampos.get(2));
									int id_salida = Integer.parseInt(vCampos.get(3));
									int prof = Integer.parseInt(vCampos.get(4));
									
									/* Llamamos al constructor de mapa */
									map = new Mapa(alto, ancho, id_salida, prof);

								} else {
									throw new ConfigNoValida("Ya existe un Mapa");
								}

							} catch (ConfigNoValida e) {
								if (map == null)
									throw e;
							}
							break;

						case ("STARK"):
							try {
								/* Si lleja a un pj y no hay mapa, error fatal */
								if (map != null) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										new Stark(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), 0, map);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else 
									throw new ConfigNoValida("No hay Mapa");
							}
							/* Los personajes no válidos no terminan el programa */
							catch (PersNoValido e) {}
							break;

						case ("TARGARYEN"):
							try {
								if (map != null) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										new Targaryen(vCampos.get(1), vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), 0, map);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("No hay Mapa");
							} 
							catch (PersNoValido e) {}
							break;

						case ("LANNISTER"):

							try {
								if (map != null) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										new Lannister(vCampos.get(1), vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), (map.getAlto() * map.getAncho()) - 1, map);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("No hay Mapa");
							} 
							catch (PersNoValido e) {}
							break;

						case ("CAMINANTE"):
							try {
								if (map != null) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										new Caminante(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), (map.getAlto() * map.getAncho())
																					- map.getAncho(), map);

									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("No hay Mapa");
							} 
							catch (PersNoValido e) {}
							break;
						default:
							break;
						} // FIN SWITCH

					} // Fin IF

				} // Fin WHILE

				flujo.close();
			} // Fin ELSE
		
		return map;
	}
	
	/**
	 * Metodo para trocear cada linea y separarla por campos
	 * 
	 * @param S cadena con la linea completa leida
	 * @param vCampos Array de String que contiene en cada posicion un campo distinto
	 * 
	 * @return numCampos Numero de campos encontrados
	 * 
	 */
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
	
	/**
	 * Metodo para mostrar los campos en los que se ha dividido una linea
	 * 
	 * @param vCampos Array de String que contiene en cada posicion un campo distinto
	 * 
	 */
	private void mostrarDatosInicial(List<String> vCampos) {
		System.out.println(vCampos);
	}
}
