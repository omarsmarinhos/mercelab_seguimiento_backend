package com.mercelab.seguimiento.models._m_seguimiento;

import java.io.Serializable;
import java.time.LocalDate;


import com.mercelab.seguimiento.models.GlobalEntity;
import com.mercelab.seguimiento.services.CapitalizeFirstLetter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@DynamicUpdate
@Entity
@Data
@Table(name = "glb_persona", schema = "seguimiento")
public class GlbPersona extends GlobalEntity
        implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pers_id;
    private String emp_empresa_empr_id;
    private Long glb_tipopersona_tper_id;
    private Long glb_estadocivil_escv_id;
    private Long glb_pais_pais_id;
    private Long glb_niveleducativo_nved_id;
    @Min(value = 1)
    @NotNull
    private Long glb_tipodocumento_tpdo_id;
    private Long glb_image_img_id;
    private Long glb_email_emai_id;
    private Long glb_telefono_telf_id;
    @NotEmpty
    private String pers_apaterno;
    @NotEmpty
    private String pers_amaterno;
    @NotEmpty
    private String pers_nombres;
    @NotEmpty
    @ColumnDefault("m")
    @Pattern(regexp = "^[mfx]$")
    private String pers_sexo;
    @CapitalizeFirstLetter
    private String pers_nombrecompleto;
    @NotEmpty
    //@UnicoDocumentoIdentidad
    @Column(name = "pers_ndocumentoidentidad")
    private String persNdocumentoidentidad;
    @NotNull
	@Column(name = "pers_fechanacimiento")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate pers_fechanacimiento;
    @ColumnDefault("1")
    private Integer glb_estado_n_est_id;

    @ColumnDefault("1")
    private String pers_tipo;
    //@NotEmpty
    @Transient
    @Email 
    private String glb_persona_email;
    @Transient
    //@NotEmpty
    @Pattern(regexp="^\\d{3}-\\d{3}-\\d{3}$")
    private String glb_persona_telefono;

    @PrePersist
    @PreUpdate
    public void beforeSaveOrUpdate() {
        this.toLowerCase();
    }
    @PostLoad
    public void afterLoad(){
        this.capitalizeFields();
    }

    
}
