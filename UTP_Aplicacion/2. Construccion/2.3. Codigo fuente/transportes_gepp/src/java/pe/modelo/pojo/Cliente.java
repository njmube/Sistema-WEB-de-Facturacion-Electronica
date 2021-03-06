package pe.modelo.pojo;
// Generated 22-ago-2017 9:06:53 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "cliente",
        schema = "ventas"
)
public class Cliente implements java.io.Serializable {

    private long id;
    //@JsonIgnore
    private Usuario usuarioByIdUsuarioCreacion;
    @JsonIgnore
    private Usuario usuarioByIdUsuarioModificacion;
    private Entidad entidad;
    private Date fechaCreacion;
    private Date fechaModificacion;
    @JsonIgnore
    private Set<DocumentoVenta> documentoVentas = new HashSet<DocumentoVenta>(0);

    public Cliente() {
    }

    public Cliente(long id, Usuario usuarioByIdUsuarioCreacion, Entidad entidad, Date fechaCreacion) {
        this.id = id;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.entidad = entidad;
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente(long id, Usuario usuarioByIdUsuarioCreacion, Usuario usuarioByIdUsuarioModificacion, Entidad entidad, Date fechaCreacion, Date fechaModificacion, Set<DocumentoVenta> documentoVentas) {
        this.id = id;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
        this.entidad = entidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.documentoVentas = documentoVentas;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_creacion", nullable = false)
    public Usuario getUsuarioByIdUsuarioCreacion() {
        return this.usuarioByIdUsuarioCreacion;
    }

    public void setUsuarioByIdUsuarioCreacion(Usuario usuarioByIdUsuarioCreacion) {
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_modificacion")
    public Usuario getUsuarioByIdUsuarioModificacion() {
        return this.usuarioByIdUsuarioModificacion;
    }

    @JsonProperty
    public void setUsuarioByIdUsuarioModificacion(Usuario usuarioByIdUsuarioModificacion) {
        this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entidad", nullable = false)
    public Entidad getEntidad() {
        return this.entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, length = 29)
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion", length = 29)
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    public Set<DocumentoVenta> getDocumentoVentas() {
        return this.documentoVentas;
    }

    public void setDocumentoVentas(Set<DocumentoVenta> documentoVentas) {
        this.documentoVentas = documentoVentas;
    }

}
