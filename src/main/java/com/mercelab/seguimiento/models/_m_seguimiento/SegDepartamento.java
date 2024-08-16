package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seg_ubigeo_departamento")
public class SegDepartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_codigo")
    private Long id;

    @Column(name = "dep_descripcion")
    private String descripcion;

}
