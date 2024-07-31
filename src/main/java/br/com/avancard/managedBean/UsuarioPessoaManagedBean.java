package br.com.avancard.managedBean;

import br.com.avancard.dao.GenericDao;
import br.com.avancard.model.UsuarioPessoa;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
    private GenericDao<UsuarioPessoa> genericDao = new GenericDao<>();
    private List<UsuarioPessoa> pessoaList = new ArrayList<>();


    //METODOS //Todo método em Jsf pode se fazer retornando uma String consigo fazer ficar na mesma tela ou redirecionar para outra tela
    public String salvar() {

        genericDao.salvar(usuarioPessoa);
        usuarioPessoa = new UsuarioPessoa();
        return "";
    }

    public String novo(){

        usuarioPessoa = new UsuarioPessoa();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação", "Salvo com sucesso"));

        return "";
    }

    public String remover(){
        genericDao.deletarPoId(usuarioPessoa);
        usuarioPessoa = new UsuarioPessoa();
        return "";

    }



    //MÉTODOS ESPECIAIS
    public UsuarioPessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }
    public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }
    public List<UsuarioPessoa> getPessoaList() {
        pessoaList = genericDao.listar(UsuarioPessoa.class);
        return pessoaList;
    }
    public void setPessoaList(List<UsuarioPessoa> pessoaList) {
        this.pessoaList = pessoaList;
    }
}
