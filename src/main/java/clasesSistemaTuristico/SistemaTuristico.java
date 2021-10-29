package clasesSistemaTuristico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import dao.AtraccionesDAOImpl;
import dao.DAOFactory;
import dao.ItinerarioDAOImpl;
import dao.PromocionesDAOImpl;
import dao.UsuariosDAOImpl;

public class SistemaTuristico {

	public ArrayList<Producto> productosPreferidos;
	public ArrayList<Producto> productosNoPreferidos;
	public Usuario usuario;
	public TipoDeAtraccion preferenciaUsuario;
	UsuariosDAOImpl usuarioDao = DAOFactory.getUsuariosDao();
	AtraccionesDAOImpl AtraccionDao = DAOFactory.getAtraccionesDao();
	PromocionesDAOImpl PromocionDao = DAOFactory.getPromocionesDao();
	ItinerarioDAOImpl iDao = DAOFactory.getItinerariosDao();

	public SistemaTuristico(ArrayList<Producto> productos, Usuario usuario) {

		this.usuario = usuario;
		this.preferenciaUsuario = usuario.getTipo();
		FiltradorPorTipo filtrador = new FiltradorPorTipo();
		this.productosPreferidos = filtrador.getProductosPreferidos(productos, preferenciaUsuario);
		this.productosNoPreferidos = filtrador.getProductosNoPreferidos(productos, preferenciaUsuario);
		ordenarLista(productosPreferidos);
		ordenarLista(productosNoPreferidos);

	}

	public void sugerirPreferencias() {
		if (productosPreferidos.size() == 0)
			sugerirNoPreferencias();
		
		System.out.println("Hola " + usuario.getNombre() + ", bienvenido al sistema de compra.\n");
		System.out.println("Podemos ofrecerte los siguientes productos: \n");

		for (int i = 0; i < productosPreferidos.size(); i++) {
			if (!productosPreferidos.get(i).estaLleno()
					&& !usuario.itinerario.getItinerario().contains(productosPreferidos.get(i))
					&& puedeComprar(usuario, productosPreferidos.get(i))
					&& !productosPreferidos.get(i).estaEn(usuario.itinerario.getAtraccionesDeItinerario())) {
				// System.out.println(productosPreferidos);
				sugerirProducto(productosPreferidos.get(i));
			}

			if (i == productosPreferidos.size() - 1)
				sugerirNoPreferencias();
		}
	}

	public void sugerirProducto(Producto producto) {
		/*if (usuario.getPresupuesto() >= producto.getCosto() || usuario.getTiempo() >= producto.getDuracion()) {

			System.out.println("Hola " + usuario.getNombre() + ", bienvenido al sistema de compra.\n");
			System.out.println("Podemos ofrecerte los siguientes productos: \n");
		}*/

		if (producto.esPromocion()) {
			System.out.println("Promoción disponible: " + producto.getNombre() + "\nIncluye: ");
			System.out.println(producto.getAtracciones());
			System.out.println("\tPrecio de la promoción: " + producto.getCosto() + " monedas. \n\tDuración total: "
					+ producto.getDuracion() + " horas.\n");
		} else {
			System.out.println("¿Desea comprar la atracción " + producto.getNombre() + "?");
			System.out.println("\tPrecio: " + producto.getCosto() + " monedas. \n\tDuración: "
					+ producto.getDuracion() + " horas.\n");
		}

//		System.out.println(productosPreferidos);
//		System.out.println(usuario.getNombre() + " ¿Desea comprar la " + producto);
//		System.out.println("\n¿Desea comprar la " + producto);
		//System.out.println("\n¿Desea comprar la promoción?");
		Scanner sc = new Scanner(System.in);
		String op;
		do {
			System.out.println("Escriba 'S' para comprar o 'N' para rechazar:");
			op = sc.next().toUpperCase();
		} while (!(op.equals("S") || op.equals("N")));
		// System.out.println(op);
		if (op.equals("S")) {
			usuario.aceptar(producto);
			actualizarDB(producto);
			producto.agregarVisitantes(1);
			System.out.println("¡Gracias por tu compra!. El producto será agregado a tu itinerario.");
		}
		System.out.println("\n*********************************************************************");

		// sc.close();
	}

	public void sugerirNoPreferencias() {
		if (productosNoPreferidos.size() == 0)
			System.out.println("No quedan productos");

		for (int i = 0; i < productosNoPreferidos.size(); i++) {
			if (!productosNoPreferidos.get(i).estaLleno()
					&& !usuario.itinerario.getItinerario().contains(productosNoPreferidos.get(i))
					&& puedeComprar(usuario, productosNoPreferidos.get(i))
					&& !productosNoPreferidos.get(i).estaEn(usuario.itinerario.getAtraccionesDeItinerario())) {

				sugerirProducto(productosNoPreferidos.get(i));
			}
		}
	}

	public void ordenarLista(ArrayList<Producto> productos) {
		Collections.sort(productos, new OrdenadorPorPromo().thenComparing(new OrdenadorPorCosto())
				.thenComparing(new OrdenadorPorDuracion()));
	}

	public boolean puedeComprar(Usuario usuario, Producto producto) {
		return usuario.getPresupuesto() >= producto.getCosto() && usuario.getTiempo() >= producto.getDuracion();
	}

	public void actualizarDB(Producto producto) {
		this.iDao.insert(usuario, producto);
		this.usuarioDao.update(usuario);
		if (producto.getClass().getSimpleName().equals("Atraccion")) {
			this.AtraccionDao.update((Atraccion) producto);
		} else {

			this.PromocionDao.update((Promocion) producto);
		}
	}
}