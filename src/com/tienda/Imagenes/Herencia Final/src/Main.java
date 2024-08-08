public class Main {
    public static void main(String[] args) {
        FigurasGeometricas miFigura1 = new FigurasGeometricas();
        Circulos mifigura2 = new Circulos();
        Triangulos mifigura3 = new Triangulos();
        Cuadrilateros mifigura4 = new Cuadrilateros();
        Hexagono mifigura6 = new Hexagono();
        Octagono mifigura8 = new Octagono();
        Decagono mifigura10 = new Decagono();
        Dodecagono mifigura12  = new Dodecagono();
        Pentagono mifigura5 = new Pentagono();
        Heptagono mifigura7 = new Heptagono();
        Eneagono mifigura9 = new Eneagono();
        Endecagono mifigura11 = new Endecagono();

        mifigura2.setNombre("El circulo");
        System.out.println("\n\nNombre: " + mifigura2.getNombre());
        mifigura2.setRadio(4.00);
        System.out.println("Las medidas son: \n Radio: " + mifigura2.getRadio());
        System.out.println("Su area es:  " + mifigura2.CalcularArea());
        System.out.println("Su Perimetro: "+ mifigura2.CalcularPerimetro());

        mifigura3.setNombre("El triangulo");
        System.out.println("\n\nNombre: " + mifigura3.getNombre());
        mifigura3.setAltura(8.00);
        mifigura3.setBase(5.00);
        mifigura3.setLadoA(5.40);
        mifigura3.setLadoB(4.60);
        mifigura3.setLadoC(8.60);
        System.out.println("Las  medidas son: \n Altura: " + mifigura3.getAltura() + "\n Base: " + mifigura3.getBase()
                + "\n LadoA: " + mifigura3.getLadoA()+ "\n LadoB: " + mifigura3.getLadoB()+ "\n LadoC: " + mifigura3.getLadoC() );
        System.out.println("El Area es: " + mifigura3.areaTriangulo());
        System.out.println("Su Perimetro es : "+ mifigura3.perimetroTriangulo());


        mifigura4.setNombre("El cuadrado");
        System.out.println("\n\nNombre: " + mifigura4.getNombre());
        mifigura4.setAlto(9.00);
        mifigura4.setAncho(8.00);
        System.out.println("Las  medidas son: \n Alto: " + mifigura4.getAlto() + "\n Ancho: " + mifigura4.getAncho());
        System.out.println("El area es: "+mifigura4.calcularArea());
        System.out.println("El perimetro es: "+mifigura4.calcularPerimetro());

        mifigura5.setNombre("El Pentagono");
        System.out.println("\n\nNombre: " + mifigura5.getNombre());
        mifigura5.setLado(7.00);
        mifigura5.setApotema(6.42);
        mifigura5.setnLados(5);
        System.out.println("Las  medidas son: \n lado: " + mifigura5.getLado() + "\n Apotema: " + mifigura5.getApotema());
        System.out.println("El area es: "+mifigura5.areaPentagono());
        System.out.println("El perimetro es: "+mifigura5.perimetroPentagono());

        mifigura6.setNombre("El Hexagono");
        System.out.println("\n\nNombre: " + mifigura6.getNombre());
        mifigura6.setLado(5.00);
        mifigura6.setApotema(4.33);
        mifigura6.setnLados(6);
        System.out.println("Las  medidas son: \n lado: " + mifigura6.getLado() + "\n Apotema: " + mifigura6.getApotema());
        System.out.println("El area es: "+mifigura6.areaHexagono());
        System.out.println("El perimetro es: "+mifigura6.perimetroHexagono());

        mifigura7.setNombre("El Heptagono");
        System.out.println("\n\nNombre: " + mifigura7.getNombre());
        mifigura7.setLado(6.00);
        mifigura7.setApotema(5.24);
        mifigura7.setnLados(7);
        System.out.println("Las  medidas son: \n lado: " + mifigura7.getLado() + "\n Apotema: " + mifigura7.getApotema());
        System.out.println("El area es: "+mifigura7.areaHeptagono());
        System.out.println("El perimetro es: "+mifigura7.perimetroHeptagono());

        mifigura8.setNombre("El Octagono");
        System.out.println("\n\nNombre: " + mifigura8.getNombre());
        mifigura8.setLado(6.00);
        mifigura8.setApotema(4.0);
        mifigura8.setnLados(8);
        System.out.println("Las  medidas son: \n lado: " + mifigura8.getLado() + "\n Apotema: " + mifigura8.getApotema());
        System.out.println("El area es: "+mifigura8.areaOctagono());
        System.out.println("El perimetro es: "+mifigura8.perimetroOctagono());

        mifigura9.setNombre("El Eneagono");
        System.out.println("\n\nNombre: " + mifigura9.getNombre());
        mifigura9.setLado(7.00);
        mifigura9.setApotema(5.91);
        mifigura9.setnLados(9);
        System.out.println("Las  medidas son: \n lado: " + mifigura9.getLado() + "\n Apotema: " + mifigura9.getApotema());
        System.out.println("El area es: "+mifigura9.areaEneagono());
        System.out.println("El perimetro es: "+mifigura9.perimetroEneagono());

        mifigura10.setNombre("El Decagono");
        System.out.println ("\n\nNombre: " + mifigura10.getNombre());
        mifigura10.setLado(5.00);
        mifigura10.setApotema(7.57);
        mifigura10.setnLados(10);
        System.out.println("Las  medidas son: \n lado: " + mifigura10.getLado() + "\n Apotema: " + mifigura10.getApotema());
        System.out.println("El area es: "+mifigura10.areaDecagono());
        System.out.println("El perimetro es: "+mifigura10.perimetroDecagono());

        mifigura11.setNombre("El Endecagono");
        System.out.println("\n\nNombre: " + mifigura11.getNombre());
        mifigura11.setLado(6.00);
        mifigura11.setApotema(7.71);
        mifigura11.setnLados(11);
        System.out.println("Las  medidas son: \n lado: " + mifigura11.getLado() + "\n Apotema: " + mifigura11.getApotema());
        System.out.println("El area es: "+mifigura11.areaEndecagono());
        System.out.println("El perimetro es: "+mifigura11.perimetroEndecagono());

        mifigura12.setNombre("El Dodecagono");
        System.out.println("\n\nNombre: " + mifigura12.getNombre());
        mifigura12.setLado(5.00);
        mifigura12.setApotema(9.18);
        mifigura12.setnLados(12);
        System.out.println("Las  medidas son: \n lado: " + mifigura12.getLado() + "\n Apotema: " + mifigura12.getApotema());
        System.out.println("El area es: "+mifigura12.areaDodecagono());
        System.out.println("El perimetro es: "+mifigura12.perimetroDodecagono());

    }
}
