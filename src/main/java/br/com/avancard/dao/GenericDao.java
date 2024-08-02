package br.com.avancard.dao;

import br.com.avancard.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

public class GenericDao<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static EntityManager entityManager = HibernateUtil.getEntityManager();

    public void salvar(E entidade){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entidade);
        transaction.commit();         //------->  COMMIT é salvar no banco de dados
    }

    public E updateMerge(E entidade){     //------->  UPDATEMERGE salva e atualiza
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        E entidadeSalva = entityManager.merge(entidade);
        transaction.commit();         //------->  COMMIT é salvar no banco de dados

        return entidadeSalva;
    }

    public E pesquisar(Long id, Class<E> entidade){

        entityManager.clear();
        E e = (E) entityManager.createQuery("from " + entidade.getSimpleName() + " where id = " + id).getSingleResult();

        return e;
    }

    public void deletarPoId(E entidade) throws Exception{

        Object id = HibernateUtil.getPrimaryKey(entidade);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.createNativeQuery("delete from " + entidade.getClass().getSimpleName().toLowerCase() + " where id =" + id).executeUpdate();     //faz delete

        transaction.commit();    //GRAVA A ALTERAÇÃO NO BANCO

    }

    public List<E> listar(Class<E> entidade){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();

        transaction.commit();

        return lista;
    }
    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
