package Pruebas;

import static org.junit.Assert.*;

import org.junit.Test;

import Estructuras.abb;

public class pruebaAbb {

	@Test
	public void esVacio() {
		
		System.out.println("Comprobando si el arbol esta vacio");
		
		abb<Integer> A = new abb<Integer>();
		
		assertTrue(A.esVacio());		
	}
	
	@Test
	public void noEsVacio() {
		
		System.out.println("Comprobando si hay algo en el arbol");
		
		abb<Integer> A = new abb<Integer>();
		
		A.insertar(7);
		A.insertar(1);
		
		assertTrue(!A.esVacio());		
	}
	
	@Test
	public void existe()  {
		
		System.out.println("Comprobando si se encuentra un numero introducido en el arbol");
		
		abb<Integer> A = new abb<Integer>();
		
		A.insertar(5);
		A.insertar(6);
		A.insertar(11);
		
		assertTrue(A.existe(6));			
			
	}
	
	@Test
	public void arbolAstring()  {
		
		System.out.println("Comprobando si el arbol se devuelve correctamente a un string");
		
		abb<Integer> A = new abb<Integer>();
		
		A.insertar(1);
		A.insertar(2);
		A.insertar(4);
		A.insertar(3);
		
		assertTrue(A.arbolAString().equals(" 1 2 3 4"));
		
	}
	
	@Test
	public void eliminar()  {
		
		System.out.println("Comprobando si se elimina un nodo correctamente");
		
		abb<Integer> A = new abb<Integer>();
		
		A.insertar(1);
		A.insertar(2);
		A.insertar(4);
		A.insertar(3);
		
		A.eliminar(2);
		
		assertTrue(!A.existe(2));
		
	}
	
	@Test
	public void numHojas()  {
		
		System.out.println("Comprobando si el numero de hojas es el correcto");
		
		abb<Integer> A = new abb<Integer>();
		
		A.insertar(1);
		A.insertar(2);
		A.insertar(3);
		A.insertar(4);
		A.insertar(9);
		A.insertar(5);
		A.insertar(6);
		A.insertar(7);
		A.insertar(10);
		
		assertTrue(A.numHojas() == 2);
		
	}
	
	@Test
	public void numNodos()  {
		
		System.out.println("Comprobando si el numero de nodos es el correcto");
		
		abb<Integer> A = new abb<Integer>();
		
		A.insertar(1);
		A.insertar(2);
		A.insertar(3);
		A.insertar(4);
		A.insertar(9);
		A.insertar(10);
		
		A.eliminar(3);
		
		A.insertar(11);
		A.insertar(12);
		A.insertar(5);
		A.insertar(6);
		A.insertar(7);
		
		assertTrue(A.numNodos() == 10);
		
	}

}
