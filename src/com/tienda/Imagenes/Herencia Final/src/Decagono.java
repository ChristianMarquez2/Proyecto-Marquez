public class Decagono extends FigurasGeometricas {
    double lado;
    double apotema;
    public Decagono(){}
    public Decagono(int nLados, String nombre,double lado, double apotema) {
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
    public double perimetroDecagono() {
        return lado*nLados;
    }
    public double areaDecagono() {
        return (lado*nLados*apotema)/2;
    }

}