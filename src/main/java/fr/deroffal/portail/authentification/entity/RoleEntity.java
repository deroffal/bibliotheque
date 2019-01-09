package fr.deroffal.portail.authentification.entity;

import javax.persistence.*;

import static fr.deroffal.portail.PortailConfiguration.SCHEMA_AUTHENTIFICATION;

@Entity
@Table(name = "role_applicatif", schema = SCHEMA_AUTHENTIFICATION)
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "seq_role_generator", sequenceName = "seq_role", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "role_name")
    private String role;

    public RoleEntity() {
        super();
    }

    public RoleEntity(final String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

}
