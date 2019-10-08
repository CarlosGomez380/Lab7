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
import java.util.Calendar;
import java.util.List;

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
        Item item= consultarItem(iditem);
        long costo= item.getTarifaxDia() * numdias;
        return costo;
       }
       catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al consultar el costo del alquiler del item "+ Integer.toString(iditem),e);
       }
       
   }
   
   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       try{
           clienteDAO.save(c);
       }
       catch (PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al registrar el cliente" + c.toString(),e);
       }
   }
   
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       try{
           itemDAO.save(i);
       }
       catch (PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al registrar el item" + i.toString(),e);
       }
   }
   
   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
       try{
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, numdias);
            clienteDAO.saveItem(item.getId(),docu,date,cal.getTime());
       }
       catch (PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al registrar el alquiler del item" + item.toString() + "con el cliente"+ Long.toString(docu),e);
       }
   }
   
   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
       try{
           clienteDAO.updateEstado(docu,estado);
       }
       catch (PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al actualizar el estado del cliente" + Long.toString(docu),e);
       }
   }
   
   @Override
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
        Calendar cal = Calendar.getInstance();
        long diff = cal.getTime().getTime() - fechaDevolucion.getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        Item item= consultarItem(iditem);
        return item.getTarifaxDia()* diffDays;
       }
       catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new ExcepcionServiciosAlquiler("Error al consultar el costo de multa item "+ Integer.toString(iditem),e);
       }
   }
   
   
   
   
   
   
   

   

   

   

   

   @Override
   public List<Item> consultarItemsDisponibles() {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   

   

   

   

   

   

   
   

   
}
