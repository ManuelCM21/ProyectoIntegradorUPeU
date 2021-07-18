package pe.edu.upeu.modelo;

import lombok.Data;

@Data
public class ProductoTO {
    public String idProducto, nombre, unidadMed, idCateg;
    public double precioUnit, porceUtil, stock;
}
