/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Venta {
    private int idVenta;
    private String nombreEmpleado;
    private int idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private double precioProducto;
    private String tipoProducto;
    private String categoriaProducto;
    private double descuento;
    private int cantidad;
    private double subtotal; // Nuevo atributo para el precio con descuento
    private double precioTotal; // Precio total de todos los productos
    private Date fechaVenta;
    
    // Constructor con parámetros
    public Venta(int idVenta, String nombreEmpleado, int idProducto, String nombreProducto,
                 String descripcionProducto, double precioProducto, String tipoProducto,
                 String categoriaProducto, double descuento, int cantidad, double subtotal, 
                 double precioTotal, Date fechaVenta) {
        
        this.idVenta = idVenta;
        this.nombreEmpleado = nombreEmpleado;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.tipoProducto = tipoProducto;
        this.categoriaProducto = categoriaProducto;
        this.descuento = descuento;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.precioTotal = precioTotal;
        this.fechaVenta = fechaVenta;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    // Método para calcular el subtotal (precio con descuento)
    public void calcularSubtotal() {
        this.subtotal = (precioProducto - (precioProducto * (descuento / 100))) * cantidad;
    }

    // Método para calcular el precio total sumando subtotales
    public void calcularPrecioTotal(double precioTotalActual) {
        this.precioTotal = precioTotalActual + this.subtotal;
    }
    
    // Método para agregar una venta a la base de datos
    public static void insertarVenta(Connection conexion, Venta venta) throws SQLException{
        String sql = "INSERT INTO Venta (idVenta, nombreEmpleado, idProducto, nombreProducto, descripProdcuto, precioProducto, tipoProducto,  categProducto, descuentoProducto, cantidadProducto, subtotalProducto, preciototalProducto, fechaventa) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            // Asignación de los valores a los parámetros de la consulta
            ps.setInt(1, venta.getIdVenta());
            ps.setString(2, venta.getNombreEmpleado());
            ps.setInt(3, venta.getIdProducto());
            ps.setString(4, venta.getNombreProducto());
            ps.setString(5, venta.getDescripcionProducto());
            ps.setDouble(6, venta.getPrecioProducto());
            ps.setString(7, venta.getTipoProducto());
            ps.setString(8, venta.getCategoriaProducto());
            ps.setDouble(9, venta.getDescuento());
            ps.setInt(10, venta.getCantidad());
            ps.setDouble(11, venta.getSubtotal());
            ps.setDouble(12, venta.getPrecioTotal());
            ps.setDate(13, new java.sql.Date(venta.getFechaVenta().getTime()));
            
            ps.executeUpdate();
 }
    }
}