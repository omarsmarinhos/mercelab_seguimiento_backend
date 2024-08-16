package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "seg_enfermedades")
public class SegEnfermedad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enf_id")
    private Long id;

    @JoinColumn(name = "pac_id")
    @ManyToOne
    private SegPaciente paciente;

    @Column(name = "enf_fecha_diagnostico")
    private LocalDate fechaDiagnostico;

    @JoinColumn(name = "id_cie10")
    @ManyToOne
    private SegCie10 cie10;

    @Column(name = "enf_cronico")
    private Boolean esCronico;

    @Column(name = "enf_descripcion")
    private String descripcion;

    @Column(name = "enf_observacion_medicamentos")
    private String observacion;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "enf_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "enf_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "enf_creado_por")
    private Long creadoPor;

    @Column(name = "enf_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2655L;

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
