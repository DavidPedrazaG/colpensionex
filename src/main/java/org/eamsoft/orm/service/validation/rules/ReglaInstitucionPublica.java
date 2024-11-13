package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaInstitucionPublica implements ReglaValidacion{

    @Override
    public ResultadoValidacion aplicar(Cotizante cotizante) {
        ReglaCivil procesarComoCivil = new ReglaCivil();
        switch (cotizante.getFondo()) {
            
            case "Armada":
                if(cotizante.getDetalles().equals("Si")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return procesarComoCivil.aplicar(cotizante);
                }
            case "Inpec":
                String[] detalles = cotizante.getDetalles().split(" ");
                if(detalles[0].equals("Si")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else if(cotizante.getDetalles().equals("No Si")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return procesarComoCivil.aplicar(cotizante);
                }                
            
            case "Policia":
                if(cotizante.getDetalles().equals("Si Si")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return procesarComoCivil.aplicar(cotizante);
                }                
            
            case "Minsalud":
                if(cotizante.getDetalles().equals("No")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Tiene observación disciplinaria, sera pasado a la lista negra hasta el dd/MM/yyyy");
                }
            
            case "Minterior":
                if(cotizante.getDetalles().equals("No")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Tiene observación disciplinaria, sera pasado a la lista negra hasta el dd/MM/yyyy");
                }
            default:
                return procesarComoCivil.aplicar(cotizante);
        }
    }
    
}
