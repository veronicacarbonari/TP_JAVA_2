package clasesSistemaTuristico;

import java.util.ArrayList;

public class PromocionAxB extends Promocion {
	Atraccion atraccionGratis;

	public PromocionAxB(ArrayList<Atraccion> atracciones, String nombre) {
		super(atracciones, nombre);
		if (atracciones.size() < 1)
			throw new Error("Cantidad de atracciones inválido");
		this.atraccionGratis = atracciones.get(atracciones.size() - 1);
		this.setCosto(costo);
	}

	@Override
	public void setCosto(int costo) {
		int acumulador = 0;
		for (int i = 0; i < atracciones.size() - 1; i++) {
			acumulador += atracciones.get(i).getCosto();
		}
		this.costo = acumulador;
	}
}