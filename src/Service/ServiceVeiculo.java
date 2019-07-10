package Service;

import Model.DAOSer.VeiculoDAOSer;
import Model.bo.Veiculo;
import java.util.List;

public class ServiceVeiculo {
    public static void Change(int id, Veiculo veiculo){
        VeiculoDAOSer veiculoDAOSer = new VeiculoDAOSer();
        veiculoDAOSer.Update(id, veiculo);
    }
    public static void Delete(int id){
        VeiculoDAOSer veiculoDAOSer = new VeiculoDAOSer();
        veiculoDAOSer.Delete(id);
    }
    public static void Include(Veiculo veiculo){
        VeiculoDAOSer veiculoDAOSer = new VeiculoDAOSer();
        veiculoDAOSer.Create(veiculo);
    }
    public static Veiculo Search(int id){
        VeiculoDAOSer veiculoDAOSer = new VeiculoDAOSer();
        return veiculoDAOSer.Retrieve(id);
    }
    public static List<Veiculo> Search(){
        VeiculoDAOSer veiculoDAOSer = new VeiculoDAOSer();
        return veiculoDAOSer.Retrieve();
    }
}
