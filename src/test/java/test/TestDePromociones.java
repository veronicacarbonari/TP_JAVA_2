package test;

import clasesSistemaTuristico.*;
import dao.DAOFactory;
import dao.PromocionesDAOImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TestDePromociones {

	ArrayList<Atraccion> atracciones;

	@Before
	public void Before() {
		atracciones = new ArrayList<Atraccion>();
	}

	@Test
	public void encontrarIdPorNombrePromoAventura() {
		PromocionesDAOImpl PromocionDao = DAOFactory.getPromocionesDao();
		//System.out.println(PromocionDao.findIdByNombrePromocion("PackAventura"));
		assertEquals(1, PromocionDao.findIdByNombrePromocion("PackAventura"));

	}

	@Test
	public void encontrarIdPorNombrePromoDegustacion() {
		PromocionesDAOImpl PromocionDao = DAOFactory.getPromocionesDao();
		//System.out.println(PromocionDao.findIdByNombrePromocion("PackDegustacion"));
		assertEquals(2, PromocionDao.findIdByNombrePromocion("PackDegustacion"));

	}

	@Test
	public void encontrarIdPorNombrePromoPaisaje() {
		PromocionesDAOImpl PromocionDao = DAOFactory.getPromocionesDao();
		//System.out.println(PromocionDao.findIdByNombrePromocion("PackPaisaje"));
		assertEquals(3, PromocionDao.findIdByNombrePromocion("PackPaisaje"));

	}

	@Test
	public void promocionAxBTest() throws Exception {
		Atraccion a = new Atraccion("Toboganes Acuaticos", 80, 50, 25, TipoDeAtraccion.ACUATICO);
		//System.out.println(a.getCosto());
		Atraccion a2 = new Atraccion("Trampolin", 70, 15, 5, TipoDeAtraccion.ACUATICO);
		//System.out.println(a2.getCosto());
		//System.out.println(a.getCupo());
		//System.out.println(a2.getCupo());
		assertEquals(80, a.getCupo());
		assertEquals(70, a2.getCupo());

		atracciones.add(a);
		atracciones.add(a2);

		Producto p = new PromocionAxB(atracciones, "Promo Acuatica");
		//System.out.println(p.getCosto());


		assertEquals(50, p.getCosto());
		assertEquals(30, 0, p.getDuracion());
		p.agregarVisitantes(15);
		assertEquals(65, a.getCupo());
		assertEquals(55, a2.getCupo());
	}

	@Test(expected = Error.class)
	public void visitantesMayorAcupo() throws Error, Exception {
		Atraccion c = new Atraccion("La Comarca", 15, 15, 40, TipoDeAtraccion.AVENTURA);
		//System.out.println(c.getCupo());
		Atraccion d = new Atraccion("Abismo de Helm", 30, 52, 40, TipoDeAtraccion.AVENTURA);
		//System.out.println(d.getCupo());

		atracciones.add(c);
		atracciones.add(d);

		Producto pCupo = new PromocionAxB(atracciones, "Promo Aventura");
		pCupo.agregarVisitantes(50);
	}

	@Test(expected = Error.class)
	public void tiposDeAtraccionDistintosTest() throws Exception {
		Atraccion c = new Atraccion("La Comarca", 150, 15, 40, TipoDeAtraccion.AVENTURA);
		Atraccion d = new Atraccion("Abismo de Helm", 60, 52, 40, TipoDeAtraccion.AVENTURA);

		atracciones.add(c);
		atracciones.add(d);

		Promocion pDistintas = new PromocionAxB(atracciones, "Promo AxB");
		assertEquals(TipoDeAtraccion.PAISAJE, pDistintas.tipo);
	}

	@Test(expected = Exception.class)
	public void valoresNegativosTest() throws Exception {
		Atraccion c = new Atraccion("La Comarca", 0, -150, 15, TipoDeAtraccion.AVENTURA);
		Atraccion d = new Atraccion("Abismo de Helm", 60, 52, -40, TipoDeAtraccion.AVENTURA);

		atracciones.add(c);
		atracciones.add(d);

		Promocion pNegativa = new PromocionAxB(atracciones, "Promo Aventura");
		pNegativa.getCosto();
	}

	@Test
	public void promoAbsolutaTest() throws Exception {
		Atraccion c = new Atraccion("La Comarca", 150, 15, 40, TipoDeAtraccion.AVENTURA);
		//System.out.println(c.getCupo());
		Atraccion d = new Atraccion("Abismo de Helm", 60, 52, 40, TipoDeAtraccion.AVENTURA);
		//System.out.println(d.getCupo());

		atracciones.add(c);
		atracciones.add(d);

		Promocion pAbs = new PromocionAbsoluta(atracciones, "promo absoluta", 60);
		//System.out.println(pAbs.getCosto());
		
		//System.out.println(c.getCosto());
		//System.out.println(d.getCosto());
		assertEquals(15, c.getCosto());
		assertEquals(52, d.getCosto());
		
		assertTrue((c.getCosto()+d.getCosto())>pAbs.getCosto());

		assertEquals(60, pAbs.getCosto());

	}

	@Test(expected = Error.class)
	public void promoMasCaraQueAtraccionesTest() throws Exception {
		Atraccion c = new Atraccion("La Comarca", 40, 150, 15, TipoDeAtraccion.AVENTURA);
		Atraccion d = new Atraccion("Abismo de Helm", 60, 52, 40, TipoDeAtraccion.AVENTURA);

		atracciones.add(c);
		atracciones.add(d);

		Promocion pMasCara = new PromocionAbsoluta(atracciones, "promo absoluta", 300);
		assertEquals(300, pMasCara.getCosto());
	}

	@Test
	public void promoPorcentualTest() throws Exception {

		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();

		Atraccion c = new Atraccion("La Comarca", 150, 15, 40, TipoDeAtraccion.AVENTURA);
		// System.out.println(c.getCosto());
		Atraccion d = new Atraccion("Abismo de Helm", 60, 52, 40, TipoDeAtraccion.AVENTURA);
		// System.out.println(d.getCosto());

		atracciones.add(c);
		atracciones.add(d);

		// System.out.println(atracciones.get(0).getCosto());
		// System.out.println(atracciones.get(1).getCosto());

		Promocion p3 = new PromocionPorcentual(atracciones, "promo porcentual", 10);

		// System.out.println(p3.getCosto());

		assertEquals(61, p3.getCosto());
		assertEquals(55, 0, p3.getDuracion());
		p3.agregarVisitantes(15);
		assertFalse(p3.estaLleno());
	}

}