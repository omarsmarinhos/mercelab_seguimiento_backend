package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_tratamientos")
@Data
public class SegTratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tra_id")
    private Long id;

    @Column(name = "enf_id")
    private Long enfermedadId;

    @Column(name = "tra_observacion")
    private String observacion;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "tra_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "tra_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "tra_creado_por")
    private Long creadoPor;

    @Column(name = "tra_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2623L;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
        this.estadoId = 3L;
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }

}
