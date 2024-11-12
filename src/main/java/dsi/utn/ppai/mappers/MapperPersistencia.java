package dsi.utn.ppai.mappers;

import dsi.utn.ppai.entidades.*;
import dsi.utn.ppai.modelo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

// podriamos dividir esta clase en muchas clases mas chiquitas pero no esta tan bueno ir de archivo en archivo
public class MapperPersistencia {
    public static TipoUva fromEntity(TipoUvaEntity tipoUvaEntity){
        return TipoUva.builder()
                .idTipoUva(tipoUvaEntity.getIdTipoUva())
                .descripcion(tipoUvaEntity.getDescripcion())
                .nombre(tipoUvaEntity.getNombre())
                .build();
    }

    public static Usuario fromEntity(UsuarioEntity usuarioEntity){
        return Usuario.builder()
                .idUsuario(usuarioEntity.getIdUsuario())
                .contrasena(usuarioEntity.getContrasena())
                .nombre(usuarioEntity.getNombre())
                .premium(usuarioEntity.getPremium())
                .build();
    }

    public static Maridaje fromEntity(MaridajeEntity maridajeEntity){
        return Maridaje.builder()
                .idMaridaje(maridajeEntity.getIdMaridaje())
                .nombre(maridajeEntity.getNombre())
                .descripcion(maridajeEntity.getDescripcion())
                .build();
    }

    public static Varietal fromEntity(VarietalEntity varietalEntity){
        return Varietal.builder()
                .idVarietal(varietalEntity.getIdVarietal())
                .descripcion(varietalEntity.getDescripcion())
                .porcentajeComposicion(varietalEntity.getPorcentajeComposicion())
                .tipoUva(fromEntity(varietalEntity.getTipoUvaEntity()))
                .build();
    }

    // la relacion bodega-vino no es complicada ya que no hay ningun vino que este en varias bodegas.
    public static Vino fromEntity(VinoEntity vinoEntity, Bodega bodega){
        return Vino.builder()
                .idVino(vinoEntity.getIdVino())
                .anada(vinoEntity.getAnada())
                .imagenEtiqueta(vinoEntity.getImagenEtiqueta())
                .fechaActualizacion(vinoEntity.getFechaActualizacion())
                .nombre(vinoEntity.getNombre())
                .notaDeCataBodega(vinoEntity.getNotaDeCataBodega())
                .precioARS(vinoEntity.getPrecioARS())
                .maridajes(vinoEntity.getMaridajeEntities().stream().map(MapperPersistencia::fromEntity).toList())
                .varietales(vinoEntity.getVarietalEntities().stream().map(MapperPersistencia::fromEntity).toList())
                .bodega(bodega)
                .build();
    }

    // la relacion bodega-vino no es complicada ya que no hay ningun vino que este en varias bodegas.
    public static Bodega fromEntity(BodegaEntity bodegaEntity){
        Bodega bodega = Bodega.builder()
                .idBodega(bodegaEntity.getIdBodega())
                .apiUrl(bodegaEntity.getApiUrl())
                .descripcion(bodegaEntity.getDescripcion())
                .historia(bodegaEntity.getHistoria())
                .nombre(bodegaEntity.getNombre())
                .periodoActualizacion(bodegaEntity.getPeriodoActualizacion())
                .ultimaActualizacion(bodegaEntity.getUltimaActualizacion())
                .build();

        // aca le pasamos el self/this para que se mantenga la relacion
        // no se si hacia falta igual
        bodega.setVinos(bodegaEntity.getVinoEntities().stream().map(vinoEntity -> MapperPersistencia.fromEntity(vinoEntity, bodega)).toList());

        return bodega;
    }

    public static Enofilo fromEntity(EnofiloEntity enofiloEntity){
        return Enofilo.builder()
                .idEnofilo(enofiloEntity.getIdEnofilo())
                .apellido(enofiloEntity.getApellido())
                .nombre(enofiloEntity.getNombre())
                .usuario(fromEntity(enofiloEntity.getUsuarioEntities()))
                // TODO siguiendos
                .build();
    }

