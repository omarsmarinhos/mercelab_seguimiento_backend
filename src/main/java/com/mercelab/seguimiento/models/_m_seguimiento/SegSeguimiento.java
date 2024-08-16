package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_seguimiento")
@Data
public class SegSeguimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_id")
    private Long id;

    @Column(name = "tra_id")
    private Long tratamientoId;

    @Column(name = "pac_id")
    private Long pacienteId;

    @Column(name = "seg_observacion")
    private String observacion;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "seg_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "seg_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "seg_creado_por")
    private Long creadoPor;

    @Column(name = "seg_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2123L;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
        this.estadoId = Estado.VACIO;
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }

}
