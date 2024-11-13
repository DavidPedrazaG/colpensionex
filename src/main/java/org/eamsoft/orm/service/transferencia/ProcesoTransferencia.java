package org.eamsoft.orm.service.transferencia;


import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.ValidadorTransferencia;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

import java.util.List;

public class ProcesoTransferencia {
    private ColaTransferencia colaTransferencia;
    private ValidadorTransferencia validador;

    public ProcesoTransferencia(ValidadorTransferencia validador) {
        this.colaTransferencia = new ColaTransferencia();
        this.validador = validador;
    }

    public void procesarCotizantes(List<Cotizante> cotizantes) {
        for (Cotizante cotizante : cotizantes) {
            ResultadoValidacion resultado = validador.validar(cotizante);
            if (resultado.esAprobado()) {
                colaTransferencia.agregarCotizante(cotizante);
                System.out.println("Cotizante " + cotizante.getNombre() + " aprobado y agregado a la cola.");
            } else {
                System.out.println("Cotizante " + cotizante.getNombre() + " rechazado: " + resultado.getMotivo());
            }
        }
    }

    public void despacharCotizantes() {
        System.out.println("\n--- Despacho de Cotizantes en Orden de Prioridad ---");
        while (!colaTransferencia.estaVacia()) {
            Cotizante cotizante = colaTransferencia.obtenerSiguienteCotizante();
            System.out.println("Procesando cotizante: " + cotizante.getNombre() + " (Documento: " + cotizante.getDocumento() + ")");
        }
    }
}
