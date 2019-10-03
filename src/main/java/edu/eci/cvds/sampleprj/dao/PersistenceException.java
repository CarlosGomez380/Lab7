
package edu.eci.cvds.sampleprj.dao;


public class PersistenceException extends Exception{
    
    public PersistenceException(){
        super();
    }
    
    public PersistenceException(String message){
        super(message);
    }

    public PersistenceException(String string, org.apache.ibatis.exceptions.PersistenceException cause) {
        super(string, cause);
    }
}
