/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Empleado {

    private String id;
    private String nombre;
    private String puesto;
    private double salario;
    private Date fechaContratacion;
    private String adminUsername;

    private static Queue<Empleado> colaEmpleadosEliminados = new ArrayDeque<>();

    public Empleado(String id, String nombre, String puesto, double salario, Date fechaContratacion, String adminUsername) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.adminUsername = adminUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public static List<Empleado> obtenerTodos(Connection conexion) throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, puesto, salario, fechaContratacion, admin_username FROM Empleado";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String puesto = rs.getString("puesto");
                double salario = rs.getDouble("salario");
                Date fechaContratacion = rs.getDate("fechaContratacion");
                String adminUsername = rs.getString("admin_username");

                Empleado empleado = new Empleado(id, nombre, puesto, salario, fechaContratacion, adminUsername);
                empleados.add(empleado);
            }
        }

        return empleados;
    }

    public static void añadirACola(Empleado empleado) {
        colaEmpleadosEliminados.add(empleado);
    }

    public static Empleado recuperarDeCola() {
        return colaEmpleadosEliminados.poll();
    }

    public static List<Empleado> listarCola() {
        return new ArrayList<>(colaEmpleadosEliminados);
    }

    public boolean guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO Empleado (id, nombre, puesto, salario, fechaContratacion, admin_username) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, puesto);
            ps.setDouble(4, salario);
            ps.setDate(5, fechaContratacion);
            ps.setString(6, adminUsername);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Connection conexion) throws SQLException {
        String sql = "UPDATE Empleado SET nombre = ?, puesto = ?, salario = ?, fechaContratacion = ?, admin_username = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, puesto);
            ps.setDouble(3, salario);
            ps.setDate(4, fechaContratacion);
            ps.setString(5, adminUsername);
            ps.setString(6, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(Connection conexion) throws SQLException {
        añadirACola(this);

        String sql = "DELETE FROM Empleado WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public static void ordenarPorSalarioAscendente(List<Empleado> listaEmpleados) {
        listaEmpleados.sort((e1, e2) -> Double.compare(e1.getSalario(), e2.getSalario()));
    }

    public static void ordenarPorFechaContratacionDescendente(List<Empleado> listaEmpleados) {
        listaEmpleados.sort((e1, e2) -> e2.getFechaContratacion().compareTo(e1.getFechaContratacion()));
    }
}





