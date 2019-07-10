package Model.bo;

import java.io.Serializable;

public abstract class Id implements Serializable{
    
    private int id;
    
    public Id () {
    }
    
    public Id (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
