package com.mercelab.seguimiento.controllers.helpers;

import com.mercelab.seguimiento.models._m_seguimiento.dto.SeguimientosDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.*;

public class PaginationResponseUtil {

    public static Map<String, Object> generatePaginationResponse(Page<?> page) {
        Map<String, Object> response = new HashMap<>();

        response.put("data", page.getContent());
        response.put("number", page.getNumber());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("size", page.getSize());
        response.put("sort", page.getSort());
        response.put("numberOfElements", page.getNumberOfElements());
        response.put("first", page.isFirst());
        response.put("last", page.isLast());

        return response;
    }

    public static Map<String, Object> generatePaginationResponse(
            Page<SeguimientosDto> page1,
            Page<SeguimientosDto> page2,
            Page<SeguimientosDto> page3,
            Page<SeguimientosDto> page4) {

        Map<String, Object> response = new HashMap<>();

        List<SeguimientosDto> list1 = page1.getContent();
        List<SeguimientosDto> list2 = page2.getContent();
        List<SeguimientosDto> list3 = page3.getContent();
        List<SeguimientosDto> list4 = page4.getContent();
        List<SeguimientosDto> seguimientosTotales = new ArrayList<>();
        seguimientosTotales.addAll(list1);
        seguimientosTotales.addAll(list2);
        seguimientosTotales.addAll(list3);
        seguimientosTotales.addAll(list4);
        seguimientosTotales.sort(Comparator.comparing(SeguimientosDto::getFechaProgramada));

        long totalElements = page1.getTotalElements() + page2.getTotalElements() + page3.getTotalElements() + page4.getTotalElements();
        int totalPages = Math.max(page1.getTotalPages(), Math.max(page2.getTotalPages(), Math.max(page3.getTotalPages(), page4.getTotalPages())));
        int size = page1.getSize() + page2.getSize() + page3.getSize() + page4.getSize();
        Sort sort = page1.getSort();
        int numberOfElements = page1.getNumberOfElements() + page2.getNumberOfElements() + page3.getNumberOfElements() + page4.getNumberOfElements();
        boolean first = page1.isFirst() && page2.isFirst() && page3.isFirst() && page4.isFirst();
        boolean last = page1.isLast() && page2.isLast() && page3.isLast() && page4.isLast();

        response.put("data", seguimientosTotales);
        response.put("number", page1.getNumber());
        response.put("totalElements", totalElements);
        response.put("totalPages", totalPages);
        response.put("size", size);
        response.put("sort", sort);
        response.put("numberOfElements", numberOfElements);
        response.put("first", first);
        response.put("last", last);

        return response;
    }
}
