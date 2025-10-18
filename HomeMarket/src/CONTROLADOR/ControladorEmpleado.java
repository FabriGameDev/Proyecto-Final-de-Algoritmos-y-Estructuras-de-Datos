/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Empleado;
import VISTA.VistaEmpleado;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ControladorEmpleado {
    private VistaEmpleado vista;
    private Connection conexion;
    private List<Empleado> listaEmpleados; // Lista para almacenar los empleados activos
    private Queue<Empleado> colaEliminados; // Cola para almacenar empleados eliminados

    public ControladorEmpleado(VistaEmpleado vista, Connection conexion) {
        this.vista = vista;
        this.conexion = conexion;
        this.listaEmpleados = new ArrayList<>(); // Inicializar lista de empleados
        this.colaEliminados = new ArrayDeque<>(); // Inicializar cola de empleados eliminados

        cargarEmpleados();

        // Listeners para los botones de la cola
        vista.addAñadirAColaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                añadirACola();
            }
        });

        vista.addRecuperarDeColaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarDeCola();
            }
        });

        vista.addListaColaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarListaCola();
            }
        });

        // Listeners para los demás botones
        vista.addAgregarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });

        vista.addModificarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEmpleado();
            }
        });

        vista.addEliminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
            }
        });

        vista.addBuscarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEmpleado();
            }
        });

        vista.addOrdenarSalarioListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorSalario();
            }
        });

        vista.addOrdenarFechaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorFecha();
            }
        });
    }

    private void cargarEmpleados() {
        try {
            listaEmpleados = Empleado.obtenerTodos(conexion); // Cargar empleados una sola vez
            mostrarEmpleadosEnTabla(listaEmpleados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos de la cola
    private void añadirACola() {
        String id = vista.getId();
        Empleado empleadoAEliminar = null;

        for (Empleado empleado : listaEmpleados) {
            if (empleado.getId().equals(id)) {
                empleadoAEliminar = empleado;
                break;
            }
        }

        if (empleadoAEliminar != null) {
            listaEmpleados.remove(empleadoAEliminar);
            colaEliminados.add(empleadoAEliminar);
            mostrarEmpleadosEnTabla(listaEmpleados);
            vista.mostrarMensaje("Empleado añadido a la cola de eliminados.");
        } else {
            vista.mostrarMensaje("Empleado no encontrado.");
        }
    }

    private void recuperarDeCola() {
        Empleado empleadoRecuperado = colaEliminados.poll();
        if (empleadoRecuperado != null) {
            listaEmpleados.add(empleadoRecuperado);
            mostrarEmpleadosEnTabla(listaEmpleados);
            vista.mostrarMensaje("Empleado recuperado de la cola.");
        } else {
            vista.mostrarMensaje("No hay empleados en la cola para recuperar.");
        }
    }

    private void mostrarListaCola() {
        List<Empleado> listaEmpleadosEnCola = new ArrayList<>(colaEliminados);
        mostrarEmpleadosEnTabla(listaEmpleadosEnCola);
    }

    private void agregarEmpleado() {
    // Obtener el nombre y recortarlo para eliminar espacios al principio y al final
    String nombre = vista.getNombre().trim();

    // Validar si el nombre está vacío después de recortarlo
    if (nombre.isEmpty()) {
        vista.mostrarMensaje("El nombre no puede estar vacío.");
        return;
    }

    // Validación para el nombre (solo letras, espacios, y acentos)
    if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        vista.mostrarMensaje("El nombre solo puede contener letras y espacios.");
        return; // Salir si el nombre no es válido
    }

    // Obtener el puesto y validar que sea texto
    String puesto = vista.getPuesto().trim();
    if (puesto.isEmpty() || !puesto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        vista.mostrarMensaje("El puesto debe ser un texto válido.");
        return; // Salir si el puesto no es válido
    }

    // Validación para el salario (debe ser un número positivo)
    String salarioStr = vista.getSalario();  // Obtén el valor de salario como String
    double salario;
    try {
        salario = Double.parseDouble(salarioStr);  // Intenta convertir a double
        if (salario <= 0) {
            vista.mostrarMensaje("El salario debe ser un número positivo.");
            return; // Salir si el salario no es válido
        }
    } catch (NumberFormatException e) {
        vista.mostrarMensaje("El salario debe ser un número válido.");
        return; // Salir si el salario no es un número
    }

    // Validación para la fecha de contratación (debe ser una fecha válida)
    try {
        Date.valueOf(vista.getFechaContratacion()); // Intentar convertir a Date
    } catch (IllegalArgumentException e) {
        vista.mostrarMensaje("La fecha de contratación no es válida.");
        return; // Salir si la fecha no es válida
    }

    // Si todas las validaciones son correctas, proceder a crear el empleado
    try {
        Empleado nuevoEmpleado = new Empleado(
                vista.getId(),
                nombre,  // Usamos el nombre validado
                puesto,  // Usamos el puesto validado
                salario, // Usamos el salario validado
                Date.valueOf(vista.getFechaContratacion()),
                vista.getAdminUsername()
        );

        if (nuevoEmpleado.guardar(conexion)) {
            listaEmpleados.add(nuevoEmpleado); 
            mostrarEmpleadosEnTabla(listaEmpleados);
            vista.limpiarCampos();
        } else {
            vista.mostrarMensaje("Error al agregar el empleado.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        vista.mostrarMensaje("Error en la base de datos al agregar el empleado.");
    }
}


    private void modificarEmpleado() {
    String id = vista.getId();
    String salarioStr = vista.getSalario();
    double salario = 0;

    // Validar que el salario sea un número válido
    try {
        salario = Double.parseDouble(salarioStr);
    } catch (NumberFormatException e) {
        vista.mostrarMensaje("El salario debe ser un número válido.");
        return; // Salir del método si el salario no es válido
    }

    for (Empleado empleado : listaEmpleados) {
        if (empleado.getId().equals(id)) {
            empleado.setNombre(vista.getNombre());
            empleado.setPuesto(vista.getPuesto());
            empleado.setSalario(salario);  // Asignar el salario validado
            empleado.setFechaContratacion(Date.valueOf(vista.getFechaContratacion()));
            empleado.setAdminUsername(vista.getAdminUsername());

            try {
                if (empleado.actualizar(conexion)) {
                    mostrarEmpleadosEnTabla(listaEmpleados);
                    vista.limpiarCampos();
                } else {
                    vista.mostrarMensaje("Error al modificar el empleado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                vista.mostrarMensaje("Error en la base de datos al modificar el empleado.");
            }
            break;
        }
    }
}


    private void eliminarEmpleado() {
        String id = vista.getId();
        Empleado empleadoAEliminar = null;

        for (Empleado empleado : listaEmpleados) {
            if (empleado.getId().equals(id)) {
                empleadoAEliminar = empleado;
                break;
            }
        }

        if (empleadoAEliminar != null) {
            try {
                if (empleadoAEliminar.eliminar(conexion)) {
                    listaEmpleados.remove(empleadoAEliminar);
                    mostrarEmpleadosEnTabla(listaEmpleados);
                    vista.limpiarCampos();
                    vista.mostrarMensaje("Empleado eliminado correctamente.");
                } else {
                    vista.mostrarMensaje("Error al eliminar el empleado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                vista.mostrarMensaje("Error en la base de datos al eliminar el empleado.");
            }
        } else {
            vista.mostrarMensaje("Empleado no encontrado.");
        }
    }

    private void buscarEmpleado() {
        String id = vista.getId();
        int rowIndex = -1;

        for (int i = 0; i < listaEmpleados.size(); i++) {
            if (listaEmpleados.get(i).getId().equals(id)) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex != -1) {
            vista.seleccionarFila(rowIndex);
        } else {
            vista.mostrarMensaje("Empleado no encontrado.");
        }
    }

    private void ordenarPorSalario() {
        Empleado.ordenarPorSalarioAscendente(listaEmpleados);
        mostrarEmpleadosEnTabla(listaEmpleados);
    }

    private void ordenarPorFecha() {
        Empleado.ordenarPorFechaContratacionDescendente(listaEmpleados);
        mostrarEmpleadosEnTabla(listaEmpleados);
    }

    private void mostrarEmpleadosEnTabla(List<Empleado> empleados) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Puesto");
        modelo.addColumn("Salario");
        modelo.addColumn("Fecha Contratación");
        modelo.addColumn("Admin Username");

        for (Empleado emp : empleados) {
            modelo.addRow(new Object[]{
                    emp.getId(),
                    emp.getNombre(),
                    emp.getPuesto(),
                    emp.getSalario(),
                    emp.getFechaContratacion().toString(),
                    emp.getAdminUsername()
            });
        }
        vista.setTablaEmpleadosModel(modelo);
    }
}
