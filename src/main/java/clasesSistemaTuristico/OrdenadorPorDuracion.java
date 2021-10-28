package clasesSistemaTuristico;


import java.util.Comparator;

public class OrdenadorPorDuracion implements Comparator<Producto> {

	public int compare(Producto o1, Producto o2) {
		return Double.compare(o1.getDuracion(), o2.getDuracion()) * -1;
	}
}