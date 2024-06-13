import java.util.ArrayList;

public class Parqueadero {
    public static final int TAMANO = 40;
    public static final int NO_HAY_PUESTO = -1;
    public static final int PARQUEADERO_CERRADO = -2;
    public static final int CARRO_NO_EXISTE = -3;
    public static final int CARRO_YA_EXISTE = -4;
    public static final int HORA_INICIAL = 6;
    public static final int HORA_CIERRE = 21;
    public static final int TARIFA_INICIAL = 1200;

    private final Puesto puestos[];
    private int tarifa;
    private int caja;
    private int horaActual;
    private boolean abierto;

    public Parqueadero() {
        horaActual = HORA_INICIAL;
        abierto = true;
        tarifa = TARIFA_INICIAL;
        caja = 0;
        puestos = new Puesto[TAMANO];
        for (int i = 0; i < TAMANO; i++) {
            puestos[i] = new Puesto(i);
        }
    }

    public String darPlacaCarro(final int pPosicion) {
        if (pPosicion < 0 || pPosicion >= TAMANO) {
            return "Posición inválida";
        }
        return puestos[pPosicion].estaOcupado() ? puestos[pPosicion].darCarro().darPlaca() : "No hay un carro en esta posición";
    }

    public int entrarCarro(final String pPlaca) {
        if (!abierto) {
            return PARQUEADERO_CERRADO;
        }

        final int numPuestoCarro = buscarPuestoCarro(pPlaca.toUpperCase());
        if (numPuestoCarro != CARRO_NO_EXISTE) {
            return CARRO_YA_EXISTE;
        }

        final int resultado = buscarPuestoLibre();
        if (resultado != NO_HAY_PUESTO) {
            final Carro carroEntrando = new Carro(pPlaca, horaActual);
            puestos[resultado].parquearCarro(carroEntrando);
        }
        return resultado;
    }

    public int sacarCarro(final String pPlaca) {
        if (!abierto) {
            return PARQUEADERO_CERRADO;
        }

        final int numPuesto = buscarPuestoCarro(pPlaca.toUpperCase());
        if (numPuesto == CARRO_NO_EXISTE) {
            return CARRO_NO_EXISTE;
        }

        final Carro carro = puestos[numPuesto].darCarro();
        final int nHoras = carro.darTiempoEnParqueadero(horaActual);
        final int porPagar = nHoras * tarifa;
        caja += porPagar;
        puestos[numPuesto].sacarCarro();
        return porPagar;
    }

    public int darMontoCaja() {
        return caja;
    }

    public int calcularPuestosLibres() {
        int puestosLibres = 0;
        for (final Puesto puesto : puestos) {
            if (!puesto.estaOcupado()) {
                puestosLibres++;
            }
        }
        return puestosLibres;
    }

    public void cambiarTarifa(final int pTarifa) {
        tarifa = pTarifa;
    }

    private int buscarPuestoLibre() {
        for (int i = 0; i < TAMANO; i++) {
            if (!puestos[i].estaOcupado()) {
                return i;
            }
        }
        return NO_HAY_PUESTO;
    }

    private int buscarPuestoCarro(final String pPlaca) {
        for (int i = 0; i < TAMANO; i++) {
            if (puestos[i].tieneCarroConPlaca(pPlaca)) {
                return i;
            }
        }
        return CARRO_NO_EXISTE;
    }

    public double darTiempoPromedio() {
        double tiempoTotal = 0;
        int carrosContados = 0;
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                final Carro carro = puesto.darCarro();
                tiempoTotal += carro.darTiempoEnParqueadero(horaActual);
                carrosContados++;
            }
        }
        return carrosContados == 0 ? 0 : tiempoTotal / carrosContados;
    }

    public boolean hayCarroMasDeOchoHoras() {
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                final Carro carro = puesto.darCarro();
                if (carro.darTiempoEnParqueadero(horaActual) > 8) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Carro> darCarrosMasDeTresHorasParqueados() {
        final ArrayList<Carro> carrosMasDeTresHoras = new ArrayList<>();
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                final Carro carro = puesto.darCarro();
                if (carro.darTiempoEnParqueadero(horaActual) > 3) {
                    carrosMasDeTresHoras.add(carro);
                }
            }
        }
        return carrosMasDeTresHoras;
    }

    public boolean hayCarrosPlacaIgual() {
        final ArrayList<String> placas = new ArrayList<>();
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                final Carro carro = puesto.darCarro();
                if (placas.contains(carro.darPlaca())) {
                    return true;
                }
                placas.add(carro.darPlaca());
            }
        }
        return false;
    }

    public int contarCarrosQueComienzanConPlacaPB() {
        int contador = 0;
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                final Carro carro = puesto.darCarro();
                if (carro.darPlaca().startsWith("PB")) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public boolean hayCarroCon24Horas() {
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                final Carro carro = puesto.darCarro();
                if (carro.darTiempoEnParqueadero(horaActual) >= 24) {
                    return true;
                }
            }
        }
        return false;
    }

    public int desocuparParqueadero() {
        int carrosSacados = 0;
        for (final Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                puesto.sacarCarro();
                carrosSacados++;
            }
        }
        return carrosSacados;
    }
    

    public int darHoraActual() {
        return horaActual;
    }

    public void avanzarHora() {
        if (horaActual < HORA_CIERRE) {
            horaActual++;
        }
        if (horaActual == HORA_CIERRE) {
            abierto = false;
        }
    }

    public int darTarifa() {
        return tarifa;
    }

}
