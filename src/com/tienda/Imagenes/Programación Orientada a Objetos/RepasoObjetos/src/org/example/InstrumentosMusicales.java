package org.example;
public class InstrumentosMusicales {
        String categoria;
        String nombre;
        String materiasl;
        Double precio;
        Double tamanio;

        public InstrumentosMusicales() {
        }
        public InstrumentosMusicales(String categoria, String nombre, String materiasl, Double precio, Double tamanio) {
                this.categoria = categoria;
                this.nombre = nombre;
                this.materiasl = materiasl;
                this.precio = precio;
                this.tamanio = tamanio;
        }
        //Metodos
        public void sonar(){
                //Aqui la funcion que permite sonar el instrumento musical
                System.out.println("El sonido es... ");
        }
        public Boolean entretener(){
                //Una funcion que me permite saber si entretiene o no
                return true;
        }

        public String getCategoria() {
                return categoria;
        }

        public void setCategoria(String categoria) {
                this.categoria = categoria;
        }

        public String getNombre() {
                return nombre;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public String getMateriasl() {
                return materiasl;
        }

        public void setMateriasl(String materiasl) {
                this.materiasl = materiasl;
        }

        public Double getPrecio() {
                return precio;
        }

        public void setPrecio(Double precio) {
                this.precio = precio;
        }

        public Double getTamanio() {
                return tamanio;
        }

        public void setTamanio(Double tamanio) {
                this.tamanio = tamanio;
        }
}
