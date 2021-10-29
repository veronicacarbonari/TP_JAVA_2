package clasesSistemaTuristico;

import java.util.ArrayList;

public class PromocionAbsoluta extends Promocion {

	public int costoFijo;

	public PromocionAbsoluta(ArrayList<Atraccion> atracciones, String nombre, int costoFijo) {
		super(atracciones, nombre);
		int sumaCostos = 0;
		if (costoFijo < 0)
			throw new Error("El costo debe ser mayor a 0");
		for (Atraccion atraccion : atracciones) {
			sumaCostos += atraccion.getCosto();
		}
		if (sumaCostos <= costoFijo)
			throw new Error("El costo de la promo debe ser menor al total costo de las atracciones");
		this.costoFijo = costoFijo;
		setCosto(costoFijo);
	}

	@Override
	public void setCosto(int costo) {
		if (costo <= 0) {
			throw new Error("Costo inválido");
		} else {
			this.costo = costo;
		}
	}

	public int getCostoFijo() {
		
		return this.costoFijo;
	}
}