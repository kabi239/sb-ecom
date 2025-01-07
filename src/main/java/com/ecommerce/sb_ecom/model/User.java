package com.ecommerce.sb_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users" , uniqueConstraints =
        {@UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.EAGER) //PERSIST: When a User is saved, any new Role instances in the roles set will also be saved automatically.
    //MERGE: Updates to the User entity will propagate changes to associated Role entities.
    //many to many because -
    //A User can have multiple Roles (e.g., ADMIN, SELLER, BUYER).
    //A Role can be assigned to multiple Users.
    //Ensures that roles are loaded immediately whenever a User is fetched
    @JoinTable(name = "user_role",   // The join table acts as a bridge that connects the two entities and also avoids duplicating data.
               joinColumns = @JoinColumn(name = "user_id"), //-> refers to the primary key of user Entity
                inverseJoinColumns = @JoinColumn(name = "role_id")) // ->refers to the primary key of role Entity
    private Set<Role> roles = new HashSet<>();
    //Represents the collection of roles assigned to a user.
    //The Set ensures no duplicate roles are assigned to the same user.
}
