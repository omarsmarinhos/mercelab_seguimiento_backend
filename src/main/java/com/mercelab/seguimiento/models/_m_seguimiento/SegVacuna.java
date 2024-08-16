package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_vacunas")
@Data
public class SegVacuna implements Serializable {

    @Id
    @Column(name = "vac_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vac_edad")
    private String edad;

    @Column(name = "vac_dosis")
    private String dosis;

    @Column(name = "vac_laboratorio")
    private String laboratorio;

    @Column(name = "vac_descripcion")
    private String descripcion;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "vac_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "vac_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "vac_creado_por")
    private Long creadoPor;

    @Column(name = "vac_editado_por")
    private Long editadoPor;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
        this.estadoId = 1L;
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }

}
