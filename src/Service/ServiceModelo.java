package Service;

import Model.DAOSer.ModeloDAOSer;
import Model.bo.Modelo;
import java.util.List;

public class ServiceModelo {

    public static void Change(int id, Modelo modelo) {
        ModeloDAOSer modeloDAOSer = new ModeloDAOSer();
        modeloDAOSer.Update(id, modelo);
    }

    public static void Delete(int id) {
        ModeloDAOSer modeloDAOSer = new ModeloDAOSer();
        modeloDAOSer.Delete(id);
    }

    public static void Include(Modelo modelo) {
        ModeloDAOSer modeloDAOSer = new ModeloDAOSer();
        modeloDAOSer.Create(modelo);
    }

    public static Modelo Search(int id) {
        ModeloDAOSer modeloDAOSer = new ModeloDAOSer();
        return (Modelo) modeloDAOSer.Retrieve(id);
    }

    public static List<Modelo> Search() {
        ModeloDAOSer modeloDAOSer = new ModeloDAOSer();
        return modeloDAOSer.Retrieve();
    }
}
