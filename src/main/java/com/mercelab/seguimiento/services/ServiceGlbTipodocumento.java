package com.mercelab.seguimiento.services;

import java.util.List;


import com.mercelab.seguimiento.models._m_seguimiento.GlbTipodocumento;
import com.mercelab.seguimiento.repositories._r_seguimiento.GlbTipodocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceGlbTipodocumento {
	
	@Autowired
	private GlbTipodocumentoRepository glbTipodocumentoRepository;

	@Transactional(readOnly = true)
	public List<GlbTipodocumento> findAll() {
		return (List<GlbTipodocumento>) glbTipodocumentoRepository.findAll();
	}


}
