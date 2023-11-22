import java.util.ArrayList;
import java.util.List;

public class CuentaBancaria {
    private final String numeroCuenta;
    private final int pin;
    private double saldo;
    static List<CuentaBancaria> cuentas = new ArrayList<>();
    static
    {
        cuentas.add( new CuentaBancaria("1029462918",4301,3700));
        cuentas.add( new CuentaBancaria("8576343952",2712,2500));
    }
    public CuentaBancaria(String numeroCuenta,int pin,double saldo)
    {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldo;
    }
    public String getNumeroCuenta()
    {
        return numeroCuenta;
    }
    public int getPin()
    {
        return pin;
    }
    public double getSaldo()
    {
        return saldo;
    }
    public void retirar(double cantidad) throws SaldoInsuficienteException
    {
        if(cantidad>saldo)
        {
            throw new SaldoInsuficienteException("SaldoInsuficiente");
        }
        saldo -= cantidad;
    }
    public void depositar(double cantidad)
    {
        this.saldo+=cantidad;
    }
    public void transferir(String cuentaDestino,double monto) throws SaldoInsuficienteException,CuentaInexistenteException
    {
        CuentaBancaria cuentaDestinoObj = buscarCuenta(cuentaDestino);

        if(cuentaDestinoObj == null)
        {
            throw new CuentaInexistenteException("La cuenta destino no existe.");
        }

        if(monto>saldo)
        {
            throw new SaldoInsuficienteException("Saldo Insuficiente para realizar la Transferencia.");
        }

        this.saldo-=monto;
        cuentaDestinoObj.depositar(monto);
        System.out.println("Transferencia exitosa. Saldo Actual (Destino): "+cuentaDestinoObj.getSaldo());
    }
    public void depositarOtraCuenta(String cuentaDestino, double monto) throws CuentaInexistenteException
    {
        CuentaBancaria cuentaDestinoObj = buscarCuenta(cuentaDestino);
        if(cuentaDestinoObj == null)
        {
            throw new CuentaInexistenteException("La cuenta destino no existe.");
        }

        cuentaDestinoObj.depositar(monto);
        System.out.println("Depósito exitoso. Saldo Actual (Destino): "+cuentaDestinoObj.getSaldo());

    }
    public void autenticar(String numeroCuenta,int pin) throws AutenticacionFallidaException
    {
        if(!this.numeroCuenta.equals(numeroCuenta)||this.pin != pin)
        {
            throw new AutenticacionFallidaException("Autenticacion Fallida");
        }
    }
    private static CuentaBancaria buscarCuenta(String numeroCuenta)
    {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }
}