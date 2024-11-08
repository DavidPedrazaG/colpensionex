package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaInstitucionPublica implements ReglaValidacion{

    @Override
    public ResultadoValidacion aplicar(Cotizante cotizante) {
        ReglaCivil procesarComoCivil = new ReglaCivil();

        switch (cotizante.getFondo()) {
            case "Armada":
                if(cotizante.getDetalles().equals("Tiene condecoración")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return procesarComoCivil.aplicar(cotizante);
                }
            case "Inpec":
                if(cotizante.getDetalles().contains("Tiene hijos en el Inpec")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else if(cotizante.getDetalles().equals("No tiene hijos en el Inpec|Tiene condecoración")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return procesarComoCivil.aplicar(cotizante);
                }                
            
            case "Policía":
                if(cotizante.getDetalles().equals("Tiene familiares en la Policía|Es el de mayor edad")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return procesarComoCivil.aplicar(cotizante);
                }                
            
            case "Minsalud":
                if(cotizante.getDetalles().equals("No tiene observación disciplinaria")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Tiene observación disciplinaria, sera pasado a la lista negra hasta el dd/MM/yyyy");
                }
            
            case "Mininterior":
                if(cotizante.getDetalles().equals("No tiene observación disciplinaria")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Tiene observación disciplinaria, sera pasado a la lista negra hasta el dd/MM/yyyy");
                }
            default:
                return procesarComoCivil.aplicar(cotizante);
        }
    }
    
}
