package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import org.mybatis.guice.transactional.Transactional;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO itemDAO;

    @Inject
    private ClienteDAO clienteDAO;

    @Inject 
    private TipoItemDAO tipoItemDAO;


   
    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try{
            return clienteDAO.loadClientes();
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar clientes",e);
        }
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try{
            return clienteDAO.load(docu);
        }
        catch (Exception e){
            throw new ExcepcionServiciosAlquiler("Error al consultar el cliente" + Long.toString(docu),e);

        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item "+Integer.toString(id),ex);
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try{
            return tipoItemDAO.load(id);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item" + Integer.toString(id),e);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try{
            return tipoItemDAO.loadItems();
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar items",e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        try{
            return clienteDAO.loadItemRentados(idcliente);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar items rentados del cliente" + Long.toString(idcliente),e);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        try{
            if(numdias<1){ throw new ExcepcionServiciosAlquiler("El numero de dias debe ser mayor que 0");}
            Item item= consultarItem(iditem);
            long costo= item.getTarifaxDia() * numdias;
            return costo;
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar el costo del alquiler del item "+ Integer.toString(iditem),e);
        }

    }

    @Override
    @Transactional
    public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
        try{
            clienteDAO.save(c);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al registrar el cliente" + c.toString(),e);
        }
    }

    @Override
    @Transactional
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try{
            itemDAO.save(i);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al registrar el item" + i.toString(),e);
        }
    }
   
    @Override
    @Transactional
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        try{
            if(numdias<1){ throw new ExcepcionServiciosAlquiler("El numero de dias debe ser mayor que 0");}
            boolean alquilado=false;
            List<Cliente> clientes= consultarClientes();
            int i=0;
            while (alquilado=false){
                 List<ItemRentado> items=consultarItemsCliente(clientes.get(i).getDocumento());
                for(int j=0;j<items.size();j++){
                    if(items.get(j).getId()==item.getId()){
                        alquilado=true;
                        break;
                    }
                }
                i++;
            }
            if(alquilado=false){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, numdias);
                clienteDAO.saveItem(item.getId(),docu,date,cal.getTime());
            }
       }
       catch (PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al registrar el alquiler del item" + item.toString() + "con el cliente"+ Long.toString(docu),e);
       }
   }
   
    @Override
    @Transactional
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try{
            clienteDAO.updateEstado(docu,estado);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al actualizar el estado del cliente" + Long.toString(docu),e);
        }
    }

    @Override
    @Transactional
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        try{
            itemDAO.updateTarifa(id,tarifa);
        }
        catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al actualizar la tarifa del item" + Integer.toString(id),e);
        }
    }

    @Override
    public long valorMultaRetrasoxDia(int itemId) throws ExcepcionServiciosAlquiler {
        try{
         Item item= consultarItem(itemId);
         return item.getTarifaxDia();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar el costo de multa por dia del item "+ Integer.toString(itemId),e);
        }
    }
   
    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       
        try{
            Date date1= (Date) new SimpleDateFormat("dd MM yyyy").parse( "0 0 0");
            boolean alquilado=false;
            List<Cliente> clientes= consultarClientes();
            int i=0;
            while (alquilado=false){
                List<ItemRentado> items= consultarItemsCliente(clientes.get(i).getDocumento());
                for(int j=0;j<items.size();j++){
                    if(items.get(j).getId()==iditem){
                        alquilado=true;
                        date1= items.get(j).getFechafinrenta();
                        break;
                    }
                }
                i++;
            }
            if(alquilado==true){
                Item item= consultarItem(iditem);
                long diff = date1.getTime() - fechaDevolucion.getTime();
                int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                if(item.getTarifaxDia()* diffDays>0){
                    return item.getTarifaxDia()* diffDays;
                }else{return 0;}
            }else{throw new ExcepcionServiciosAlquiler("el item no esta alquilado");}
        }
        catch(Exception e){
            throw new ExcepcionServiciosAlquiler("Error al consultar el costo de multa item "+ Integer.toString(iditem),e);
        }
    }
   
   
   
   
   
   
   

   

   

   

   

   @Override
   public List<Item> consultarItemsDisponibles() {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   

   

   

   

   

   

   
   

   
}
