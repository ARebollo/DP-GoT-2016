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
*   <b> Curso 16/17 </b>
*/
public abstract class Personaje {
       	
		/** Direcciones posibles */
        enum dir {N, S, E, O};
       
        /** Nombre del Personaje */
        protected String nombrePersonaje;
        
        /** Numero identificador de la Sala en la que se encuentra el Personaje */
        protected int idSala;
        
        /** Letra que identifica al Personaje */
        protected char marcaId;
        
        /** Turno a partir del cual el Personaje empieza a actuar */
        protected int turno;
        
        /** Turno actual del Personaje en la simulacion */
        protected int turnoActual;
        
        /** Indica si un Personaje ha realizado su turno (T) o no (F) */
        protected boolean haMovido; 
        
        /** Cola que almacena el camino que ha de seguir el Personaje por la Galaxia */
        protected Queue<dir> camino;
        
        /** Lista que almacena los Midi que posee el Personaje */
        protected LinkedList<Llave> pilaLlave;
        
       /**
        * Constructor default de la clase Personaje
       	* 
       	*/
        public Personaje(){
        	
                this.nombrePersonaje = "";
                this.marcaId = ' ';
                this.idSala = -1;
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
        * @param idSala Sala en la que se encuentra el personaje
        * @param map Nuestro Mapa
        * 
        */
        public Personaje(String nombre, char marcaId, int turno, int idSala, Mapa map){
        	
                this.nombrePersonaje = nombre;
                this.marcaId = marcaId;
                this.idSala = idSala;
                this.turno = turno;
                this.turnoActual = 0;
                haMovido = false;
                camino = new LinkedList<dir>();
                pilaLlave = new LinkedList<Llave>();
                map.buscarSala(idSala).aniadirPj(this);
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
				
				aux = aux + " " + it.next();
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
		 * Obtiene el atributo idSala de la clase Personaje
		 * 
		 * @return Id de la Sala en la que se encuentra el Personaje
		 * 
		 */
		public int getidSala() {
			return idSala;
		}
		
		/**
		 * Cambia el valor del atributo idSala de la clase Personaje
		 * 
		 * @param idSala Nuevo valor
		 * 
		 */
		public void setidSala(int idSala) {
			this.idSala = idSala;
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
		 * Obtiene el valor mas alto entre el turnoActual o el turno
		 * 
		 * @return Entero con el valor mas alto entre turno o turnoActual
		 * 
		 */
		public int getTurnoMasAlto(){
			
			int res = 0;
			
			if (getTurnoActual() <= getTurno())
				res = getTurno();
			else
				res = getTurnoActual()-1;
			
			return res;
			
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
		 * Metodo para convertir un tipo dir a un entero que se adapte a las dimensiones de la Mapa y seniale la Sala destino
		 * 
		 * @param direccion Tipo dir
		 * @param ancho Anchura del Mapa
		 * 
		 * @return Entero que se�ala la id de la Sala destino
		 * 
		 */
		protected int dirACamino(dir direccion, int ancho){
			
			int sig = 0;
			
			switch (direccion){
			case S:
				sig = idSala + ancho;
				break;
			case E:
				sig = idSala + 1;
				break;
			case N:
				sig = idSala - ancho;
				break;
			case O:
				sig = idSala - 1;  
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
		public boolean esSuTurno(){
			return turnoActual>=turno;
		}
		
		
		/**
		 * Metodo para llevar a cabo el movimiento del personaje
		 * 
		 * @param map Nuestro Mapa
		 * 
		 */
		public void turnoPj(Mapa map){
			setHaMovido(true);
			turnoActual++;
			
			if (map.getId_salida() == idSala)	//Si la Sala en la que se encuentra el pj es la de salida, interactuar con la puerta, y si queda camino, moverse al que indica
			{
				if(!map.finJuego())
				{
					tocarPuerta(map.getPuertaTrono());
					moverse(map);
				}
				//Si la puerta se ha abierto hay que sacar al personaje
				if (map.finJuego())
				{
					map.buscarSala(idSala).getColaPers().remove(this);
					map.getPuertaTrono().aniadirGanador(this);
					setidSala(1111);
				}
				else accionPersonaje(map.buscarSala(idSala));
			}
			else
			{
				moverse(map);
				accionPersonaje(map.buscarSala(idSala));
			}
		}
		
		//Realiza la accion apropiada para cada pj, es llamado por mover

		protected abstract void tocarPuerta(Puerta puertamap);
		
		protected abstract void moverse(Mapa map);
		
		protected abstract void accionPersonaje(Sala sala);

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
		
		  return getClass().getSimpleName().toLowerCase() + ":" + marcaId + ":" + idSala + ":" + getTurnoMasAlto() + ":" + Llaves + ")";
		}
 
}