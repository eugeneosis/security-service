package com.ru.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Пожалуйста, введите Ваш адрес электронной почты")
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 8, message = "Пароль должен состоять не менее чем из 8 символов")
    private String password;

    @Column(name = "name", nullable = false)
    @Length(min = 3, message = "Имя должно состоять не менее чем из 3 символов")
    private String name;

    @Column(name = "last_name", nullable = false)
    @Length(min = 3, message = "Фамилия должна состоять не менее чем из 3 символов")
    private String lastName;

    @Column(name = "active")
    private Boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}