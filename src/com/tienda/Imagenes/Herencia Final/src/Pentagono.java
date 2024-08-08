public class Pentagono extends FigurasGeometricas {
    double lado;
    double apotema;

    public Pentagono() {
    }

    public Pentagono(int nLados, String nombre, double lado, double apotema) {
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

    public double perimetroPentagono() {
        return lado * nLados;
    }

    public double areaPentagono() {
        return (lado * nLados * apotema) / 2;
    }
}