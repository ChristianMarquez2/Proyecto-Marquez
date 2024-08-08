public class Eneagono extends FigurasGeometricas {
    double lado;
    double apotema;

    public Eneagono() {
    }

    public Eneagono(int nLados, String nombre, double lado, double apotema) {
        super(nombre, nLados);
        this.lado = lado;
        this.apotema = apotema;
    }

    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }

    public double getApotema() {
        return apotema;
    }

    public void setApotema(double apotema) {
        this.apotema = apotema;
    }

    public double perimetroEneagono() {
        return lado * nLados;
    }

    public double areaEneagono() {
        return (lado * nLados * apotema) / 2;
    }
}