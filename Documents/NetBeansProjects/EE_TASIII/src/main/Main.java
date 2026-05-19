package main;

import dao.CuentaDAO;
import views.GestionCuentasPanel;

public class Main {

    public static void main(String[] args) {
        
        new GestionCuentasPanel();
        CuentaDAO dao = new CuentaDAO();
        
        /*
        // CREAR
        Cuenta cuenta = new Cuenta(
                "1.1.01",
                "Caja",
                0,
                "D"
        );

        dao.crearCuenta(cuenta);

        // LISTAR
        dao.listarCuentas();

        // MODIFICAR
        cuenta.setNombre("Caja Principal");

        dao.modificarCuenta(cuenta);

        // ELIMINAR
        dao.eliminarCuenta("1.1.01");
    */
    }
}