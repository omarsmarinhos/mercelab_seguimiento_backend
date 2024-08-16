package com.mercelab.seguimiento.models._m_seguimiento;

import com.mercelab.seguimiento.models._m_seguimiento.Estado;
import com.mercelab.seguimiento.models._m_seguimiento.SegSeguimiento;
import com.mercelab.seguimiento.models._m_seguimiento.SegTratamientoEspecialidad;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "seg_seguimientoespecialidad")
@Data
@Entity
public class SegSeguimientoEspecialidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_esp_id")
    private Long id;

    @JoinColumn(name = "seg_id")
    @ManyToOne
    private SegSeguimiento seguimiento;

    @JoinColumn(name = "tra_esp_id")
    @ManyToOne
    private SegTratamientoEspecialidad tratamientoEspecialidad;

    @Column(name = "seg_esp_tipo_notificacion")
    private char tipoNotificacion;

    @Column(name = "seg_esp_fecha_programada")
    private LocalDate fechaProgramada;

    @Column(name = "seg_esp_observacion")
    private String observacion;

    @Column(name = "seg_esp_usuario_notificacion")
    private Long usuarioNotificacion;

    @Column(name = "seg_esp_fecha_notificacion")
    private LocalDate fechaNotificacion;

    @Column(name = "seg_esp_respuesta")
    private String respuesta;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "seg_esp_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "seg_esp_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "seg_esp_creado_por")
    private Long creadoPor;

    @Column(name = "seg_esp_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 2789L;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }
}
