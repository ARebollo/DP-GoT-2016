package DEV;


import java.util.LinkedList;
import java.util.List;

import Estructuras.Llave;
import Estructuras.abb;
import Personajes.Personaje;

/**
* Declaracion de la clase Puerta
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Puerta {
	private List<Personaje> persTrono;
	private abb<Llave> Probados;
	private abb<Llave> Combinacion;
	private boolean estado; // True abierta, False cerrada
	private int profundidad;
	private Llave[] vectorCfg;
	
	/**
   	 * Constructor default de la clase Puerta
   	 * 
   	 */
	public Puerta(){
		
		estado = false;
		Probados = new abb<Llave>();
		Combinacion = new abb<Llave>();
		profundidad = 0;
		vectorCfg = new Llave[200];	
		persTrono = new LinkedList<Personaje>();
	}
	
	public Puerta(int prof){ // Constructor parametrizado, que utiliza el vector por defecto
		
		estado = false;
		Probados = new abb<Llave>();
		Combinacion = new abb<Llave>();
		profundidad = prof;
		vectorCfg = new Llave[15];
		int pos = 0;
		for (int i = 1;i<30;i = i+2)
		{
			vectorCfg[pos] = new Llave(i);
			pos++;
		}
		configurarCombinacionCfg(0, vectorCfg.length-1);
		persTrono = new LinkedList<Personaje>();
	}
	
	/**
   	 * Constructor parametrizado de la clase Puerta
   	 * 
   	 * @param vector Vector de Llaves
   	 * @param constante Constante de profundidad para la cerradura de la puerta
   	 * 
   	 */
	public Puerta(Llave [] vector, int constante){ // Constructor parametrizado, para configurar la combinacion pertinente
		
		estado = false;
		Probados = new abb<Llave>();
		Combinacion = new abb<Llave>();
		profundidad = constante;
		vectorCfg = vector;	
		persTrono = new LinkedList<Personaje>();
		configurarCombinacionCfg(0, vectorCfg.length-1);
	}
	
	/**
   	 * Crea la combinacion de la puerta a traves de una lista de Llaves
   	 * 
   	 */
	public void setCombinacionList(List<Llave> list){
				
		vectorCfg = new Llave[list.size()];
		
		for (int i = 0; i < list.size(); i++){
			
			vectorCfg[i] = list.get(i);
		}
		
		configurarCombinacionCfg(0, vectorCfg.length-1);
	}
	
	/**
   	 * Transforma una linkedlist de Llaves a un vector de Llaves
   	 * 
   	 */
	public void setVectorCfgLinkedList(LinkedList<Llave> list){
		
		vectorCfg = new Llave[list.size()];
		
		for (int i = 0; i < list.size(); i++){
			
			vectorCfg[i] = list.get(i);
		}
		
	}
	
	/**
   	 * Muestra el vector de configuracion por pantalla
   	 * 
   	 */
	public void mostrarVectorCfg(){
		
		for (int i = 0;i<vectorCfg.length;i++){
			
			System.out.print(" "+ vectorCfg[i]);
		}
		System.out.println();
	}
	
	/**
   	 * Metodo para devolver el vector de configuracion que tiene la Puerta en forma de String
   	 * 
   	 * @return Cadena con el vector de configuracion de la Puerta
   	 * 
   	 */
	public String getVectorCfgString(){
		
		String a ="";
		
		for (int i = 0;i<vectorCfg.length;i++){

			a += vectorCfg[i] + " ";
		}
		
	  return a;
	}

	
	/**
   	 * Cierra la puerta en caso de que este abierta, si no, la reinicia a su estado original
   	 * 
   	 */
	public void cerrarPuerta (){
		
		if (this.estado == false)  // Si ya estaba cerrada reiniciarla
		{		 
		 this.Combinacion = new abb<Llave>();
		 configurarCombinacionCfg(0, vectorCfg.length-1);
		 this.Probados = new abb<Llave>();
		 //TODO Quizás quitar los mensajes de texto.
		 System.out.println();
		 System.out.println("Reiniciando puerta...");
		}
		else  // Si estaba abierta, cerrarla
		{
		 this.estado = false;
		 System.out.println();
		 System.out.println("Cerrando puerta...");
		}
		
	}

	/**
   	 * Crea la combinacion de la puerta a partir del vector de Llaves vectorCfg, siguiendo el algoritmo especificado en la entrega <br>
   	 * Para ello inserta el valor medio y se llama recursivamente primero para la mitad derecha del vector y posteriormente para la izquierda.
   	 * 
   	 * @param izq Limite izquierdo del subvector en cada llamada 
   	 * 
   	 * @param der Limite derecho del subvector en cada llamada
   	 * 
   	 */
	private void configurarCombinacionCfg (int izq, int der){
	       
        int mit = izq + (der - izq)/2;
         //Insercion valor intermedio
        Combinacion.insertar(vectorCfg[mit]);
        if (izq<der && mit != 0)
        { 	        	
         //Llamada recursiva subvector derecho
         configurarCombinacionCfg(mit+1, der);
         
         //Llamada recursiva subvector izquierdo
         configurarCombinacionCfg(izq, mit-1);
        }
       
    }
	
	/**
   	 * Metodo para probar una unica Llave en la cerradura
   	 * 
   	 * @param Llave Objeto de la clase Llave
   	 * 
   	 */
	public void probarLlave (Llave Llave){  
			
			if (Llave != null)
			{
				if (Probados.existe(Llave) == true)
				{
					System.out.println("Esta llave ya se ha probado.");
					//TODO Quizás quitar mensajes de texto?
				}
				else
				{
					Probados.insertar(Llave);
				}
				if (Combinacion.existe(Llave) == true)
				{
					Combinacion.eliminar(Llave);
				}
		
				if (Combinacion.altura() < profundidad && Combinacion.numHojas() <= (Combinacion.numNodos() - Combinacion.numHojas()) )
				{
					estado = true;
					System.out.println("Abriendo puerta...");
				}
			}

	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el arbol Probados de la clase Puerta
   	 * 
   	 * @return Arbol de los Llave probados
   	 * 
   	 */
	public abb<Llave> getProbados() {
		
		return Probados;
	}
	
	/**
   	 * Cambia el arbol Probados de la clase Puerta
   	 * 
   	 * @param probados Nuevo arbol de Llave
   	 * 
   	 */
	public void setProbados(abb<Llave> probados) {
		
		Probados = probados;
	}
	
	public String getProbadosString (){
		
		String combi = getProbados().arbolAString();
		
		return combi;
		
	}
	
	/**
   	 * Obtiene el arbol Combinacion de la clase Puerta
   	 * 
   	 * @return Arbol de la combinacion de la Puerta
   	 * 
   	 */
	public abb<Llave> getCombinacion() {
		
		return Combinacion;
	}
	
	/**
   	 * Cambia el arbol Combinacion de la clase Puerta
   	 * 
   	 * @param combinacion Nuevo arbol de Llave
   	 * 
   	 */
	public void setCombinacion(abb<Llave> combinacion) {
		
		Combinacion = combinacion;
	}
	
	public String getCombinacionString (){
		
		String combi = getCombinacion().arbolAString();
		
		return combi;
		
	}
	
	/**
   	 * Indica si la Puerta esta abierta o cerrada
   	 * 
   	 * @return True : si la Puerta esta abierta <br> False : si la Puerta esta cerrada
   	 * 
   	 */
	public boolean isEstado() {
		
		return estado;
	}
	
	/**
   	 * Cambia el valor del booleano Estado de la clase Estacion
   	 * 
   	 * @param estado Nuevo valor (True || False)
   	 * 
   	 */
	public void setEstado(boolean estado) {
		
		this.estado = estado;
	}
	
	/**
   	 * Obtiene el atributo Profundidad de la clase Puerta
   	 * 
   	 * @return Entero con la profundidad de la Puerta
   	 * 
   	 */
	public int getProfundidad() {
		
		return profundidad;
	}
	
	/**
   	 * Cambia el valor del atributo Profundidad de la clase Puerta
   	 * 
   	 * @param profundidad Nuevo valor entero
   	 * 
   	 */
	public void setProfundidad(int profundidad) {
		
		this.profundidad = profundidad;
	}
	
	/**
   	 * Obtiene el vector de configuracion de la clase Puerta
   	 * 
   	 * @return Vector con los Llaveclorianos que se van a usar en la configuracion de la combinacion
   	 * 
   	 */
	public Llave[] getVectorCfg() {
		
		return vectorCfg;
	}
	
	/**
   	 * Cambia el vector de configuracion de la clase Puerta
   	 * 
   	 * @param vectorCfg Nuevo vector de Llave
   	 * 
   	 */
	public void setVectorCfg(Llave[] vectorCfg) {
		
		this.vectorCfg = vectorCfg;
	}

	public void aniadirGanador(Personaje personaje)
	{
		persTrono.add(personaje);
	}

	protected List<Personaje> getPersTrono() {
		return persTrono;
	}

	protected void setPersTrono(List<Personaje> persTrono) {
		this.persTrono = persTrono;
	}
	
	
}

