package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seg_ubigeo_provincia")
public class SegProvincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_codigo")
    private Long id;

    @Column(name = "pro_descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "dep_codigo")
    private SegDepartamento departamento;
}
