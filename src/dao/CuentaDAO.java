package dao;

import conexion.Conexion;
import models.Cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {

    // ══════════════════════════════════════════════════════════════
    //  GENERACIÓN AUTOMÁTICA DE CÓDIGO (grupo.tipo.rubro.numero)
    // ══════════════════════════════════════════════════════════════

    // Devuelve el código que le correspondería a la PRÓXIMA cuenta,
    // sin registrar nada todavía. Sirve para mostrar una vista previa
    // en el formulario mientras el usuario elige grupo/tipo/rubro.
    public String previsualizarCodigo(int grupoId, int tipo, int rubroId) {
        String codigoRubro = obtenerCodigoRubro(rubroId);
        if (codigoRubro == null) return null;

        int siguienteNumero = obtenerSiguienteNumero(grupoId, tipo, rubroId);
        return construirCodigo(grupoId, tipo, codigoRubro, siguienteNumero);
    }

    private String obtenerCodigoRubro(int rubroId) {
        String sql = "SELECT codigo FROM rubros WHERE id = ?";
        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setInt(1, rubroId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("codigo");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener código de rubro");
            System.out.println(e.getMessage());
        }
        return null;
    }

    // El número de cuenta se reinicia por cada combinación grupo+tipo+rubro
    // (ej: 1.1.01.01, 1.1.01.02, 1.1.01.03 ...)
    private int obtenerSiguienteNumero(int grupoId, int tipo, int rubroId) {
        String sql = "SELECT COALESCE(MAX(numero_cuenta), 0) + 1 AS siguiente " +
                     "FROM cuentas WHERE grupo_id = ? AND tipo = ? AND rubro_id = ?";
        int siguiente = 1;
        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setInt(1, grupoId);
            pst.setInt(2, tipo);
            pst.setInt(3, rubroId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) siguiente = rs.getInt("siguiente");
            }
        } catch (Exception e) {
            System.out.println("Error al calcular número de cuenta");
            System.out.println(e.getMessage());
        }
        return siguiente;
    }

    private String construirCodigo(int grupoId, int tipo, String codigoRubro, int numeroCuenta) {
        return grupoId + "." + tipo + "." + codigoRubro + "." + String.format("%02d", numeroCuenta);
    }

    // ══════════════════════════════════════════════════════════════
    //  CREAR CUENTA
    // ══════════════════════════════════════════════════════════════
    public boolean crearCuenta(Cuenta cuenta) {

        String codigoRubro = obtenerCodigoRubro(cuenta.getRubroId());
        if (codigoRubro == null) {
            System.out.println("El rubro seleccionado no existe");
            return false;
        }

        String sql = "INSERT INTO cuentas(codigo, grupo_id, tipo, rubro_id, numero_cuenta, " +
                     "nombre, saldo, tipo_saldo) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = Conexion.conectar()) {

            // Evita que dos altas simultáneas generen el mismo número de cuenta
            synchronized (CuentaDAO.class) {

                int siguienteNumero = obtenerSiguienteNumero(
                        cuenta.getGrupoId(), cuenta.getTipo(), cuenta.getRubroId());
                String codigoGenerado = construirCodigo(
                        cuenta.getGrupoId(), cuenta.getTipo(), codigoRubro, siguienteNumero);

                try (PreparedStatement pst = conn.prepareStatement(sql)) {

                    pst.setString(1, codigoGenerado);
                    pst.setInt(2, cuenta.getGrupoId());
                    pst.setInt(3, cuenta.getTipo());
                    pst.setInt(4, cuenta.getRubroId());
                    pst.setInt(5, siguienteNumero);
                    pst.setString(6, cuenta.getNombre());

                    // SIEMPRE 0 AL CREAR
                    pst.setDouble(7, 0);

                    pst.setString(8, cuenta.getTipoSaldo());

                    pst.executeUpdate();

                    cuenta.setCodigo(codigoGenerado);
                    cuenta.setNumeroCuenta(siguienteNumero);

                    System.out.println("Cuenta creada correctamente: " + codigoGenerado);
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al crear cuenta");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  LISTAR CUENTAS
    // ══════════════════════════════════════════════════════════════
    public List<Cuenta> listarCuentas() {

        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas ORDER BY codigo";

        try (
                Connection conn = Conexion.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {
                lista.add(mapearCuenta(rs));
            }

        } catch (Exception e) {
            System.out.println("Error al listar cuentas");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    // BUSCAR CUENTAS POR NOMBRE (para la barra de búsqueda)
    public List<Cuenta> buscarPorNombre(String texto) {

        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas WHERE nombre LIKE ? ORDER BY codigo";

        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setString(1, "%" + texto + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCuenta(rs));
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar cuentas");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    private Cuenta mapearCuenta(ResultSet rs) throws Exception {
        return new Cuenta(
                rs.getString("codigo"),
                rs.getInt("grupo_id"),
                rs.getInt("tipo"),
                rs.getInt("rubro_id"),
                rs.getInt("numero_cuenta"),
                rs.getString("nombre"),
                rs.getDouble("saldo"),
                rs.getString("tipo_saldo")
        );
    }

    // ══════════════════════════════════════════════════════════════
    //  MODIFICAR CUENTA
    //  Solo se pueden modificar nombre y tipo_saldo.
    //  El código NUNCA se modifica una vez generado.
    // ══════════════════════════════════════════════════════════════
    public boolean modificarCuenta(Cuenta cuenta) {

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
                return true;
            } else {
                System.out.println("No existe la cuenta");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error al modificar cuenta");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  ELIMINAR CUENTA (solo si el saldo es 0)
    // ══════════════════════════════════════════════════════════════
    public String eliminarCuenta(String codigo) {

        String consulta = "SELECT saldo FROM cuentas WHERE codigo=?";

        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pstConsulta = conn.prepareStatement(consulta)
        ) {

            pstConsulta.setString(1, codigo);

            try (ResultSet rs = pstConsulta.executeQuery()) {

                if (rs.next()) {

                    double saldo = rs.getDouble("saldo");

                    // SOLO ELIMINA SI EL SALDO ES 0
                    if (saldo == 0) {

                        String eliminar = "DELETE FROM cuentas WHERE codigo=?";

                        try (PreparedStatement pstEliminar = conn.prepareStatement(eliminar)) {
                            pstEliminar.setString(1, codigo);
                            pstEliminar.executeUpdate();
                        }

                        System.out.println("Cuenta eliminada correctamente");
                        return "OK";

                    } else {
                        return "SALDO_DISTINTO_DE_CERO";
                    }

                } else {
                    return "NO_EXISTE";
                }
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar cuenta");
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
}
