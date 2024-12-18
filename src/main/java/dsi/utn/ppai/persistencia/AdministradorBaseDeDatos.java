package dsi.utn.ppai.persistencia;

import dsi.utn.ppai.persistencia.entidades.BodegaEntity;
import dsi.utn.ppai.persistencia.entidades.MaridajeEntity;
import dsi.utn.ppai.inicio.FXMain;
import dsi.utn.ppai.persistencia.mappers.MapperPersistencia;
import dsi.utn.ppai.modelo.*;
import dsi.utn.ppai.persistencia.repositorios.*;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga de persistir las clases del modelo.
 * <p>Conoce a los repositorios y les delega la implementación de la persistencia.</p>
 * Conseguir instancia: <pre>{@code AdministradorBaseDeDatos.getInstance();}</pre>
 */
public class AdministradorBaseDeDatos {
    private BodegaRepository bodegaRepository;
    private EnofiloRepository enofiloRepository;
    private MaridajeRepository maridajeRepository;
    private SiguiendoRepository siguiendoRepository;
    private TiposUvaRepository tiposUvaRepository;
    private UsuariosRepository usuariosRepository;
    private VarietalesRepository varietalesRepository;
    private VinoRepository vinoRepository;

    private List<Bodega> bodegas = new ArrayList<>();
    private List<Vino> vinos = new ArrayList<>();
    private List<Maridaje> maridajes = new ArrayList<>();
    private List<TipoUva> tiposUva = new ArrayList<>();
    private List<Enofilo> enofilos = new ArrayList<>();
    private static AdministradorBaseDeDatos falsaBaseDeDatos;

    public static AdministradorBaseDeDatos getInstance() {
        if (falsaBaseDeDatos == null) {
            falsaBaseDeDatos = new AdministradorBaseDeDatos();
        }
        return falsaBaseDeDatos;
    }

    // TODO: PONER PERSISTENCIA AL GUARDAR LOS VINOS NUEVOS, LOS VINOS VIEJOS Y LA FECHA DE CONSULTA DE LA BODEGA
    public void persistirCambiosBodegaYVinos(Bodega bodega){
        // buscar entidades extra para poder construir las entidades de nuevo
        List<MaridajeEntity> maridajeEntities = maridajeRepository.findAll();
        BodegaEntity bodegaEntity = MapperPersistencia.fromModel(bodega, maridajeEntities);
        bodegaRepository.save(bodegaEntity);


        maridajeRepository.saveAll(maridajeEntities);

    }

    private AdministradorBaseDeDatos() {
        ApplicationContext context = FXMain.getApplicationContext();
        this.bodegaRepository = context.getBean(BodegaRepository.class);
        this.maridajeRepository = context.getBean(MaridajeRepository.class);
        this.enofiloRepository = context.getBean(EnofiloRepository.class);
        this.tiposUvaRepository = context.getBean(TiposUvaRepository.class);


        //System.out.println(bodegaRepository.findAll());

        // inicializarConDatosMock();
    }


    public List<Bodega> getBodegas() {
        return bodegaRepository.findAll().stream().map(MapperPersistencia::fromEntity).toList();
    }

    /*public List<Vino> getVinos() {
        return vinoRepository.findAll().stream().map(MapperPersistencia::fromEntity).toList();
    }*/

    public List<Maridaje> getMaridajes() {
        return maridajeRepository.findAll().stream().map(MapperPersistencia::fromEntity).toList();
    }

    public List<TipoUva> getTiposUva() {
        return tiposUvaRepository.findAll().stream().map(MapperPersistencia::fromEntity).toList();
    }

    public List<Enofilo> getEnofilos() {
        return MapperPersistencia.fromEntities(enofiloRepository.findAll(), bodegaRepository.findAll());
    }

