package com.mercelab.seguimiento.models._m_seguimiento;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "seg_menus")
public class SegMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "men_id")
    private Long id;

    @Column(name = "men_nombre")
    private String nombre;

    @Column(name = "men_url")
    private String url;

    @Column(name = "men_icono")
    private String icono;

    @Column(name = "men_padre_id")
    private Long padreId;

    @Column(name = "est_id")
    private Long estadoId;

    @Column(name = "men_creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creadoEn;

    @Column(name = "men_editado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editadoEn;

    @Column(name = "men_creado_por")
    private Long creadoPor;

    @Column(name = "men_editado_por")
    private Long editadoPor;

    @Serial
    private static final long serialVersionUID = 26537L;

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
