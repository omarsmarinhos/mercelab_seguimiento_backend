package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_historialseguimiento")
@Data
public class SegHistorial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "his_id")
    private Long id;

    @Column(name = "elemento_id")
    private Long elementoId;

    @Column(name = "his_tipo_nombre")
    private String elementoTipoNombre;

    @Column(name = "seg_id")
    private Long segId;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "his_fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "his_fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "his_usuario_inicia")
    private Long usuarioInicia;

    @Column(name = "his_usuario_termina")
    private Long usuarioTermina;

    @PrePersist
    public void prePersist() {
        this.estadoId = Estado.ACTIVO;
    }

}
