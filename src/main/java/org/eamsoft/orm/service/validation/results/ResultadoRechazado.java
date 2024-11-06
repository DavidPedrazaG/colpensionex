package org.eamsoft.orm.service.validation.results;

public class ResultadoRechazado extends ResultadoValidacion{
    public ResultadoRechazado(String motivo) {
        super(false, motivo);
    }
}
