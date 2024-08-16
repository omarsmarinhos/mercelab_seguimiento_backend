package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "seg_menuroles")
public class SegMenuRoles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mr_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private SegRol rol;

    @ManyToOne
    @JoinColumn(name = "men_id")
    private SegMenu menu;

    private Boolean crear;
    private Boolean leer;
    private Boolean editar;
    private Boolean eliminar;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "mr_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "mr_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "mr_creado_por")
    private Long creadoPor;

    @Column(name = "mr_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 26537L;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.editadoEn = LocalDateTime.now();
    }

}
