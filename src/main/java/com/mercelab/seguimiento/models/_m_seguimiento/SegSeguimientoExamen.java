package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_seguimientoexamen")
@Data
public class SegSeguimientoExamen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_exa_id")
    private Long id;

    @JoinColumn(name = "seg_id")
    @ManyToOne
    private SegSeguimiento seguimiento;

    @JoinColumn(name = "tra_exa_id")
    @ManyToOne
    private SegTratamientoExamen tratamientoExamen;

    @Column(name = "seg_exa_tipo_notificacion")
    private char tipoNotificacion;

    @Column(name = "seg_exa_fecha_programada")
    private LocalDate fechaProgramada;

    @Column(name = "seg_exa_observacion")
    private String observacion;

    @Column(name = "seg_exa_usuario_notificacion")
    private Long usuarioNotificacion;

    @Column(name = "seg_exa_fecha_notificacion")
    private LocalDate fechaNotificacion;

    @Column(name = "seg_exa_respuesta")
    private String respuesta;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "seg_exa_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "seg_exa_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "seg_exa_creado_por")
    private Long creadoPor;

    @Column(name = "seg_exa_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2695L;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }
}
