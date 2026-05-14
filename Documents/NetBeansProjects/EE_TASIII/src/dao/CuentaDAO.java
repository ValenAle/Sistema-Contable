package dao;

import conexion.Conexion;
import models.Cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CuentaDAO {

    // CREAR CUENTA
    public void crearCuenta(Cuenta cuenta) {

        String sql = "INSERT INTO cuentas(codigo, nombre, saldo, tipo_saldo) VALUES(?,?,?,?)";

        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setString(1, cuenta.getCodigo());
            pst.setString(2, cuenta.getNombre());

            // SIEMPRE 0
            pst.setDouble(3, 0);

            pst.setString(4, cuenta.getTipoSaldo());

            pst.executeUpdate();

            System.out.println("Cuenta creada correctamente");

        } catch (Exception e) {

            System.out.println("Error al crear cuenta");
            System.out.println(e.getMessage());
        }
    }

    // LISTAR CUENTAS
    public void listarCuentas() {

        String sql = "SELECT * FROM cuentas";

        try (
                Connection conn = Conexion.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                System.out.println(
                        "Código: " + rs.getString("codigo") +
                        " | Nombre: " + rs.getString("nombre") +
                        " | Saldo: " + rs.getDouble("saldo") +
                        " | Tipo: " + rs.getString("tipo_saldo")
                );
            }

        } catch (Exception e) {

            System.out.println("Error al listar cuentas");
            System.out.println(e.getMessage());
        }
    }

    // MODIFICAR CUENTA
    public void modificarCuenta(Cuenta cuenta) {

        String sql = "UPDATE cuentas SET nombre=?, tipo_saldo=? WHERE codigo=?";

        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setString(1, cuenta.getNombre());
            pst.setString(2, cuenta.getTipoSaldo());
            pst.setString(3, cuenta.getCodigo());

            int filas = pst.executeUpdate();

            if (filas > 0) {

                System.out.println("Cuenta modificada correctamente");

            } else {

                System.out.println("No existe la cuenta");
            }

        } catch (Exception e) {

            System.out.println("Error al modificar cuenta");
            System.out.println(e.getMessage());
        }
    }

    // ELIMINAR CUENTA
    public void eliminarCuenta(String codigo) {

        String consulta = "SELECT saldo FROM cuentas WHERE codigo=?";

        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pstConsulta = conn.prepareStatement(consulta)
        ) {

            pstConsulta.setString(1, codigo);

            ResultSet rs = pstConsulta.executeQuery();

            if (rs.next()) {

                double saldo = rs.getDouble("saldo");

                // SOLO ELIMINA SI EL SALDO ES 0
                if (saldo == 0) {

                    String eliminar = "DELETE FROM cuentas WHERE codigo=?";

                    PreparedStatement pstEliminar =
                            conn.prepareStatement(eliminar);

                    pstEliminar.setString(1, codigo);

                    pstEliminar.executeUpdate();

                    System.out.println("Cuenta eliminada correctamente");

                } else {

                    System.out.println(
                            "No se puede eliminar. El saldo debe ser 0."
                    );
                }

            } else {

                System.out.println("La cuenta no existe");
            }

        } catch (Exception e) {

            System.out.println("Error al eliminar cuenta");
            System.out.println(e.getMessage());
        }
    }
}