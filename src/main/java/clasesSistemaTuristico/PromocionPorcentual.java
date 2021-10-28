package clasesSistemaTuristico;

import java.util.ArrayList;

public class PromocionPorcentual extends Promocion {

	public int porcentajeDescuento;

	public PromocionPorcentual(ArrayList<Atraccion> atracciones, String nombre, int porcentajeDescuento) {
		super(atracciones, nombre);
		if (porcentajeDescuento < 0 || porcentajeDescuento > 100)
			throw new Error("Descuento inválido");
		this.porcentajeDescuento = porcentajeDescuento;
		setCosto(atracciones);
	}

	
	public void setCosto(ArrayList<Atraccion> atracciones) {
		int acumulador = 0;
		for (int i = 0; i < atracciones.size(); i++) {
			acumulador += atracciones.get(i).getCosto();
		}

		acumulador = Math.round((acumulador - (acumulador * this.porcentajeDescuento) / 100));
		this.costo = acumulador;

	}

	public int getPorcentaje() {
		
		return this.porcentajeDescuento;
	}
	
}
