package clasesSistemaTuristico;

import java.util.ArrayList;

public class Itinerario {

	public ArrayList<Producto> productos;
	

	public Itinerario() {
		
		this.productos = new ArrayList<Producto>();

	}

	public Itinerario(ArrayList<Producto> productos) {
		
		this.productos = productos;

	}
	
	public ArrayList<Producto> getProductosItinerario() {
		return this.productos;
	}

	public ArrayList<Producto> getAtraccionesDeItinerario() {
		ArrayList<Producto> atraccionesDeItinerario = new ArrayList<Producto>();
		for (int i = 0; i < this.productos.size(); i++) {
			if (this.productos.get(i).esPromocion()) {
				atraccionesDeItinerario.addAll(productos.get(i).getAtracciones());
			} else {
				atraccionesDeItinerario.add(this.productos.get(i));
			}
		}
		return atraccionesDeItinerario;
	}

	public int calcularCostoItinerario() {
		int suma = 0;
		for (int i = 0; i < this.productos.size(); i++) {
			suma += productos.get(i).getCosto();
		}
		return suma;
	}

	public double calcularTiempoItinerario() {
		double suma = 0;
		for (int i = 0; i < this.productos.size(); i++) {
			suma += productos.get(i).getDuracion();
		}
		return suma;
	}
	

}