package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seg_ubigeo_distrito")
public class SegDistrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dis_codigo")
    private Long id;

    @Column(name = "dis_descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "pro_codigo")
    private SegProvincia provincia;

}
