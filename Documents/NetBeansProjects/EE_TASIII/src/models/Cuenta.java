package models;

public class Cuenta {

    private String codigo;
    private String nombre;
    private double saldo;
    private String tipoSaldo;

    public Cuenta(String codigo, String nombre, double saldo, String tipoSaldo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.saldo = saldo;
        this.tipoSaldo = tipoSaldo;
    }

    // getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

}
