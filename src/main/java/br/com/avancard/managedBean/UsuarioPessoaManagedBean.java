package br.com.avancard.managedBean;

import br.com.avancard.dao.EmailDao;
import br.com.avancard.dao.UsuarioDao;
import br.com.avancard.model.EmailUser;
import br.com.avancard.model.UsuarioPessoa;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
    private List <UsuarioPessoa> pessoaList = new ArrayList<>();
    private UsuarioDao<UsuarioPessoa> genericDao = new UsuarioDao<UsuarioPessoa>();
    private BarChartModel barChartModel = new BarChartModel(); //BarChartModel = uso para a construção de gráficos
    private EmailUser emailUser = new EmailUser();
    private EmailDao<EmailUser> emailDao = new EmailDao<EmailUser>();
    private String campoPesquisa;



    //METODOS //Todo método em Jsf pode se fazer retornando uma String consigo fazer ficar na mesma tela ou redirecionar para outra tela
    @PostConstruct //PostConstruct = vai servir para que sempre que o UsuarioPessoaManagedBean for construído na memória, ele vai executar esse método apenas uma vez
    public void init(){
        pessoaList = genericDao.listar(UsuarioPessoa.class);
        montarGrafico();

    }


    public void montarGrafico(){
        barChartModel = new BarChartModel();
        ChartSeries userSalario = new ChartSeries(); //GRUPO DE FUNCIONARIOS

        for(UsuarioPessoa usuarioPessoa : pessoaList){ //nesse For eu estou construindo meu gráfico de salários (ADD O SALARIO PARA O GRUPO)
            userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario()); //ADD SALARIOS
        }
        barChartModel.addSeries(userSalario); //ADD O GRUPO
        barChartModel.setTitle("Gráfico de salários");
    }

    public String salvar() {

        genericDao.salvar(usuarioPessoa);
        pessoaList.add(usuarioPessoa);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
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
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
        }catch (Exception e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Existem telefones para o usuário!"));
            }
            else {
                e.printStackTrace();
            }
        }
        return "";
    }


    private void mostrarMsg(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(msg);
        context.addMessage(null, message);
    }


    public void addEmail(){
        emailUser.setUsuarioPessoa(usuarioPessoa);
        emailUser = emailDao.updateMerge(emailUser);
        usuarioPessoa.getEmailUsers().add(emailUser);
        emailUser = new EmailUser();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
    }


    public void removerEmail() throws Exception {
        String codigoemail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoemail");
        EmailUser remover = new EmailUser();
        remover.setId(Long.parseLong(codigoemail));
        emailDao.deletarPoId(remover);
        usuarioPessoa.getEmailUsers().remove(remover);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
    }



    public void upload(FileUploadEvent image){  //CONVERTENDO IMAGEM EM BASE 64
        String imagem = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
        usuarioPessoa.setImagem(imagem);
    }



    public void download() throws IOException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String fileDownloadId = params.get("fileDownloadId");

        UsuarioPessoa pessoa = genericDao.pesquisar(Long.parseLong(fileDownloadId), UsuarioPessoa.class);

        byte[] imagem = Base64.decodeBase64(pessoa.getImagem().split("\\,")[1]);

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-Disposition", "attachment; filename=download.png");
        response.setContentType("application/octet-stream");
        response.setContentLength(imagem.length);
        response.getOutputStream().write(imagem);
        response.getOutputStream().flush();
        FacesContext.getCurrentInstance().responseComplete();
    }


    public void pesquisacep(AjaxBehaviorEvent event) {

        try {
            URL url = new URL("https://viacep.com.br/ws/" + usuarioPessoa.getCep() + "/json/"); //consumindo WEB SERVICE DE CEP
            URLConnection connection = url.openConnection(); //URLConnection = iniciando minha conexão de cep
            InputStream inputStream = connection.getInputStream();  //InputStream = executa as configurações no servidor e retorna a informação
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)); //BufferedReader = vai ser usado para fazer leitura de fluxo de dados

            String cep = "";
            StringBuilder jsonCep = new StringBuilder(); //StringBuilder = facilita a criação e manipulação de strings de maneira eficiente.

            while ((cep = br.readLine()) != null) {
                jsonCep.append(cep);  //append = adicionar um novo elemento ao final da lista.
            }

            UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
            usuarioPessoa.setCep(userCepPessoa.getCep());
            usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
            usuarioPessoa.setComplemento(userCepPessoa.getComplemento());
            usuarioPessoa.setUf(userCepPessoa.getUf());
            usuarioPessoa.setBairro(userCepPessoa.getBairro());
            usuarioPessoa.setLocalidade(userCepPessoa.getLocalidade());
            usuarioPessoa.setUnidade(userCepPessoa.getUnidade());
            usuarioPessoa.setIbge(userCepPessoa.getIbge());
            usuarioPessoa.setGia(userCepPessoa.getGia());

        }catch (Exception e) {
            e.printStackTrace();
            mostrarMsg("Erro ao consultar o CEP");
        }
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
    public void setPessoaList(List<UsuarioPessoa> pessoaList) {
        this.pessoaList = pessoaList;
    }
    public BarChartModel getBarChartModel() {
        return barChartModel;
    }
    public EmailUser getEmailUser() {
        return emailUser;
    }
    public void setEmailUser(EmailUser emailUser) {
        this.emailUser = emailUser;
    }
    public String getCampoPesquisa() {
        return campoPesquisa;
    }
    public void setCampoPesquisa(String campoPesquisa) {
        this.campoPesquisa = campoPesquisa;
    }
}
