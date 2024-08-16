package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamiento;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoMedicamento;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegTratamientoMedicamentoDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoMedicamentoRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegTratamientoRepository;
import com.mercelab.seguimiento.services.mapping.TratamientoMedicamentoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SegTratamientoMedicamentoService {

    @Autowired
    private SegTratamientoMedicamentoRepository tratamientoMedicamentoRepository;

    @Autowired
    private SegTratamientoRepository tratamientoRepository;

    @Autowired
    private TratamientoMedicamentoMapper tratamientoMedicamentoMapper;

    public List<SegTratamientoMedicamentoDto> getMedicamentosAsignados(Long tratamientoId) {
        return tratamientoMedicamentoRepository.getMedicamentosAsignados(tratamientoId);
    }

    @Transactional
    public SegTratamientoMedicamentoDto agregarMedicamento(SegTratamientoMedicamentoDto tratamientoMedicamentoDto) {
        SegTratamiento tratamiento = tratamientoRepository.findById(tratamientoMedicamentoDto.getTratamientoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con ID " + tratamientoMedicamentoDto.getTratamientoId()));

        SegTratamientoMedicamento tratamientoMedicamento = tratamientoMedicamentoMapper.toEntity(tratamientoMedicamentoDto);

        if (tratamiento.getEstadoId() == Estado.VACIO) {
            tratamiento.setEstadoId(Estado.ACTIVO);
            tratamientoRepository.save(tratamiento);
        }

        tratamientoMedicamento.setTratamiento(tratamiento);

        return tratamientoMedicamentoMapper.toSegTratamientoMedicamentoDto(tratamientoMedicamentoRepository.save(tratamientoMedicamento));
    }

    public void anular(Long id) {
        SegTratamientoMedicamento tratamientoMedicamento = tratamientoMedicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tratamiento con medicamento de ID " + id));
        tratamientoMedicamento.setEstadoId(Estado.ELIMINADO);
        tratamientoMedicamentoRepository.save(tratamientoMedicamento);
    }

    public Boolean estaRegistradoTratamientoMedicamento(SegTratamientoMedicamentoDto tratamientoMedicamentoDto) {
        return tratamientoMedicamentoRepository.existeTratamientoMedicamento(
                        tratamientoMedicamentoDto.getTratamientoId(),
                        tratamientoMedicamentoDto.getMedicamentoId())
                .isPresent();
    }

}
