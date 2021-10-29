package test;

import clasesSistemaTuristico.*;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;


public class TestDeSistemaTuristico {

	@Test
	public void sistemaTuristicoTest() throws Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		ArrayList<Atraccion> atracsPaisaje = new ArrayList<Atraccion>();
		ArrayList<Atraccion> atracsAventura = new ArrayList<Atraccion>();

		Atraccion a = new Atraccion("La Comarca", 150, 3, 6.5, TipoDeAtraccion.PAISAJE);
		Atraccion b = new Atraccion("Abismo de Helm", 15, 5, 2, TipoDeAtraccion.PAISAJE);
		Atraccion c = new Atraccion("Mordor", 4, 25, 3, TipoDeAtraccion.AVENTURA);
		Atraccion d = new Atraccion("Erebor", 32, 12, 3, TipoDeAtraccion.AVENTURA);

		atracsPaisaje.add(a);
		atracsPaisaje.add(b);
		atracsAventura.add(c);
		atracsAventura.add(d);

		Promocion promoPaisaje = new PromocionAxB(atracsPaisaje, "promo axb");
		Promocion promoAventura = new PromocionAbsoluta(atracsAventura, "promo absoluta", 12);

		productos.add(promoPaisaje);
		productos.add(a);
		productos.add(b);
		productos.add(c);
		productos.add(d);
		productos.add(promoAventura);

		Usuario u = new Usuario("Pedro", TipoDeAtraccion.AVENTURA, 150, 200);

		SistemaTuristico sist = new SistemaTuristico(productos, u);

		assertEquals(promoAventura, sist.productosPreferidos.get(0));
		assertEquals(promoPaisaje, sist.productosNoPreferidos.get(0));
		assertEquals(a, sist.productosNoPreferidos.get(2));
		assertEquals(u, sist.usuario);
		assertEquals(u.getTipo(), sist.preferenciaUsuario);
		
		
	}
}