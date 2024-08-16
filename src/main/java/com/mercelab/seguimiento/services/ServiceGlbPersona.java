package com.mercelab.seguimiento.services;


import com.mercelab.seguimiento.models._m_seguimiento.GlbPersona;
import com.mercelab.seguimiento.repositories._r_seguimiento.GlbPersonaRepository;
import com.mercelab.seguimiento._setting._api.exceptions.DeleteException;
import com.mercelab.seguimiento._setting._api.exceptions.SaveException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceGlbPersona {

    @Autowired
    private GlbPersonaRepository glbPersonaRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    public List<GlbPersona> findAll() {
        return (List<GlbPersona>) glbPersonaRepository.findAll();
    }

    public GlbPersona save(GlbPersona glb_persona) throws SaveException {
        try {
            return glbPersonaRepository.save(glb_persona);
        } catch (Exception e) {
            throw new SaveException(e);
        }
    }

    public GlbPersona update(Long id, GlbPersona glb_persona) throws SaveException {

        GlbPersona glbPersonaUpdate = glbPersonaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID " + id));
        try {
            glbPersonaUpdate.setGlb_persona_email("supportmercelab@gmail.com");
            glbPersonaUpdate.setGlb_persona_telefono("945-567-345");
            glbPersonaUpdate.setPers_id(id);
            modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
            modelMapper.map(glb_persona, glbPersonaUpdate);
        } catch (Exception e) {
            throw new SaveException(e);
        }
        return glbPersonaRepository.save(glbPersonaUpdate);
    }

    @Transactional(readOnly = true)
    public GlbPersona findOne(Long id) {
        return glbPersonaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID " + id));
    }

    public void delete(Long id) throws DeleteException {
        glbPersonaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la persona con ID " + id));
        try {
            glbPersonaRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteException(e);
        }
    }
    @Transactional(readOnly = true)
    public GlbPersona buscarDni(String numeroDocIdent) {
        GlbPersona glbPersona = glbPersonaRepository.findByPersNdocumentoidentidad(numeroDocIdent);
        return glbPersona;
    }










}
