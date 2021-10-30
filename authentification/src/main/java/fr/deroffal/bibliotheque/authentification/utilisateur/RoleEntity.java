package fr.deroffal.bibliotheque.authentification.utilisateur;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "role_applicatif")
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "seq_role_generator", sequenceName = "seq_role", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "role_name")
    private String role;
}
