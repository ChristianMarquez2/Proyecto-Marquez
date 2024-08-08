public class Octagono extends FigurasGeometricas {
    double lado;
    double apotema;
    public Octagono() {
    }
    public Octagono(int nLados, String nombre,double lado, double apotema) {
        super(nombre,nLados);

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
    public double perimetroOctagono() {
        return lado*nLados;
    }
    public double areaOctagono() {
        return (lado*nLados*apotema)/2;
    }
}