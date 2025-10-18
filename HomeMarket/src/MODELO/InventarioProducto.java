/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

public class InventarioProducto {
    private Producto producto;  // Producto relacionado
    private InventarioProducto siguiente;  // Referencia al siguiente producto en la lista
    
    // Constructor
    public InventarioProducto(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public InventarioProducto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(InventarioProducto siguiente) {
        this.siguiente = siguiente;
    }
}

