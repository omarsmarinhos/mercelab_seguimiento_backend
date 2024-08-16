package com.mercelab.seguimiento.services;

import com.mercelab.seguimiento.models._m_seguimiento.*;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioRequestDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioTableDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegPersonaRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegRolRepository;
import com.mercelab.seguimiento.repositories._r_seguimiento.SegUsuarioRepository;
import com.mercelab.seguimiento.services.mapping.PersonaMapper;
import com.mercelab.seguimiento.services.mapping.UsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SegUsuarioService {

    @Autowired
    private SegUsuarioRepository usuarioRepository;

    @Autowired
    private SegPersonaRepository personaRepository;

    @Autowired
    private SegRolRepository rolRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<SegUsuarioTableDto> listarUsuarios(String query, Pageable pageable) {
        return usuarioRepository.getPaginado(query, pageable);
    }

    public List<SegRol> listarRoles() {
        return rolRepository.findAll();
    }

    public Optional<SegUsuarioTableDto> getUserByEmail(String email) {
        return usuarioRepository.getByEmail(email);
    }

    public Optional<SegUsuarioRequestDto> getUserById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toUsuarioDTOWithExtras);
    }


    public void anular(Long id) {
        SegUsuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Usurio con ID " + id + " no encontrado."));
        usuario.setEstadoId(Estado.ELIMINADO);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public SegUsuarioRequestDto guardar(SegUsuarioRequestDto usuarioDto) {

        SegUsuario usuario = usuarioMapper.toEntity(usuarioDto);
        SegRol rol = rolRepository.findById(usuarioDto.getRolId()).orElseThrow(() -> new EntityNotFoundException("Rol no encontrado."));
        SegPersona persona;

        Optional<SegPersona> personaOpt = personaRepository.findByNumeroDocumento(usuarioDto.getPersona().getNumeroDocumento());
        if (personaOpt.isPresent()) {
            persona = personaOpt.get();
            personaMapper.actualizarPersonaDesdeDto(persona, usuarioDto.getPersona());
        } else {
            persona = usuario.getPersona();
        }

        if (usuarioDto.getPersona().getDistritoId() != null) {
            SegDistrito distrito = new SegDistrito();
            distrito.setId(usuarioDto.getPersona().getDistritoId());
            persona.setDistrito(distrito);
        }

        personaRepository.save(persona);

        usuario.setPersona(persona);
        usuario.setRol(rol);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioMapper.toUsuarioDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public SegUsuarioRequestDto editar(Long id, SegUsuarioRequestDto usuarioDto) {
        SegUsuario usuarioUpdate = usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No se encontró el usuario con ID " + id));

        usuarioMapper.actualizarUsuarioDesdeDto(usuarioUpdate, usuarioDto);
        usuarioUpdate.setEditadoEn(LocalDateTime.now());

        if (usuarioDto.getPersona().getDistritoId() != null) {
            SegDistrito distrito = new SegDistrito();
            distrito.setId(usuarioDto.getPersona().getDistritoId());
            usuarioUpdate.getPersona().setDistrito(distrito);
        } else {
            usuarioUpdate.getPersona().setDistrito(null);
        }

        SegRol rol = new SegRol();
        rol.setId(usuarioDto.getRolId());
        usuarioUpdate.setRol(rol);
        personaRepository.save(usuarioUpdate.getPersona());

        if (!usuarioDto.getPassword().isBlank()) {
            usuarioUpdate.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        }

        return usuarioMapper.toUsuarioDTO(usuarioRepository.save(usuarioUpdate));
    }
}
