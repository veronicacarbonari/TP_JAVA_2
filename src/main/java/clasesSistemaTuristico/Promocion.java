package clasesSistemaTuristico;

import java.util.ArrayList;

public abstract class Promocion extends Producto {

	ArrayList<Atraccion> atracciones;
	String nombre;

	public Promocion(ArrayList<Atraccion> atracciones, String nombre) {
		super(nombre);
		for (int i = 0; i < atracciones.size(); i++) {
			if (atracciones.get(i).estaLleno)
				throw new Error("Atracci�n con cupo lleno");
			if (atracciones.get(0).getTipo() != atracciones.get(i).getTipo())
				throw new Error("Las promociones solo pueden ser para atracciones del mismo tipo");
		}
		this.atracciones = atracciones;
		this.tipo = atracciones.get(0).getTipo();
		setDuracion(this.atracciones);
	}

	
	public int getCosto() {
		return costo;
	}
	
	public void setCosto(int costo) {
		if (costo <= 0)
			throw new Error("Costo inv�lido");
		this.costo = costo;
	}

	public double getDuracion() {
		return this.duracion;
	}

	
	public void setDuracion(ArrayList<Atraccion> atrac) {
		int acumulador = 0;
		for (int i = 0; i < atrac.size(); i++) {
			acumulador += atrac.get(i).getDuracion();
		}
		this.duracion = acumulador;
	}

	@Override
	public boolean estaLleno() {
		boolean rta = false;
		for (int i = 0; i < atracciones.size(); i++) {
			if (atracciones.get(i).estaLleno)
				rta = true;
		}
		return rta;
	}

	@Override
	public void agregarVisitantes(int cantidad) {
		for (int i = 0; i < atracciones.size(); i++) {
			if (cantidad  > atracciones.get(i).getCupo())
				throw new Error("Demasiados visitantes para el cupo");

			atracciones.get(i).agregarVisitantes(cantidad);

		}
	}

	public ArrayList<Atraccion> getAtracciones() {
		return this.atracciones;
	}

	public int getIdTipoProducto(Promocion p) {
		return 2;
	}
	
	@Override
	public String toString() {
		return "Promocion disponible: " + this.getClass().getSimpleName() + "]\n" +  "La promocion incluye estas atracciones: ";
		//+ " llamada " + this.nombre + "? \nTipo: " + tipo + ". \nCosto: " + costo
			//	+ " monedas." + "\nDuraci�n: " + duracion + " horas.\n";
		
	}
}