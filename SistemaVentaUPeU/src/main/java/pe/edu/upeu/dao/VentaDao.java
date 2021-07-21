package pe.edu.upeu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.modelo.VentaDetalleTO;
import pe.edu.upeu.modelo.VentaTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;

public class VentaDao extends AppCrud {
    LeerArchivo lea;
    ProductoTO prodTO;
    VentaTO venTO;
    VentaDetalleTO vdTO;
    LeerTeclado lt = new LeerTeclado();
    UtilsX ut = new UtilsX();

    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public void ventaGeneral() {
        AnsiConsole.systemUninstall();
        String venta = "SI";
        VentaTO vent = registroVenta();
        double preciototal=0;
        do {
            ut.clearConsole();
            VentaDetalleTO vt = registroDetalleVenta(vent);
            preciototal = preciototal+vt.getPrecioTotal();
            mostrarProductos();
            venta = lt.leer("", "¿Desea algo mas (SI = S / NO = N)? : ").toUpperCase();
        }while (venta.charAt(0)=='S' || venta.charAt(0)=='S'+'I');
        vent.setPrecioTotal(preciototal);
        vent.setNetoTotal((Math.round((vent.getPrecioTotal()/1.18)*100.0)/100.0));
        vent.setIgv((Math.round((vent.getNetoTotal()*0.18)*100.0)/100.0));
        lea = new LeerArchivo("Venta.txt");
        System.out.println("");
        editarRegistro(lea, 0, vent.getIdVenta(), vent);
        System.out.println("");
        ut.pintarLine('H',43);
        ut.pintarTextHeadBody('H', 9, (ansi().render("@|yellow Precio Neto:|@ S/.").toString())+vent.getNetoTotal());
        ut.pintarTextHeadBody('H', 7, (ansi().render("@|yellow IGV:|@ S/.").toString())+vent.getIgv());
        ut.pintarTextHeadBody('H', 9, (ansi().render("@|yellow Total a pagar:|@ S/.").toString())+vent.getPrecioTotal());
        System.out.println("");
        ut.pintarLine('H',43);
    }

    public VentaTO registroVenta() {
        lea =new LeerArchivo("Venta.txt");
        venTO = new VentaTO();
        venTO.setIdVenta(generarId(lea, 0, "V", 1));
        System.out.println("");
        ut.pintarLine('H', 40);
        System.out.println(ansi().render("@|green ||@\t\t@|yellow INGRESE LOS DATOS CORRESPONDIENTES "+
        "PARA LA VENTA|@\t\t@|green ||@"));
        ut.pintarLine('H', 40);
        venTO.setDniCliente(lt.leer("", "Ingrese el DNI del cliente: "));
        System.out.println("");
        venTO.setFechaVenta(formato.format(new Date()));
        venTO.setIgv(0.0);
        venTO.setNetoTotal(0.0);
        venTO.setPrecioTotal(0.0);
        lea = new LeerArchivo("Venta.txt");
        agregarContenido(lea, venTO);
        return venTO;
    }

    public VentaDetalleTO registroDetalleVenta(VentaTO vTO) {
        mostrarProductos();
        vdTO = new VentaDetalleTO();
        lea = new LeerArchivo("VentaDetalle.txt");
        vdTO.setIdVentaDetalle(generarId(lea, 0, "VD", 2));
        vdTO.setIdProducto(lt.leer("", "Ingrese ID producto: ").toUpperCase());
        vdTO.setIdVenta(vTO.getIdVenta());
        LeerArchivo lar = new LeerArchivo("Producto.txt");
        Object[][] dataProd = buscarContenido(lar, 0, vdTO.getIdProducto());
        double porcentUt = Double.parseDouble(String.valueOf(dataProd[0][5]));
        double precioUnit = Double.parseDouble(String.valueOf(dataProd[0][4]));
        vdTO.setPorceUtil(Double.parseDouble(String.valueOf(dataProd[0][5])));
        vdTO.setPorceUtil(porcentUt);
        vdTO.setCantidad(lt.leer(0.0, "Ingrese Cantidad: "));
        System.out.println("");
        vdTO.setPrecioUnit((Math.round((precioUnit+precioUnit*porcentUt)*100.0)/100.0));
        vdTO.setPrecioTotal((Math.round((vdTO.getCantidad()*vdTO.getPrecioUnit())*100.0)/100.0));
        lea = new LeerArchivo("VentaDetalle.txt");
        agregarContenido(lea, vdTO);
        actualizarStockVenta(dataProd, vdTO.getCantidad());
        ut.clearConsole();
        return vdTO;
    }

