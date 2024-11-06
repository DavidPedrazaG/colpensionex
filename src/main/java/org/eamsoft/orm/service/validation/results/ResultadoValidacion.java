package org.eamsoft.orm.service.validation.results;

public class ResultadoValidacion {
    private boolean aprobado;
    private String motivo;

    public ResultadoValidacion(boolean aprobado, String motivo) {
        this.aprobado = aprobado;
        this.motivo = motivo;
    }

    public boolean esAprobado() {
        return aprobado;
    }

    public String getMotivo() {
        return motivo;
    }
}
