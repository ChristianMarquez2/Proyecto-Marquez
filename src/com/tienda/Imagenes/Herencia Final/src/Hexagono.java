public class Hexagono extends FigurasGeometricas{
    double lado;
    double apotema;

    public Hexagono(){}

    public Hexagono(String nombre, int nLados,double lado,double apotema){
        super(nombre,nLados);
    }

    public double getApotema() {
        return apotema;
    }

    public void setApotema(double apotema) {
        this.apotema = apotema;
    }

    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }
    public double perimetroHexagono() {
        return lado*nLados;
    }
    public double areaHexagono() {
        return (lado*nLados*apotema)/2;
    }


}