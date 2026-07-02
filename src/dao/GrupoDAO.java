package dao;

import conexion.Conexion;
import models.Grupo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    // LISTAR TODOS LOS GRUPOS
    public List<Grupo> listarGrupos() {

        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT * FROM grupos ORDER BY id";

        try (
                Connection conn = Conexion.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {
                lista.add(new Grupo(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error al listar grupos");
            System.out.println(e.getMessage());
        }

        return lista;
    }
}
