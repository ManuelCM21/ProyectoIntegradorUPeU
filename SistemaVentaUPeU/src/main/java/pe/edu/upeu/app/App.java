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
import static org.fusesource.jansi.Ansi.Color.*;

public class App {
    
    public static void menuAdmin(){
        String mensaje="Seleccione el algoritmo que desea ejecutar\n"+
        "\n\t\t1 = Reportar Categoria"+
        "\t\t\t6 = Registrar Categoria\n"+
        "\t\t2 = Reportar Producto"+
        "\t\t\t7 = Registrar Producto\n"+
        "\t\t3 = Reportar Ventas en un rango"+
        "\t\t8 = Registrar Usuario\n"+
        "\t\t4 = Realizar Venta"+
        "\t\t\t9 = Modificar Producto\n"+
        "\t\t5 = Cerrar Sesión"+
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
                case 5: ut.clearConsole();validarAcceso();break;
                case 6: ut.clearConsole();dao.crearCategoria();break;
                case 7: ut.clearConsole();daoPro.crearProducto(); break;
                case 8: ut.clearConsole();daoUso.crearNuevoUsuario();break;
                case 9: ut.clearConsole();venDO.modificarProducto();;break;
                default: ut.clearConsole();
                System.out.println(ansi().fg(RED).a("\n¡La opcion que eligio no existe!").reset()); break;
                case 0: ut.clearConsole();
            }
            if(opcion!=0){
            System.out.println("\n¿Desea seguir probando?...");
            opcion=lt.leer(0, mensaje);}
        }while(opcion!=0); 
    }

    public static void menuVendedor(){
        String mensajev="Seleccione el algoritmo que desea ejecutar\n"+
        "\n\t1 = Reportar Categoria\n"+
        "\t2 = Reportar Producto\n"+
        "\t3 = Reportar Ventas en un rango\n"+
        "\t4 = Realizar Venta\n"+
        "\t5 = Cerrar Sesión\n"+
        "\t0 = Salir del programa : ";
        LeerTeclado lt = new LeerTeclado();
        CategoriaDao dao = new CategoriaDao();
        UtilsX ut = new UtilsX();
        VentaDao venDO = new VentaDao();
        ProductoDao daoPro = new ProductoDao();
        int opcion = 0;
        opcion=lt.leer(0, mensajev);
        do{
            switch(opcion){
                case 1: ut.clearConsole();dao.reporteCategoria(); break;
                case 2: ut.clearConsole();daoPro.reportarProducto(); break;
                case 3: ut.clearConsole();venDO.reporteVentasRangoFecha();break;
                case 4: ut.clearConsole();venDO.ventaGeneral();break;
                case 5: ut.clearConsole();validarAcceso();break;
                default: ut.clearConsole();
                System.out.println(ansi().fg(RED).a("\n¡La opcion que eligio no existe!").reset()); break;
                case 0: ut.clearConsole();
            }
            if(opcion!=0){
            System.out.println("\n¿Desea seguir probando?");
            opcion=lt.leer(0, mensajev);}
        }while(opcion!=0);
    }

    public static void validarAcceso() {
        UtilsX ut = new UtilsX();
        ut.clearConsole();
        AnsiConsole.systemUninstall();
        LeerTeclado lt = new LeerTeclado();
        Console cons = System.console();
        System.out.println("");
        ut.pintarLine('H', 48);
        System.out.println(ansi().render("@|green ||@\t\t\t@|yellow INGRESE SU USUARIO "+
        "Y CLAVE PARA ACCEDER AL SISTEMA|@\t\t\t@|green ||@"));
        ut.pintarLine('H', 48);
        String usuario = (lt.leer("", "INGRESE SU USUARIO: ")).toUpperCase();
        System.out.print("INGRESE LA CONTRASEÑA: ");
        char[] clave = cons.readPassword();
        for (int i = 0; i < clave.length; i++){
            clave[i] = (char)(clave[i]+'*');
        }
        System.out.println("");
        UsuarioDao usuDao = new UsuarioDao();
        if (usuDao.login(usuario, clave)) {
            if (usuDao.acceso(usuario)){
                menuAdmin();   
            } else {
                menuVendedor();
            }
        }else {
            ut.clearConsole();
            validarAcceso();
        }
    }

    public static void main(String[] args) {
        validarAcceso();
        //menuAdmin();
    }
}
