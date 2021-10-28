package clasesSistemaTuristico;


import java.util.Comparator;

public class OrdenadorPorPromo implements Comparator<Producto> {

	public int compare(Producto p1, Producto p2) {

		boolean b1 = p1.esPromocion();
		boolean b2 = p2.esPromocion();

		return (b1 == b2) ? 0 : (b1 ? -1 : 1);
	}
}