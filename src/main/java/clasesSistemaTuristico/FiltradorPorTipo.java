package clasesSistemaTuristico;


import java.util.ArrayList;

public class FiltradorPorTipo {

	public ArrayList<Producto> getProductosPreferidos(ArrayList<Producto> productos, TipoDeAtraccion tipo) {
		ArrayList<Producto> productosPreferidos = new ArrayList<Producto>();

		for (Producto cu : productos) {
			
			if (cu.getTipo() == tipo)
				productosPreferidos.add(cu);
		}
		return productosPreferidos;
	}

	public ArrayList<Producto> getProductosNoPreferidos(ArrayList<Producto> productos, TipoDeAtraccion tipo) {
		ArrayList<Producto> productosNoPreferidos = new ArrayList<Producto>();

		for (Producto cu : productos) {
			if (cu.getTipo() != tipo)
				productosNoPreferidos.add(cu);
		}
		return productosNoPreferidos;
	}
}