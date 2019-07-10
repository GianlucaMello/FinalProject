package Service;
import Model.DAOSer.MarcaDAOSer;
import Model.bo.Marca;
import java.util.List;
public class ServiceMarca {
    public static void Change(int id, Marca marca){
        MarcaDAOSer marcaDAOSer = new MarcaDAOSer();
        marcaDAOSer.Update(id, marca);
    }
    public static void Delete(int id){
        MarcaDAOSer marcaDAOSer = new MarcaDAOSer();
        marcaDAOSer.Delete(id);
    }
    public static void Include(Marca marca){
        MarcaDAOSer marcaDAOSer = new MarcaDAOSer();
        marcaDAOSer.Create(marca);
    }
    public static Marca Search(int id){
        MarcaDAOSer marcaDAOSer = new MarcaDAOSer();
        return marcaDAOSer.Retrieve(id);
    }
    public static List<Marca> Search(){
        MarcaDAOSer marcaDAOSer = new MarcaDAOSer();
        return marcaDAOSer.Retrieve();
    }
}