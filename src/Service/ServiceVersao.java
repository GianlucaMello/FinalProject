
package Service;

import Model.DAOSer.VersaoDAOSer;
import Model.bo.Versao;
import java.util.List;

public class ServiceVersao {
    public static void Change(int id, Versao versao){
        VersaoDAOSer versaoDAOSer = new VersaoDAOSer();
        versaoDAOSer.Update(id, versao);
    }
    public static void Delete(int id){
        VersaoDAOSer versaoDAOSer = new VersaoDAOSer();
        versaoDAOSer.Delete(id);
    }
    public static void Include(Versao versao){
        VersaoDAOSer versaoDAOSer = new VersaoDAOSer();
        versaoDAOSer.Create(versao);
    }
    public static Versao Search(int id){
        VersaoDAOSer versaoDAOSer = new VersaoDAOSer();
        return (Versao) versaoDAOSer.Retrieve(id);
    }
    public static List<Versao> Search(){
        VersaoDAOSer versaoDAOSer = new VersaoDAOSer();
        return versaoDAOSer.Retrieve();
    }
}
