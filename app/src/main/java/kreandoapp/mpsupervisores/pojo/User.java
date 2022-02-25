package kreandoapp.mpsupervisores.pojo;

public class User {
    private String id;
    private String username;
    private String imageUrl;
    private  String midireccion;
    private  String mitelefono;
    private  int countpedidos;
    private String mail;


    public User(String id, String username, String imageUrl, String midireccion, String mitelefono, int countpedidos, String mail) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.midireccion = midireccion;
        this.mitelefono = mitelefono;
        this.countpedidos = countpedidos;
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMidireccion() {
        return midireccion;
    }

    public void setMidireccion(String midireccion) {
        this.midireccion = midireccion;
    }

    public String getMitelefono() {
        return mitelefono;
    }

    public void setMitelefono(String mitelefono) {
        this.mitelefono = mitelefono;
    }

    public int getCountpedidos() {
        return countpedidos;
    }

    public void setCountpedidos(int countpedidos) {
        this.countpedidos = countpedidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
