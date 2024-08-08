public class Cuadrilateros extends FigurasGeometricas{
    double ancho;
    double alto;

    public Cuadrilateros(String nombre, int nLados, double ancho, double alto) {
        super(nombre, nLados);
        this.ancho = ancho;
        this.alto = alto;
    }

    public Cuadrilateros() {}


    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }
    public double getAncho() {
        return ancho;
    }
    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double calcularArea() {
        return getAncho() * getAlto();
    }
    public double calcularPerimetro() {
        return 2 * (ancho+alto);
    }













}