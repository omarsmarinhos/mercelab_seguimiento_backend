package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seg_cie10_diagnosticos")
public class SegCie10 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "clave")
    private String codigoCie10;


}
