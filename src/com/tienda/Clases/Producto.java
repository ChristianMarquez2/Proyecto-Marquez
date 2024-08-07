package com.tienda.Clases;

/**
 * La clase {@code Producto} representa un producto en la tienda con sus detalles,
 * incluyendo id, nombre, precio, stock, ruta de imagen, marca y modelo.
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String imagenPath;
    private int marcaId;
    private String modelo;

    /**
     * Constructor para inicializar un producto con los detalles proporcionados.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param stock Cantidad de stock disponible.
     * @param imagenPath Ruta de la imagen del producto.
     * @param marcaId Identificador de la marca del producto.
     * @param modelo Modelo del producto.
     */
    public Producto(int id, String nombre, double precio, int stock, String imagenPath, int marcaId, String modelo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.imagenPath = imagenPath;
        this.marcaId = marcaId;
        this.modelo = modelo;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador del producto.
     *
     * @return El identificador del producto.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param id El identificador del producto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio El precio del producto.
     * @throws IllegalArgumentException Si el precio es negativo.
     */
    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    /**
     * Obtiene la cantidad de stock disponible.
     *
     * @return La cantidad de stock disponible.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad de stock disponible.
     *
     * @param stock La cantidad de stock disponible.
     * @throws IllegalArgumentException Si el stock es negativo.
     */
    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }

    /**
     * Obtiene la ruta de la imagen del producto.
     *
     * @return La ruta de la imagen del producto.
     */
    public String getImagenPath() {
        return imagenPath;
    }

    /**
     * Establece la ruta de la imagen del producto.
     *
     * @param imagenPath La ruta de la imagen del producto.
     */
    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    /**
     * Obtiene el identificador de la marca del producto.
     *
     * @return El identificador de la marca del producto.
     */
    public int getMarcaId() {
        return marcaId;
    }

    /**
     * Establece el identificador de la marca del producto.
     *
     * @param marcaId El identificador de la marca del producto.
     */
    public void setMarcaId(int marcaId) {
        this.marcaId = marcaId;
    }

    /**
     * Obtiene el modelo del producto.
     *
     * @return El modelo del producto.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del producto.
     *
     * @param modelo El modelo del producto.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Devuelve una representación en cadena del producto con sus detalles.
     *
     * @return Una cadena que representa el producto.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", imagenPath='" + imagenPath + '\'' +
                ", marcaId=" + marcaId +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
