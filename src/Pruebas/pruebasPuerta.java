package Pruebas;

import DEV.Puerta;
import Estructuras.Llave;

/**
* Declaracion de la clase pruebasPuerta
* @author
*   <b> Antonio Rebollo Guerra,</b><br>
*   <b>Carlos Salguero Sanchez. </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 16/17 </b>
*/
public class pruebasPuerta {
	
	//Para estas pruebas se creará la combinación a usar durante el resto del proyecto,
	//mostrándola por anchura para verificar la correcta implementación de cada método.
	public static void main(String[] args){
		
		//Generación llaves
		Llave[] vect = new Llave[15];
		for (int i = 1; i<=15;i++)
		{
			vect[i-1] = new Llave(2*i - 1);
		}
		Puerta p = new Puerta(vect, 4);
		p.mostrarVectorCfg();
		System.out.println(p.getCombinacion().arbolAString());
		p.getCombinacion().anchura();
		p.probarLlave(new Llave(5));
		p.getCombinacion().anchura();
	}
}
