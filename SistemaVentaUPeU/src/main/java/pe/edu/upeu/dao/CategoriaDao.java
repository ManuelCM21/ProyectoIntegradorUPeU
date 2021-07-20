package pe.edu.upeu.dao;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.CategoriaTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;

public class CategoriaDao extends AppCrud {
    LeerArchivo lar;
    CategoriaTO catTO;
    LeerTeclado lt = new LeerTeclado();
    UtilsX ut = new UtilsX();

    public Object [][] crearCategoria() {
        catTO = new CategoriaTO();
        ut.pintarLine('H', 36);
        ut.pintarLine1('V', 0);
        System.out.print(ansi().render("@|yellow \t\t\t REGISTRO DE NUEVA CATEGORIA |@\t\t\t"));
        ut.pintarLine('V', 0);ut.pintarLine('H', 36);
        lar = new LeerArchivo("Categoria.txt");
        catTO.setIdCateg(generarId(lar, 0, "C", 1));
        catTO.setNombre(lt.leer("", "Ingrese nombre de la categoria: ").toUpperCase());
        return agregarContenido(lar,catTO);
    }

    public void reporteCategoria() {
        AnsiConsole.systemUninstall();
        ut.pintarLine('H',14);
        System.out.println(ansi().render("@|green |---|@ REPORTE DE CATEGORIA @|green --||@"));
        lar = new LeerArchivo("Categoria.txt");
        Object[][] data =listarContenido(lar);
        String dataX="";
        ut.pintarLine('H', 14);
        ut.pintarTextHeadBody1('H', 3, "    ID,NOMBRE");
        System.out.println("");
        ut.pintarLine('H', 14);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j==0) {
                    dataX +="    "+data[i][j];
                }else {
                    dataX +=","+data[i][j];
                }
            }
            ut.pintarTextHeadBody('B', 3, dataX);
            dataX = "";
        }
        ut.pintarLine('H',14);
    }

}
