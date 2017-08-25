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
 * Entidad generated by hbm2java
 */
@Entity
@Table(name = "entidad",
         schema = "public",
         uniqueConstraints = @UniqueConstraint(columnNames = "documento")
)
public class Entidad implements java.io.Serializable {

    private long id;
    @JsonIgnore
    private Usuario usuarioByIdUsuarioModificacion;
    @JsonIgnore
    private Usuario usuarioByIdUsuarioCreacion;
    private TipoDocumentoEntidad tipoDocumentoEntidad;
    private Ubigeo ubigeo;
    private String documento;
    private String nombre;
    private String codigoPais;
    private String zona;
    private String direccion;
    private String correoElectronico1;
    private String correoElectronico2;
    private String correoElectronico3;
    @JsonIgnore
    private Date fechaCreacion;
    @JsonIgnore
    private Date fechaModificacion;
    @JsonIgnore
    private Set<Cliente> clientes = new HashSet<Cliente>(0);
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<Usuario>(0);
    @JsonIgnore
    private Set<Empresa> empresas = new HashSet<Empresa>(0);

    public Entidad() {
    }

    public Entidad(long id, Usuario usuarioByIdUsuarioCreacion, TipoDocumentoEntidad tipoDocumentoEntidad, String documento, String nombre, Date fechaCreacion) {
        this.id = id;
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
        this.tipoDocumentoEntidad = tipoDocumentoEntidad;
        this.documento = documento;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }

        public Entidad(long id, Usuario usuarioByIdUsuarioModificacion, Usuario usuarioByIdUsuarioCreacion, TipoDocumentoEntidad tipoDocumentoEntidad, Ubigeo ubigeo, String documento, String nombre, String codigoPais, String zona, String direccion, String correoElectronico1, String correoElectronico2, String correoElectronico3, Date fechaCreacion, Date fechaModificacion, Set<Cliente> clientes, Set<Usuario> usuarios, Set<Empresa> empresas) {
       this.id = id;
       this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
       this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
       this.tipoDocumentoEntidad = tipoDocumentoEntidad;
       this.ubigeo = ubigeo;
       this.documento = documento;
       this.nombre = nombre;
       this.codigoPais = codigoPais;
       this.zona = zona;
       this.direccion = direccion;
       this.correoElectronico1 = correoElectronico1;
       this.correoElectronico2 = correoElectronico2;
       this.correoElectronico3 = correoElectronico3;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.clientes = clientes;
       this.usuarios = usuarios;
       this.empresas = empresas;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_modificacion")
    public Usuario getUsuarioByIdUsuarioModificacion() {
        return this.usuarioByIdUsuarioModificacion;
    }

    public void setUsuarioByIdUsuarioModificacion(Usuario usuarioByIdUsuarioModificacion) {
        this.usuarioByIdUsuarioModificacion = usuarioByIdUsuarioModificacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_creacion", nullable = false)
    public Usuario getUsuarioByIdUsuarioCreacion() {
        return this.usuarioByIdUsuarioCreacion;
    }

    public void setUsuarioByIdUsuarioCreacion(Usuario usuarioByIdUsuarioCreacion) {
        this.usuarioByIdUsuarioCreacion = usuarioByIdUsuarioCreacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_tipo_documento_entidad", nullable = false)
    public TipoDocumentoEntidad getTipoDocumentoEntidad() {
        return this.tipoDocumentoEntidad;
    }

    public void setTipoDocumentoEntidad(TipoDocumentoEntidad tipoDocumentoEntidad) {
        this.tipoDocumentoEntidad = tipoDocumentoEntidad;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_ubigeo")
    public Ubigeo getUbigeo() {
        return this.ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    @Column(name = "documento", unique = true, nullable = false, length = 11)
    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Column(name = "nombre", nullable = false, length = 250)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "codigo_pais", length = 3)
    public String getCodigoPais() {
        return this.codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    @Column(name = "zona", length = 250)
    public String getZona() {
        return this.zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Column(name = "direccion", length = 250)
    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "correo_electronico1", length = 50)
    public String getCorreoElectronico1() {
        return this.correoElectronico1;
    }

    public void setCorreoElectronico1(String correoElectronico1) {
        this.correoElectronico1 = correoElectronico1;
    }

    @Column(name = "correo_electronico2", length = 50)
    public String getCorreoElectronico2() {
        return this.correoElectronico2;
    }

    public void setCorreoElectronico2(String correoElectronico2) {
        this.correoElectronico2 = correoElectronico2;
    }

    @Column(name = "correo_electronico3", length = 50)
    public String getCorreoElectronico3() {
        return this.correoElectronico3;
    }

    public void setCorreoElectronico3(String correoElectronico3) {
        this.correoElectronico3 = correoElectronico3;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entidad")
    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entidad")
    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entidad")
    public Set<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

}
