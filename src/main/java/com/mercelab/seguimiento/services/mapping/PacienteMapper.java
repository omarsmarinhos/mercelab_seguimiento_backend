package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegPaciente;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegPacienteDto;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface PacienteMapper {

    @Mapping(source = "persona", target = "persona")
    SegPacienteDto toPacienteDTO(SegPaciente paciente);

    SegPaciente toEntity(SegPacienteDto pacienteDTO);

    default Integer calcularEdad(LocalDate fechaNacimiento) {
        return fechaNacimiento != null ? Period.between(fechaNacimiento, LocalDate.now()).getYears() : null;
    }

    @Named("toPacienteDTOWithExtras")
    default SegPacienteDto toPacienteDTOWithExtras(SegPaciente paciente) {
        SegPacienteDto pacienteDTO = toPacienteDTO(paciente);
        pacienteDTO.setEdad(calcularEdad(paciente.getPersona().getFechaNacimiento()));
        pacienteDTO.setNombreCompleto(paciente.getPersona().getApellidoPaterno()
                .concat(" ").concat(paciente.getPersona().getApellidoMaterno())
                .concat(" ").concat(paciente.getPersona().getNombres())
        );
        if (paciente.getPersona().getDistrito() != null) {
            String distrito = paciente.getPersona().getDistrito().getDescripcion();
            String provincia = paciente.getPersona().getDistrito().getProvincia().getDescripcion();
            String departamento = paciente.getPersona().getDistrito().getProvincia().getDepartamento().getDescripcion();
            pacienteDTO.setUbigeo(distrito.concat(" - ")
                    .concat(provincia).concat(" - ")
                    .concat(departamento)
            );
            pacienteDTO.getPersona().setDistritoId(paciente.getPersona().getDistrito().getId());
        }
        return pacienteDTO;
    }

    @Mappings({
            // Ignoramos los campos que no se deben actualizar
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "persona.id", ignore = true),
            @Mapping(target = "codHistorialClinico", ignore = true),
    })
    void actualizarPacienteDesdeDto(@MappingTarget SegPaciente pacienteExistente, SegPacienteDto pacienteDto);

}
