package org.eamsoft.orm.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public abstract class CsvOrmBase<T> {

    protected abstract T mapearFila(Map<String, String> fila);

    protected abstract Map<String, String> extraerAtributos(T elemento);

    public LinkedList<T> leerTodasLasFilas(String archivoCsv) {
        LinkedList<T> resultados = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCsv))) {
            String headerLine = br.readLine();
            if (headerLine == null) return resultados;

            String[] headers = headerLine.split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] valores = line.split(",");
                Map<String, String> fila = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    fila.put(headers[i], valores[i]);
                }
                T entidad = mapearFila(fila);
                resultados.add(entidad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public void escribirFila(String archivoCsv, T elemento) {
        Map<String, String> atributos = extraerAtributos(elemento);

        try (FileWriter writer = new FileWriter(archivoCsv, true)) {
            StringBuilder fila = new StringBuilder();
            for (String valor : atributos.values()) {
                fila.append(valor).append(",");
            }
            fila.setLength(fila.length() - 1);
            writer.write(fila.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
