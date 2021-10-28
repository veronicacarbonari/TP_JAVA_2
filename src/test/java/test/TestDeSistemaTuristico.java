package test;

import clasesSistemaTuristico.*;
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
		Promocion promoAventura = new PromocionAbsoluta(atracsAventura, "promo absoluta", 95);

		productos.add(promoPaisaje);
		productos.add(c);

		Usuario u = new Usuario("Pedro", TipoDeAtraccion.ACUATICO, 15, 200);

		SistemaTuristico sist = new SistemaTuristico(productos, u);

		sist.sugerirPreferencias();
		sist.ordenarLista(productos);
		System.out.println(sist.puedeComprar(u, promoPaisaje));
		sist.sugerirProducto(promoAventura);
	}
}