package com.mercelab.seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.SegEnfermedad;
import com.mercelab.seguimiento.models._m_seguimiento.SegMenuRoles;
import com.mercelab.seguimiento.models._m_seguimiento.SegPaciente;
import com.mercelab.seguimiento.models._m_seguimiento.SegUsuario;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegEnfermedadDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegSeguimientoExamenDto;
import com.mercelab.seguimiento.models._m_seguimiento.dto.SegUsuarioTableDto;
import com.mercelab.seguimiento.repositories._r_seguimiento.*;
import com.mercelab.seguimiento.services.SegEnfermedadService;
import com.mercelab.seguimiento.services.SegPacienteService;
import com.mercelab.seguimiento.services.SegPermisosService;
import com.mercelab.seguimiento.services.SegSeguimientoExamenService;
import com.mercelab.seguimiento.services.mapping.PacienteMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SeguimientoApplicationTests {

    @Autowired
    private SegPacienteService segPacienteService;

    @Autowired
    private SegPacienteRepository segPacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private SegSeguimientoVacunaRepository vacunaRepository;

    @Autowired
    private SegSeguimientoEspecialidadRepository especialidadRepository;

    @Autowired
    private SegSeguimientoExamenRepository examenRepository;

    @Autowired
    private SegSeguimientoMedicamentoRepository medicamentoRepository;

    @Autowired
    private SegUsuarioRepository usuarioRepository;

    @Autowired
    private SegRolRepository rolRepository;

    @Autowired
    private SegPermisosService permisosService;

    @Test
    void contextLoads() {
        permisosService.getRoles("", PageRequest.of(0, 10)).getContent()
                .forEach(System.out::println);
    }

}
