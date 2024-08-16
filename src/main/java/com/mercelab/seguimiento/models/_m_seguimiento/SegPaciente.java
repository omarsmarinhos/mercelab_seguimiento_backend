package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "seg_pacientes")
public class SegPaciente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pac_id")
    private Long id;

    @Column(name = "pac_cod_historial_clinico")
    private String codHistorialClinico;

    @Column(name = "pac_tiene_alergia")
    private Boolean tieneAlergia;

    //@OneToOne(fetch = FetchType.LAZY)
    @OneToOne()
    @JoinColumn(name = "per_id")
    private SegPersona persona;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "pac_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "pac_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "pac_creado_por")
    private Long creadoPor;

    @Column(name = "pac_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2657L;

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
