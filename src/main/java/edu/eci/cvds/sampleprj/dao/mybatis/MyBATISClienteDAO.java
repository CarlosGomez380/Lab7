
package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import java.util.Date;
import java.util.List;


public class MyBATISClienteDAO implements ClienteDAO {
    
    @Inject
    private ClienteMapper clienteMapper;    

    @Override
    public void save(Cliente cli) throws PersistenceException{
       try{
           clienteMapper.insertarCliente(cli);
       } 
       catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al registrar el cliente "+cli.toString(),e);
       }
    }
    
    @Override
    public Cliente load(long documento) throws PersistenceException{
        try{
            return clienteMapper.consultarCliente(documento);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al registrar el cliente "+Long.toString(documento),e);
            
        }
    }
    
    @Override
    public List<Cliente> loadClientes() throws PersistenceException{
        try{
            return clienteMapper.consultarClientes();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al consultar clientes ",e);
        }
    }
    
    @Override
    public List<ItemRentado> loadItemRentados(long documento) throws PersistenceException{
        try{
            return clienteMapper.consultarItem(documento);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al consultar los items rentados del cliente "+ Long.toString(documento),e);
        }
    }
    
    @Override
    public void saveItem(int it,long documento,Date fechainicio, Date fechafin) throws PersistenceException{
        try{
            clienteMapper.agregarItemRentadoACliente(documento,it,fechainicio,fechafin);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al registrar el cliente "+Long.toString(documento),e);
            
        }
    }
    
    @Override
    public void updateEstado(long docu,boolean estado) throws PersistenceException {
        try{
            clienteMapper.actualizarEstado(docu, estado);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
        throw new PersistenceException("Error al actualizar el estado del cliente "+Long.toString(docu),e);
        }
    }
}
