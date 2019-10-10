package edu.eci.cvds.samples.services;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
    }

    @Test 
    public void consultarItemsClienteNoRegistradoTest(){
        long a= 13324;
        try{
            serviciosAlquiler.consultarCliente(a);
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(),"Error al consultar el cliente" + Long.toString(a));
        }
    }
  
    @Test 
    public void consultarItemsClienteTest(){
        try{
            Cliente c= new Cliente("Manuel",74658393, "7625435", "gadjsdfhkjhe", "ahgduye@mail.com");
            serviciosAlquiler.consultarCliente(74658393);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void consultarMultaAlquilerNoseEncuentraTest(){
        try{
            LocalDate localDate = LocalDate.parse("1985-10-26");
            serviciosAlquiler.consultarMultaAlquiler(21, Date.valueOf(localDate));        
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(),"Error al consultar el costo de multa item "+ Integer.toString(21));
        }
    }
    
    @Test
    public void consultarMultaAlquilerNoestáAlquiladoTest(){
        try{
            TipoItem tipo= new TipoItem();
            LocalDate localDate = LocalDate.parse("1985-10-26");
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh", new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 123245, "adfhfg", "sjgfhdg");
            serviciosAlquiler.consultarMultaAlquiler(2, Date.valueOf(localDate));        
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(),"Error al consultar el costo de multa item "+ Integer.toString(2));
        }
    }
    
    
    @Test
    public void consultarMultaAlquilerRetrasoTest(){
        try{
            TipoItem tipo= new TipoItem();
    
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh",new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 20000, "adfhfg", "sjgfhdg");
            LocalDate localDate = LocalDate.parse("2019-08-09");
            LocalDate localDate1= LocalDate.parse("2019-10-01");
            ItemRentado itemR= new ItemRentado(1,item, Date.valueOf(localDate),Date.valueOf(localDate1));
            ArrayList<ItemRentado> rentados= new ArrayList<ItemRentado>();
            rentados.add(itemR);
            LocalDate localDate2 = LocalDate.parse("2019-10-10");
            Cliente cliente= new Cliente("Manuel",74658393, "7625435", "gadjsdfhkjhe", "ahgduye@mail.com",false,rentados);
            long multa=serviciosAlquiler.consultarMultaAlquiler(2, Date.valueOf(localDate2));
            Assert.assertEquals(200000,multa);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void consultarMultaAlquilerNoRetrasoTest(){
        try{
            TipoItem tipo= new TipoItem();
            
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh",new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 20000, "adfhfg", "sjgfhdg");
            LocalDate localDate=LocalDate.parse("2019-09-09");
            LocalDate localDate1= LocalDate.parse("2019-10-01");
            ItemRentado itemR= new ItemRentado(1,item, Date.valueOf(localDate),Date.valueOf(localDate1));
            ArrayList<ItemRentado> rentados= new ArrayList<ItemRentado>();
            rentados.add(itemR);
            LocalDate localDate2 = LocalDate.parse("2019-09-21");
            Cliente cliente= new Cliente("Manuel",74658393, "7625435", "gadjsdfhkjhe", "ahgduye@mail.com",false,rentados);
            long multa=serviciosAlquiler.consultarMultaAlquiler(2, Date.valueOf(localDate2));
            Assert.assertEquals(0,multa);
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(),"Error al consultar el costo de multa item "+ Integer.toString(2));
        }
    }
    
    
    @Test
    public void registrarAlquilerClienteDiasTest(){
        try{
            Item item= new Item();
            Cliente cliente= new Cliente("Carlos",234563, "7681634", "ajksgfsfg", "ahsfbjhge@mail.com");
            LocalDate localDate = LocalDate.now( ZoneId.of( "America/Bogota" ) );
            serviciosAlquiler.registrarAlquilerCliente(Date.valueOf(localDate), 234563, item,0);
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(),"El numero de dias debe ser mayor que 0");
        }
    }
    
    
    @Test
    public void registrarAlquilerClienteNoDisponibleTest(){
        try{
            TipoItem tipo= new TipoItem();
            
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh",new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 20000, "adfhfg", "sjgfhdg");
            LocalDate localDate = LocalDate.parse("2019-08-09");
            LocalDate localDate1= LocalDate.parse("2019-10-10");
            ItemRentado itemR= new ItemRentado(1,item, Date.valueOf(localDate),Date.valueOf(localDate1));
            ArrayList<ItemRentado> rentados= new ArrayList<ItemRentado>();
            rentados.add(itemR);
            Cliente cliente= new Cliente("Manuel",74658393, "7625435", "gadjsdfhkjhe", "ahgduye@mail.com",false,rentados);
            Cliente cliente2= new Cliente("Carlos",234563, "7681634", "ajksgfsfg", "ahsfbjhge@mail.com");
            LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "America/Bogota" ) ); 
            serviciosAlquiler.registrarAlquilerCliente(Date.valueOf(todayLocalDate), 234563, item,4);
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(),"El item "+ Integer.toString(2)+" ya está registrado");
        }
    }
    
    @Test
    public void registrarAlquilerClienteTest(){
        try{
            TipoItem tipo= new TipoItem();
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh",new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 20000, "adfhfg", "sjgfhdg");
            Cliente cliente2= new Cliente("Carlos",234563, "7681634", "ajksgfsfg", "ahsfbjhge@mail.com");
            LocalDate localDate = LocalDate.parse("2019-08-09");
            serviciosAlquiler.registrarAlquilerCliente(Date.valueOf(localDate), 234563, item,4);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void consultarCostoAlquilerDiasTest(){
        try{
            TipoItem tipo= new TipoItem();
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh",new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 20000, "adfhfg", "sjgfhdg");
            serviciosAlquiler.consultarCostoAlquiler(2,0);
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(),"El numero de dias debe ser mayor que 0");
        }
    }
    
    @Test
    public void consultarCostoAlquilerTest(){
        try{
            TipoItem tipo= new TipoItem();
            Item item= new Item(tipo,2, "bicicleta", "shdgdjfh",new SimpleDateFormat("dd/MM/yyyy").parse("26/10/1985"), 20000, "adfhfg", "sjgfhdg");
            long costo=serviciosAlquiler.consultarCostoAlquiler(2,3);
            Assert.assertEquals(60000,costo);
        }
        catch(Exception e){
            
        }
    }
  
    /**
    @Test
    public void emptyDB() {
        for(int i = 0; i < 100; i += 10) {
            boolean r = false;
            try {
                Cliente cliente = serviciosAlquiler.consultarCliente(documento);
            } catch(ExcepcionServiciosAlquiler e) {
                r = true;
            } catch(IndexOutOfBoundsException e) {
                r = true;
            }
            // Validate no Client was found;
            Assert.assertTrue(r);
        };
    }
    */
}
