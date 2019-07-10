package Model.DAOSer;
import java.util.List;
public interface InterfaceDAO<T> {
    public abstract void Create(T object);
    public abstract void Delete(int id);
    public abstract List<T> Retrieve();
    public abstract T Retrieve(int id);
    public abstract void Update(int id, T object);
}
