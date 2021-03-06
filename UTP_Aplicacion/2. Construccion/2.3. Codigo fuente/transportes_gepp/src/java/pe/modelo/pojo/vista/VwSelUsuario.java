package pe.modelo.pojo.vista;
// Generated 29-sep-2017 09:00:00 by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VwSelDocumentoVenta generated by hbm2java
 */
@Entity
@Table(name = "vw_sel_usuario",
        schema = "administracion"
)
public class VwSelUsuario implements java.io.Serializable {

    private Long id;
    private Long idEmpresa;
    private String nombreEntidad;
    private String nombre;
    private String clave;
    private Boolean activo;
    private Long idRol;
    private String nombreRol;
    private Long idPuntoVenta;
    private String nombrePuntoVenta;

    public VwSelUsuario() {
    }

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "id_empresa", insertable = false, updatable = false)
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Column(name = "nombre_entidad", insertable = false, updatable = false)
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    @Column(name = "nombre", insertable = false, updatable = false)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "clave", insertable = false, updatable = false)
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Column(name = "activo", insertable = false, updatable = false)
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Column(name = "id_rol", insertable = false, updatable = false)
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    @Column(name = "nombre_rol", insertable = false, updatable = false)
    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Column(name = "id_punto_venta", insertable = false, updatable = false)
    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    @Column(name = "nombre_punto_venta", insertable = false, updatable = false)
    public String getNombrePuntoVenta() {
        return nombrePuntoVenta;
    }

    public void setNombrePuntoVenta(String nombrePuntoVenta) {
        this.nombrePuntoVenta = nombrePuntoVenta;
    }

}
