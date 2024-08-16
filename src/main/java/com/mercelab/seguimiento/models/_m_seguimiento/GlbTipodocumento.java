package com.mercelab.seguimiento.models._m_seguimiento;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Entity
@Table(name = "seg_gen_tipodocumento", schema = "seguimiento")
public class GlbTipodocumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tpdo_id")
	private String tpdoId;

	@NotEmpty
	@Column(name = "tpdo_nombre")
	private String tpdoNombre;

	@NotEmpty
	@Column(name = "tpdo_descripcion")
	private String tpdoDescripcion;

	@NotEmpty
	@Column(name = "tpdo_sunat_codigo")
	private String tpdoSnatCodigo;

	@Column(name = "glb_estado_n_est_id")
	private String glbEstadoEstId;


}
