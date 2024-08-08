public class Heptagono extends FigurasGeometricas {
    double lado;
    double apotema;

    public Heptagono() {
    }

    public Heptagono(int nLados, String nombre, double lado, double apotema) {
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

    public double perimetroHeptagono() {
        return lado * nLados;
    }

    public double areaHeptagono() {
        return (lado * nLados * apotema) / 2;
    }
}
