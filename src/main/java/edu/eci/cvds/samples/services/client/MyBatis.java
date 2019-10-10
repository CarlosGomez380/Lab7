package edu.eci.cvds.samples.services.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerImpl;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerItemsStub;




public class MyBatis {
    
    
    public static void main(String[] args) {
        try{
            ServiciosAlquilerFactory.getInstance().getServiciosAlquiler().consultarCliente(0); 
        }catch(Exception e){
            System.out.println("error");
        }
        
    }
}
