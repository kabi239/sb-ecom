package com.ecommerce.sb_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ToString.Exclude
    @OneToMany(mappedBy = "user" , cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    orphanRemoval = true) // if a user is deleted all the product will become orphan
    // or not mapped with anything, by using orphanRemoval ,the product associated will also be removed
    private Set<Product> products = new HashSet<>();

    // User is the owner of this relationship
    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name= "user_address", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();
}
