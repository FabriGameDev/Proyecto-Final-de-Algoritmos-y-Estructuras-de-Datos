/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.util.LinkedList;
import java.util.Queue;

public class ColaEmpleado {
    private Queue<Empleado> cola;

    public ColaEmpleado() {
        this.cola = new LinkedList<>();
    }

    // Método para añadir empleados a la cola
    public void añadirACola(Empleado empleado) {
        cola.offer(empleado);
    }

    // Método para restablecer el primer empleado de la cola
    public Empleado restablecerDeCola() {
        return cola.poll();
    }

    // Obtener la lista de empleados en la cola
    public Queue<Empleado> obtenerCola() {
        return cola;
    }
}

