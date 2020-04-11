package model;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Nombre pagina.clase
 * @Autores: Gabriel Barboza && Néstor Leiva
 * Descripción: 
 */
public class movimiento {
   private int id_movimiento;
   private int cuenta_num_cuenta;
   private double monto;
   private String fecha;
   private String detalle;
   private String depositante;
   int aplicado;
   private static DecimalFormat df2 = new DecimalFormat("#.##");
    @Override
    public String toString() {
        return "movimiento{" + "id_movimiento=" + id_movimiento + ", cuenta_num_cuenta=" + cuenta_num_cuenta + ", monto=" + monto + ", fecha=" + fecha + ", detalle=" + detalle + ", depositante=" + depositante + ", aplicado=" + aplicado + '}';
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public int getCuenta_num_cuenta() {
        return cuenta_num_cuenta;
    }

    public void setCuenta_num_cuenta(int cuenta_num_cuenta) {
        this.cuenta_num_cuenta = cuenta_num_cuenta;
    }

    public double getMonto() {
        return  Double.parseDouble(df2.format(monto));
    }

    public void setMonto(double monto) {
        df2.setRoundingMode(RoundingMode.UP);
        this.monto = Double.parseDouble(df2.format(monto));
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }
    
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDepositante() {
        return depositante;
    }

    public void setDepositante(String depositante) {
        this.depositante = depositante;
    }

    public int getAplicado() {
        return aplicado;
    }

    public void setAplicado(int aplicado) {
        this.aplicado = aplicado;
    }

    public movimiento(int id_movimiento, int cuenta_num_cuenta, double monto, String fecha, String detalle, String depositante, int aplicado) {
        this.id_movimiento = id_movimiento;
        this.cuenta_num_cuenta = cuenta_num_cuenta;
        this.monto = monto;
        this.fecha = fecha;
        this.detalle = detalle;
        this.depositante = depositante;
        this.aplicado = aplicado;
    }

    public movimiento() {
        this.depositante="dueño de la cuenta";
        this.aplicado=0;
        this.detalle="NINGUNO";
        this.detalle="";
    }
}
