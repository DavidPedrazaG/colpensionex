package org.example;


import org.eamsoft.orm.manager.CotizanteEntityManager;
import org.eamsoft.orm.modelo.Cotizante;
import org.eamsoft.orm.service.ValidadorTransferencia;
import org.eamsoft.orm.service.validation.results.ResultadoValidacion;
import org.eamsoft.orm.service.validation.rules.ReglaListaNegra;
import org.eamsoft.orm.service.validation.rules.ReglaValidacion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) {
        CotizanteEntityManager cotizanteManager = new CotizanteEntityManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Mostrar todos los cotizantes");
            System.out.println("2. Ejecutar proceso de validación para cada cotizante");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    List<Cotizante> cotizantes = cotizanteManager.findAll();
                    mostrarCotizantesEnTabla(cotizantes);
                    break;
                case 2:
                    ejecutarProcesoDeValidacion(cotizanteManager);
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarCotizantesEnTabla(List<Cotizante> cotizantes) {
        String formatoTabla = "| %-15s | %-10s | %-5s | %-15s | %-15s | %-15s | %-15s |%n";
        System.out.format("+-----------------+------------+-------+------------------+--------+---------+---------+%n");
        System.out.format("| Nombre          | Documento  | Edad  | SemanasCotizadas | Fondo  | Ciudad  | Pais    |%n");
        System.out.format("+-----------------+------------+-------+------------------+--------+---------+---------+%n");

        for (Cotizante cotizante : cotizantes) {
            System.out.format(formatoTabla,
                    cotizante.getNombre(),
                    cotizante.getDocumento(),
                    cotizante.getEdad(),
                    cotizante.getSemanasCotizadas(),
                    cotizante.getFondo(),
                    cotizante.getCiudad(),
                    cotizante.getPais()
            );
        }

        System.out.format("+-----------------+------------+-------+-----------------+%n");
    }

    private static void ejecutarProcesoDeValidacion(CotizanteEntityManager cotizanteManager) {
        int totalAprobados = 0;
        int totalRechazados = 0;

        List<Cotizante> cotizantes = cotizanteManager.findAll();
        ValidadorTransferencia validador = new ValidadorTransferencia(Arrays.asList(
                new ReglaListaNegra()
        ));

        System.out.println("\n--- Resultados del Proceso de Validación ---");
        for (Cotizante cotizante : cotizantes) {
            ResultadoValidacion resultado = validador.validar(cotizante);
            if (resultado.esAprobado()) {
                System.out.println("Cotizante " + cotizante.getNombre() + " (Documento: " + cotizante.getDocumento() + ") fue aprobado.");
                totalAprobados++;
            } else {
                System.out.println("Cotizante " + cotizante.getNombre() + " (Documento: " + cotizante.getDocumento() + ") fue rechazado: " + resultado.getMotivo());
                totalRechazados++;
            }
        }

        System.out.println("\n--- Resumen de Resultados ---");
        System.out.println("Total de Cotizantes Aprobados: " + totalAprobados);
        System.out.println("Total de Cotizantes Rechazados: " + totalRechazados);
    }
}
