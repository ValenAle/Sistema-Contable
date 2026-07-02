package dao;

import conexion.Conexion;
import models.Rubro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RubroDAO {

    // LISTAR RUBROS SEGÚN GRUPO Y TIPO SELECCIONADOS
    public List<Rubro> listarPorGrupoYTipo(int grupoId, int tipo) {

        List<Rubro> lista = new ArrayList<>();
        String sql = "SELECT * FROM rubros WHERE grupo_id = ? AND tipo = ? ORDER BY codigo";

        try (
                Connection conn = Conexion.conectar();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            pst.setInt(1, grupoId);
            pst.setInt(2, tipo);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Rubro(
                            rs.getInt("id"),
                            rs.getInt("grupo_id"),
                            rs.getInt("tipo"),
                            rs.getString("codigo"),
                            rs.getString("nombre")
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("Error al listar rubros");
            System.out.println(e.getMessage());
        }

        return lista;
    }
}
