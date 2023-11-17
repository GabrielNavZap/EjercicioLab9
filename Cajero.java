import java.util.Scanner;
public class Cajero {
	private static double fondo = 100000;
	public static double getFondo()
	{
		return fondo;
	}
	public static void ajustarFondo(double cantidad)
	{
		fondo-=cantidad;
	}
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuentaIngresado = scanner.next();
        System.out.print("Ingrese el PIN: ");
        int pinIngresado = scanner.nextInt();

        //autenticar los datos ingresados
        CuentaBancaria cuentaAutenticada = null;
        for(CuentaBancaria cuenta : CuentaBancaria.cuentas)
        {
            try
            {
                cuenta.autenticar(numeroCuentaIngresado,pinIngresado);
                cuentaAutenticada = cuenta;
                break;
            } catch (AutenticacionFallidaException ignored)
            {}
        }
        if (cuentaAutenticada!=null)
        {
            Cajero.mostrarMenuOperaciones();
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    try {
                        Cajero.realizarRetiro(cuentaAutenticada);
                        break;
                    }catch(SaldoInsuficienteException e){
                        System.out.println("Error: "+e.getMessage());
                    }
                case 2:
                    Cajero.realizarDeposito(cuentaAutenticada);
                    break;
                case 3:
                    try {
                        Cajero.realizarDepositoOtraCuenta(cuentaAutenticada);
                        break;
                    } catch(CuentaInexistenteException e){
                        System.out.println("Error: "+e.getMessage());
                    }
                case 4:
                    try {
                        Cajero.realizarTransferencia(cuentaAutenticada);
                        break;
                    } catch(SaldoInsuficienteException | CuentaInexistenteException e){
                        System.out.println("Error: "+e.getMessage());
                    }
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
    private static void mostrarMenuOperaciones()
    {
        System.out.println("Seleccione una operación:");
        System.out.println("1. Retiro en efectivo");
        System.out.println("2. Depósito en efectivo a esta cuenta");
        System.out.println("3. Depósito en efectivo a otra cuenta");
        System.out.println("4. Transferencia a otra cuenta");
        System.out.print("Ingrese el número de la operación: ");
    }
    private static void realizarRetiro(CuentaBancaria cuenta) throws SaldoInsuficienteException
    {
        try {
            System.out.print("Ingrese la cantidad a retirar: ");
            double cantidad = new Scanner(System.in).nextDouble();
            cuenta.retirar(cantidad);
            System.out.println("Retiro exitoso. Saldo actual: "+cuenta.getSaldo());
        } catch(SaldoInsuficienteException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    private static void realizarDeposito(CuentaBancaria cuenta)
    {
        System.out.print("Ingrese la cantidad a depositar: ");
        double cantidad = new Scanner(System.in).nextDouble();
        cuenta.depositar(cantidad);
        System.out.println("Depósito exitoso. Nuevo saldo: " + cuenta.getSaldo());
    }
    private static void realizarDepositoOtraCuenta(CuentaBancaria cuenta) throws CuentaInexistenteException
    {
        try {
            System.out.println("Ingrese la cuenta para depositar: ");
            String cuentaDestino = new Scanner(System.in).next();
            System.out.println("Ingrese la cantidad a depositar: ");
            double cantidad = new Scanner(System.in).nextDouble();
            cuenta.depositarOtraCuenta(cuentaDestino, cantidad);
            System.out.println("Operacion exitosa.");
        } catch (CuentaInexistenteException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    private static void realizarTransferencia(CuentaBancaria cuenta) throws SaldoInsuficienteException,CuentaInexistenteException
    {
        try {
            System.out.print("Ingrese el número de cuenta de destino: ");
            String cuentaDestino = new Scanner(System.in).next();
            System.out.print("Ingrese la cantidad a transferir: ");
            double cantidad = new Scanner(System.in).nextDouble();
            cuenta.transferir(cuentaDestino, cantidad);
            System.out.println("Transferencia exitosa. Saldo Actual (Origen): " + cuenta.getSaldo());
        } catch(SaldoInsuficienteException e)
        {
            System.out.println("Error: "+e.getMessage());
        }

    }
}
