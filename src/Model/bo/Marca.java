package Model.bo;

import java.io.Serializable;

public class Marca extends Id implements Serializable {

    private String name;

    public Marca() {
    }

    public Marca(String name) {
        this.name = name;
    }

    public Marca(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Marca{" + "name=" + name + '}';
    }
}
