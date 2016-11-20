package Personajes;
 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import DEV.Sala;
import DEV.Mapa;
import DEV.Puerta;
import Estructuras.Llave;

/**
* Declaracion de la clase Personaje
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public abstract class Personaje {
       
        enum dir {N, S, E, O};
       
        private String nombrePersonaje;
        private int idEstacion;
        private char marcaId;
        private int turno;
        private int turnoActual;
        private boolean haMovido; //Indica si el personaje ha actuado este turno o no
        private Queue<dir> camino;
        private LinkedList<Llave> pilaLlave;
        
       /**
        * Constructor default de la clase Personaje
       	* 
       	*/
        public Personaje(){
        	
                this.nombrePersonaje = "";
                this.marcaId = ' ';
                this.idEstacion = -1;
                this.turno = -1;
                this.turnoActual = 0;
                haMovido = false;
                camino = new LinkedList<dir>();
                pilaLlave = new LinkedList<Llave>();
        }
        
       /**
        * Constructor default de la clase Personaje
        * 
        * @param nombre Nombre del personaje
        * @param marcaId Marca del personaje
        * @param turno Turno en el que empieza a moverse el personaje
        * @param idEstacion Estacion en la que se encuentra el personaje
        * @param map Nuestra Mapa
        * 
        */
        public Personaje(String nombre, char marcaId, int turno, int idEstacion, Mapa map){
        	
                this.nombrePersonaje = nombre;
                this.marcaId = marcaId;
                this.idEstacion = idEstacion;
                this.turno = turno;
                this.turnoActual = 0;
                haMovido = false;
                camino = new LinkedList<dir>();
                pilaLlave = new LinkedList<Llave>();
                hallarCamino(map);
        }
        
        /**
       	 * Metodo para aniadir un tipo dir a la cola del Personaje
       	 * 
       	 * @param Dir Objeto de tipo dir
       	 * 
       	 */
        public void aniadirCamino(dir Dir){
        	
        	camino.add(Dir);	
        }
        
        /**
		 * Indica si el Personaje tiene camino
		 * 
		 * @return True : si el Personaje tiene algun camino <br> False : si el Personaje no tiene ningun camino
		 * 
		 */
		public boolean tieneCamino(){
			
			return !camino.isEmpty();
		}

		/**
		 * Metodo para devolver el camino de un Personaje en forma de String
		 * 
		 * @return Cadena con el camino del personaje
		 * 
		 */
		public String mostrarCamino(){
			
			Iterator<dir> it = camino.iterator();
			String aux ="";
			
			while (it.hasNext()){
				
				aux += it.next() + " ";
			}
			
		  return aux;
		}
		
		/**
       	 * Metodo para aniadir un Llave a la pila del Personaje
       	 * 
       	 * @param Llave Objeto de la clase Llave
       	 * 
       	 */
		public void aniadirLlave(Llave Llave){
			
			pilaLlave.addFirst(Llave);
		}
		
		/**
	   	 * Metodo para obtener y quitar el primer Llave de la pila del Personaje
	   	 * 
	   	 * @return Objeto de la clase Llave
	   	 * 
	   	 */
		public Llave sacarLlave(){
			
			return pilaLlave.poll();
		}
		
		/**
	   	 * Indica si el Personaje tiene Llave
	   	 * 
	   	 * @return True : si el Personaje tiene algun Llave <br> False : si el Personaje no tiene ningun Llave
	   	 * 
	   	 */
		public boolean tieneLlaves(){
			return !pilaLlave.isEmpty();
		}
		
		/**
	   	 * Obtiene el numero de Llave que se encuentran en la pila del Personaje
	   	 * 
	   	 * @return Entero con el numero de Llave que tiene el Personaje
	   	 * 
	   	 */
		public int cuantosLlaves(){
			return pilaLlave.size();
		}

		// Getters & Setters
        
        /**
         * Indica si el Personaje ha realizado algun movimiento durante un turno de la simulacion
         * 
         * @return True si el Personaje ha movido durante el turno actual <br> False si el Personaje no se ha movido todavia durante el turno actual
         * 
         */
		public boolean isHaMovido() {
			return haMovido;
		}
		
		/**
		 * Cambia el valor del booleano haMovido de la clase Personaje
		 * 
		 * @param haMovido Nuevo valor (True || False)
		 * 
		 */
		public void setHaMovido(boolean haMovido) {
			this.haMovido = haMovido;
		}
		
		/**
		 * Obtiene el atributo nombrePersonaje de la clase Personaje
		 * 
		 * @return String con el nombre del Personaje
		 * 
		 */
		public String getNombrePersonaje() {
			return nombrePersonaje;
		}
		
		/**
		 * Cambia el valor del atributo nombrePersonaje de la clase Personaje
		 * 
		 * @param nombrePersonaje Nuevo valor
		 * 
		 */
		public void setNombrePersonaje(String nombrePersonaje) {
			this.nombrePersonaje = nombrePersonaje;
		}
		
		/**
		 * Obtiene el atributo idEstacion de la clase Personaje
		 * 
		 * @return Id de la estacion en la que se encuentra el Personaje
		 * 
		 */
		public int getIdEstacion() {
			return idEstacion;
		}
		
		/**
		 * Cambia el valor del atributo idEstacion de la clase Personaje
		 * 
		 * @param idEstacion Nuevo valor
		 * 
		 */
		public void setIdEstacion(int idEstacion) {
			this.idEstacion = idEstacion;
		}
		
		/**
		 * Obtiene el atributo marcaId de la clase Personaje
		 * 
		 * @return Caracter que identifica al Personaje
		 * 
		 */
		public char getMarcaId() {
			return marcaId;
		}
		
		/**
		 * Cambia el valor del atributo marcaId de la clase Personaje
		 * 
		 * @param marcaId Nuevo valor
		 * 
		 */
		public void setMarcaId(char marcaId) {
			this.marcaId = marcaId;
		}
		
		/**
		 * Obtiene el atributo turno de la clase Personaje
		 * 
		 * @return Turno en el que el Personaje comienza a moverse
		 * 
		 */
		public int getTurno() {
			return turno;
		}
		
		/**
		 * Cambia el valor del atributo turno de la clase Personaje
		 * 
		 * @param turno Nuevo valor
		 * 
		 */
		public void setTurno(int turno) {
			this.turno = turno;
		}
		
		/**
		 * Obtiene el atributo turnoActual de la clase Personaje
		 * 
		 * @return Turno de la simulacion en el que se encuentra el Personaje
		 * 
		 */
		public int getTurnoActual() {
			return turnoActual;
		}
		
		/**
		 * Cambia el valor del atributo turnoActual de la clase Personaje
		 * 
		 * @param turnoActual Nuevo valor
		 * 
		 */
		public void setTurnoActual(int turnoActual) {
			this.turnoActual = turnoActual;
		}
		
		/**
	   	 * Obtiene la cola de caminos de la clase Personaje
	   	 * 
	   	 * @return Cola de tipo dir
	   	 * 
	   	 */
		public Queue<dir> getCamino() {
			return camino;
		}
		
		/**
	   	 * Cambia la cola de caminos de la clase Personaje
	   	 * 
	   	 * @param camino Nueva cola de tipo dir
	   	 * 
	   	 */
		public void setCamino(Queue<dir> camino) {
			this.camino.clear();
			Iterator<dir> it = camino.iterator();
			while (it.hasNext())
				this.camino.add(it.next());
		}
		
		/**
	   	 * Obtiene la lista de Llave de la clase Personaje
	   	 * 
	   	 * @return Lista de tipo Llave
	   	 * 
	   	 */
		public LinkedList<Llave> getPilaLlave() {
			return pilaLlave;
		}
		
		/**
	   	 * Cambia la lista de Llave de la clase Personaje
	   	 * 
	   	 * @param pilaLlave Nueva lista de tipo Llave
	   	 * 
	   	 */
		public void setPilaLlave(LinkedList<Llave> pilaLlave) {
			this.pilaLlave = pilaLlave;
		}
		
		// Metodos PJ
		
		//Halla el camino que tiene que seguir el personaje. Lo implementa cada uno individualmente
		protected abstract void hallarCamino(Mapa map);
		
		/**
		 * Metodo para convertir un movimiento de una casilla a otra en un tipo dir
		 * 
		 * @param origen Casilla de origen
		 * @param destino Casilla destino
		 * 
		 * @return Tipo dir
		 * 
		 */
		protected dir interpretarCamino(int origen, int destino){
			if (origen < destino && origen != destino - 1)
				return dir.S;
			if (origen == destino - 1)
				return dir.E;
			if (origen == destino + 1)
				return dir.O;
			if (origen > destino && origen !=  destino + 1)
				return dir.N;
			return null;
		}
		
		/**
		 * Metodo para convertir un tipo dir a un entero que se adapte a las dimensiones de la Mapa y seniale la Estacion destino
		 * 
		 * @param direccion Tipo dir
		 * @param ancho Anchura de la Mapa
		 * 
		 * @return Entero que señala la id de la Estacion destino
		 * 
		 */
		private int dirACamino(dir direccion, int ancho){
			
			int sig = 0;
			
			switch (direccion){
			case S:
				sig = idEstacion + ancho;
				break;
			case E:
				sig = idEstacion + 1;
				break;
			case N:
				sig = idEstacion - ancho;
				break;
			case O:
				sig = idEstacion - 1;  
				break;
			}
			return sig;
		}
		
		/**
	   	 * Indica si el Personaje puede actuar en el turno actual
	   	 * 
	   	 * @return True : si el Personaje puede actuar <br> False : si el Personaje ha de esperar a su turno
	   	 * 
	   	 */
		public boolean esSuTurno(Mapa map){
			
			boolean turnoPJ = false;
					
			if (turnoActual >= turno-1)	// -1 Para que actue en el turno exacto que indica el archivo de config
			{
			    turnoPJ = true;
			}
			
			return turnoPJ;
		}
		
		
		/**
		 * Metodo para llevar a cabo el movimiento del personaje
		 * 
		 * @param map Nuestra Mapa
		 * 
		 */
		public void turnoPj(Mapa map){
			setHaMovido(true);
			turnoActual++;
			
			if (map.getId_salida() == idEstacion)	//Si la estacion en la que se encuentra el pj es la de salida, interactuar con la puerta, y si queda camino, moverse al que indica
			{
				tocarPuerta(map.getPuertaTrono());
			 	if (camino.peek() != null)
			 	{
			 		map.buscarSala(dirACamino(camino.peek(), map.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
			 		setIdEstacion(dirACamino(camino.remove(), map.getAncho()));
			 	}
			 	else map.buscarSala(idEstacion).aniadirPj(this);
			}
			else
			{
				map.buscarSala(dirACamino(camino.peek(), map.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
				setIdEstacion(dirACamino(camino.remove(), map.getAncho()));
			 
				tocarLlave(map.buscarSala(idEstacion));
			}
		}
		
		//Realiza la accion apropiada para cada pj, es llamado por mover
		protected abstract void tocarPuerta(Puerta puertamap);
		
		protected abstract void tocarLlave(Sala sala);

		// To
		
		/**
		 * Muestra la informacion de la clase Personaje
		 * 
		 */
		@Override
		public String toString() {
		
			String Llaves = "";
			Iterator<Llave> it = pilaLlave.iterator();
			
			while(it.hasNext() == true){
				 
				Llaves = Llaves + " " + it.next().toString();
			}
		
		  return "(" + getClass().getSimpleName() + ":" + marcaId + ":" + idEstacion + ":" + turnoActual + ":" + Llaves + ")";
		}
 
}