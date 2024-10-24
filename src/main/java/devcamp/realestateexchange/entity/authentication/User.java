package devcamp.realestateexchange.entity.authentication;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    // Tên đăng nhập
    @Column(name = "username", unique = true)
    private String username;

    // Mật khẩu
    @Column(name = "password")
    private String password;

    // Email
    @Column(name = "email", unique = true)
    private String email;

    // Số điện thoại
    @Column(name = "phone", unique = true)
    private String phone;

    // Quan hệ n-n với Role
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles = new HashSet<>();

    // Trạng thái tài khoản
    @Column(name = "enabled")
    private boolean enabled;
    
    public User(String username, String password, String  email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
