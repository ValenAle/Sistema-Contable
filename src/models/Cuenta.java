package models;

public class Cuenta {

    // El código se genera automáticamente en CuentaDAO y NUNCA se
    // debe modificar manualmente una vez creada la cuenta.
    private String codigo;

    private int grupoId;
    private int tipo;          // 0, 1 (corriente) o 2 (no corriente)
    private int rubroId;
    private int numeroCuenta;  // autoincremental dentro de grupo+tipo+rubro

    private String nombre;
    private double saldo;
    private String tipoSaldo;  // "Deudor" o "Acreedor"

    // Constructor para CREAR una cuenta nueva (el código todavía no existe)
    public Cuenta(int grupoId, int tipo, int rubroId, String nombre, String tipoSaldo) {
        this.grupoId = grupoId;
        this.tipo = tipo;
        this.rubroId = rubroId;
        this.nombre = nombre;
        this.tipoSaldo = tipoSaldo;
        this.saldo = 0;
    }

    // Constructor para reconstruir una cuenta ya existente (leída de la BD)
    public Cuenta(String codigo, int grupoId, int tipo, int rubroId, int numeroCuenta,
                  String nombre, double saldo, String tipoSaldo) {
        this.codigo = codigo;
        this.grupoId = grupoId;
        this.tipo = tipo;
        this.rubroId = rubroId;
        this.numeroCuenta = numeroCuenta;
        this.nombre = nombre;
        this.saldo = saldo;
        this.tipoSaldo = tipoSaldo;
    }

    public String getCodigo() {
        return codigo;
    }

    // Solo debe ser llamado por CuentaDAO al generar el código automático
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public int getTipo() {
        return tipo;
    }

    public int getRubroId() {
        return rubroId;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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
