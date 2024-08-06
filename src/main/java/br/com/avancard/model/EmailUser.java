package br.com.avancard.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class EmailUser implements Serializable {
    private static final long serialVersionUID = 1L;


    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private UsuarioPessoa usuarioPessoa;



    //MÉTODOS ESPECIAIS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public UsuarioPessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }
    public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailUser emailUser = (EmailUser) o;
        return Objects.equals(id, emailUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
