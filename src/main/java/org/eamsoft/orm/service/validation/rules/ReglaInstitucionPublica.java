package org.eamsoft.orm.service.validation.rules;

import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.transferencia.TransferirListaNegra;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;

public class ReglaInstitucionPublica implements ReglaValidacion{

    private TransferirListaNegra tListaNegra = new TransferirListaNegra();

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
                    cotizante.setMotivo("Tiene observación disciplinaria en Minsalud");
                    return tListaNegra.transferirAListaNegra(cotizante);
                }

            case "Minterior":
                if(cotizante.getDetalles().equals("No")){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    cotizante.setMotivo("Tiene observación disciplinaria en Minterior");
                    return tListaNegra.transferirAListaNegra(cotizante);
                }
            default:
                return procesarComoCivil.aplicar(cotizante);
        }
    }

}