    public void mostrarProductos() {
        System.out.println("");
        ut.pintarLine('H', 27);
        ut.pintarLine1('V', 0);
        System.out.print(ansi().render("@|yellow \t  PRODUCTOS DISPONIBLES PARA LA VENTA|@\t      "));
        ut.pintarLine('V', 0);
        ut.pintarLine('H', 27);
        lea = new LeerArchivo("Producto.txt");
        Object[][] data = listarContenido(lea);
        String dataX = "";
        for (int i = 0; i < data.length; i++) {
            if (Double.parseDouble(String.valueOf(data[i][6]))>0) {
                dataX += ""+data[i][0]+" = "+data[i][1];
                dataX += ","+"Precio: "+data[i][4];
                dataX += ","+"Stock: "+data[i][6];
            }
            ut.pintarTextHeadBody('B', 4, dataX);
            dataX = "";
        }
        ut.pintarLine('H', 27);
        System.out.println("");
    }

    public void reporteVentasRangoFecha(){
        System.out.println("");
        ut.pintarLine('H', 36);
        System.out.println(ansi().render("@|green ||@\t\t\t@|yellow REPORTE DE VENTAS POR FECHA|@ "+
        "\t\t\t@|green ||@"));
        ut.pintarLine('H', 36);
        String fechaIni = lt.leer("", "Ingrese la fecha de inicio (dd-MM-yyyy): ");
        String fechaFin = lt.leer("", "Ingrese fecha final (dd-MM-yyyy): ");
        lea = new LeerArchivo("Venta.txt");
        Object[][] dataV = listarContenido(lea);
        int contarVentasRF = 0;
        try {
            for (int i = 0; i < dataV.length; i++){
                String[] fechaVenta = String.valueOf(dataV[i][2]).split(" ");
                Date fechaVentaX = formatoFecha.parse(fechaVenta[0]);
                if ((fechaVentaX.after(formatoFecha.parse(fechaIni)) || fechaVenta[0].equals(fechaIni)) && 
                (fechaVentaX.before(formatoFecha.parse(fechaFin)) || fechaVenta[0].equals(fechaFin))) {
                    contarVentasRF = contarVentasRF+1;
                }
            }
            Object[][] dataVRF = new Object[contarVentasRF][dataV[0].length];
            int filaIndice = 0;
            double netoTotalX=0, igvX=0, precioTotalX=0;
            for (int i = 0; i < dataV.length; i++){
                String[] fechaVenta = String.valueOf(dataV[i][2]).split(" ");
                Date fechaVentaX = formatoFecha.parse(fechaVenta[0]);
                if ((fechaVentaX.after(formatoFecha.parse(fechaIni)) || fechaVenta[0].equals(fechaIni)) && 
                (fechaVentaX.before(formatoFecha.parse(fechaFin)) || fechaVenta[0].equals(fechaFin))) {
                    for (int j = 0; j < dataV[0].length; j++) {
                        dataVRF[filaIndice][j] = dataV[i][j];
                        if (j==3) {netoTotalX += Double.parseDouble(String.valueOf(dataV[i][j]));}
                        if (j==4) {igvX += Double.parseDouble(String.valueOf(dataV[i][j]));}
                        if (j==5) {precioTotalX += Double.parseDouble(String.valueOf(dataV[i][j]));}
                    }
                    filaIndice++;
                }
            }
        ut.clearConsole1();
        ut.pintarLine1('V',0);ut.pintarLine1('H',12);
        System.out.print("  REPORTE DE VENTAS POR FECHA  ");
        ut.pintarLine1('H',13);ut.pintarLine('V',0);
        ut.pintarLine1('V',0);ut.pintarLine1('H',12);
        System.out.print(" Entre "+fechaIni+" a "+fechaFin+" ");
        ut.pintarLine1('H',13);ut.pintarLine('V',0);
        ut.pintarLine('H',42);
        ut.pintarTextHeadBody1('H', 3, "ID,DNI Cliente,F.Venta,Imp.Neto,IGV,Pre.Total");
        System.out.println("");
        ut.pintarLine('H',42);
        for (Object[] objects : dataVRF) {
            String dataCadena = ""+objects[0]+","+objects[1]+","+objects[2]+","+objects[3]+","+objects[4]+","+objects[5];
            ut.pintarTextHeadBody('B', 3, dataCadena);
        }
        ut.pintarLine('H',42);
        System.out.println("");
        ut.pintarLine('H',17);
        ut.pintarTextHeadBody('H', 10, (ansi().render("@|yellow Total Neto de ventas:|@ S/.").toString())+(Math.round(netoTotalX)));
        System.out.println("");ut.pintarLine('H',17);
        ut.pintarTextHeadBody('H', 10, (ansi().render("@|yellow IGV a pagar:|@ S/.").toString())+Math.round(igvX));
        System.out.println("");ut.pintarLine('H',17);
        ut.pintarTextHeadBody('H', 10, (ansi().render("@|yellow Monto recaudado:|@ S/.").toString())+Math.round(precioTotalX));
        System.out.println("");
        ut.pintarLine('H',17);
        } catch (Exception e) {
        }
    }

