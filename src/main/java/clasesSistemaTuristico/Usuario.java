package clasesSistemaTuristico;

import dao.ItinerarioDAOImpl;

public class Usuario {

	private int presupuesto = 0;
	private double tiempoDisponible = 0;
	private TipoDeAtraccion tipoDeAtraccionPreferida;
	String nombre;
	public Itinerario itinerario;
	ItinerarioDAOImpl itinerarioDao = new ItinerarioDAOImpl();

	public Usuario(String nombre, TipoDeAtraccion tipoDeAtraccionPreferida, double tiempoDisponible, int presupuesto) {
		// throws Exception {
		// if (presupuesto <= 0 || tiempoDisponible <= 0)
		// throw new Exception("Datos de presupuesto y/o tiempo inválidos");

		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.nombre = nombre;
		this.tipoDeAtraccionPreferida = tipoDeAtraccionPreferida;
		
		if (itinerarioDao.existe(this)) {
			this.itinerario = itinerarioDao.findItinerarioByUser(this);
		} else {
			this.itinerario = new Itinerario();
		}
		
		if (presupuesto <= 0) {
			System.out.println("El usuario " + this.getNombre() + " NO posee dinero suficiente para comprar más productos. \n");
			//throw new Exception("Datos de presupuesto y/o tiempo inválidos");			
		}
		
		if(tiempoDisponible <= 0) {
			System.out.println("El usuario " + this.getNombre() + " NO posee tiempo suficiente para comprar más productos. \n");

		}
	}
		
	public int getPresupuesto() {
		return this.presupuesto;
	}

	public double getTiempo() {
		return this.tiempoDisponible;
	}

	public TipoDeAtraccion getTipo() {
		return this.tipoDeAtraccionPreferida;
	}

	public int getIdTipoAtraccion() {
		int rta;
		if (getTipo().equals("AVENTURA")) {
			rta = 1;
		} else if (getTipo().equals("PAISAJE")) {
			rta = 2;
		} else {
			rta = 3;
		}
		return rta;
	}

	public void aceptar(Producto producto) {
		this.setPresupuesto(this.getPresupuesto() - producto.getCosto());
		this.setTiempo(this.getTiempo() - producto.getDuracion());
		agregarAlItinerario(producto);
		//System.out.println(itinerario.getAtraccionesDeItinerario());
		//System.out.println(itinerario.getItinerario());

	}

	private void setTiempo(double tiempo) {
		if (tiempo < 0)
			throw new Error("Tiempo inválido");

		this.tiempoDisponible = tiempo;
	}

	private void setPresupuesto(int presupuesto) {
		if (presupuesto < 0)
			throw new Error("Presupuesto inválido");

		this.presupuesto = presupuesto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void agregarAlItinerario(Producto producto) {
		this.itinerario.productos.add(producto);
	}

}