package dsi.utn.ppai.mappers;

import dsi.utn.ppai.entidades.*;
import dsi.utn.ppai.modelo.*;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

// podriamos dividir esta clase en muchas clases mas chiquitas pero no esta tan bueno ir de archivo en archivo

/**
 * Un mapper entre Entidad y Modelo
 */
public class MapperPersistencia {
    private static final boolean DEBUG_MODE = false;

    /**
     * Conversión necesaria para que SQLITE no estalle.
     * @param epochTimeMillis la cantidad de días que pasaron desde 1970
     * @return fechaActual LocalDate
     */
    private static LocalDate toLocalDate(Long epochTimeMillis){
        if (epochTimeMillis == null) return null;
        Instant instant = Instant.ofEpochSecond(epochTimeMillis);
        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone

        LocalDate fechaActual = instant.atZone(zoneId).toLocalDate();

        if (DEBUG_MODE) System.out.println(fechaActual.toString() + " " + epochTimeMillis);
        return fechaActual;
    }

    private static Long toUnixTime(LocalDate fecha){
        if (fecha == null) return null;

        return fecha.atStartOfDay(ZoneId.of("America/Argentina/Buenos_Aires")).toEpochSecond();
    }

    public static BodegaEntity fromModel(Bodega bodega, List<MaridajeEntity> maridajeEntities){
        BodegaEntity bodegaEntity = BodegaEntity.builder()
                .idBodega(bodega.getIdBodega())
                .apiUrl(bodega.getApiUrl())
                .descripcion(bodega.getDescripcion())
                .historia(bodega.getHistoria())
                .nombre(bodega.getNombre())
                .periodoActualizacion(bodega.getPeriodoActualizacion())
                .ultimaActualizacion(toUnixTime(bodega.getUltimaActualizacion()))
                .build();
        List<VinoEntity> vinoEntities = bodega.getVinos().stream().map(x -> fromModel(x, bodegaEntity, maridajeEntities)).toList();
        bodegaEntity.setVinoEntities(vinoEntities);
        return bodegaEntity;
    }

    private static VinoEntity fromModel(Vino vino, BodegaEntity bodegaEntity, List<MaridajeEntity> maridajeEntities){


        VinoEntity vinoEntity = VinoEntity.builder()
                .idVino(vino.getIdVino())
                .imagenEtiqueta(vino.getImagenEtiqueta())
                .anada(vino.getAnada())
                .fechaActualizacion(toUnixTime(vino.getFechaActualizacion()))
                .nombre(vino.getNombre())
                .notaDeCataBodega(vino.getNotaDeCataBodega())
                .precioARS(vino.getPrecioARS())
                .bodega(bodegaEntity)
                .build();

        vinoEntity.setVarietalEntities(vino.getVarietales().stream().map(x -> fromModel(x, vinoEntity)).toList());

        // TODO maridaje
        List<MaridajeEntity> maridajesDelVino = maridajeEntities.stream()
                .filter(marid -> vino.getMaridajes().stream()
                        .anyMatch(mariModel -> mariModel.getIdMaridaje() == marid.getIdMaridaje())).toList();
        vinoEntity.setMaridajeEntities(maridajesDelVino);

        return vinoEntity;
    }

    private static VarietalEntity fromModel(Varietal varietal, VinoEntity vinoEntity){
        return VarietalEntity.builder()
                .idVarietal(varietal.getIdVarietal())
                .descripcion(varietal.getDescripcion())
                .porcentajeComposicion(varietal.getPorcentajeComposicion())
                .tipoUvaEntity(fromModel(varietal.getTipoUva()))
                .build();
    }

    private static TipoUvaEntity fromModel(TipoUva tipoUva){
        return TipoUvaEntity.builder()
                .idTipoUva(tipoUva.getIdTipoUva())
                .descripcion(tipoUva.getDescripcion())
                .nombre(tipoUva.getNombre())
                .build();
    }


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
                .fechaActualizacion(toLocalDate(vinoEntity.getFechaActualizacion()))
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
                .ultimaActualizacion(toLocalDate(bodegaEntity.getUltimaActualizacion()))
                .build();

        // aca le pasamos el self/this para que se mantenga la relacion
        // no se si hacia falta igual
        List<Vino> listaVinos = new ArrayList<>();
        //System.out.println(bodegaEntity.getVinoEntities());
        bodegaEntity.getVinoEntities().stream().map(x -> fromEntity(x, bodega)).forEach(listaVinos::add);

        bodega.setVinos(listaVinos);
        //System.out.println(bodega.getVinos().getClass());

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
                .fechaInicio(toLocalDate(siguiendoEntity.getFechaInicio()))
                .fechaFin(toLocalDate(siguiendoEntity.getFechaFin()))
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
                    if (enofilo.getSiguiendos() == null) enofilo.setSiguiendos(new ArrayList<>());
                    enofilo.getSiguiendos().add(siguiendo.get());
                }
            }
        }

        return enofiloHashMap.values().stream().toList();

    }


}





// Java es el mejor lenguaje del mundo