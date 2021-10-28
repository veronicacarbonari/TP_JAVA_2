package test;

import clasesSistemaTuristico.*;
import dao.ItinerarioDAOImpl;
import dao.UsuariosDAOImpl;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestDeUsuarios {
	Usuario u;

	@Before
	public void setup() throws Exception  {
		u = new Usuario("Juan Carlos", TipoDeAtraccion.ACUATICO, 10, 150);
	}
	
	@Test
	public void t() {
		ItinerarioDAOImpl i = new ItinerarioDAOImpl();
		UsuariosDAOImpl usuarioDao = new UsuariosDAOImpl();
		System.out.println(usuarioDao.findIdByNombreUsuario("Sam"));
		//System.out.println(i.findItinerarioByUsername("Eowyn"));
	}

	
	@Test
	public void usuarioTest() throws Exception {
		Atraccion a = new Atraccion("Mountain Splash", 100, 10, 3, TipoDeAtraccion.ACUATICO);
		Atraccion b = new Atraccion("Acuatic", 90, 17, 2, TipoDeAtraccion.ACUATICO);
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		atracciones.add(a);
		atracciones.add(b);

		Promocion p = new PromocionAxB(atracciones, "promo2X1");

		u.aceptar(p);
		
		//assertEquals("[Atraccion Mountain Splash, tipo: ACUATICO, costo: 10, duración: 3.0, Atraccion Acuatic, tipo: ACUATICO, costo: 17, duración: 2.0]", u.getItinerario().toString());
		//Este método hay que crearlo en userDao, si quieren les paso el query para que tire todos los costos buscando
		//por usuario
		//assertEquals(10, u.calcularCostoItinerario());
		
	}

	@Test
	public void ofreceNoPreferidoTest() throws Exception {
		Atraccion a = new Atraccion("Mountain Splash", 100, 10, 3, TipoDeAtraccion.ACUATICO);
		Atraccion b = new Atraccion("Acuatic", 90, 17, 2, TipoDeAtraccion.ACUATICO);
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		atracciones.add(a);
		atracciones.add(b);

		Promocion p1 = new PromocionPorcentual(atracciones, "promo porcentual", 30);

		u.aceptar(p1);
	}

	@Test(expected = Exception.class)
	public void valoresNegativosTest() throws Exception {
		Usuario u2 = new Usuario("Leonardo", TipoDeAtraccion.ACUATICO, 10, -50);
		u2.getPresupuesto();
	}

	@Test(expected = Exception.class)
	public void valoresNegativosTest2() throws Exception {
		Usuario u2 = new Usuario("Maria", TipoDeAtraccion.AVENTURA, 70, -10);
		u2.getTiempo();
	}
	
}