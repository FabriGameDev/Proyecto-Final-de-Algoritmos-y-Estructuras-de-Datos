/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

public class ProductoCambio {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String tipo;
    private String adminUsername;
    private String categoriaId;
    private String promocionId;
    private double porcentaje_descuento;

    public ProductoCambio(String id, String nombre, String descripcion, double precio, int stock, String tipo, String adminUsername, String categoriaId,String promocionId, double porcentaje_descuento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
        this.adminUsername = adminUsername;
        this.categoriaId = categoriaId;
        this.promocionId=promocionId;
        this.porcentaje_descuento=porcentaje_descuento;
    }

    // Métodos getters y setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public String getTipo() { return tipo; }
    public String getAdminUsername() { return adminUsername; }
    public String getCategoriaId() { return categoriaId; }
    public String getPromocionId() { return promocionId; }
    public double getPorcentaje_descuento() { return porcentaje_descuento; }
}

