
package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import java.util.Date;
import java.util.List;


public interface ClienteDAO {
    
    public void save(Cliente cli) throws PersistenceException;

    public Cliente load(long documento) throws PersistenceException;
    
    public void saveItem(int it,long cli,Date fechainicio, Date fechainfin) throws PersistenceException;
    
    public List<Cliente> loadClientes() throws PersistenceException;
    
    public List<ItemRentado> loadItemRentados(long documento) throws PersistenceException;
    
}
