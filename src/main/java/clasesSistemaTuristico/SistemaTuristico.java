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
	boolean puedeSugerencias = true;
	boolean puedeNoSugerencias = true;

	public SistemaTuristico(ArrayList<Producto> productos, Usuario usuario) {

		this.usuario = usuario;
		this.preferenciaUsuario = usuario.getTipo();
		FiltradorPorTipo filtrador = new FiltradorPorTipo();
		this.productosPreferidos = filtrador.getProductosPreferidos(productos, preferenciaUsuario);
		this.productosNoPreferidos = filtrador.getProductosNoPreferidos(productos, preferenciaUsuario);
		ordenarLista(productosPreferidos);
		ordenarLista(productosNoPreferidos);

	}

	public void iniciar() throws Exception {
		sugerirPreferencias();
	}

	public void sugerirPreferencias() throws Exception {

		if (productosPreferidos.size() == 0)
			sugerirNoPreferencias();

		System.out.println("Hola " + usuario.getNombre() + ", bienvenido al sistema de compra.\n");
		System.out.println("Tenés \t$" + usuario.getPresupuesto() + " monedas.");
		System.out.println("Tenés \t" + usuario.getTiempo() + " horas disponibles. \n");

		for (int i = 0; i < productosPreferidos.size(); i++) {
			if (!productosPreferidos.get(i).estaLleno()
					&& !usuario.getItinerario().getProductosItinerario().contains(productosPreferidos.get(i))
					&& puedeComprar(usuario, productosPreferidos.get(i))
					&& !productosPreferidos.get(i).estaEn(usuario.getItinerario().getAtraccionesDeItinerario())) {
				System.out.println("Podemos ofrecerte el siguiente producto: \n");
				sugerirProducto(productosPreferidos.get(i));

			} else {
				puedeSugerencias = false;
			}

			if (i == productosPreferidos.size() - 1)
				sugerirNoPreferencias();
		}

		if (!puedeSugerencias && !puedeNoSugerencias) {
			System.out.println("Ya no tenemos productos para ofrecerte.\n");
		}

		imprimirItinerario();

	}

	public void sugerirProducto(Producto producto) throws Exception {
		if (!puedeComprar(usuario, producto)) {
			System.out.println("Ya no podés seguir comprando, tu dinero y/o tiempo restante son insuficientes.\n");
		}
		iniciarChat(producto);
		Scanner sc = new Scanner(System.in);
		String op;
		do {
			System.out.println("Escriba 'S' para comprar o 'N' para rechazar:");
			op = sc.next().toUpperCase();
		} while (!(op.equals("S") || op.equals("N")));
		System.out.println(op);
		if (op.equals("S")) {
			usuario.aceptar(producto);
			actualizarDB(producto);
			producto.agregarVisitantes(1);
			System.out.println("¡Gracias por tu compra!. El producto será agregado a tu itinerario.");

			System.out.println("Ten quedan \t$" + usuario.getPresupuesto() + " monedas.");
			System.out.println("Te quedan \t" + usuario.getTiempo() + " horas. \n");
		}

		System.out.println("\n*********************************************************************");
	}

	public void sugerirNoPreferencias() throws Exception {
		if (productosNoPreferidos.size() == 0)
			System.out.println("No quedan productos");

		for (int i = 0; i < productosNoPreferidos.size(); i++) {
			if (!productosNoPreferidos.get(i).estaLleno()
					&& !usuario.getItinerario().getProductosItinerario().contains(productosNoPreferidos.get(i))
					&& puedeComprar(usuario, productosNoPreferidos.get(i))
					&& !productosNoPreferidos.get(i).estaEn(usuario.getItinerario().getAtraccionesDeItinerario())) {
				System.out.println("Podemos ofrecerte el siguiente producto: \n");
				sugerirProducto(productosNoPreferidos.get(i));

			} else {
				puedeNoSugerencias = false;
			}
		}

//		if (!puedeSugerencias && !puedeNoSugerencias) {
//			System.out.println("Ya no tenemos productos para ofrecerte.\n");
//
//			}		
//		
//		imprimirItinerario();
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

	public void iniciarChat(Producto producto) {

		if (producto.esPromocion()) {
			System.out.println("Tipo de promoción: " + producto.getClass().getSimpleName());
			System.out.println("Promoción disponible: " + producto.getNombre() + "\nIncluye: ");
			System.out.println(producto.getAtracciones());
			System.out.println("\tPrecio de la promoción: " + producto.getCosto() + " monedas. \n\tDuración total: "
					+ producto.getDuracion() + " horas.\n");
		} else {
			// System.out.println("Podemos ofrecerte los siguientes productos: \n");
			System.out.println("¿Desea comprar la atracción " + producto.getNombre() + "?");
			System.out.println("\tPrecio: " + producto.getCosto() + " monedas. \n\tDuración: " + producto.getDuracion()
					+ " horas.\n");
		}
	}

	public void imprimirItinerario() {
		System.out.println("¿Desea imprimir su itinerario? 'S' para aceptar, 'N' para rechazar: \n");
		String respuesta = " ";
		Scanner leer = new Scanner(System.in);
		respuesta = leer.nextLine().toUpperCase();

		while (!respuesta.equals("S") && !respuesta.equals("N")) {
			System.out.println("No se reconoce " + respuesta + " como una opción válida. Intente nuevamente: ");
			respuesta = leer.nextLine().toUpperCase();
		}

		if (respuesta.contentEquals("S")) {
			System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

			System.out.println("\n**** ITINERARIO ****");
			System.out.println("Productos comprados: \n");
			System.out.println(usuario.itinerario.getAtraccionesDeItinerario());
		}

		if (respuesta.contentEquals("S")) {
			System.out.println("\tDuración total \t" + usuario.getItinerario().calcularTiempoItinerario() + " horas.");
			System.out.println("\tTotal a pagar \t" + usuario.getItinerario().calcularCostoItinerario() + " monedas.");
			System.out.println("\tTotal ahorro \t" + usuario.calcularAhorroDeItinerario() + " monedas.");
			System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		}

		System.out.println("\n*********************************************************************");
	}

}