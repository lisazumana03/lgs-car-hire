package za.co.carhire.repository;

@Deprecated
public interface IRepository <T, ID>{
    T create(T entity);
    T read(ID id);
    T update(T entity);
    void delete(T entity);
}
