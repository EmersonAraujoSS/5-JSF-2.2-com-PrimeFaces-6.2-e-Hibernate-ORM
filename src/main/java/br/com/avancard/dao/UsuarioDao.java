package br.com.avancard.dao;

import br.com.avancard.model.UsuarioPessoa;

public class UsuarioDao<E> extends GenericDao<UsuarioPessoa> {

    public void removerUsuario(UsuarioPessoa pessoa) throws Exception {

        //getEntityManager().getTransaction().begin();
        //String sqlDeleteFone = "DELETE FROM TelefoneUser WHERE usuarioPessoa_id = " + pessoa.getId();
        //getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate(); //executeUpdate = faz tanto a atualização quanto o delete
        //getEntityManager().getTransaction().commit();

        //super.deletarPoId(pessoa);


        getEntityManager().getTransaction().begin();
        getEntityManager().remove(pessoa);
        getEntityManager().getTransaction().commit();
    }
}
