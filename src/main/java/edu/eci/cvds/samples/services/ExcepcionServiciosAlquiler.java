
package edu.eci.cvds.samples.services;


public class ExcepcionServiciosAlquiler extends Exception {
    
    public ExcepcionServiciosAlquiler(){
        super();
    }
    
    public ExcepcionServiciosAlquiler(String message){
        super(message);
    }
    
    public ExcepcionServiciosAlquiler(String string, Throwable cause) {
        super(string, cause);
    }
}
