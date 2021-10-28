package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clasesSistemaTuristico.Atraccion;
import clasesSistemaTuristico.Itinerario;
import clasesSistemaTuristico.Producto;
import clasesSistemaTuristico.Promocion;
import clasesSistemaTuristico.PromocionAxB;
import clasesSistemaTuristico.TipoDeAtraccion;
import clasesSistemaTuristico.Usuario;
import dao.AtraccionesDAOImpl;
import dao.DAOFactory;
import dao.ItinerarioDAOImpl;
import dao.PromocionesDAOImpl;
import dao.UsuariosDAOImpl;

public class TestDeItinerarioDAO {

	ItinerarioDAOImpl iDao = DAOFactory.getItinerariosDao();
	UsuariosDAOImpl uDao = DAOFactory.getUsuariosDao();
	AtraccionesDAOImpl aDao = DAOFactory.getAtraccionesDao();
	PromocionesDAOImpl pDao = DAOFactory.getPromocionesDao();

	@Test
	public void existeYdeleteTest() throws Exception {
		Usuario carlos = new Usuario("Carlos", TipoDeAtraccion.PAISAJE, 150, 150);
		uDao.insert(carlos);
		Producto p = new Atraccion("playa", 12, 12, 12, TipoDeAtraccion.PAISAJE);
		aDao.insert((Atraccion) p);
		iDao.insert(carlos, p);

		assertTrue(iDao.existe(carlos));

		iDao.delete(carlos);

		aDao.delete((Atraccion) p);
		assertFalse(iDao.existe(carlos));
		uDao.delete(carlos);

	}

	@Test
	public void findAtraccionesItinerarioTest() throws Exception {
		Usuario carlos = new Usuario("Carlos", TipoDeAtraccion.PAISAJE, 150, 150);
		uDao.insert(carlos);
		Producto p = new Atraccion("p", 12, 12, 12, TipoDeAtraccion.PAISAJE);
		aDao.insert((Atraccion) p);
		iDao.insert(carlos, p);

		assertEquals(p, iDao.findAtraccionesItinerarioByUser(carlos).get(0));
		assertEquals(1, iDao.findAtraccionesItinerarioByUser(carlos).size());

		iDao.delete(carlos);

		aDao.delete((Atraccion) p);

		uDao.delete(carlos);

	}

	@Test
	public void findItinerarioTest() throws Exception {
		Usuario carlos = new Usuario("Carlos", TipoDeAtraccion.PAISAJE, 150, 150);
		uDao.insert(carlos);
		Producto a = new Atraccion("campiña", 12, 12, 12, TipoDeAtraccion.PAISAJE);
		Producto a1 = new Atraccion("montaña", 12, 12, 12, TipoDeAtraccion.PAISAJE);
		Producto a2 = new Atraccion("campo", 12, 12, 12, TipoDeAtraccion.PAISAJE);
		ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
		atracciones.add((Atraccion) a);
		atracciones.add((Atraccion) a1);
		atracciones.add((Atraccion) a2);
		Producto promo = new PromocionAxB(atracciones, "testPromo");

		ArrayList<Producto> prod = new ArrayList<Producto>();
		prod.add(promo);
		pDao.insert((Promocion) promo);
		iDao.insert(carlos, promo);
		Itinerario i = new Itinerario(prod);
		System.out.println(iDao.findItinerarioByUser(carlos));
		assertEquals(i.getItinerario().get(0).getNombre(),
				iDao.findItinerarioByUser(carlos).getItinerario().get(0).getNombre());

		iDao.delete(carlos);
		pDao.delete((Promocion) promo);
		uDao.delete(carlos);

	}

}
