package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_seguimientomedicamento")
@Data
public class SegSeguimientoMedicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_med_id")
    private Long id;

    @JoinColumn(name = "seg_id")
    @ManyToOne
    private SegSeguimiento seguimiento;

    @JoinColumn(name = "tra_med_id")
    @ManyToOne
    private SegTratamientoMedicamento tratamientoMedicamento;

    @Column(name = "seg_med_tipo_notificacion")
    private char tipoNotificacion;

    @Column(name = "seg_med_fecha_programada")
    private LocalDate fechaProgramada;

    @Column(name = "seg_med_observacion")
    private String observacion;

    @Column(name = "seg_med_usuario_notificacion")
    private Long usuarioNotificacion;

    @Column(name = "seg_med_fecha_notificacion")
    private LocalDate fechaNotificacion;

    @Column(name = "seg_med_respuesta")
    private String respuesta;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "seg_med_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "seg_med_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "seg_med_creado_por")
    private Long creadoPor;

    @Column(name = "seg_med_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2215L;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }
}
