public class Puesto {
    private Carro carro;
    private int numeroPuesto;

    public Puesto(int pPuesto) {
        carro = null;
        numeroPuesto = pPuesto;
    }

    public Carro darCarro() {
        return carro;
    }

    public boolean estaOcupado() {
        return carro != null;
    }

    public void parquearCarro(Carro pCarro) {
        carro = pCarro;
    }

    public void sacarCarro() {
        carro = null;
    }

    public int darNumeroPuesto() {
        return numeroPuesto;
    }

    public boolean tieneCarroConPlaca(String pPlaca) {
        if (carro == null) {
            return false;
        }
        return carro.tienePlaca(pPlaca);
    }
}
