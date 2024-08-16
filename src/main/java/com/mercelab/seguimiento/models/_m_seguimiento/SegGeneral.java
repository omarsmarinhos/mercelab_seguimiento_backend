package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seg_general")
@Data
public class SegGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id")
    private Long id;

    @Column(name = "hc_correlativo")
    private Long hcCorrelativo;

}
