package com.mercelab.seguimiento.controllers.helpers;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientoCantidad;

import java.util.*;

public class GraficoResponseUtil {

    public static Map<String, Object> graficoBarraResponse(
            List<SeguimientoCantidad> seguimientosExamenes,
            List<SeguimientoCantidad> seguimientosMedicamentos,
            List<SeguimientoCantidad> seguimientosVacunas,
            List<SeguimientoCantidad> seguimientosEspecialidades
    ) {
        String[] fixedOrderMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Set<String> allMonths = new HashSet<>();

        List<List<SeguimientoCantidad>> allSeguimientos = Arrays.asList(
                seguimientosExamenes,
                seguimientosMedicamentos,
                seguimientosVacunas,
                seguimientosEspecialidades
        );

        for (List<SeguimientoCantidad> seguimientos : allSeguimientos) {
            for (SeguimientoCantidad seguimiento : seguimientos) {
                allMonths.add(seguimiento.getLabel());
            }
        }

        List<String> sortedMonths = new ArrayList<>(Arrays.asList(fixedOrderMonths));
        sortedMonths.retainAll(allMonths);

        List<String> meses = getMeses(sortedMonths);

        List<Long> dataExamenes = createDataArray(sortedMonths, seguimientosExamenes);
        List<Long> dataMedicamentos = createDataArray(sortedMonths, seguimientosMedicamentos);
        List<Long> dataVacunas = createDataArray(sortedMonths, seguimientosVacunas);
        List<Long> dataEspecialidades = createDataArray(sortedMonths, seguimientosEspecialidades);

        Map<String, Object> response = new HashMap<>();
        response.put("labels", meses);

        List<Map<String, Object>> datasets = new ArrayList<>();

        datasets.add(createDataset("Exámenes", "#007bff", dataExamenes));
        datasets.add(createDataset("Medicamentos", "#28a745", dataMedicamentos));
        datasets.add(createDataset("Vacunas", "#dc3545", dataVacunas));
        datasets.add(createDataset("Especialidades", "#ffc107", dataEspecialidades));

        long totalSeguimientos = getTotalSeguimientos(allSeguimientos);
        response.put("totalSeguimientos", totalSeguimientos);

        response.put("datasets", datasets);

        return response;
    }

    public static Map<String, Object> graficoCircularResponse(
            List<SeguimientoCantidad> seguimientosExamenes,
            List<SeguimientoCantidad> seguimientosMedicamentos,
            List<SeguimientoCantidad> seguimientosVacunas,
            List<SeguimientoCantidad> seguimientosEspecialidades
    ) {
        List<String> labels = Arrays.asList("En seguimiento", "Eliminado", "Terminado");

        Map<String, Integer> sumas = new HashMap<>();
        sumas.put("En seguimiento", 0);
        sumas.put("Eliminado", 0);
        sumas.put("Terminado", 0);

        List<List<SeguimientoCantidad>> allSeguimientos = Arrays.asList(
                seguimientosExamenes,
                seguimientosMedicamentos,
                seguimientosVacunas,
                seguimientosEspecialidades
        );

        for (List<SeguimientoCantidad> seguimientos : allSeguimientos) {
            sumarCantidad(seguimientos, sumas);
        }

        List<Integer> data = new ArrayList<>(sumas.values());

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);

        List<Map<String, Object>> datasets = new ArrayList<>();
        Map<String, Object> alo = new HashMap<>();
        alo.put("data", data);
        alo.put("backgroundColor", Arrays.asList("#00a65a", "#f56954", "#f39c12"));
        datasets.add(alo);
        response.put("datasets", datasets);

        long totalSeguimientos = getTotalSeguimientos(allSeguimientos);
        response.put("totalSeguimientos", totalSeguimientos);

        return response;
    }

    private static void sumarCantidad(List<SeguimientoCantidad> seguimientos, Map<String, Integer> sumas) {
        for (SeguimientoCantidad seguimiento : seguimientos) {
            String label = seguimiento.getLabel();
            Integer cantidad = Math.toIntExact(seguimiento.getCantidad());
            sumas.put(label, sumas.getOrDefault(label, 0) + cantidad);
        }
    }

    private static List<Long> createDataArray(List<String> sortedMonths, List<SeguimientoCantidad> seguimientos) {
        Map<String, Long> map = new LinkedHashMap<>();
        for (SeguimientoCantidad seguimiento : seguimientos) {
            map.put(seguimiento.getLabel(), seguimiento.getCantidad());
        }

        List<Long> data = new ArrayList<>();
        for (String month : sortedMonths) {
            data.add(map.getOrDefault(month, 0L));
        }
        return data;
    }

    private static Map<String, Object> createDataset(String label, String color, List<Long> data) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("backgroundColor", color);
        dataset.put("borderColor", color);
        dataset.put("data", data);
        return dataset;
    }

    private static long getTotalSeguimientos(List<List<SeguimientoCantidad>> allSeguimientos) {
        long total = 0;
        for (List<SeguimientoCantidad> seguimientos : allSeguimientos) {
            for (SeguimientoCantidad seguimiento : seguimientos) {
                total += seguimiento.getCantidad();
            }
        }
        return total;
    }

    public static List<String> getMeses(List<String> sortedMonths) {
        Map<String, String> traducciones = new HashMap<>();
        traducciones.put("January", "Enero");
        traducciones.put("February", "Febrero");
        traducciones.put("March", "Marzo");
        traducciones.put("April", "Abril");
        traducciones.put("May", "Mayo");
        traducciones.put("June", "Junio");
        traducciones.put("July", "Julio");
        traducciones.put("August", "Agosto");
        traducciones.put("September", "Septiembre");
        traducciones.put("October", "Octubre");
        traducciones.put("November", "Noviembre");
        traducciones.put("December", "Diciembre");

        List<String> meses = new ArrayList<>();

        for (String mesIngles : sortedMonths) {
            String mesEspanol = traducciones.get(mesIngles);
            meses.add(mesEspanol);
        }

        return meses;
    }
}
