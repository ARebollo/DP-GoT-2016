package Estructuras;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
* Declaracion de la clase grafo
* @author
*   <b> Profesores DP </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 11/12 </b>
*/
public class Grafo {
	public static final int MAXVERT=115;
	public static final int INFINITO = 9999;
	public static final int NOVALOR = -1;
	
	/** Numero de nodos del grafo */
    private int numNodos;        

    /** Vector que almacena los nodos del grafo */
    private int[] nodos = new int[MAXVERT];           

    /** Matriz de adyacencia, para almacenar los arcos del grafo */
    private int[][] arcos = new int[MAXVERT][MAXVERT];    

	/** Matriz de Camino (Warshall - Path) */
    private boolean [][] warshallC = new boolean[MAXVERT][MAXVERT];    

    /** Matriz de Costes (Floyd - Cost) */
    private int[][] floydC = new int[MAXVERT][MAXVERT];    
	
    /** Matriz de Camino (Floyd - Path) */
    private int[][] floydP = new int[MAXVERT][MAXVERT];	

   /**
   	* Constructor default de la clase Grafo
   	* 
   	*/
    Grafo() {
    	
        int x,y;
        setNumNodos(0);

        for (x=0;x<MAXVERT;x++)
            nodos[x]= NOVALOR;

        for (x=0;x<MAXVERT;x++)
            for (y=0;y<MAXVERT;y++){
                arcos[x][y]=INFINITO;
                warshallC[x][y]=false;
                floydC[x][y]=INFINITO;
                floydP[x][y]=NOVALOR;
            }
      
        // Diagonales
        for (x=0;x<MAXVERT;x++){
            arcos[x][x]=0;
            warshallC[x][x]=false;
            floydC[x][x]=0;
            //floydP[x][x]=NO_VALOR;
        }
    }
    
   /**
   	* Constructor parametrizado de la clase Grafo
   	* 
   	* @param alto Altura del mapa
   	* @param ancho Anchura del mapa
   	* @param puertaGal id de la sala con la Puerta del trono
   	* 
   	*/
    public Grafo(int alto, int ancho){
    	
    	int x,y;
        setNumNodos(alto*ancho);

        for (x=0;x<MAXVERT;x++)
            nodos[x]= x;

        for (x=0;x<MAXVERT;x++)
            for (y=0;y<MAXVERT;y++){
                arcos[x][y]=INFINITO;
                warshallC[x][y]=false;
                floydC[x][y]=INFINITO;
                floydP[x][y]=NOVALOR;
            }
      
        // Diagonales
        for (x=0;x<MAXVERT;x++){
            arcos[x][x]=0;
            warshallC[x][x]=false;
            floydC[x][x]=0;
            //floydP[x][x]=NO_VALOR;
        }
        
        almacenarParedesPair(ancho); 
    	
    }
    
    
   /**
    * Metodo que comprueba si el grafo esta vacio
    * 
    * @return Retorna un valor booleano que indica si el grafo esta o no vacio
    * 
    */
    public boolean esVacio () {
        return (getNumNodos()==0);
    }

