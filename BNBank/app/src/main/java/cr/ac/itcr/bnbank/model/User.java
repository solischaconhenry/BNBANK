package cr.ac.itcr.bnbank.model;

/**
 * Created by usuario on 7/6/2016.
 */
public class User {
    String user;
    String password;
    String _id;
    String email;

    public User(String user, String password, String _id, String email) {
        this.user = user;
        this.password = password;
        this._id = _id;
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
