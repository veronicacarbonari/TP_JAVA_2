package test;

import org.junit.Before;
import clasesSistemaTuristico.*;
import static org.junit.Assert.*;
import org.junit.Test;


public class TestDeAtracciones {
	Atraccion atraccion;
	
	@Before
	public void Setup() throws Exception {
		atraccion = new Atraccion("PruebasAtracciones", 100, 10, 3, TipoDeAtraccion.ACUATICO);		
	}

	
	@Test
	public void setearCosto() {
		atraccion.setCosto(500);
		assertEquals(500, atraccion.getCosto());
	}
	
	@Test(expected = Error.class)
	public void setearCostoNegativo() throws Exception{
		atraccion.setCosto(-500);
	}
	
	
	@Test
	public void agregarVisitanteYCupoLlenoTest() throws Exception {
		//Atraccion atraccion = new Atraccion("Mountain Splash", 100, 10, 3, TipoDeAtraccion.ACUATICO);
		assertEquals(100, atraccion.getCupo());
		atraccion.agregarVisitantes(20);
		atraccion.agregarVisitantes(80);
		assertEquals(0, atraccion.getCupo());
		assertTrue(atraccion.estaLleno());
		
	}
	
		
	@Test(expected = Error.class)
	public void sobrepasarCupoTest() throws Exception {
		//Atraccion a = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.AVENTURA);
		atraccion.agregarVisitantes(250);
		
	}

	@Test(expected = Exception.class)
	public void duracionNegativaTest() throws Exception {
		Atraccion atraccion = new Atraccion("Scape Room", 50, 25, -50, TipoDeAtraccion.AVENTURA);
		atraccion.getDuracion();
	}

	@Test(expected = Exception.class)
	public void costoNegativoTest() throws Exception {
		Atraccion atraccion = new Atraccion("Scape Room", 50, -25, 50, TipoDeAtraccion.AVENTURA);
		atraccion.getCosto();
	}

	@Test(expected = Exception.class)
	public void cupoNegativoTest() throws Exception {
		Atraccion atraccion = new Atraccion("Scape Room", -50, 25, 50, TipoDeAtraccion.AVENTURA);
		atraccion.getCupo();
	}

	@Test(expected = Error.class)
	public void agregarVisitantesNegativosTest() throws Exception {
		//Atraccion a = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.AVENTURA);
		atraccion.agregarVisitantes(-73);
	}

	@Test(expected = Error.class)
	public void implementarCostoNegativoTest() throws Exception {
		//Atraccion a = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.AVENTURA);
		atraccion.setCosto(-2);
	}


	@Test(expected = Error.class)
	public void implementarDuracionNegativaTest() throws Exception {
		//Atraccion a = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.AVENTURA);
		atraccion.setDuracion(-200);
	}
	
	@Test
	public void getCodigoDeAtraccionAventura() throws Exception {
		Atraccion atraccion = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.AVENTURA);
		assertEquals(1, atraccion.getCodigoAtraccion());
	}
	
	@Test
	public void getCodigoDeAtraccionPaisaje() throws Exception {
		Atraccion atraccion = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.PAISAJE);
		assertEquals(2, atraccion.getCodigoAtraccion());
	}
	
	@Test
	public void getCodigoDeAtraccionDegustacion() throws Exception {
		Atraccion atraccion = new Atraccion("Scape Room", 50, 25, 50, TipoDeAtraccion.DEGUSTACION);
		assertEquals(3, atraccion.getCodigoAtraccion());
	}
}