package pe.edu.upeu.dao;

import java.io.Console;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.UsuarioTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;

public class UsuarioDao extends AppCrud{
    LeerTeclado lt = new LeerTeclado();
    UtilsX ut = new UtilsX();

    LeerArchivo lea;
    UsuarioTO usTo;

    public void crearNuevoUsuario() {
        AnsiConsole.systemUninstall();
        usTo = new UsuarioTO();
        lea = new LeerArchivo("Usuario.txt");
        ut.pintarLine('H', 32);
        ut.pintarLine1('V', 0);
        System.out.print(ansi().render("@|yellow \t\t\tCREANDO NUEVO USUARIO |@\t\t\t"));
        ut.pintarLine('V', 0);
        ut.pintarLine('H', 32);
        String user = lt.leer("", "Ingrese un Usuario: ").toUpperCase();
        if (validarExistUser(user)) {
            usTo.setUsuario(user);
            usTo.setIdUsuario(generarId(lea, 0, "U", 1));
            usTo.setPerfil(lt.leer("", "Ingrese el Perfil del Usuario (ADMIN, VENDEDOR): ").toUpperCase());
            Console cons = System.console();
            System.out.print("Ingrese Clave: ");
            char[] clave = cons.readPassword();
            usTo.setClave(String.valueOf(clave));
            agregarContenido(lea, usTo);
        } else {
            System.out.print("Elige otro Usuario: ");
            crearNuevoUsuario();
        }
    }

    public boolean login(String usuario, char[] clave) {
        lea = new LeerArchivo("Usuario.txt");
        Object[][] data = buscarContenido(lea, 1, usuario);
        if (data.length == 1 && data[0][2].equals(String.valueOf(clave))) {
            return true;
        }
        return false;
    }

    public boolean validarExistUser(String user) {
        lea=new LeerArchivo("Usuario.txt");
       Object[][] data=buscarContenido(lea, 1, user);  
       if(data!=null && data.length>0){
        System.out.println("-----ya existe otro usuario con ese nombre-----");
        return false;
       }
       return true;
    }
}