    public void actualizarStockVenta(Object[][] data, double cant) {
        lea = new LeerArchivo("Producto.txt");
        ProductoTO pdt = new ProductoTO();
        pdt.setIdProducto(data[0][0].toString());
        pdt.setStock(Double.parseDouble(data[0][6].toString())-cant);
        editarRegistro(lea, 0, pdt.getIdProducto(), pdt);
    }

    public void modificarProducto() {
        lea = new LeerArchivo("Producto.txt");
        mostrarProductos2();
        String idProd = lt.leer("","Ingrese el ID del Producto: ").toUpperCase();
        ProductoTO prodTO = new ProductoTO();
        Object[][] proT = buscarContenido(lea, 0, idProd);
        prodTO.setStock(lt.leer(0.0, "Ingrese la cantidad: "));
        sumarStock(proT, prodTO.getStock());
        ut.clearConsole();
        mostrarProductos2();
    }

    public void sumarStock(Object[][] data, double cant) {
        lea = new LeerArchivo("Producto.txt");
        ProductoTO pdt = new ProductoTO();
        pdt.setIdProducto(data[0][0].toString());
        pdt.setStock(Double.parseDouble(data[0][6].toString())+cant);
        editarRegistro(lea, 0, pdt.getIdProducto(), pdt);
    }

    public void mostrarProductos2() {
        System.out.println("");
        ut.pintarLine('H', 27);
        ut.pintarLine1('V', 0);
        System.out.print(ansi().render("@|yellow \t      MODIFICAR STOCK"+
        " DE PRODUCTO|@\t      "));
        ut.pintarLine('V', 0);
        ut.pintarLine('H', 27);
        lea = new LeerArchivo("Producto.txt");
        Object[][] data = listarContenido(lea);
        String dataX="";
        for (int i = 0; i < data.length; i++) {
            if (Double.parseDouble(String.valueOf(data[i][6]))>0) {
                dataX += ""+data[i][0]+" = "+data[i][1];
                dataX += ","+"Precio: "+data[i][4];
                dataX += ","+"Stock: "+data[i][6];
            }
            ut.pintarTextHeadBody('B', 4, dataX);
            dataX = "";
        }
        ut.pintarLine('H', 27);
        System.out.println("");
    }

}
