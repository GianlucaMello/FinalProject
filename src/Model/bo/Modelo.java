package Model.bo;

import java.io.Serializable;

/**
 *
 * @author gianl
 */
public class Modelo extends Id implements Serializable {

    private String model;
    private Marca marca;

    public Modelo() {
    }

    public Modelo(int id, String model, Marca marca) {
        super(id);
        this.model = model;
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Modelo{" + "model=" + model + ", marca=" + marca + '}';
    }

}
