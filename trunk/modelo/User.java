/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;

/**
 *
 * @author Iuga
 */
public class User {
    
    public final static String ESTADO_ALTA = "Alta";
    public final static String ESTADO_BAJA = "Baja";   
    
    public final static String PERMISO_SEPARADOR = ", ";   
    public final static String PERMISO_MODULO_COMPRAS = "Compras";   
    public final static String PERMISO_MODULO_RRHH = "Recursos Humanos";   
    public final static String PERMISO_MODULO_COMERCIALIZACION = "Comercialización";   
    public final static String PERMISO_MODULO_COTIZACION = "Cotización";   
    public final static String PERMISO_MODULO_PLANIFICACION = "Planificación";   
    public final static String PERMISO_MODULO_EJECUCION = "Ejecución";   
    public final static String PERMISO_MODULO_CONTROL = "Control";   
    
    private int id;
    private String usuario;
    private String password;
    private boolean isAdmin;
    private String estado;
    private String urlFoto;
    private String permisos;
    
    private List<FavoritoBean> listaFavoritos;

    public User() {
        this.permisos = "";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<FavoritoBean> getListaFavoritos() {
        return listaFavoritos;
    }

    public void setListaFavoritos(List<FavoritoBean> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
    }
    
    public String isAdmin(){
        if(this.isAdmin){
            return "Si";
        }
        return "No";
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
    
    public boolean tienePermisos(String permiso){
        if(this.isAdmin){
            return true;
        }
        if(this.permisos==null){
            return false;
        }
        if(this.permisos.contains(permiso)){
            return true;
        }
        return false;
    }
    
}
