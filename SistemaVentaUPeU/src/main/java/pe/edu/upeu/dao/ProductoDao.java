package pe.edu.upeu.dao;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.CategoriaTO;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class ProductoDao extends AppCrud{
    LeerArchivo lea;
    ProductoTO prodTO;
    CategoriaTO catTO;
    LeerTeclado lt= new LeerTeclado();
    UtilsX ut = new UtilsX();

    public void crearProducto() {
        prodTO = new ProductoTO();
        AnsiConsole.systemUninstall();
        System.out.println("");
        ut.pintarLine('H', 28);
        System.out.print(ansi().render("@|green ||@"));
        System.out.print(ansi().fg(YELLOW).a("\t\t CREANDO UN NUEVO PRODUCTO \t\t").reset());
        System.out.println(ansi().render("@|green ||@"));
        ut.pintarLine('H', 28);
        prodTO.setNombre(lt.leer("", "Ingrese nombre del producto: "));
        mostrarCategoria();
        prodTO.setIdCateg(lt.leer("", "\nElige el ID de la Categoria: ").toUpperCase());
        lea = new LeerArchivo("Producto.txt");
        prodTO.setIdProducto(generarId(lea, 0, "P", 1));
        prodTO.setUnidadMed(lt.leer("", "Ingrese unidad de Medida: "));
        prodTO.setPrecioUnit(lt.leer(0.0, "Ingrese el precio unitario: "));
        prodTO.setPorceUtil(lt.leer(0.0, "Ingrese porcentaje de utilidad: "));
        prodTO.setStock(lt.leer(0.0, "Ingrese el stock: "));
        lea = new LeerArchivo("Producto.txt");
        System.out.println("");
        agregarContenido(lea, prodTO);
    }

    public void reportarProducto() {
        AnsiConsole.systemUninstall();
        System.out.println("");
        ut.pintarLine('H', 49);
        System.out.print(ansi().render("@|green ||@"));
        ut.pintarLine1('H', 19);
        System.out.print(" REPORTE DE PRODUCTO ");
        ut.pintarLine1('H', 18);
        System.out.println(ansi().render("@|green ||@"));
        lea = new LeerArchivo("Producto.txt");
        Object[][] data = listarContenido(lea);
        String dataX="";
        ut.pintarLine('H', 49);
        ut.pintarTextHeadBody1('H', 3, "ID,NOMBRE,U.MEDIDA,ID CATEG,P.UNITARIO,P.UTIL,STOCK");
        System.out.println("");
        ut.pintarLine('H', 49);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j==0) {
                    dataX +=""+data[i][j];
                }else {
                    dataX +=","+data[i][j];
                }
            }
            ut.pintarTextHeadBody('B', 3, dataX);
            dataX = "";
        }
        ut.pintarLine('H', 49);
    }

    public void mostrarCategoria() {
        lea = new LeerArchivo("Categoria.txt");
        Object[][] data = listarContenido(lea);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i][0]+" = "+data[i][1]+", ");
        }
        System.out.println("");
    }
}
