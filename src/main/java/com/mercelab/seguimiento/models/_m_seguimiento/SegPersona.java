package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "seg_personas")
public class SegPersona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id")
    private Long id;

    @Column(name = "per_tipo_documento")
    private Integer tipoDocumento;

    @Column(name = "per_nro_documento")
    private String numeroDocumento;

    @Column(name = "per_apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "per_apellido_materno")
    private String apellidoMaterno;

    @Column(name = "per_nombres")
    private String nombres;

    @Column(name = "per_telefono")
    private String telefono;

    @JoinColumn(name = "per_ubigeo")
    @ManyToOne
    private SegDistrito distrito;

    @Column(name = "per_direccion")
    private String direccion;

    @Column(name = "per_email")
    private String email;

    @Column(name = "per_sexo")
    private String sexo;

    @Column(name = "per_fecha_nacimiento")
    private LocalDate fechaNacimiento;

}
