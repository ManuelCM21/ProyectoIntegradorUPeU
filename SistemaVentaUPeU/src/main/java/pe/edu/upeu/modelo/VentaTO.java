package pe.edu.upeu.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class VentaTO {
    public String idVenta, dniCliente, fechaVenta;
    public double netoTotal, igv, precioTotal;
}
