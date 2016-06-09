package cr.ac.itcr.bnbank.model;

/**
 * Created by usuario on 7/6/2016.
 */
public class User {
    String user;
    String password;
    String _id;
    String email;
    String type;

    public User(String user, String password, String _id, String email,String type) {
        this.user = user;
        this.password = password;
        this._id = _id;
        this.email = email;
        this.type = type;

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

    public void setEmail(String type) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
