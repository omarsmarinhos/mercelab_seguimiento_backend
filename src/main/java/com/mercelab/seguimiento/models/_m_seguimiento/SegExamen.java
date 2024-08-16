package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_examenes")
@Data
public class SegExamen implements Serializable {

    @Id
    @Column(name = "exa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exa_codigo")
    private String codigo;

    @JoinColumn(name = "exa_tipo_id")
    @ManyToOne
    private SegExamenTipo examenTipo;

    @Column(name = "exa_descripcion")
    private String descripcion;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "exa_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "exa_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "exa_creado_por")
    private Long creadoPor;

    @Column(name = "exa_editado_por")
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
