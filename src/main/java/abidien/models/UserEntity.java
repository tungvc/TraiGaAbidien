package abidien.models;

import javax.persistence.*;

/**
 * Created by ABIDIEN on 03/07/2016.
 */
@Entity
@Table(schema = "chuong_ga", catalog = "")
public class UserEntity implements IItem<Integer> {
    private Integer id;
    private String name;
    private String email;
    private String avatar;
    private String password;

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserEntity() {
    }


    @Id @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id; }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (disable != that.disable) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (!email.equals(that.email)) return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
        return password.equals(that.password);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + password.hashCode();
        result = 31 * result + (disable ? 1 : 0);
        return result;
    }

    private boolean disable;

    @Basic
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
