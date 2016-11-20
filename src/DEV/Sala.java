package DEV;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Estructuras.Llave;
import Personajes.Personaje;

/**
* Declaracion de la clase Sala
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Sala{
	
	private int id;
	private boolean puertaSalida;
    private List<Llave> listaLlaveSala;
	private List<Personaje> colaPers;
	
	/**
   	 * Constructor default de la clase Sala
   	 * 
   	 */
	public Sala(){
		
		id = -1;
		puertaSalida = false;
		listaLlaveSala = new LinkedList<Llave>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Sala
   	 * 
   	 * @param id Id de la puerta
   	 * 
   	 */
	Sala(int id){
		
		this.id = id;	
		puertaSalida = false;
		listaLlaveSala = new LinkedList<Llave>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Sala
   	 * 
   	 * @param id Id de la puerta
   	 * @param puerta Indica si la Sala contiene una puerta (True : la contiene | False : no la contiene)
   	 * 
   	 */
	Sala(int id, boolean puerta){
		
		this.id = id;
		puertaSalida = puerta;	
		listaLlaveSala = new LinkedList<Llave>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor por copia de la clase Sala
   	 * 
   	 * @param est Objeto de la clase Sala
   	 * 
   	 */
	Sala (Sala est) {
		
		this.id = est.getId();
		this.puertaSalida = est.isPuertaSalida();
		this.listaLlaveSala = est.listaLlaveSala;
		this.colaPers = est.colaPers;
		
	}
	
	// List
	
	/**
   	 * Metodo para aniadir una llave a la Sala, ordenandolos de menor a mayor
   	 * 
   	 * @param Llave Objeto de la clase Llave
   	 * 
   	 */
	public void aniadirLlave(Llave Llave){
		
		int i = 0;
		boolean enc = false;
		
		if (listaLlaveSala.isEmpty() == true)
		{
		 listaLlaveSala.add(Llave);	 // Si la lista esta vacia, insertamos el valor
		 enc = true;
		}
		
		while (i < listaLlaveSala.size() && enc == false){
			
			if (Llave.getId() < listaLlaveSala.get(i).getId())
			{
			 listaLlaveSala.add(i, Llave);
			 enc = true;
			}
			
			i++;
		}
		
		if (enc == false)
		{
		 listaLlaveSala.add(Llave);  // Si no ha encontrado, es que el elemento es el mayor de la lista y lo insertamos al final
		}
		
	}
	
	/**
   	 * Metodo para obtener y quitar la primera llave de la lista en la Sala
   	 * 
   	 * @return Objeto de la clase Llave
   	 * 
   	 */
	public Llave sacarLlave(){
		
		return listaLlaveSala.remove(0);
	}
	
	/**
   	 * Indica si la Sala contiene llaves
   	 * 
   	 * @return True : si la Sala contiene alguna llave <br> False : si la Sala no contiene ninguna llave
   	 * 
   	 */
	public boolean hayLlaves(){
		return !listaLlaveSala.isEmpty();
	}
	
	/**
   	 * Metodo para añadir un Personaje a la lista de la Sala
   	 * 
   	 * @param pj Objeto de la clase Personaje
   	 * 
   	 */
	public void aniadirPj(Personaje pj){		
		colaPers.add(pj);
	}
	
	/**
   	 * Metodo para obtener al primer Personaje que se encuentra en la Sala
   	 * 
   	 * @return Objeto de la clase Personaje
   	 * 
   	 */
	public Personaje mirarPJ(){
		return colaPers.get(0);
	}
	
	/**
   	 * Metodo para mostrar las llaves que tiene la Sala
   	 * 
   	 */
	public void mostrarLista(){
		
		Iterator<Llave> it = listaLlaveSala.iterator();
		
		while (it.hasNext() == true){
			
			System.out.print(it.next().toString() + " ");

		}
		
	}
	
	/**
   	 * Metodo para devolver las llaves que tiene la Sala en forma de String
   	 * 
   	 * @return Cadena con la lista de llaves de la Sala
   	 * 
   	 */
	public String mostrarListaStr(){
		
		String a = "";
		Iterator<Llave> it = listaLlaveSala.iterator();
		
		while (it.hasNext() == true){
			
			a += it.next().toString() + " ";
		}
		
	  return a;
	}
	
	/**
   	 * Muestra la informacion de todos los Personajes que se encuentran en la Sala
   	 * 
   	 */
	public void mostrarPersonajes(){
		
		Iterator<Personaje> it = colaPers.iterator();
		
		while (it.hasNext() == true){
			
			System.out.print(it.next().toString() + " ");

		}
	}
	//TODO: Los hay que ganan con puerta cerrada?
	/**
   	 * Muestra la informacion de los Personajes que ganan con puerta cerrada que se encuentran en la Sala
   	 * 
   	 */
	public void mostrarGanadoresCerrada(){
		
		Iterator<Personaje> it = colaPers.iterator();
		Personaje persAux;
		
		while (it.hasNext() == true){
			
			persAux = it.next();

			if (persAux.getClass().getSimpleName().contains("Imperial") || //TODO: AUGH
					persAux.getClass().getSimpleName().contains("ContrabandistaReves")){
					
				System.out.println(persAux.toString() + " ");
			}
		}
	}
	
	/**
   	 * Muestra la informacion de los Personajes que ganan con puerta abierta que se encuentran en la Sala
   	 * 
   	 */
	public void mostrarGanadoresAbierta(){
		
		Iterator<Personaje> it = colaPers.iterator();
		Personaje persAux;
		while (it.hasNext() == true){
			persAux = it.next();
			if (!persAux.getClass().getSimpleName().contains("Imperial") && //TODO: AUGH
					!persAux.getClass().getSimpleName().contains("ContrabandistaReves")){
			System.out.print(persAux.toString() + " ");}
		}
	}
	
	/**
   	 * Indica si la Sala contiene Personajes
   	 * 
   	 * @return True : si la Sala contiene algun Personaje <br> False : si la Sala no contiene Personajes
   	 * 
   	 */
	public boolean hayPersonajes(){
		return !colaPers.isEmpty();
	}
	
	/**
   	 * Obtiene el numero de Personajes que se encuentran en la lista de la clase Sala
   	 * 
   	 * @return Entero con el numero de Personajes que se encuentran en la Sala
   	 * 
   	 */
	public int cuantosPJ(){
		return colaPers.size();
	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el atributo Id de la clase Sala
   	 * 
   	 * @return Entero con la id de la Sala
   	 * 
   	 */
	public int getId() {
		return id;
	}
	
	/**
   	 * Cambia el valor del atributo Id de la clase Sala
   	 * 
   	 * @param id Nuevo valor entero
   	 * 
   	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
   	 * Obtiene la lista de llaves de la clase Sala
   	 * 
   	 * @return Lista de tipo Llave
   	 * 
   	 */
	public List<Llave> getlistaLlaveSala() {
		return listaLlaveSala;
	}
	
	/**
   	 * Cambia la lista de llaves de la clase Sala
   	 * 
   	 * @param listaLlaveSala Nueva lista de tipo Llave
   	 * 
   	 */
	public void setlistaLlaveSala(List<Llave> listaLlaveSala) {
		this.listaLlaveSala = listaLlaveSala;
	}
	
	/**
   	 * Obtiene la lista de Personajes de la clase Sala
   	 * 
   	 * @return Lista de tipo Personaje
   	 * 
   	 */
	public List<Personaje> getColaPers() {
		return colaPers;
	}
	
	/**
   	 * Cambia la lista de Personajes de la clase Sala
   	 * 
   	 * @param colaPers Nueva lista de tipo Personaje
   	 * 
   	 */
	public void setColaPers(List<Personaje> colaPers) {
		this.colaPers = colaPers;
	}

	/**
   	 * Indica si la Sala contiene una puerta de salida
   	 * 
   	 * @return True si la Sala contiene una puerta de salida <br> False si la Sala no contiene una puerta de salida
   	 * 
   	 */
	public boolean isPuertaSalida() {
		return puertaSalida;
	}
	
	/**
   	 * Cambia el valor del booleano PuertaSalida de la clase Sala
   	 * 
   	 * @param puertaSalida Nuevo valor (True || False)
   	 * 
   	 */
	public void setPuertaSalida(boolean puertaSalida) {
		this.puertaSalida = puertaSalida;
	}
	
	/**
   	 * Cambia el valor del booleano haMovido de cada Personaje que se encuentra en la Sala a False
   	 * 
   	 */
	public void reiniciarTurnoPj(){
		
		Personaje persAux;
		
		for (int i = 0; i < colaPers.size(); i++) {
			
			persAux = colaPers.get(i);
			
			persAux.setHaMovido(false);
		}	
	}
	
	/**
   	 * Llama al metodo turnoPj de cada Personaje que se encuentra en la Sala y no haya realizado su turno
   	 * 
   	 * @param mapa Nuestro mapa
   	 * 
   	 */
	public boolean activarPJ (Mapa mapa){
		
		Personaje persAux;
		boolean fin = false;
		int cuantos = colaPers.size();
		
		for (int i = 0; i < cuantos && fin == false; i++) {
			
			persAux = colaPers.remove(0);
			
			if (persAux.isHaMovido() == false && persAux.esSuTurno(mapa) == true)
				persAux.turnoPj(mapa);
			else if (persAux.isHaMovido() == true)
				colaPers.add(persAux);
			else {
				colaPers.add(persAux);
				persAux.setTurnoActual(persAux.getTurnoActual() + 1);	// Para que no se mueva antes de tiempo, es decir turno < turnoActual
			}
			
			fin = mapa.finJuego();
		}
		return fin;
	}
	
	// To
	
	/**
   	 * Muestra la informacion de la clase Sala
   	 * 
   	 */
	@Override
	public String toString() {
		return "Sala [id=" + id + "]";
	}

}