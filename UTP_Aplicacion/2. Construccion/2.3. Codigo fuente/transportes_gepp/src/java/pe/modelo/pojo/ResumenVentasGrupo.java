package pe.modelo.pojo;
// Generated 17-ago-2017 14:14:43 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ResumenVentasGrupo generated by hbm2java
 */
@Entity
@Table(name="resumen_ventas_grupo"
    ,schema="ventas"
)
public class ResumenVentasGrupo  implements java.io.Serializable {


     private long id;
     private Usuario usuarioByIdUsuarioModificacion;
     private Usuario usuarioByIdUsuarioCreacion;
     private Moneda moneda;
     private PuntoVentaSerie puntoVentaSerie;
     private ResumenVentas resumenVentas;
     private TipoDocumentoVenta tipoDocumentoVenta;
     private String inicioDocumentoVenta;
     private String finDocumentoVenta;
     private String descripcion;
     private Double totalGrabado;
     private Double totalIgv;
     private Double total;
     private Date fechaCreacion;
     private Date fechaModificacion;
     @JsonIgnore
     private Set<ResumenVentasGrupoVenta> resumenVentasGrupoVentas = new HashSet<ResumenVentasGrupoVenta>(0);

    public ResumenVentasGrupo() {
    }

	
    public ResumenVentasGrupo(long id, Usuario usuarioByIdUsuarioCreacion, PuntoVentaSerie puntoVentaSerie, ResumenVentas resumenVentas, TipoDocumentoVenta tipoDocumentoVenta, String inicioDocumentoVenta, String finDocumentoVenta, Date fechaCreacion) {
        this.id = id;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.puntoVentaSerie = puntoVentaSerie;
        this.resumenVentas = resumenVentas;
        this.tipoDocumentoVenta = tipoDocumentoVenta;
        this.inicioDocumentoVenta = inicioDocumentoVenta;
        this.finDocumentoVenta = finDocumentoVenta;
        this.fechaCreacion = fechaCreacion;
    }
    public ResumenVentasGrupo(long id, Usuario usuarioByIdUsuarioModificacion, Usuario usuarioByIdUsuarioCreacion, Moneda moneda, PuntoVentaSerie puntoVentaSerie, ResumenVentas resumenVentas, TipoDocumentoVenta tipoDocumentoVenta, String inicioDocumentoVenta, String finDocumentoVenta, String descripcion, Double totalGrabado, Double totalIgv, Double total, Date fechaCreacion, Date fechaModificacion, Set<ResumenVentasGrupoVenta> resumenVentasGrupoVentas) {
       this.id = id;
       this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
       this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
       this.moneda = moneda;
       this.puntoVentaSerie = puntoVentaSerie;
       this.resumenVentas = resumenVentas;
       this.tipoDocumentoVenta = tipoDocumentoVenta;
       this.inicioDocumentoVenta = inicioDocumentoVenta;
       this.finDocumentoVenta = finDocumentoVenta;
       this.descripcion = descripcion;
       this.totalGrabado = totalGrabado;
       this.totalIgv = totalIgv;
       this.total = total;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.resumenVentasGrupoVentas = resumenVentasGrupoVentas;
    }
   
     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_usuario_modificacion")
    public Usuario getUsuarioByIdUsuarioModificacion() {
        return this.usuarioByIdUsuarioModificacion;
    }
    
    public void setUsuarioByIdUsuarioModificacion(Usuario usuarioByIdUsuarioModificacion) {
        this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
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
    @JoinColumn(name="codigo_moneda")
    public Moneda getMoneda() {
        return this.moneda;
    }
    
    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="codigo_punto_venta_serie", nullable=false)
    public PuntoVentaSerie getPuntoVentaSerie() {
        return this.puntoVentaSerie;
    }
    
    public void setPuntoVentaSerie(PuntoVentaSerie puntoVentaSerie) {
        this.puntoVentaSerie = puntoVentaSerie;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_resumen_ventas", nullable=false)
    public ResumenVentas getResumenVentas() {
        return this.resumenVentas;
    }
    
    public void setResumenVentas(ResumenVentas resumenVentas) {
        this.resumenVentas = resumenVentas;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="codigo_tipo_documento_venta", nullable=false)
    public TipoDocumentoVenta getTipoDocumentoVenta() {
        return this.tipoDocumentoVenta;
    }
    
    public void setTipoDocumentoVenta(TipoDocumentoVenta tipoDocumentoVenta) {
        this.tipoDocumentoVenta = tipoDocumentoVenta;
    }

    
    @Column(name="inicio_documento_venta", nullable=false, length=8)
    public String getInicioDocumentoVenta() {
        return this.inicioDocumentoVenta;
    }
    
    public void setInicioDocumentoVenta(String inicioDocumentoVenta) {
        this.inicioDocumentoVenta = inicioDocumentoVenta;
    }

    
    @Column(name="fin_documento_venta", nullable=false, length=8)
    public String getFinDocumentoVenta() {
        return this.finDocumentoVenta;
    }
    
    public void setFinDocumentoVenta(String finDocumentoVenta) {
        this.finDocumentoVenta = finDocumentoVenta;
    }

    
    @Column(name="descripcion", length=50)
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    @Column(name="total_grabado", precision=17, scale=17)
    public Double getTotalGrabado() {
        return this.totalGrabado;
    }
    
    public void setTotalGrabado(Double totalGrabado) {
        this.totalGrabado = totalGrabado;
    }

    
    @Column(name="total_igv", precision=17, scale=17)
    public Double getTotalIgv() {
        return this.totalIgv;
    }
    
    public void setTotalIgv(Double totalIgv) {
        this.totalIgv = totalIgv;
    }

    
    @Column(name="total", precision=17, scale=17)
    public Double getTotal() {
        return this.total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="resumenVentasGrupo")
    public Set<ResumenVentasGrupoVenta> getResumenVentasGrupoVentas() {
        return this.resumenVentasGrupoVentas;
    }
    
    public void setResumenVentasGrupoVentas(Set<ResumenVentasGrupoVenta> resumenVentasGrupoVentas) {
        this.resumenVentasGrupoVentas = resumenVentasGrupoVentas;
    }




}


