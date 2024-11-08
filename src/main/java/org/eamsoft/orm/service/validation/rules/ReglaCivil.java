package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaCivil implements ReglaValidacion{

    @Override
    public ResultadoValidacion aplicar(Cotizante cotizante) {
        switch (cotizante.getCiudad()) {
            case "BogotÃ¡":
                return new ResultadoValidacion(false, "Rechazado: Nació y reside en Bogotá");
            case "MedellÃn":
                return new ResultadoValidacion(false, "Rechazado: Nació y reside en Medellín");
            case "Cali":
                return new ResultadoValidacion(false, "Rechazado: Nació y reside en Cali");
            default:
                if(cotizante.getPais().endsWith("tÃ¡n")){
                    return new ResultadoValidacion(false, "Rechazado: Es de un país que termina en tán");
                }
        }
        if(cotizante.getEdad() > 62 && cotizante.getGenero().equals("Masculino")){
            return new ResultadoValidacion(false, "Rechazado: Alcanzó la edad para aplicar al RPM");
        }else if(cotizante.getEdad() > 57 && cotizante.getGenero().equals("Femenino")){
            return new ResultadoValidacion(false, "Rechazado: Alcanzó la edad para aplicar al RPM");
        }
        switch (cotizante.getFondo()) {
            case "Porvenir":
                if(cotizante.getSemanasCotizadas() <= 800){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "ProtecciÃ³n":
                if(cotizante.getSemanasCotizadas() <= 590){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Colfondos":
                if(cotizante.getSemanasCotizadas() <= 300){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Old Mutual":
                if(cotizante.getSemanasCotizadas() <= 100){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Fondo extranjero":
                return new ResultadoValidacion(true, "Aprovado");
        
            default:
                return new ResultadoValidacion(false, "Rechazado: No pertenece a ninguna institución");

        }
    }
    
}