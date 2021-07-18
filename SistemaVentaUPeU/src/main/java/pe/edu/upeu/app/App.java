package pe.edu.upeu.app;

import java.io.Console;

import pe.edu.upeu.dao.CategoriaDao;
import pe.edu.upeu.dao.ProductoDao;
import pe.edu.upeu.dao.UsuarioDao;
import pe.edu.upeu.dao.VentaDao;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;

public class App {
    
    public static void menuMain(){
        String mensaje="Seleccion el algoritmo que desea ejecutar\n"+
        "\n\t1 = Reportar Categoria"+
        "\t\t\t5 = Registrar Categoria\n"+
        "\t2 = Reportar Producto"+
        "\t\t\t6 = Registrar Producto\n"+
        "\t3 = Reportar Ventas en un rango"+
        "\t\t7 = Registrar Usuario\n"+
        "\t4 = Realizar Venta"+
        "\t\t\t0 = Salir del programa : ";
        LeerTeclado lt = new LeerTeclado();
        CategoriaDao dao = new CategoriaDao();
        UtilsX ut = new UtilsX();
        UsuarioDao daoUso = new UsuarioDao();
        VentaDao venDO = new VentaDao();
        ProductoDao daoPro = new ProductoDao();
        int opcion=0;
        opcion=lt.leer(0, mensaje);
        do{
            switch(opcion){
                case 1: ut.clearConsole();dao.reporteCategoria(); break;
                case 2: ut.clearConsole();daoPro.reportarProducto(); break;
                case 3: ut.clearConsole();venDO.reporteVentasRangoFecha();break;
                case 4: ut.clearConsole();venDO.ventaGeneral();break;
                case 5: ut.clearConsole();dao.crearCategoria();break;
                case 6: ut.clearConsole();daoPro.crearProducto(); break;
                case 7: ut.clearConsole();daoUso.crearNuevoUsuario();break;
                default: ut.clearConsole();
                System.out.println("La opcion que eligio no existe!"); break;
            }
            if(opcion!=0){
            System.out.println("\n¿Desea seguir probando?");
            opcion=lt.leer(0, mensaje);}
        }while(opcion!=0); 
        if (opcion==0) {
            ut.clearConsole();
        }    
    }

    public static void validarAcceso() {
        AnsiConsole.systemUninstall();
        LeerTeclado lt = new LeerTeclado();
        Console cons = System.console();
        UtilsX ut = new UtilsX();
        ut.pintarLine('H', 48);
        System.out.println(ansi().render("@|green ||@\t\t\t@|yellow INGRESE SU USUARIO "+
        "Y CLAVE PARA ACCEDER AL SISTEMA|@\t\t\t@|green ||@"));
        ut.pintarLine('H', 48);
        String usuario = (lt.leer("", "INGRESE SU USUARIO: ")).toUpperCase();
        System.out.print("INGRESE SU CLAVE: ");
        char[] clave = cons.readPassword();
        System.out.println("");
        UsuarioDao usuDao = new UsuarioDao();
        if (usuDao.login(usuario, clave)) {
            ut.clearConsole();
            menuMain();
        }else {
            ut.clearConsole();
            System.out.println("Intente nuevamente...");
            validarAcceso();
        }
    }

    public static void main(String[] args) {
        validarAcceso();
        //menuMain();
    }
}
