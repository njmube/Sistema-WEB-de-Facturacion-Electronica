package pe.modelo.pojo;
// Generated 17-ago-2017 14:14:43 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Unidad generated by hbm2java
 */
@Entity
@Table(name="unidad"
    ,schema="almacen"
    , uniqueConstraints = @UniqueConstraint(columnNames="nombre") 
)
public class Unidad  implements java.io.Serializable {


<<<<<<< HEAD
     private String codigo; 
=======
     private String codigo;
>>>>>>> master
     @JsonIgnore
     private Usuario usuarioByIdUsuarioCreacion;
     @JsonIgnore
     private Usuario usuarioByIdUsuarioModificacion;
     private String nombre;
     private Date fechaCreacion;
     private Date fechaModificacion;
     @JsonIgnore
     private Set<Producto> productos = new HashSet<Producto>(0);
     @JsonIgnore
     private Set<DocumentoVentaDetalle> documentoVentaDetalles = new HashSet<DocumentoVentaDetalle>(0);

    public Unidad() {
    }

	
    public Unidad(String codigo, Usuario usuarioByIdUsuarioCreacion, String nombre, Date fechaCreacion) {
        this.codigo = codigo;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }
    public Unidad(String codigo, Usuario usuarioByIdUsuarioCreacion, Usuario usuarioByIdUsuarioModificacion, String nombre, Date fechaCreacion, Date fechaModificacion, Set<Producto> productos, Set<DocumentoVentaDetalle> documentoVentaDetalles) {
       this.codigo = codigo;
       this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
       this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
       this.nombre = nombre;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.productos = productos;
       this.documentoVentaDetalles = documentoVentaDetalles;
    }
   
     @Id 

    
    @Column(name="codigo", unique=true, nullable=false, length=3)
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_usuario_creacion", nullable=false)
    public Usuario getUsuarioByIdUsuarioCreacion() {
        return this.usuarioByIdUsuarioCreacion;
    }
    
    public void setUsuarioByIdUsuarioCreacion(Usuario usuarioByIdUsuarioCreacion) {
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_usuario_modificacion")
    public Usuario getUsuarioByIdUsuarioModificacion() {
        return this.usuarioByIdUsuarioModificacion;
    }
    
    public void setUsuarioByIdUsuarioModificacion(Usuario usuarioByIdUsuarioModificacion) {
        this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
    }

    
    @Column(name="nombre", unique=true, nullable=false, length=50)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_creacion", nullable=false, length=29)
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_modificacion", length=29)
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="unidad")
    public Set<Producto> getProductos() {
        return this.productos;
    }
    
    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="unidad")
    public Set<DocumentoVentaDetalle> getDocumentoVentaDetalles() {
        return this.documentoVentaDetalles;
    }
    
    public void setDocumentoVentaDetalles(Set<DocumentoVentaDetalle> documentoVentaDetalles) {
        this.documentoVentaDetalles = documentoVentaDetalles;
    }




}


