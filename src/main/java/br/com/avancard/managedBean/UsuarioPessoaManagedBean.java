package br.com.avancard.managedBean;

import br.com.avancard.dao.GenericDao;
import br.com.avancard.dao.UsuarioDao;
import br.com.avancard.model.UsuarioPessoa;

import javax.annotation.PostConstruct;
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
    private List<UsuarioPessoa> pessoaList = new ArrayList<>();
    private UsuarioDao<UsuarioPessoa> genericDao = new UsuarioDao<UsuarioPessoa>();


    //METODOS //Todo método em Jsf pode se fazer retornando uma String consigo fazer ficar na mesma tela ou redirecionar para outra tela
    @PostConstruct //PostConstruct = vai servir para que sempre que o UsuarioPessoaManagedBean for construído na memória, ele vai executar esse método apenas uma vez
    public void init(){
        pessoaList = genericDao.listar(UsuarioPessoa.class);
    }

    public String salvar() {

        genericDao.salvar(usuarioPessoa);
        pessoaList.add(usuarioPessoa);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Salvo com sucesso"));
        return "";
    }

    public String novo(){

        usuarioPessoa = new UsuarioPessoa();
        return "";
    }

    public String remover() throws Exception {

        try {
            genericDao.removerUsuario(usuarioPessoa);
            pessoaList.remove(usuarioPessoa);
            usuarioPessoa = new UsuarioPessoa();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Removido com sucesso"));
        }catch (Exception e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Existem telefones para o usuário"));
            }
            else {
                e.printStackTrace();
            }
        }
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
        return pessoaList;
    }
}
