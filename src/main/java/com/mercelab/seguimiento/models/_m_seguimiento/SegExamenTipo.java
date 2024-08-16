package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "seg_examenes_tipo")
@Data
public class SegExamenTipo implements Serializable {

    @Id
    @Column(name = "exa_tipo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exa_tipo_descripcion")
    private String descripcion;



}
