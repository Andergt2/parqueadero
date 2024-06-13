import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Parqueadero parqueadero = new Parqueadero();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("-----------------------------------------------");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ingresar un carro");
            System.out.println("2. Dar salida a un carro");
            System.out.println("3. Informar los ingresos del parqueadero");
            System.out.println("4. Consultar la cantidad de puestos disponibles");
            System.out.println("5. Avanzar el reloj del parqueadero");
            System.out.println("6. Cambiar la tarifa del parqueadero");
            System.out.println("7. Salir");
            System.out.println("-----------------------------------------------");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la placa del carro:");
                    String placa = scanner.nextLine();
                    int puesto = parqueadero.entrarCarro(placa);
                    if (puesto >= 0) {
                        System.out.println("Carro ingresado en el puesto " + puesto);
                    } else {
                        System.out.println("Error al ingresar el carro: " + puesto);
                    }
                    break;
                case 2:
                    System.out.println("Ingrese la placa del carro:");
                    placa = scanner.nextLine();
                    int pago = parqueadero.sacarCarro(placa);
                    if (pago >= 0) {
                        System.out.println("Carro salido, monto a pagar: " + pago);
                    } else {
                        System.out.println("Error al sacar el carro: " + pago);
                    }
                    break;
                case 3:
                    System.out.println("Ingresos totales del parqueadero: " + parqueadero.darMontoCaja());
                    break;
                case 4:
                    System.out.println("Puestos disponibles: " + parqueadero.calcularPuestosLibres());
                    break;
                case 5:
                    parqueadero.avanzarHora();
                    System.out.println("Hora actual: " + parqueadero.darHoraActual());
                    break;
                case 6:
                    System.out.println("Ingrese la nueva tarifa:");
                    int nuevaTarifa = scanner.nextInt();
                    parqueadero.cambiarTarifa(nuevaTarifa);
                    System.out.println("Tarifa cambiada a: " + parqueadero.darTarifa());
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }

        scanner.close();
    }
}