   /**
    * Metodo que inserta un nuevo arco en el grafo
    * 
    * @param origen Es el nodo de origen del arco nuevo
    * @param destino Es el nodo de destino del arco nuevo
    * @param valor Es el peso del arco nuevo 
    * 
    * @return True si se pudo insertar
    * 
    */
    private boolean nuevoArco(int origen, int destino, int valor) {
        boolean resultado= false;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos()))	{
            arcos[origen][destino]=valor; 
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que borra un arco del grafo
    * 
    * @param origen Es el nodo de origen del arco nuevo
    * @param destino Es el nodo de destino del arco nuevo
    * 
    * @return True si se pudo borrar
    * 
    */
    @SuppressWarnings("unused")
	private boolean borraArco(int origen, int destino) {
        boolean resultado = false;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos())) {
        	arcos[origen][destino]=INFINITO;	
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que comprueba si dos nodos son adyacentes
    * 
    * @param origen Es el primer nodo
    * @param destino Es el segundo nodo
    * 
    */
    @SuppressWarnings("unused")
	private boolean adyacente (int origen, int destino) {
        boolean resultado= false;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos()))      
    		resultado = (arcos[origen][destino]!=INFINITO); 
        return resultado;
    }

   /**
    * Metodo que retorna el peso de un arco
    * 
    * @param origen Es el primer nodo del arco
    * @param destino Es el segundo nodo del arco
    * 
    */
    public int getArco (int origen, int destino) {
        int arco=NOVALOR;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos())) 	
    		arco=arcos[origen][destino];				     
        return arco;
    }

   /**
    * Metodo que inserta un nuevo nodo en el grafo
    * 
    * @param n Es el nodo que se desea insertar
    * 
    */
    @SuppressWarnings("unused")
	private boolean nuevoNodo(int n) {
        boolean resultado=false;

        if (getNumNodos()<MAXVERT){
            nodos[getNumNodos()]=n;
            setNumNodos(getNumNodos() + 1);
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que elimina un nodo del grafo
    * 
    * @param nodo Nodo que se desea eliminar
    * 
    */
    @SuppressWarnings("unused")
	private boolean borraNodo(int nodo) {
        boolean resultado=false;
    	int pos = nodo; 

        if ((getNumNodos()>0) && (pos <= getNumNodos())) {
            int x,y;
            // Borrar el nodo
            for (x=pos; x<getNumNodos()-1; x++){		
                nodos[x]=nodos[x+1];
    			System.out.println(nodos[x+1]);
    		}
            // Borrar en la Matriz de Adyacencia
            // Borra la fila
            for (x=pos; x<getNumNodos()-1; x++)		
                for (y=0;y<getNumNodos(); y++)
                    arcos[x][y]=arcos[x+1][y];
            // Borra la columna
            for (x=0; x<getNumNodos(); x++)
                for (y=pos;y<getNumNodos()-1; y++)	
                    arcos[x][y]=arcos[x][y+1];
            // Decrementa el numero de nodos
            setNumNodos(getNumNodos() - 1);
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que muestra el vector de nodos del grafo
    * 
    */
    public void mostrarNodos() {
        System.out.println("NODOS:");
        for (int x=0;x<getNumNodos();x++)
            System.out.print(nodos[x] + " ");
        System.out.println();
    }

   /**
    * Metodo que muestra los arcos del grafo (la matriz de adyacencia)
    * 
    */
    public void mostrarArcos()
    {
        int x,y;

        System.out.println("ARCOS:");
        for (x=0;x<getNumNodos();x++) {
            for (y=0;y<getNumNodos();y++) {
                //cout.width(3);
                if (arcos[x][y]!=INFINITO)
                    System.out.format("%4d",arcos[x][y]);
                else
                    System.out.format("%4s","#");
            }
            System.out.println();
        }
    }


   /**
    * Metodo que devuelve el conjunto de nodos adyacentes al nodo actual
    * 
    * @param origen Es el nodo actual
    * @param ady En este conjunto se almacenaran los nodos adyacentes al nodo origen
    * 
    */
    public void adyacentes(int origen, Set<Integer> ady){
       if ((origen >= 0) && (origen < getNumNodos())) {
    		for (int i=0;i<getNumNodos();i++) {
           	 	if ((arcos[origen][i]!=INFINITO) && (arcos[origen][i]!=0))	
              		ady.add(i);	
          	}
    	}
    }

    
    /**
     * Metodo que muestra la matriz de Warshall
     * 
     */
     public void mostrarPW()
     {
         int x,y;

         System.out.println("warshallC:");
         for (x=0;x<getNumNodos();x++) {
             for (y=0;y<getNumNodos();y++)
                 System.out.format("%6b",warshallC[x][y]);
             System.out.println();
         }
     }

    /**
     * Metodo que muestra las matrices de coste y camino de Floyd
     * 
     */
     public void mostrarFloydC()
     {
         int x,y;
         System.out.println("floydC:");
         for (y=0;y<getNumNodos();y++) {
             for (x=0;x<getNumNodos();x++) {
                 //cout.width(3);
                 System.out.format("%4d",floydC[x][y]);
             }
             System.out.println();
         }

         System.out.println("floydP:");
         for (x=0;x<getNumNodos();x++) {
             for (y=0;y<getNumNodos();y++) {
                 //cout.width(2);
                 System.out.format("%4d",floydP[x][y]);
             }
             System.out.println();
         }
     }

    /**
     * Metodo que realiza el algoritmo de Warshall sobre el grafo
     * 
     */
     @SuppressWarnings("unused")
	private void warshall() {
         int i,j,k;

         // Obtener la matriz de adyacencia en P
         for (i=0;i<getNumNodos();i++)
             for (j=0;j<getNumNodos();j++)
                 warshallC[i][j]=(arcos[i][j]!=INFINITO);

         // Iterar
         for (k=0;k<getNumNodos();k++)
             for (i=0;i<getNumNodos();i++)
                 for (j=0;j<getNumNodos();j++)
                     warshallC[i][j]=(warshallC[i][j] || (warshallC[i][k] && warshallC[k][j]));
     }

    /**
     * Metodo que realiza el algoritmo de Floyd sobre el grafo
     * 
     */
     private void floyd (){
         int i,j,k;

         // Obtener la matriz de adyacencia en P
         for (i=0;i<getNumNodos();i++)
             for (j=0;j<getNumNodos();j++){
                 floydC[i][j]=arcos[i][j];
     			floydP[i][j]=NOVALOR; 	
     		}

         // Iterar
         for (k=0;k<getNumNodos();k++)
             for (i=0;i<getNumNodos();i++)
                 for (j=0;j<getNumNodos();j++)
                     if (i!=j)
                         if ((floydC[i][k]+floydC[k][j] < floydC[i][j])) {
                             floydC[i][j]=floydC[i][k]+floydC[k][j];		
                             floydP[i][j]=k;
                         }
     }

     /**
      * Metodo que devuelve el siguiente nodo en la ruta entre un origen y un destino
      * 
      * @param origen Es el primer nodo
      * @param destino Es el siguiente nodo
      * 
      */
      @SuppressWarnings("unused")
	private int siguiente(int origen, int destino){
      	int sig=-1; // Si no hay camino posible
          if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos())) {
      		if (warshallC[origen][destino]){ // Para comprobar que haya camino
      	    	if (floydP[origen][destino]!=NOVALOR)
      				sig = siguiente(origen, floydP[origen][destino]);	
          		else
          			sig=destino;
      		}
      	}
          return sig;
      }
      
	 /**
	  * Metodo que almacena las paredes adyacentes de cada nodo, siguiendo el orden N-E-S-O
	  * 
	  * @param ancho Anchura del mapa
	  * 
	  * @return LinkedList con parejas de enteros
	  * 
	  */  
	  private LinkedList<Pair<Integer,Integer>> almacenarParedesPair(int ancho){
			
		  LinkedList<Pair<Integer,Integer>> listaParedes = new LinkedList<Pair<Integer,Integer>>();	
		
		  for (int i = 0; i < getNumNodos(); i++){
			
			if (i - ancho >= 0)  // N 
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i-ancho));
			}
			
			if (i/ancho == (i+1)/ancho && i+1 <= getNumNodos())   // E
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i+1));
			}
			
			if (i + ancho < getNumNodos())   // S
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i+ancho));
			}
			
			if (i/ancho == (i-1)/ancho && i-1 >= 0)   // O
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i-1));
			}
			
		  }
	    return listaParedes;		
	  }
	  

 	 /**
 	  * Metodo para tirar las paredes de la matriz, siguiendo el algoritmo de Kruskal y mediante la genereacion de numeros aleatorios (con semilla)
 	  * 
 	  * @param lista LinkedList que contiene las paredes de todos los nodos con el formato (x-y)
 	  * @param semilla Semilla para el generador de numeros aleatorios
 	  * 
 	  */ 
       private void tirarParedesPair(LinkedList<Pair<Integer,Integer>> lista, Random randomGenerator){	
     	  
     	 Pair<Integer,Integer> aux;
     	 int x = 0;
     	 int y = 0;
     	 int randomInt = 0;
     	 
     	 
     	 while (lista.isEmpty() == false){
     		
         	 randomInt = randomGenerator.nextInt(lista.size());
         	 
     		 aux = lista.remove(randomInt);
         	 
         	 x = aux.getFirst();
    	 
         	 y = aux.getSecond();
         	 
         	 if (floydC[x][y] == Grafo.INFINITO) // Si no existe un camino hasta el nodo, tirar la pared, asi evitamos ciclos
         	 {
         	  nuevoArco (x,y,1);
         	  nuevoArco (y,x,1);
         	 }
         	 
         	 x = 0;
         	 y = 0;
         	 floyd();
     	 }	 
     	 	 
      }
       
     /**
   	  * Metodo para buscar de manera aleatoria si una pared puede tener un atajo en cualquier direccion cardinal
   	  * 
   	  * @param ancho Anchura del Mapa
   	  * @param randomGenerator Generador de numeros aleatorios
   	  * 
   	  */ 
      public void tirarParedesAtajo(int ancho, Random randomGenerator){	
       	  
       	 int randomInt = 0;
       	 int n = getNumNodos() / 20; // Esto es igual al 5%
       	 boolean atajoCreado = false;
       	 
         for (int i = 0; i < n;){  
        	 
        	 randomInt = randomGenerator.nextInt(getNumNodos());
             
        	 atajoCreado = false;
        	 
        	 if (randomInt - ancho >= 0) // N
        	 {
        		 if (floydC[randomInt][randomInt-ancho] > 1) // Si el camino de X -> Y es mayor que 1, es que hay una pared entre medias
        		 {
        			 if (atajoCreado = AtajoN(ancho, randomInt, tipoCasilla(ancho, randomInt))){
        					 i++;
        			 }
        		 }	
        	 }
             
             if (randomInt + ancho < getNumNodos() && !atajoCreado) // S
             {	 
            	 if (floydC[randomInt][randomInt+ancho] > 1) 
            	 {
            		 if (atajoCreado = AtajoS(ancho, randomInt, tipoCasilla(ancho, randomInt))){
    					 i++;
            	 	}
            	 }
         	 }
         	
         	 if (randomInt/ancho == (randomInt-1)/ancho && randomInt-1 >= 0 && !atajoCreado) // O
         	 {	 
         		 if (floydC[randomInt][randomInt-1] > 1) 
         		 {
         			 if (atajoCreado = AtajoO(ancho, randomInt, tipoCasilla(ancho, randomInt))){
         				 i++;
         		 	}
         		 }
         	 }
             
             if (randomInt/ancho == (randomInt+1)/ancho && randomInt+1 <= getNumNodos() && !atajoCreado) // E
             {
            	 if (floydC[randomInt][randomInt+1] > 1) 
            	 {
            		 if (atajoCreado = AtajoE(ancho, randomInt, tipoCasilla(ancho, randomInt))){
    					 i++;
            	 	}
            	 }
             }        	 	 
         }    	     	 	 
      }
      
      /**
    	* Metodo para comprobar si se puede crear un atajo al Norte de la Sala seleccionada
    	* 
    	* @param ancho Anchura del Mapa
    	* @param casilla Sala a comprobar
    	* @param tipo Tipo de casilla
    	* 
    	* @return True : Si el atajo se ha creado <br> False : Si el atajo no se ha creado
    	* 
    	*/
      private boolean AtajoN(int ancho, int casilla, int tipo){	
    	  
    	  boolean correcto = true; 
    	  
    	  // Si es el borde superior o las esquinas superiores, ignorar
    	  if (tipo == 0 || tipo == 1 || tipo == 12){
    		  correcto = false;
    	  }
    	   
    	  // Si esta dentro de la matriz
    	  if (tipo == -1 && correcto){
    		  
    		  if (floydC[casilla][casilla-1] == 1 && floydC[casilla-ancho][casilla-ancho-1] == 1 && floydC[casilla-1][casilla-ancho-1] == 1)
    			  correcto = false;
    		  
    		  if (floydC[casilla][casilla+1] == 1 && floydC[casilla-ancho][casilla-ancho+1] == 1 && floydC[casilla+1][casilla-ancho+1] == 1)
    			  correcto = false;  	  
    	  }
    	  
    	  // Si esta en el borde izq o esquina inferior izq 
    	  if ( (tipo == 21 || tipo == 2) && correcto){
    		  
    		  if (floydC[casilla][casilla+1] == 1 && floydC[casilla-ancho][casilla-ancho+1] == 1 && floydC[casilla+1][casilla-ancho+1] == 1)
    			  correcto = false; 	  
    	  }
    	  
    	  // Si esta en el borde der o esquina inferior der
    	  if ( (tipo == 15 || tipo == 3) && correcto){
    		  
    		  if (floydC[casilla][casilla-1] == 1 && floydC[casilla-ancho][casilla-ancho-1] == 1 && floydC[casilla-1][casilla-ancho-1] == 1)
    			  correcto = false; 
    	  }
    	  
    	  if (correcto){
    		  
    		  nuevoArco (casilla, casilla-ancho,1);
         	  nuevoArco (casilla-ancho, casilla,1); 
         	  floyd();
    	  }
    	  
    	  return correcto; 	    	  
      }
      
   /**
  	* Metodo para comprobar si se puede crear un atajo al Sur de la Sala seleccionada
  	* 
  	* @param ancho Anchura del Mapa
  	* @param casilla Sala a comprobar
  	* @param tipo Tipo de casilla
  	* 
  	* @return True : Si el atajo se ha creado <br> False : Si el atajo no se ha creado
  	* 
  	*/
      private boolean AtajoS(int ancho, int casilla, int tipo){	
    	  
    	  boolean correcto = true; 
    	  
    	  // Si es el borde inferior, ignorar
    	  if (tipo == 2 || tipo == 3 || tipo == 18)
    		  correcto = false;
    	   
    	  // Si esta dentro de la matriz
    	  if (tipo == -1 && correcto){
    		  
    		  if (floydC[casilla][casilla-1] == 1 && floydC[casilla+ancho][casilla+ancho-1] == 1 && floydC[casilla-1][casilla+ancho-1] == 1)
    			  correcto = false;
    		  
    		  if (floydC[casilla][casilla+1] == 1 && floydC[casilla-ancho][casilla+ancho+1] == 1 && floydC[casilla+1][casilla+ancho+1] == 1)
    			  correcto = false;  		  
    	  }
    	  
    	  // Si esta en el borde izq o esquina superior izq 
    	  if ( (tipo == 21 || tipo == 0) && correcto){
    		  
    		  if (floydC[casilla][casilla+1] == 1 && floydC[casilla+ancho][casilla+ancho+1] == 1 && floydC[casilla+1][casilla+ancho+1] == 1)
    			  correcto = false;   		  
    	  }
    	  
    	  // Si esta en el borde der o esquina superior der
    	  if ( (tipo == 15 || tipo == 1) && correcto){
    		  
    		  if (floydC[casilla][casilla-1] == 1 && floydC[casilla+ancho][casilla+ancho-1] == 1 && floydC[casilla-1][casilla+ancho-1] == 1)
    			  correcto = false;  		  
    	  }
    	  
    	  if (correcto){
    		  
    		  nuevoArco (casilla, casilla+ancho,1);
         	  nuevoArco (casilla+ancho, casilla,1); 
         	  floyd();
    	  }
    	  
    	  return correcto;	  
      }
      
   /**
  	* Metodo para comprobar si se puede crear un atajo al Oeste de la Sala seleccionada
  	* 
  	* @param ancho Anchura del Mapa
  	* @param casilla Sala a comprobar
  	* @param tipo Tipo de casilla
  	* 
  	* @return True : Si el atajo se ha creado <br> False : Si el atajo no se ha creado
  	* 
  	*/
      private boolean AtajoO(int ancho, int casilla, int tipo){	
    	  
    	  boolean correcto = true; 
    	  
    	  // Si es el borde izq, ignorar
    	  if (tipo == 0 || tipo == 2 || tipo == 21)
    		  correcto = false;
    	   
    	  // Si esta dentro de la matriz
    	  if (tipo == -1 && correcto){
    		  
    		  if (floydC[casilla][casilla-ancho] == 1 && floydC[casilla-1][casilla-1-ancho] == 1 && floydC[casilla-ancho][casilla-ancho-1] == 1)
    			  correcto = false;
    		  
    		  if (floydC[casilla][casilla+ancho] == 1 && floydC[casilla-1][casilla-1+ancho] == 1 && floydC[casilla+ancho][casilla+ancho-1] == 1)
    			  correcto = false;  		  
    	  }
    	  
    	  // Si esta en el borde inferior o esquina inferior der
    	  if ( (tipo == 18 || tipo == 3) && correcto){
    		  
    		  if (floydC[casilla][casilla-ancho] == 1 && floydC[casilla-1][casilla-1-ancho] == 1 && floydC[casilla-ancho][casilla-ancho-1] == 1)
    			  correcto = false;  		  
    	  }
    	  
    	  // Si esta en el borde superior o esquina superior der
    	  if ( (tipo == 12 || tipo == 1) && correcto){
    		  
    		  if (floydC[casilla][casilla+ancho] == 1 && floydC[casilla-1][casilla-1+ancho] == 1 && floydC[casilla+ancho][casilla+ancho-1] == 1)
    			  correcto = false;  		  
    	  }
    	  
    	  if (correcto){
    		  
    		  nuevoArco (casilla, casilla-1,1);
         	  nuevoArco (casilla-1, casilla,1); 
         	  floyd();
    	  }
    	  
    	  return correcto;		  
      }
      
   /**
  	* Metodo para comprobar si se puede crear un atajo al Este de la Sala seleccionada
  	* 
  	* @param ancho Anchura del Mapa
  	* @param casilla Sala a comprobar
  	* @param tipo Tipo de casilla
  	* 
  	* @return True : Si el atajo se ha creado <br> False : Si el atajo no se ha creado
  	* 
  	*/
      private boolean AtajoE(int ancho, int casilla, int tipo){	
    	  
    	  boolean correcto = true; 
    	  
    	  // Si es el borde der, ignorar
    	  if (tipo == 1 || tipo == 3 || tipo == 15)
    		  correcto = false;
    	   
    	  // Si esta dentro de la matriz
    	  if (tipo == -1 && correcto){
    		  
    		  if (floydC[casilla][casilla-ancho] == 1 && floydC[casilla+1][casilla+1-ancho] == 1 && floydC[casilla-ancho][casilla-ancho+1] == 1)
    			  correcto = false;
    		  
    		  if (floydC[casilla][casilla+ancho] == 1 && floydC[casilla+1][casilla+1+ancho] == 1 && floydC[casilla+ancho][casilla+ancho+1] == 1)
    			  correcto = false;  		  
    	  }
    	  
    	  // Si esta en el borde inferior o esquina inferior izq
    	  if ( (tipo == 18 || tipo == 2) && correcto){
    		  
    		  if (floydC[casilla][casilla-ancho] == 1 && floydC[casilla+1][casilla+1-ancho] == 1 && floydC[casilla-ancho][casilla-ancho+1] == 1)
    			  correcto = false; 		  
    	  }
    	  
    	  // Si esta en el borde superior o esquina superior izq
    	  if ( (tipo == 12 || tipo == 0) && correcto){
    		  
    		  if (floydC[casilla][casilla+ancho] == 1 && floydC[casilla+1][casilla+1+ancho] == 1 && floydC[casilla+ancho][casilla+ancho+1] == 1)
    			  correcto = false;    		  
    	  }
    	  
    	  if (correcto){
    		  
    		  nuevoArco (casilla, casilla+1,1);
         	  nuevoArco (casilla+1, casilla,1); 
         	  floyd();
    	  }
    	  
    	  return correcto;		  
      }
      
   /**
  	* Metodo que devuelve un entero segun donde se encuentre la casilla en la matriz, dentro, en los bordes o en las esquinas
  	* 
  	* @param ancho Anchura del Mapa
  	* @param casilla Sala a comprobar
  	* 
  	* @return Entero con el tipo de casilla
  	* 
  	*/
      private int tipoCasilla(int ancho, int casilla){
    	  
    	int tipo = -1;
    	boolean esquina = false;
    		
    	// Comprobar esquinas
    	
    	if (casilla == 0)
    		tipo = 0; // * ... -
    				  // - ... -
    	
    	if (casilla == ancho-1)
    		tipo = 1; // - ... *
    				  // - ... -
    	
    	if (casilla == getNumNodos() - ancho)
    		tipo = 2; // - ... -
		  			  // * ... -
    	
  		if (casilla == getNumNodos() - 1)
  			tipo = 3; // - ... -
		  			  // - ... *
  		
  		// Comprobar laterales (formato reloj 12-15-18-21)
  		
  		if (!esquina && ancho > 2){
  			
  			if(casilla > 0 && casilla < ancho-1)
  				tipo = 12;
  			
  			if(casilla % ancho == 0)
  				tipo = 21;
  			
  			if(casilla % ancho-1 == 0)
  				tipo = 15;
  			
  			if(casilla > getNumNodos() - ancho && casilla < getNumNodos() - 1)
  				tipo = 18;
  			
  		}
    	  
  		return tipo;
      }
      
     /**
   	  * Metodo para manejar los metodos relacionados con la eliminacion de paredes de manera sencilla
   	  * 
   	  * @param ancho Anchura del mapa
   	  * @param semilla Semilla para el generador de numeros aleatorios
   	  * 
   	  */ 
      public void procesarParedes(int ancho, Random randomGenerator){
    	  
    	  tirarParedesPair(almacenarParedesPair(ancho), randomGenerator); 
      } 
      
      public List<Integer> encontrarMasCorto(int i, int k)
      {
    	  LinkedList<Integer> camino = new LinkedList<Integer>();
    	  List<LinkedList<Integer>> listaCam = new LinkedList<LinkedList<Integer>>();
    	  encontrarListaCaminos(i,k,listaCam,camino);
    	  int length = 9999;
    	  for (int j = 0;j<listaCam.size();j++)
    	  {
    		  if (listaCam.get(j).size()<length)
    		  {
    			  camino = listaCam.get(j);
    			  length = listaCam.get(j).size();
    		  }
    	  }
    	  return camino;
      }
      
     /**
       * Metodo que dado un inicio y final devuelve el camino entre los dos puntos de una matriz. NO DEVUELVE EL NODO ORIGEN
       * 
       * @param i Es el nodo actual, en la primera llamada es el inicial
       * @param k Es el nodo al que se desea llegar
       * @param Camino camino que se devuelve
       * 
       * @return Devuelve el camino en forma de lista de enteros
       * 
       */
       public List<Integer> encontrarCaminoList(int i, int k, List<Integer> Camino){
    	   
    	   
    	 if (Camino.isEmpty() == true)
    	 {
          Camino.add(0, i); // A�adimos el origen
    	 }

      	 if (arcos[i][k] != Grafo.INFINITO )
  		 {
  		  Camino.add(k);  
  		 }
      	 
      	 else for (int j = 0; j < getNumNodos() && Camino.contains(k) == false; j++){
      		 if (arcos[i][j] != Grafo.INFINITO && !Camino.contains(j))
      		 {
      		  Camino.add(j);
      		  encontrarCaminoList(j,k,Camino);

      	      	if (Camino.contains(k) == false)   // Quitar del camino final los que hemos ido probando y no han resultado
      	      	{
      	      	 Camino.remove(Camino.indexOf(j));
      	      	}
      		 }   		 
      	 }

         return Camino;
       }
      

       /**
         * Metodo que devuelve todos los caminos posibles entre un inicio y un final
         * 
         * @param listaCam la lista de caminos que se devuelve
         * @param i Es el nodo actual, en la primera llamada es el inicial
         * @param k Es el nodo al que se desea llegar
         * @param caminoAct El camino que estamos recorriendo ahora
         * 
         * 
         */
         public void encontrarListaCaminos(int i, int k, List<LinkedList<Integer>> listaCam, List<Integer> caminoAct){
      	 
        	 if (caminoAct.isEmpty() == true)
        	 {
        		 caminoAct.add(i); // A�adimos el origen
        	 }
      	 	 if (arcos[i][k] != Grafo.INFINITO )
    		 {
    		  caminoAct.add(k);
    		  listaCam.add(new LinkedList<Integer>(caminoAct));
    		  caminoAct.remove(caminoAct.indexOf(k));
    		 }
        	 else for (int j = 0; j < getNumNodos() && caminoAct.contains(k) == false; j++){
        		 if (arcos[i][j] != Grafo.INFINITO && !caminoAct.contains(j))
        		 {
        			 caminoAct.add(j);
        			 encontrarListaCaminos(j,k,listaCam, caminoAct);
        			 caminoAct.remove(caminoAct.indexOf(j));
        			 
        		 }   		 
        	 }
         }
       
         
     /**
      * Metodo que aplica el algoritmo de la mano derecha a la matriz de arcos, devolviendo el camino en forma de List
      *
      * @param i El nodo de inicio
      * @param j El nodo fin
      * @param ancho Anchura del mapa
      * 
      * @return Devuelve el camino seguido en forma de lista de enteros
      * 
      */
      public List<Integer> manoDerecha (int i, int j, int ancho){
          int anterior = i;
          boolean fin = false;
          List<Integer> camino = new LinkedList<Integer>();
          boolean haMovido = false;
          //MOVIMIENTO INICIAL
          
          camino.add(i); // A�adimos el origen
          
          if (i+ancho < getNumNodos())
          {
        	  if (arcos[i][i+ancho] != Grafo.INFINITO)
        	  {
        		  camino.add(i+ancho);
        		  i+=ancho;
        		  haMovido = true;
        	  }       	  
          }
          
          if ((i+1)/ancho == i/ancho && !haMovido)
          {	  
        	  if (arcos[i][i+1] != Grafo.INFINITO)
        	  {
        		  camino.add(i+1);
        		  i++;
        		  haMovido = true;
        	  }  
          }
          
          if (i-ancho >= 0 && !haMovido)
          {
        	  if (arcos[i][i-ancho] != Grafo.INFINITO)
        	  {
        		  camino.add(i-ancho);
        		  i-=ancho;
        		  haMovido = true;
        	  }
          }
          
          if (haMovido == false) 
          {
        	  camino.add(i-1);
        	  i--;
        	  haMovido = true;
          }
          
          while (!fin)
          {
        	  haMovido = false;
              if (i-anterior == 1) //Si venimos de la izquierda comprobamos abajo, derecha, arriba, izquierda
              {
                  if (i+ancho < getNumNodos())
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
                  if ((i+1)/ancho == i/ancho && haMovido == false)
                  {
                	  if (arcos[i][i+1] != Grafo.INFINITO)
                	  {
                		  camino.add(i+1);
                		  anterior = i;
                		  i++;
                		  haMovido = true;
                	  }
                  }
                  if (i-ancho >= 0 && haMovido == false)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                    	  camino.add(i-ancho);
                    	  anterior = i;
                    	  i -= ancho;
                    	  haMovido = true;
                      }
                  }
                  if ((i-1)/ancho == i/ancho && haMovido == false && i != 0)
                  {
                      camino.add(i-1);
                      anterior = i;
                      i--;
                      haMovido = true;
                  }
              }
              else if(i-anterior == ancho*(-1)) //Si venimos desde abajo comprobamos derecha, arriba, izquierda, abajo
              {
                  if ((i+1)/ancho == i/ancho)
                  {
                	  if (arcos[i][i+1] != Grafo.INFINITO)
                	  {
                		  camino.add(i+1);
                		  anterior = i;
                		  i++;
                		  haMovido = true;
                	  }
                  }
                  if (i-ancho >= 0 && haMovido == false)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                          camino.add(i-ancho);
                          anterior = i;
                          i -= ancho;
                          haMovido = true;
                      }
                  }
                  if ((i-1)/ancho == i/ancho && haMovido == false && i != 0)
                  {
                  	  if (arcos[i][i-1] != Grafo.INFINITO)
                      {
                          camino.add(i-1);
                          anterior = i;
                          i--;
                          haMovido = true;
                      }
                  }
                  if (i+ancho < getNumNodos() && haMovido == false)
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
              }
              else if(i-anterior == ancho) //Si venimos desde arriba comprobamos izquierda, abajo, derecha, arriba
              {
                  if ((i-1)/ancho == i/ancho && i != 0)
                  {
                	  if (arcos[i][i-1] != Grafo.INFINITO)
                	  {
                		  camino.add(i-1);
                		  anterior = i;
                		  i--;
                		  haMovido = true;
                	  }
                  }
                  if (i+ancho < getNumNodos() && haMovido == false)
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
                  if ((i+1)/ancho == i/ancho && haMovido == false)
                  {
                  		if (arcos[i][i+1] != Grafo.INFINITO)
                  		{
                  			camino.add(i+1);
                  			anterior = i;
                  			i++;
                  			haMovido = true;
                  		}
                  }
                  if (i-ancho >= 0 && haMovido == false )
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                          camino.add(i-ancho);
                          anterior = i;
                          i -= ancho;
                          haMovido = true;
                      }
                  }
              }
              else //Si venimos desde la derecha comprobamos ariba, izquierda, abajo, derecha
              {
                  if (i-ancho >= 0)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                          camino.add(i-ancho);
                          anterior = i;
                          i -= ancho;
                          haMovido = true;
                      }
                  }
                  if ((i-1)/ancho == i/ancho && haMovido == false && i != 0)
                  {
                	  if (arcos[i][i-1] != Grafo.INFINITO)
                	  {
                		  camino.add(i-1);
                		  anterior = i;
                		  i--;
                		  haMovido = true;
                	  }
                  }
                  if (i+ancho < getNumNodos() && haMovido == false)
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
                  if ((i+1)/ancho == i/ancho && haMovido == false)
                  {
                	  if (arcos[i][i+1] != Grafo.INFINITO)
                	  {
                		  camino.add(i+1);
                		  anterior = i;
                		  i++;
                		  haMovido = true;
                	  }
                  }
              }
                  
              if (i == j)
                  fin = true;
          }
          
        return camino;
      }
    
      /**
       * Metodo que aplica el algoritmo de la mano izquierda a la matriz de arcos, devolviendo el camino en forma de List
       *
       * @param i El nodo de inicio
       * @param j El nodo fin
       * @param ancho Anchura del mapa
       * 
       * @return Devuelve el camino seguido en forma de lista de enteros
       * 
       */
       public List<Integer> manoIzquierda (int i, int j, int ancho){
           int anterior = i;
           boolean fin = false;
           List<Integer> camino = new LinkedList<Integer>();
           boolean haMovido = false;
           //MOVIMIENTO INICIAL
           
           camino.add(i); // A�adimos el origen
           
           if ((i-1)/ancho == i/ancho && haMovido == false && i != 0) //Izquierda
           {
         	  if (arcos[i][i-1] != Grafo.INFINITO)
         	  {
         		  camino.add(i-1);
     		  	  i--;
     		  	  haMovido = true;
     	  	  }
           }
           
           if (i-ancho >= 0 && haMovido == false) //Arriba
           {
               if(arcos[i][i-ancho] != Grafo.INFINITO)
               {
             	  camino.add(i-ancho);
             	  i -= ancho;
             	  haMovido = true;
               }
           }
           
           if ((i+1)/ancho == i/ancho && haMovido == false)
           {	  
         	  if (arcos[i][i+1] != Grafo.INFINITO)
         	  {
         		  camino.add(i+1);
         		  i++;
         		  haMovido = true;
         	  }  
           }
           
           if (haMovido == false)
           {
         	  if (arcos[i][i+ancho] != Grafo.INFINITO)
         	  {
         		  camino.add(i+ancho);
         		  i+=ancho;
         		  haMovido = true;
         	  }       	  
           }
            
           while (!fin)
           {
         	  haMovido = false;
               if (i-anterior == 1) //Si venimos de la izquierda comprobamos arriba, derecha, abajo, izquierda
               {
            	   if (i-ancho >= 0 && haMovido == false) //Arriba
                   {
                       if(arcos[i][i-ancho] != Grafo.INFINITO)
                       {
                     	  camino.add(i-ancho);
                     	  anterior = i;
                     	  i -= ancho;
                     	  haMovido = true;
                       }
                   }
                   if ((i+1)/ancho == i/ancho && haMovido == false)	//Derecha
                   {
                 	  if (arcos[i][i+1] != Grafo.INFINITO)
                 	  {
                 		  camino.add(i+1);
                 		  anterior = i;
                 		  i++;
                 		  haMovido = true;
                 	  }
                   }
                   if (i+ancho < getNumNodos() && haMovido == false) //Abajo
                   {
                       if (arcos[i][i+ancho] != Grafo.INFINITO)
                       {
                           camino.add(i+ancho);
                           anterior = i;
                           i += ancho;
                           haMovido = true;
                       }
                   }
                   
                   if ((i-1)/ancho == i/ancho && haMovido == false && i != 0)	//Izquierda
                   {
                       camino.add(i-1);
                       anterior = i;
                       i--;
                       haMovido = true;
                   }
               }
               else if(i-anterior == ancho*(-1)) //Si venimos desde abajo comprobamos izquierda, arriba, derecha, abajo
               {
            	   if ((i-1)/ancho == i/ancho && haMovido == false && i != 0) //Izquierda
                   {
                   	  if (arcos[i][i-1] != Grafo.INFINITO)
                       {
                           camino.add(i-1);
                           anterior = i;
                           i--;
                           haMovido = true;
                       }
                   }
                   if (i-ancho >= 0 && haMovido == false) //Arriba
                   {
                       if(arcos[i][i-ancho] != Grafo.INFINITO)
                       {
                           camino.add(i-ancho);
                           anterior = i;
                           i -= ancho;
                           haMovido = true;
                       }
                   }
                   if ((i+1)/ancho == i/ancho && haMovido == false)	//Derecha
                   {
                  	  if (arcos[i][i+1] != Grafo.INFINITO)
                  	  {
                  		  camino.add(i+1);
                  		  anterior = i;
                  		  i++;
                  		  haMovido = true;
                  	  }
                    }
                   
                   if (i+ancho < getNumNodos() && haMovido == false) //Abajo
                   {
                       if (arcos[i][i+ancho] != Grafo.INFINITO)
                       {
                           camino.add(i+ancho);
                           anterior = i;
                           i += ancho;
                           haMovido = true;
                       }
                   }
               }
               else if(i-anterior == ancho) //Si venimos desde arriba comprobamos derecha, abajo, izquierda, arriba
               {
            	   if ((i+1)/ancho == i/ancho && haMovido == false) //Derecha
            	   {
            		   if (arcos[i][i+1] != Grafo.INFINITO)
            		   {
            			   camino.add(i+1);
            			   anterior = i;
            			   i++;
            			   haMovido = true;
            		   }
            	   }
                  
                   if (i+ancho < getNumNodos() && haMovido == false) //Abajo
                   {
                       if (arcos[i][i+ancho] != Grafo.INFINITO)
                       {
                           camino.add(i+ancho);
                           anterior = i;
                           i += ancho;
                           haMovido = true;
                       }
                   }
                   if ((i-1)/ancho == i/ancho && i != 0 && haMovido == false) //Izquierda
                   {
                 	  if (arcos[i][i-1] != Grafo.INFINITO)
                 	  {
                 		  camino.add(i-1);
                 		  anterior = i;
                 		  i--;
                 		  haMovido = true;
                 	  }
                   }
                   if (i-ancho >= 0 && haMovido == false ) //Arriba
                   {
                       if(arcos[i][i-ancho] != Grafo.INFINITO)
                       {
                           camino.add(i-ancho);
                           anterior = i;
                           i -= ancho;
                           haMovido = true;
                       }
                   }
               }
               else //Si venimos desde la derecha comprobamos abajo, izquierda, arriba, derecha
               {
            	   if (i+ancho < getNumNodos() && haMovido == false) //Abajo
                   {
                       if (arcos[i][i+ancho] != Grafo.INFINITO)
                       {
                           camino.add(i+ancho);
                           anterior = i;
                           i += ancho;
                           haMovido = true;
                       }
                   }
                   if ((i-1)/ancho == i/ancho && haMovido == false && i != 0) //Izquierda
                   {
                 	  if (arcos[i][i-1] != Grafo.INFINITO)
                 	  {
                 		  camino.add(i-1);
                 		  anterior = i;
                 		  i--;
                 		  haMovido = true;
                 	  }
                   }
                   if (i-ancho >= 0 && haMovido == false) //Arriba
                   {
                       if(arcos[i][i-ancho] != Grafo.INFINITO)
                       {
                           camino.add(i-ancho);
                           anterior = i;
                           i -= ancho;
                           haMovido = true;
                       }
                   }
                   
                   if ((i+1)/ancho == i/ancho && haMovido == false) //Derecha
                   {
                 	  if (arcos[i][i+1] != Grafo.INFINITO)
                 	  {
                 		  camino.add(i+1);
                 		  anterior = i;
                 		  i++;
                 		  haMovido = true;
                 	  }
                   }
               }
                   
               if (i == j)
                   fin = true;
           }
           
         return camino;
       }
      
    /**
     * Metodo que devuelve los arcos que existen en el tablero del mapa en forma de una lista de pares
     *
     * @return Lista de pares con la id de los nodos que forman arcos
     * 
     */
    public List<Pair<Integer,Integer>> devolverArcos(){
    	
    	List<Pair<Integer,Integer>> listaArcos = new LinkedList<Pair<Integer,Integer>>();
    	
    	for (int i = 0; i < getNumNodos(); i++){
    		
    		for (int j = i; j < getNumNodos(); j++){  // Solo diagonal superior
    			
    			if (arcos[i][j] == 1)
    			{
    			 listaArcos.add(new Pair<Integer,Integer>(i,j));	
    			}		
    		}
    	}
      return listaArcos; 
    }
    
    /**
   	 * Obtiene el numero de nodos de la clase Grafo
   	 * 
   	 * @return Entero con el numero de nodos totales
   	 * 
   	 */
	public int getNumNodos() {
		return numNodos;
	}
	
	/**
   	 * Cambia el valor del atributo numNodos de la clase Grafo
   	 * 
   	 * @param numNodos Nuevo valor entero
   	 * 
   	 */
	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}
	
	
	public static void main(String[] args) {
		
		Grafo pepe = new Grafo(10,6);
		Random randomGen = new Random(1987);
		pepe.procesarParedes(10, randomGen);
		pepe.tirarParedesAtajo(10, randomGen);
		List<LinkedList<Integer>> listaCam = new LinkedList<LinkedList<Integer>>();
		List<Integer> caminoAct = new LinkedList<Integer>();
		pepe.encontrarListaCaminos(0, 59, listaCam, caminoAct );
		while(listaCam.isEmpty() == false)
		{
			System.out.println("LONG" + listaCam.get(0).size() +  listaCam.get(0));
			listaCam.remove(0);
		}
		caminoAct = pepe.encontrarMasCorto(0, 59);
		System.out.println("CAMINO" + caminoAct);

		
	}
	
}

