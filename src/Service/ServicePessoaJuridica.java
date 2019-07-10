package Service;

import Model.DAOSer.PessoaJuridicaDAOSer;
import Model.bo.PessoaJuridica;
import java.util.List;
public class ServicePessoaJuridica {
    public static void Change(int id, PessoaJuridica juridica){
        PessoaJuridicaDAOSer pessoaJuridicaDAOSer = new PessoaJuridicaDAOSer();
        pessoaJuridicaDAOSer.Update(id, juridica);
    }
    public static void Delete(int id){
        PessoaJuridicaDAOSer pessoaJuridicaDAOSer = new PessoaJuridicaDAOSer();
        pessoaJuridicaDAOSer.Delete(id);
    }
    public static void Include(PessoaJuridica juridica){
        PessoaJuridicaDAOSer pessoaJuridicaDAOSer = new PessoaJuridicaDAOSer();
        pessoaJuridicaDAOSer.Create(juridica);
    }
    public static PessoaJuridica Search(int id){
        PessoaJuridicaDAOSer pessoaJuridicaDAOSer = new PessoaJuridicaDAOSer();
        return pessoaJuridicaDAOSer.Retrieve(id);
    }
    public static List<PessoaJuridica> Search(){
        PessoaJuridicaDAOSer pessoaJuridicaDAOSer = new PessoaJuridicaDAOSer();
        return pessoaJuridicaDAOSer.Retrieve();
    }
}
