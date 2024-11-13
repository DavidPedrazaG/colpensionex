package org.eamsoft.orm.service.transferencia;

import org.eamsoft.orm.modelo.Cotizante;

import java.util.Comparator;

public class ComparadorPrioridadCotizante implements Comparator<Cotizante> {

    @Override
    public int compare(Cotizante c1, Cotizante c2) {
        int cmp = Integer.compare(c2.getSemanasCotizadas(), c1.getSemanasCotizadas());
        if (cmp != 0) return cmp;

        cmp = Integer.compare(c2.getEdad(), c1.getEdad());
        if (cmp != 0) return cmp;

        cmp = c1.getFondo().compareTo(c2.getFondo());
        if (cmp != 0) return cmp;

        return Boolean.compare(c2.esPrePensionado(), c1.esPrePensionado());
    }
}
