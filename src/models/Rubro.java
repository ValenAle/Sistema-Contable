package models;

public class Rubro {

    private int id;
    private int grupoId;
    private int tipo;      // 0, 1 (corriente) o 2 (no corriente)
    private String codigo; // 2 dígitos, ej: "01"
    private String nombre;

    public Rubro(int id, int grupoId, int tipo, String codigo, String nombre) {
        this.id = id;
        this.grupoId = grupoId;
        this.tipo = tipo;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public int getTipo() {
        return tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // Usado por el JComboBox para mostrar el texto del rubro
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
