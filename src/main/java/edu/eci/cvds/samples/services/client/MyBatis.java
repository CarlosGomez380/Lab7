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
        ServiciosAlquilerFactory.getInstance(); 
        ServiciosAlquilerImpl at= new ServiciosAlquilerImpl();
        long a=2342456;
        try{
            Cliente c= at.consultarCliente(a);
            System.out.println(c);
        }catch(ExcepcionServiciosAlquiler e){
            System.out.println("El cliente con documento "+Long.toString(a)+" no se encuentra.");
        }
    }
}
