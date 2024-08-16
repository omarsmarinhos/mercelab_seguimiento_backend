package com.mercelab.seguimiento.services.mapping;

import com.mercelab.seguimiento.models._m_seguimiento.SegUsuario;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioRequestDto;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface UsuarioMapper {

    @Mapping(source = "persona", target = "persona")
    @Mapping(source = "rol.id", target = "rolId")
    SegUsuarioRequestDto toUsuarioDTO(SegUsuario usuario);

    SegUsuario toEntity(SegUsuarioRequestDto usuarioDto);

    default Integer calcularEdad(LocalDate fechaNacimiento) {
        return fechaNacimiento != null ? Period.between(fechaNacimiento, LocalDate.now()).getYears() : null;
    }
    @Named("toUsuarioDTOWithExtras")
    default SegUsuarioRequestDto toUsuarioDTOWithExtras(SegUsuario usuario) {
        SegUsuarioRequestDto usuarioDTO = toUsuarioDTO(usuario);
        usuarioDTO.setPassword("");
        usuarioDTO.setEdad(calcularEdad(usuario.getPersona().getFechaNacimiento()));
        if (usuario.getPersona().getDistrito() != null) {
            String distrito = usuario.getPersona().getDistrito().getDescripcion();
            String provincia = usuario.getPersona().getDistrito().getProvincia().getDescripcion();
            String departamento = usuario.getPersona().getDistrito().getProvincia().getDepartamento().getDescripcion();
            usuarioDTO.setUbigeo(distrito.concat(" - ")
                    .concat(provincia).concat(" - ")
                    .concat(departamento)
            );
            usuarioDTO.getPersona().setDistritoId(usuario.getPersona().getDistrito().getId());
        }
        return usuarioDTO;
    }

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "persona.id", ignore = true),
            @Mapping(target = "password", ignore = true),
    })
    void actualizarUsuarioDesdeDto(@MappingTarget SegUsuario usuarioExistente, SegUsuarioRequestDto usuarioDto);

}
