package kreandoapp.mpsupervisores.pojo;

public class ModeloNodo {
    private String nombreUsuario;
    private String nombreNodo;
    private String fechaNodo;
    private String estado;
    private String id_usuario;
    private String id_supervisor;
    private String nombre_supervisor;
    private String autorizacion1;
    private String autorizacion2;
    private String notificacion1;
    private String notificacion2;
    private String idnodo;
    private String visto;

    public ModeloNodo() {
    }

    public ModeloNodo(String nombreUsuario, String nombreNodo, String fechaNodo, String estado, String id_usuario, String id_supervisor, String nombre_supervisor, String autorizacion1, String autorizacion2, String notificacion1, String notificacion2, String idnodo, String visto) {
        this.nombreUsuario = nombreUsuario;
        this.nombreNodo = nombreNodo;
        this.fechaNodo = fechaNodo;
        this.estado = estado;
        this.id_usuario = id_usuario;
        this.id_supervisor = id_supervisor;
        this.nombre_supervisor = nombre_supervisor;
        this.autorizacion1 = autorizacion1;
        this.autorizacion2 = autorizacion2;
        this.notificacion1 = notificacion1;
        this.notificacion2 = notificacion2;
        this.idnodo = idnodo;
        this.visto = visto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreNodo() {
        return nombreNodo;
    }

    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }

    public String getFechaNodo() {
        return fechaNodo;
    }

    public void setFechaNodo(String fechaNodo) {
        this.fechaNodo = fechaNodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_supervisor() {
        return id_supervisor;
    }

    public void setId_supervisor(String id_supervisor) {
        this.id_supervisor = id_supervisor;
    }

    public String getNombre_supervisor() {
        return nombre_supervisor;
    }

    public void setNombre_supervisor(String nombre_supervisor) {
        this.nombre_supervisor = nombre_supervisor;
    }

    public String getAutorizacion1() {
        return autorizacion1;
    }

    public void setAutorizacion1(String autorizacion1) {
        this.autorizacion1 = autorizacion1;
    }

    public String getAutorizacion2() {
        return autorizacion2;
    }

    public void setAutorizacion2(String autorizacion2) {
        this.autorizacion2 = autorizacion2;
    }

    public String getNotificacion1() {
        return notificacion1;
    }

    public void setNotificacion1(String notificacion1) {
        this.notificacion1 = notificacion1;
    }

    public String getNotificacion2() {
        return notificacion2;
    }

    public void setNotificacion2(String notificacion2) {
        this.notificacion2 = notificacion2;
    }

    public String getIdnodo() {
        return idnodo;
    }

    public void setIdnodo(String idnodo) {
        this.idnodo = idnodo;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }
}
