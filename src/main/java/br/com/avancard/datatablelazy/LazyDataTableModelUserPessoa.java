package br.com.avancard.datatablelazy;

import br.com.avancard.dao.GenericDao;
import br.com.avancard.dao.UsuarioDao;
import br.com.avancard.model.UsuarioPessoa;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LazyDataTableModelUserPessoa<T> extends LazyDataModel<UsuarioPessoa> {

    //ATRIBUTOS
    private UsuarioDao<UsuarioPessoa> dao = new UsuarioDao<UsuarioPessoa>();
    public List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();

    private String sql = " from UsuarioPessoa ";



    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return 0;
    }

    @Override
    public List<UsuarioPessoa> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

        list = GenericDao.getEntityManager().createQuery(getSql())
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
        sql = " from UsuarioPessoa ";

        setPageSize(pageSize);

        Integer qtdRegistro = Integer.parseInt(GenericDao.getEntityManager().createQuery("select count(1) " + getSql()).getSingleResult().toString());
        setRowCount(qtdRegistro);

        return list;
    }


    //MÉTODOS
    public void pesquisa(String campoPesquisa){
        sql += " where nome like '%" + campoPesquisa + "%'";
    }


    //MÉTODOS ESPECIAIS
    public String getSql() {
        return sql;
    }
    public List<UsuarioPessoa> getList() {
        return list;
    }
}
