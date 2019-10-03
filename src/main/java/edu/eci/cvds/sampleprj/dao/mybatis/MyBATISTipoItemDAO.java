
package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import java.util.List;


public class MyBATISTipoItemDAO implements TipoItemDAO{
    
    @Inject
    private TipoItemMapper TipoItemMapper;
    
    @Override
    public TipoItem load(int id) throws PersistenceException{
        try{
            return TipoItemMapper.getTipoItem(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al consultar el tipo de item "+Integer.toString(id),e);
            
        }
    }
    
    @Override
    public List<TipoItem> loadItems() throws PersistenceException{
        try{
            return TipoItemMapper.getTiposItems();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
           throw new PersistenceException("Error al consultar los tipos de items",e);
        }
    }
}