    private static Enofilo simpleFromEntity(EnofiloEntity enofiloEntity){
        return Enofilo.builder()
            .idEnofilo(enofiloEntity.getIdEnofilo())
            .apellido(enofiloEntity.getApellido())
            .nombre(enofiloEntity.getNombre())
            .usuario(fromEntity(enofiloEntity.getUsuarioEntities()))
            // TODO siguiendos
            .build();
    }

    // aca esta private para no mandarnos moco despues.
    // esta atado con alambre (hashmaps) para que no se quintupliquen los objetos
    private static Siguiendo fromEntity(SiguiendoEntity siguiendoEntity,
                                       HashMap<Integer, Bodega> bodegas, HashMap<Integer, Enofilo> enofilos){
        Siguiendo sig = Siguiendo.builder()
                .idSiguiendo(siguiendoEntity.getIdSiguiendo())
                .fechaInicio(siguiendoEntity.getFechaInicio())
                .fechaFin(siguiendoEntity.getFechaFin())
                .build();

        if (siguiendoEntity.getBodegaEntity() != null){
            sig.setBodega(bodegas.get(siguiendoEntity.getBodegaEntity().getIdBodega()));
        } else if (siguiendoEntity.getEnofiloEntity() != null){
            sig.setEnofilo(enofilos.get(siguiendoEntity.getEnofiloEntity().getIdEnofilo()));
        }

        return sig;
    }


    // aca la idea es conseguir todos los objetos que necesitamos,
    // convertir los que tengan menos relaciones primero,
    // guardarlos en hashmap para no repetirlos
    // y luego convertir los objetos más complejos (siguiendo) con esos hashmaps.


    // hacemos un mapeo de atributos sencillos primero, luego asignamos las entidades con mapas.
    public static List<Enofilo> fromEntities(List<EnofiloEntity> enofiloEntities, List<BodegaEntity> bodegaEntities){
        // conseguir las entidades de siguiendos desde los enofilos
        List<SiguiendoEntity> siguiendoEntities = new ArrayList<>();
        enofiloEntities.forEach(en -> siguiendoEntities.addAll(
                en.getSiguiendoEntities()));

        // conseguir los enofilos y bodegas
        HashMap<Integer, Enofilo> enofiloHashMap = new HashMap<>();
        enofiloEntities.forEach(enEn -> enofiloHashMap.put(enEn.getIdEnofilo(), simpleFromEntity(enEn)));

        HashMap<Integer, Bodega> bodegaHashMap = new HashMap<>();
        bodegaEntities.forEach(bode -> bodegaHashMap.put(bode.getIdBodega(), fromEntity(bode)));

        // conseguir los siguiendos y asignarles bodegas y enofilos
        List<Siguiendo> siguiendos = siguiendoEntities.stream().map(sig -> fromEntity(sig, bodegaHashMap, enofiloHashMap)).toList();

        // asignarle los siguiendos a los enofilos
        for (Enofilo enofilo : enofiloHashMap.values()) {
            //conseguir entidad
            Optional<EnofiloEntity> entidad = enofiloEntities.stream().filter(en -> en.getIdEnofilo() == enofilo.getIdEnofilo()).findFirst();
            if (entidad.isPresent()){
                for (SiguiendoEntity siguiendoEntity : entidad.get().getSiguiendoEntities()) {
                    Optional<Siguiendo> siguiendo = siguiendos.stream()
                            .filter(sig -> sig.getIdSiguiendo() == siguiendoEntity.getIdSiguiendo()).findFirst();
                    enofilo.getSiguiendos().add(siguiendo.get());
                }
            }
        }

        return enofiloHashMap.values().stream().toList();

    }


}





// Java es el mejor lenguaje del mundo