package Estructuras;

/**
* Declaracion de la clase Llave
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class Llave implements Comparable<Llave> {
	
	/** Identificador de la llave*/
	int id;
	
	/**
   	 * Constructor default de la clase Llave
   	 */
	Llave (){
		id = -1;
	}
	
	/**
   	 * Constructor parametrizado de la clase Llave
   	 * 
   	 * @param id Id de la llave
   	 * 
   	 */
	public Llave (int id){
		this.id = id;
	}

	/**
   	 * Constructor por copia de la clase Llave
   	 * 
   	 * @param Llave Objeto de la clase Llave
   	 * 
   	 */
	Llave (Llave Llave) {	
		this.id = Llave.getId();
	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el atributo Id de la clase Llave
   	 * 
   	 * @return Id de la llave
   	 * 
   	 */
	public int getId (){
		return id;
	}
	
	/**
   	 * Cambia el valor del atributo Id de la clase Llave
   	 * 
   	 * @param id Nuevo valor entero
   	 * 
   	 */
	public void setId (int id){
		this.id = id;
	}
	
	/**
   	 * Compara el valor de id entre dos Llaves
   	 * 
   	 * @param mComp Objeto de la clase Llave
   	 * 
   	 * @return -1 si la id de la clase es menor <br> 0 si la id de la clase es la misma <br> 1 si la id de la clase es mayor
   	 * 
   	 */
	@Override
	public int compareTo(Llave mComp){
		if (this.id < mComp.getId())
			return -1;
		if (this.id == mComp.getId())
			return 0;
		else
			return 1;
	}
	
	/**
   	 * Muestra la informacion de la clase Llave
   	 * 
   	 */
	@Override
	public String toString(){

		return Integer.toString(id);
		
	}

	@Override
	/**
   	 * Devuelve el hashCode de una Llave
   	 * 
   	 * @return Entero con el valor del hashCode
   	 * 
   	 */
	public int hashCode() {
		return 31 * (1+id);
	}
	
	/**
   	 * Comprueba si dos Llave son iguales
   	 * 
   	 * @return True : si ambos Llave son iguales <br> False : si las Llaves son distintas
   	 * 
   	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Llave other = (Llave)obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
