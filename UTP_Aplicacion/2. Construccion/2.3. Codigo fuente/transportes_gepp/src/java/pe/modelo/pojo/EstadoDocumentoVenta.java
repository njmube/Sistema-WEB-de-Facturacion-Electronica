package pe.modelo.pojo;
// Generated 14-ago-2017 12:43:55 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EstadoDocumentoVenta generated by hbm2java
 */
public class EstadoDocumentoVenta  implements java.io.Serializable {


     private String codigo;
     private Usuario usuarioByIdUsuarioCreacion;
     private Usuario usuarioByIdUsuarioModificacion;
     private String nombre;
     private Date fechaCreacion;
     private Date fechaModificacion;
     private Set documentoVentas = new HashSet(0);
     private Set resumenVentases = new HashSet(0);

    public EstadoDocumentoVenta() {
    }

	
    public EstadoDocumentoVenta(String codigo, Usuario usuarioByIdUsuarioCreacion, String nombre, Date fechaCreacion) {
        this.codigo = codigo;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }
    public EstadoDocumentoVenta(String codigo, Usuario usuarioByIdUsuarioCreacion, Usuario usuarioByIdUsuarioModificacion, String nombre, Date fechaCreacion, Date fechaModificacion, Set documentoVentas, Set resumenVentases) {
       this.codigo = codigo;
       this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
       this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
       this.nombre = nombre;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.documentoVentas = documentoVentas;
       this.resumenVentases = resumenVentases;
    }
   
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public Set getDocumentoVentas() {
        return this.documentoVentas;
    }
    
    public void setDocumentoVentas(Set documentoVentas) {
        this.documentoVentas = documentoVentas;
    }
    public Set getResumenVentases() {
        return this.resumenVentases;
    }
    
    public void setResumenVentases(Set resumenVentases) {
        this.resumenVentases = resumenVentases;
    }




}


