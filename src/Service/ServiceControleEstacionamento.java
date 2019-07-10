package Service;

import Model.DAOSer.ControleEstacionamentoDAOSer;
import Model.bo.ControleEstacionamento;
import java.util.List;

public class ServiceControleEstacionamento {
    public static void Delete(String placa){
        ControleEstacionamentoDAOSer controleEstacionamentoDAOSer = new ControleEstacionamentoDAOSer();
        controleEstacionamentoDAOSer.Delete(placa);
    }
    public static void Include(ControleEstacionamento veiculo){
        ControleEstacionamentoDAOSer controleEstacionamentoDAOSer = new ControleEstacionamentoDAOSer();
        controleEstacionamentoDAOSer.Create(veiculo);
    }
    public static List<ControleEstacionamento> Search(){
        ControleEstacionamentoDAOSer controleEstacionamentoDAOSer = new ControleEstacionamentoDAOSer();
        return controleEstacionamentoDAOSer.Retrieve();
    }
    public static ControleEstacionamento Search(String placa){
        ControleEstacionamentoDAOSer controleEstacionamentoDAOSer = new ControleEstacionamentoDAOSer();
        return controleEstacionamentoDAOSer.Retrieve(placa);
    }
}
