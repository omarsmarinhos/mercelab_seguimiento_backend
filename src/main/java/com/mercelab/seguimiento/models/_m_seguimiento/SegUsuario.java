package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "seg_usuarios")
public class SegUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;

    @Column(name = "usu_password")
    private String password;

    @OneToOne
    @JoinColumn(name = "per_id")
    private SegPersona persona;

    @OneToOne
    @JoinColumn(name = "rol_id")
    private SegRol rol;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "usu_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "usu_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "usu_creado_por")
    private Long creadoPor;

    @Column(name = "usu_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 26957L;

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
