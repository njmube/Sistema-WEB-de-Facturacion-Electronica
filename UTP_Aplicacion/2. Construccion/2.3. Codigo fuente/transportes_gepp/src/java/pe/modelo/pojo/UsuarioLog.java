package pe.modelo.pojo;
// Generated 14-ago-2017 12:43:55 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * UsuarioLog generated by hbm2java
 */
public class UsuarioLog  implements java.io.Serializable {


     private long id;
     private Usuario usuarioByIdUsuarioCreacion;
     private Usuario usuarioByIdUsuarioModificacion;
     private String descripcion;
     private String consulta;
     private String ip;
     private Date fechaCreacion;
     private Date fechaModificacion;

    public UsuarioLog() {
    }

	
    public UsuarioLog(long id, Usuario usuarioByIdUsuarioCreacion, String descripcion, String consulta, String ip, Date fechaCreacion) {
        this.id = id;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.descripcion = descripcion;
        this.consulta = consulta;
        this.ip = ip;
        this.fechaCreacion = fechaCreacion;
    }
    public UsuarioLog(long id, Usuario usuarioByIdUsuarioCreacion, Usuario usuarioByIdUsuarioModificacion, String descripcion, String consulta, String ip, Date fechaCreacion, Date fechaModificacion) {
       this.id = id;
       this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
       this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
       this.descripcion = descripcion;
       this.consulta = consulta;
       this.ip = ip;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
    }
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public Usuario getUsuarioByIdUsuarioCreacion() {
        return this.usuarioByIdUsuarioCreacion;
    }
    
    public void setUsuarioByIdUsuarioCreacion(Usuario usuarioByIdUsuarioCreacion) {
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
    }
    public Usuario getUsuarioByIdUsuarioModificacion() {
        return this.usuarioByIdUsuarioModificacion;
    }
    
    public void setUsuarioByIdUsuarioModificacion(Usuario usuarioByIdUsuarioModificacion) {
        this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getConsulta() {
        return this.consulta;
    }
    
    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }




}


