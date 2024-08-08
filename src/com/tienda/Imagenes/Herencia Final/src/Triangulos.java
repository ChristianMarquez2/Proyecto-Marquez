public class Triangulos extends FigurasGeometricas {
    double base;
    double altura;
    double ladoA;
    double ladoB;
    double ladoC;

    public Triangulos(String nombre, int nLados, double base, double altura, double ladoA, double ladoB, double ladoC) {
        super(nombre, nLados);
        this.base = base;
        this.altura = altura;
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        this.ladoC = ladoC;
    }

    public double getLadoA() {
        return ladoA;
    }

    public void setLadoA(double ladoA) {
        this.ladoA = ladoA;
    }

    public double getLadoB() {
        return ladoB;
    }

    public void setLadoB(double ladoB) {
        this.ladoB = ladoB;
    }

    public double getLadoC() {
        return ladoC;
    }

    public void setLadoC(double ladoC) {
        this.ladoC = ladoC;
    }

    public Triangulos() {
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getAltura() {
        return altura;
    }

    public double areaTriangulo() {
        return (base * altura) /2;
    }
    public double perimetroTriangulo() {
        return ladoA + ladoB +ladoC;
    }






}