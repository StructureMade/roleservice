package de.structuremade.ms.roleservice.util.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", schema = "services", indexes = {
        @Index(name = "id_userid", columnList = "id", unique = true),
        @Index(name = "id_activationcode", columnList = "activationcode", unique = true),
        @Index(name = "id_email", columnList = "email", unique = true)})
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String name;

    @Column
    private String password;

    @Column(nullable = false)
    private Date creationDate;

    @Column
    private String activationCode;

    @Column(nullable = false)
    private boolean verified;

    @Column
    private String lastSchool;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "userroles", schema = "services", joinColumns = @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "fk_userid"))
            , inverseJoinColumns = @JoinColumn(name = "roleid", foreignKey = @ForeignKey(name = "fk_roleid")))
    private List<Role> roles = new ArrayList<>();
}
