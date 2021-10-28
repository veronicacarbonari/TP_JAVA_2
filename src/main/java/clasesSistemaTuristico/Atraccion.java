package clasesSistemaTuristico;

import java.util.ArrayList;

public class Atraccion extends Producto {

	public int cupo;
	public boolean estaLleno;
	public String nombre;

	public Atraccion( String nombre,  int cupo, int costo, double duracion, TipoDeAtraccion tipo) throws Exception {
		super(nombre);
		if (costo <= 0 || duracion <= 0 || cupo <= 0)
			throw new Exception("Datos inválidos");
		setCosto(costo);
		this.duracion = duracion;
		this.cupo = cupo;
		this.tipo = tipo;
	}

	@Override
	public void agregarVisitantes(int cantidad) {
		if (cantidad < 0)
			throw new Error("Cantidad inválida de visitantes");

		if (cantidad  > this.cupo)
			throw new Error("Cupo lleno");
		this.cupo -= cantidad;

		if (this.cupo == 0)
			this.estaLleno = true;
	}

	@Override
	public void setCosto(int costo) {
		if (costo <= 0)
			throw new Error("Costo inválido");
		this.costo = costo;
	}


	@Override
	public boolean estaLleno() {
		return this.estaLleno;
	}

	public int getCupo() {
		return this.cupo;
	}



	@Override
	public ArrayList<Atraccion> getAtracciones() {
		return null;
	}

	public int getCodigoAtraccion() {
		int i;
		if (this.tipo == TipoDeAtraccion.AVENTURA) {
			i = 1;
		} else if (this.tipo == TipoDeAtraccion.PAISAJE) {
			i = 2;
		} else {
			i = 3;
		}
		return i;
	}
	
	@Override
	public String toString() {
		return "\n" + this.getNombre();//Tipo: " + tipo + ". \nCosto: " + costo
			//+ " monedas." + "\nDuración: " + duracion + " horas.\n";
		
	}
}