public class Circulos extends FigurasGeometricas {

    double radio;



    public Circulos(String nombre, int nLados, double radio) {
        super(nombre, nLados);
        this.radio = radio;
    }
    public Circulos (){}

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
    public double CalcularArea(){
        return Math.PI*radio*radio;
    }
    public double CalcularPerimetro(){
        return  2 * Math.PI * radio;
    }
}


