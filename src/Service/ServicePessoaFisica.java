package Service;

import Model.DAOSer.PessoaFisicaDAOSer;
import Model.bo.PessoaFisica;
import java.util.List;

public class ServicePessoaFisica {
    public static void Change(int id, PessoaFisica fisica){
        PessoaFisicaDAOSer pessoaFisicaDAOSer = new PessoaFisicaDAOSer();
        pessoaFisicaDAOSer.Update(id, fisica);
    }
    public static void Delete(int id){
        PessoaFisicaDAOSer pessoaFisicaDAOSer = new PessoaFisicaDAOSer();
        pessoaFisicaDAOSer.Delete(id);
    }
    public static void Include(PessoaFisica fisica){
        PessoaFisicaDAOSer pessoaFisicaDAOSer = new PessoaFisicaDAOSer();
        pessoaFisicaDAOSer.Create(fisica);
    }
    public static PessoaFisica Search(int id){
        PessoaFisicaDAOSer pessoaFisicaDAOSer = new PessoaFisicaDAOSer();
        return pessoaFisicaDAOSer.Retrieve(id);
    }
    public static List<PessoaFisica> Search(){
        PessoaFisicaDAOSer pessoaFisicaDAOSer = new PessoaFisicaDAOSer();
        return pessoaFisicaDAOSer.Retrieve();
    }
}
