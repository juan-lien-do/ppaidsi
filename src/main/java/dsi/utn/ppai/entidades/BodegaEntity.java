package dsi.utn.ppai.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bodegas")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BodegaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBodega")
    private Integer idBodega;
    @Column(name = "apiUrl")
    private String apiUrl;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "historia")
    private String historia;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "periodoActualizacion")
    private Integer periodoActualizacion;
    @Column(name = "ultimaActualizacion")
    private LocalDate ultimaActualizacion;

    // perdon por usar fetchType Eager es que tenemos poco tiempo
    @OneToMany(mappedBy = "bodega", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VinoEntity> vinoEntities;
}

/*
@OneToMany
@JoinColumn(name = "bodegaId")*/