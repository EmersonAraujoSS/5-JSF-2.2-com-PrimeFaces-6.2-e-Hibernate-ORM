package br.com.avancard.model;

import javax.persistence.Entity;
import javax.persistence.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({

        @NamedQuery(name = "UsuarioPessoa.todos", query = "select u from UsuarioPessoa u"),
        @NamedQuery(name = "UsuarioPessoa.buscaPorNome", query = "select u from UsuarioPessoa u where u.nome = :nome")
})

public class UsuarioPessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nome;
    private String sobrenome;
    private String email;
    private String login;
    private String senha;
    private int idade;
    private String sexo;

    @OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.EAGER)
    private List<TelefoneUser> telefoneUsers;


    //MÉTODOS ESPECIAIS
    public void setTelefoneUsers(List<TelefoneUser> telefoneUsers) {
        this.telefoneUsers = telefoneUsers;
    }
    public List<TelefoneUser> getTelefoneUsers() {
        return telefoneUsers;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPessoa that = (UsuarioPessoa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



    @Override
    public String toString() {
        return "UsuarioPessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", idade=" + idade +
                '}';
    }

}
