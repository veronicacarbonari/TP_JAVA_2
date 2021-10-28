package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clasesSistemaTuristico.Atraccion;
import clasesSistemaTuristico.Producto;
import clasesSistemaTuristico.Promocion;
import clasesSistemaTuristico.PromocionAxB;
import clasesSistemaTuristico.PromocionPorcentual;
import clasesSistemaTuristico.TipoDeAtraccion;
import dao.DAOFactory;
import dao.PromocionesDAOImpl;

public class TestDePromoDAO {

	PromocionesDAOImpl PromocionDao = DAOFactory.getPromocionesDao();
	
	@Test
	public void findIdTest() {
		
		assertEquals(1, PromocionDao.findIdByNombrePromocion("PackAventura"));
		assertEquals(2, PromocionDao.findIdByNombrePromocion("PackDegustacion"));
		assertEquals(3, PromocionDao.findIdByNombrePromocion("PackPaisaje"));
	}
	
	@Test 
	public void toPromocionTest() {
		assertEquals(3, PromocionDao.findAll().size());
	
	}

	
	@Test 
	public void findAtraccionesDePromocionTest() {
		
		assertEquals(3, PromocionDao.findAtraccionesDePromocion("select A.nombre, A.cupo, A.costo, A.tiempo, TA.tipo_atraccion from PROMOCIONES as P "
				+ "JOIN TIPODEATRACCION AS TA ON TA.id_tipo_atraccion = a.id_tipo_atraccion "
				+ "JOIN ATRACCIONES_DE_PROMOS AS PA ON PA.id_promo = P.id_promo "
				+ "JOIN ATRACCIONES AS A ON PA.id_atraccion = a.id_atraccion "
		
				+ "WHERE P.tipo_promo LIKE 'PromocionAxB';").size());
		
		
		assertEquals(2, PromocionDao.findAtraccionesDePromocion(
						"select A.nombre, A.cupo, A.costo, A.tiempo, TA.tipo_atraccion from PROMOCIONES as P "
								+ "JOIN TIPODEATRACCION AS TA ON TA.id_tipo_atraccion = a.id_tipo_atraccion "
								+ "JOIN ATRACCIONES_DE_PROMOS AS PA ON PA.id_promo = P.id_promo "
								+ "JOIN ATRACCIONES AS A ON PA.id_atraccion = a.id_atraccion "
		
								+ "WHERE P.tipo_promo LIKE 'PromocionPorcentual';").size());
		
		assertEquals(2, PromocionDao.findAtraccionesDePromocion(
				"select A.nombre, A.cupo, A.costo, A.tiempo, TA.tipo_atraccion from PROMOCIONES as P "
						+ "JOIN TIPODEATRACCION AS TA ON TA.id_tipo_atraccion = a.id_tipo_atraccion "
						+ "JOIN ATRACCIONES_DE_PROMOS AS PA ON PA.id_promo = P.id_promo "
						+ "JOIN ATRACCIONES AS A ON PA.id_atraccion = a.id_atraccion "
						+ "WHERE P.tipo_promo LIKE 'PromocionAbsoluta';").size());
	
				
	}
	
	@Test
	public void insertDeleteTest() throws Exception {
		Producto a1 = new Atraccion("playa", 15, 15, 15, TipoDeAtraccion.AVENTURA);
		Producto a2 = new Atraccion("montaña", 15, 15, 15, TipoDeAtraccion.AVENTURA);
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		atracciones.add((Atraccion) a1);
		atracciones.add((Atraccion) a2);
		Producto p = new PromocionAxB (atracciones, "promo");
		PromocionDao.insert((Promocion) p);
		
		assertEquals(4, PromocionDao.countAll());
		
		PromocionDao.delete((Promocion) p);
		
		assertEquals(3, PromocionDao.countAll());
		
		
		
	}
	
	@Test
	public void findPromocion() throws Exception {
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		Producto a1 = new Atraccion("playa", 15, 16, 15, TipoDeAtraccion.AVENTURA);
		Producto a2 = new Atraccion("montaña", 15, 15, 15, TipoDeAtraccion.AVENTURA);
		atracciones.add((Atraccion) a1);
		atracciones.add((Atraccion) a2);
		Producto p = new PromocionPorcentual (atracciones, "PackAventura", 20);
		
		assertEquals(p.getNombre(), PromocionDao.findByPromocion("PackAventura").getNombre());
	}

	

}
