package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import java.util.ArrayList;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("idcli")long id); 
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("idcli")long id, 
            @Param("idtcli")int idit, 
            @Param("fechaicli")Date fechainicio,
            @Param("fechafincli")Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();
    
    public void insertarCliente(@Param("cliente")Cliente cli); 
    
    public List<ItemRentado> consultarItem(@Param("itdcli") long id);
    
    public void actualizarEstado(@Param("idcli")long docu, @Param("idestado")boolean estado);
}
