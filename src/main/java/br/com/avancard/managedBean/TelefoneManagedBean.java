package br.com.avancard.managedBean;

import br.com.avancard.dao.TelefoneDao;
import br.com.avancard.dao.UsuarioDao;
import br.com.avancard.model.TelefoneUser;
import br.com.avancard.model.UsuarioPessoa;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

    //ATRUBUTOS
    private UsuarioPessoa user = new UsuarioPessoa();
    private UsuarioDao<UsuarioPessoa> daoUser = new UsuarioDao<UsuarioPessoa>();
    private TelefoneDao<TelefoneUser> telefoneDao = new TelefoneDao<TelefoneUser>();
    private TelefoneUser telefone = new TelefoneUser();


    //MÉTODOS
    @PostConstruct //PostConstruct = vai servir para que sempre que o UsuarioPessoaManagedBean for construído na memória, ele vai executar esse método apenas uma vez
    public void init(){

        String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigouser");
        user = daoUser.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);
    }

    public String salvar(){ // o método retornando uma String faz com que eu consiga retornar fazio e continuar na mesama tela
        telefone.setUsuarioPessoa(user);
        telefoneDao.salvar(telefone);
        telefone = new TelefoneUser();
        user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação: ", "Telefone salvo com sucesso!"));

        return "";
    }

    public String removeTelefone() throws Exception {

        telefoneDao.deletarPoId(telefone);
        user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
        telefone = new TelefoneUser();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação: ", "Telefone removido com sucesso!"));

        return "";

    }



    //MÉTODOS ESPECIAIS
    public UsuarioPessoa getUser() {
        return user;
    }
    public void setUser(UsuarioPessoa user) {
        this.user = user;
    }
    public TelefoneDao<TelefoneUser> getTelefoneDao() {
        return telefoneDao;
    }
    public void setTelefoneDao(TelefoneDao<TelefoneUser> telefoneDao) {
        this.telefoneDao = telefoneDao;
    }
    public TelefoneUser getTelefone() {
        return telefone;
    }
    public void setTelefone(TelefoneUser telefone) {
        this.telefone = telefone;
    }

}
