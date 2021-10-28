package clasesSistemaTuristico;


import java.util.Comparator;

public class OrdenadorPorCosto implements Comparator<Producto> {

	public int compare(Producto producto, Producto otroProducto) {
		return Integer.compare(producto.getCosto(), otroProducto.getCosto()) * -1;
	}
}