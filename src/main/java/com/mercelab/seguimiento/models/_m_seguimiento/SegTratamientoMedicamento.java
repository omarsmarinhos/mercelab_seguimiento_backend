package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_tratamientosmedicamentos")
@Data
public class SegTratamientoMedicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tra_med_id")
    private Long id;

    @JoinColumn(name = "tra_id")
    @ManyToOne
    private SegTratamiento tratamiento;

    @Column(name = "id_medicamento")
    private Long medicamentoId;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "tra_med_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "tra_med_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "tra_med_creado_por")
    private Long creadoPor;

    @Column(name = "tra_med_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2323L;

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