    private void inicializarConDatosMock(){
        List<TipoUva> tiposUvaXD = new ArrayList<>();
        tiposUvaXD.add(new TipoUva("La uva Malbec es una variedad de uva tinta que ha ganado popularidad en todo el mundo, especialmente en Argentina, aunque sus orígenes se encuentran en Francia",
                "Malbec"));
        tiposUvaXD.add(new TipoUva("Originaria de la región del Loira, Francia, es conocida por sus sabores a hierba, grosella espinosa, cítricos y, en algunos casos, notas minerales.",
                "Sauvignon Blanc"));
        tiposUvaXD.add(new TipoUva("En Italia, se llama Pinot Grigio y suele producir vinos ligeros y refrescantes con sabores a manzana y pera. En Alsacia, Francia, como Pinot Gris, puede ser más rico y especiado.",
                "Pinot Gris"));
        this.tiposUva = tiposUvaXD;

        List<Maridaje> maridajesXD = new ArrayList<>();
        maridajesXD.add(new Maridaje("Se recomienda el salame de Colonia Caroya.",
                "Salame"));
        maridajesXD.add(new Maridaje("Se recomienda el queso de Colonia Caroya.",
                "Queso"));
        maridajesXD.add(new Maridaje("Se recomienda pan casero",
                "Pan"));
        this.maridajes = maridajesXD;

        List<Usuario> usuariosXD = new ArrayList<>();
        usuariosXD.add(new Usuario("pepito123", "pepe_ama_el_vino", true));
        usuariosXD.add(new Usuario("calisteni213aPopular", "vino_con_soda", false));
        usuariosXD.add(new Usuario("incorrecta2024_", "tina_patagonia", false));

        List<Bodega> bodegasXD = new ArrayList<>();
        bodegasXD.add(new Bodega(new float[]{-23, 24}, "bodegagenial.com/api/", "Es una bodega muy feliz", "Inicia en 1789",
                "BodegaGenial", 14, LocalDate.now().minusDays(20)));
        bodegasXD.add(new Bodega(new float[]{10, 20}, "bodegamar.com/api/", "Bodega con vista al mar", "Fundada en 1850",
                "BodegaMar", 14, LocalDate.now().minusDays(18)));
        bodegasXD.add(new Bodega(new float[]{35, -45}, "bodegamontana.com/api/", "Bodega en las montañas", "Establecida en 1900",
                "BodegaMontaña", 14, LocalDate.now().minusDays(10)));

        Vino vino1 = new Vino(1997, LocalDate.now().minusDays(60),
                "Suspiro de Otoño", "El vino está muy rico", 5000,
                List.of(new Varietal(null, 87, tiposUva.get(1))), null);
        Vino vino2 =new Vino(2001, LocalDate.now().minusDays(90),
                "Vino de Primavera", "Notas florales y frescas", 4500,
                List.of(new Varietal(null, 90, tiposUva.get(0))), null);
        Vino vino3 = new Vino(2005, LocalDate.now().minusDays(150),
                "Invierno Agradable", "Aroma a madera y vainilla", 6000,
                List.of(new Varietal(null, 88, tiposUva.get(1))), null);
        Vino vino4 = new Vino(2010, LocalDate.now().minusDays(180),
                "Rocío Matinal", "Sabor afrutado y ligero", 5000,
                List.of(new Varietal(null, 80, tiposUva.get(0))), null);
        Vino vino5 = new Vino(2012, LocalDate.now().minusDays(210),
                "Noche Estrellada", "Buena acidez y taninos suaves", 5200,
                List.of(new Varietal(null, 92, tiposUva.get(2))), null);
        Vino vino6 = new Vino(2015, LocalDate.now().minusDays(240),
                "Ocaso Dorado", "Final largo y complejo", 5700,
                List.of(new Varietal(null, 89, tiposUva.get(1))), null);
        Vino vino7 = new Vino(2003, LocalDate.now().minusDays(120),
                "Verano Ardiente", "Cuerpo robusto y especiado", 5500,
                List.of(new Varietal(null, 85, tiposUva.get(2))), null);
        Vino vino8 = new Vino(2018, LocalDate.now().minusDays(270),
                "Aurora Boreal", "Equilibrado y elegante", 5800,
                List.of(new Varietal(null, 86, tiposUva.get(0))), null);
        Vino vino9 = new Vino(2020, LocalDate.now().minusDays(300),
                "Crepúsculo Sereno", "Textura aterciopelada y redonda", 5900,
                List.of(new Varietal(null, 91, tiposUva.get(2))), null);

        vino1.setBodega(bodegasXD.get(1));  bodegasXD.get(1).agregarVino(vino1);
        vino7.setBodega(bodegasXD.get(1));  bodegasXD.get(1).agregarVino(vino7);
        vino4.setBodega(bodegasXD.get(1));  bodegasXD.get(1).agregarVino(vino4);
        vino2.setBodega(bodegasXD.get(2));  bodegasXD.get(2).agregarVino(vino2);
        vino5.setBodega(bodegasXD.get(2));  bodegasXD.get(2).agregarVino(vino5);
        vino8.setBodega(bodegasXD.get(2));  bodegasXD.get(2).agregarVino(vino8);
        vino3.setBodega(bodegasXD.get(0));  bodegasXD.get(0).agregarVino(vino3);
        vino6.setBodega(bodegasXD.get(0));  bodegasXD.get(0).agregarVino(vino6);
        vino9.setBodega(bodegasXD.get(0));  bodegasXD.get(0).agregarVino(vino9);

        this.bodegas = bodegasXD;
        this.vinos = new ArrayList<>();
        this.vinos.add(vino1);
        this.vinos.add(vino2);
        this.vinos.add(vino3);
        this.vinos.add(vino4);
        this.vinos.add(vino5);
        this.vinos.add(vino6);
        this.vinos.add(vino7);
        this.vinos.add(vino8);
        this.vinos.add(vino9);

        List<Enofilo> enofilosXD = new ArrayList<>();
        enofilosXD.add(new Enofilo("Spinetta", "Luis Alberto", usuariosXD.get(0)));
        enofilosXD.add(new Enofilo("Cavani", "Edinson", usuariosXD.get(1)));
        enofilosXD.add(new Enofilo("Stoessel", "Martina", usuariosXD.get(2)));

        enofilosXD.get(0).agregarSiguiendoBodega(bodegas.get(0));
        enofilosXD.get(0).agregarSiguiendoBodega(bodegas.get(2));
        enofilosXD.get(1).agregarSiguiendoBodega(bodegas.get(1));
        enofilosXD.get(1).agregarSiguiendoBodega(bodegas.get(2));
        enofilosXD.get(2).agregarSiguiendoBodega(bodegas.get(1));
        enofilosXD.get(2).agregarSiguiendoBodega(bodegas.get(0));

        enofilosXD.get(0).agregarSiguiendoEnofilo(enofilosXD.get(2));
        enofilosXD.get(2).agregarSiguiendoEnofilo(enofilosXD.get(1));
        enofilosXD.get(1).agregarSiguiendoEnofilo(enofilosXD.get(0));

        this.enofilos = enofilosXD;
    }


}
