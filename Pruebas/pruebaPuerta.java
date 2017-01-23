package Pruebas;

import static org.junit.Assert.*;

import org.junit.Test;

import DEV.Puerta;
import Estructuras.Llave;

public class pruebaPuerta {

	@Test
	public void configurarCombinacion() {
		
		System.out.println("Comprobando si se configura la combinacion correctamente");
		
		Puerta P = new Puerta(4);
        
        assertTrue(P.getCombinacionString().equals(" 1 3 5 7 9 11 13 15 17 19 21 23 25 27 29"));
        
	}
	
	@Test
	public void probarLlave()  {
		
		System.out.println("Comprobando si se elimina una llave usada de la combinacion y se aniade al arbol de probados");
		
		Puerta P = new Puerta(3);
		
		P.probarLlave(new Llave (3));
        
        assertTrue(P.getCombinacionString().equals(" 1 5 7 9 11 13 15 17 19 21 23 25 27 29") && P.getProbadosString().equals(" 3"));
		
	}
	
	@Test
	public void isEstado()  {
		
		System.out.println("Comprobando si se abre la puerta al cumplir la condicion");
		
		Puerta P = new Puerta(4);
		
		P.probarLlave(new Llave (1));
		P.probarLlave(new Llave (5));
		P.probarLlave(new Llave (9));
		P.probarLlave(new Llave (13));
		P.probarLlave(new Llave (17));
		P.probarLlave(new Llave (21));
		P.probarLlave(new Llave (25));
		P.probarLlave(new Llave (29));
		P.probarLlave(new Llave (11));
		
		assertTrue(P.isEstado());
		
	}
	
	@Test
	public void cerrarPuerta()  {
		
		System.out.println("Comprobando si la puerta se cierra correctamente cuando esta abierta");
		
		Puerta P = new Puerta(4);
		
		P.probarLlave(new Llave (1));
		P.probarLlave(new Llave (5));
		P.probarLlave(new Llave (9));
		P.probarLlave(new Llave (13));
		P.probarLlave(new Llave (17));
		P.probarLlave(new Llave (21));
		P.probarLlave(new Llave (25));
		P.probarLlave(new Llave (29));
		P.probarLlave(new Llave (11));
		
		P.cerrarPuerta();
		
		assertTrue(!P.isEstado() && !P.getProbados().esVacio());
		
	}
	
	@Test
	public void reiniciarPuerta()  {
		
		System.out.println("Comprobando si la puerta se reinicia correctamente");
		
		Puerta P = new Puerta(4);
		
		P.probarLlave(new Llave (1));
		P.probarLlave(new Llave (5));
		P.probarLlave(new Llave (9));
		P.probarLlave(new Llave (13));
		P.probarLlave(new Llave (17));
		P.probarLlave(new Llave (21));
		P.probarLlave(new Llave (25));
		P.probarLlave(new Llave (29));
		
		P.cerrarPuerta();
		
		assertTrue(!P.isEstado() && P.getProbados().esVacio() && P.getCombinacionString().equals(" 1 3 5 7 9 11 13 15 17 19 21 23 25 27 29"));
		
	}
}
