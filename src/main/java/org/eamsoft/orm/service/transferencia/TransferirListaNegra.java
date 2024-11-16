package org.eamsoft.orm.service.transferencia;

import java.util.ArrayList;
import java.util.List;

import org.eamsoft.orm.manager.CotizanteEntityManager;
import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class TransferirListaNegra {

    private CotizanteEntityManager cotizanteManager = new CotizanteEntityManager();

    public ResultadoValidacion transferirAListaNegra(Cotizante cotizante, String motivoRechazo) {
        try {
            // Verificar si el cotizante ya está en la lista negra
            if (cotizanteManager.existsInListaNegra(cotizante.getDocumento())) {
                return new ResultadoValidacion(false, "El cotizante ya está en la lista negra.");
            }

            // Actualizar los detalles del cotizante con el motivo del rechazo
            cotizante.setDetalles(motivoRechazo + " (Transferido a lista negra el " + java.time.LocalDate.now() + ")");

            // Crear una lista temporal para guardar este cotizante
            List<Cotizante> listaNegra = new ArrayList<>();
            listaNegra.add(cotizante);

            // Guardar en el archivo lista_negra.csv
            cotizanteManager.guardarListaNegraSimplificada(listaNegra);

            // Retornar resultado exitoso
            return new ResultadoValidacion(true, "Cotizante transferido a lista negra exitosamente.");
        } catch (Exception e) {
            // Manejar cualquier excepción
            return new ResultadoValidacion(false, "Error al transferir a lista negra: " + e.getMessage());
        }
    }
}

