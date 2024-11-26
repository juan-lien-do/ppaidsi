package dsi.utn.ppai.notificacion;
public class ImprimirDatosAPantalla {
    public static void imprimir(String mensaje){
        int largo = 68;
        int renglones = mensaje.length() / largo + 1;
        System.out.println("\n");
        System.out.println("----------------------------------------------------------------------------------");
        for ( int i = 0; i < renglones; i++) {
            String contenidoRenglon = mensaje.substring(i * largo ,
                    Math.min((i + 1) * largo, mensaje.length()));
            System.out.println("||" + contenidoRenglon + "||");
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("\n");

    }
}
