import br.com.avancard.dao.GenericDao;
import br.com.avancard.model.TelefoneUser;
import br.com.avancard.model.UsuarioPessoa;
import org.junit.Test;

import java.util.List;

public class TesteHibernate {

    @Test
    public void testeHibernateUtil() {
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();

        UsuarioPessoa pessoa = new UsuarioPessoa();

        pessoa.setIdade(21);
        pessoa.setLogin("teste");
        pessoa.setNome("Castelo");
        pessoa.setSenha("12345677");
        pessoa.setSobrenome("Teste");
        pessoa.setEmail("java.avancado@gmail.com");

        genericDao.salvar(pessoa);
    }

    @Test
    public void testeBuscar2() {
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();
        UsuarioPessoa pessoa = genericDao.pesquisar(1l, UsuarioPessoa.class);

        System.out.println(pessoa);
    }

    @Test
    public void testeUpdate() {
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();
        UsuarioPessoa pessoa = genericDao.pesquisar(2l, UsuarioPessoa.class);

        pessoa.setIdade(99);
        pessoa.setNome("Nome atualizado hibernate");

        pessoa = genericDao.updateMerge(pessoa);

        System.out.println(pessoa);
    }

    @Test
    public void testeDelete() {
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();
        UsuarioPessoa pessoa = genericDao.pesquisar(5l, UsuarioPessoa.class);

        genericDao.deletarPoId(pessoa);
    }

    @Test
    public void testeConsultar() {
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();

        List<UsuarioPessoa> list = genericDao.listar(UsuarioPessoa.class);

        for (UsuarioPessoa usuarioPessoa : list) {
            System.out.println(usuarioPessoa);
            System.out.println("_______________________________________");
        }
    }

    @Test
    public void testeQueryList(){
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();
        List<UsuarioPessoa> list = genericDao.getEntityManager().createQuery("from UsuarioPessoa ").getResultList();

        for (UsuarioPessoa usuarioPessoa : list) {
            System.out.println(usuarioPessoa);
        }

    }

    @Test
    public void testeQueryListMaxResult(){
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();
        List<UsuarioPessoa> list = genericDao.getEntityManager().createQuery("from UsuarioPessoa order by id").setMaxResults(4).getResultList();

        for (UsuarioPessoa usuarioPessoa : list) {
            System.out.println(usuarioPessoa);
        }

    }

    @Test
    public void testeQueryListParameter(){    //
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();

        List<UsuarioPessoa> list = genericDao.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome") .
                setParameter("nome","Castelo").getResultList();

        for (UsuarioPessoa usuarioPessoa : list) {
            System.out.println(usuarioPessoa);
        }
    }
    //Anotação @Test:
    //

    //A anotação @Test é parte do framework JUnit e indica que o método é um caso de teste.

    //Método testeQueryListParameter():
    //
    //Este método é responsável por realizar o teste. Ele usa o Hibernate para fazer uma consulta no banco de dados.

    //Instanciando GenericDao<UsuarioPessoa>:
    //

    //Cria uma instância da classe GenericDao parametrizada com UsuarioPessoa. Isso indica que a consulta será feita na entidade UsuarioPessoa.

    //Criando uma Consulta com Parâmetros:
    //
    //genericDao.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome"): Isso cria uma consulta HQL (Hibernate Query Language) que seleciona entidades UsuarioPessoa onde o campo nome é igual a um parâmetro nomeado :nome. Essa consulta será usada para buscar os registros no banco de dados.

    //Definindo Parâmetros:
    //
    //.setParameter("nome","Castelo"): Isso define o valor do parâmetro :nome na consulta como "Castelo". Isso é uma prática segura para evitar injeção de SQL e permite que você parametrize suas consultas.

    //Obtendo Resultados:
    //
    //.getResultList(): Executa a consulta no banco de dados e retorna uma lista de objetos UsuarioPessoa que atendem aos critérios da consulta.

    //Iterando e Imprimindo Resultados:
    //
    //for (UsuarioPessoa usuarioPessoa : list) { System.out.println(usuarioPessoa); }: Itera sobre a lista de resultados e imprime cada objeto UsuarioPessoa. O método toString() da classe UsuarioPessoa geralmente foi implementado para fornecer uma representação legível do objeto.

    @Test
    public void testeQuerySomaIdade(){
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();

        Long somaIdade = (Long) genericDao.getEntityManager().
                createQuery("select sum (u.idade) from UsuarioPessoa u ").getSingleResult();

        System.out.println("Soma de todas as idades é: " +somaIdade);
    }

    @Test
    public void testeNameQyery1(){
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();

        List<UsuarioPessoa> list = genericDao.getEntityManager().createNamedQuery("UsuarioPessoa.todos").getResultList();

        for (UsuarioPessoa usuarioPessoa : list)
            System.out.println(usuarioPessoa);
    }

    @Test
    public void testeNameQyery2(){
        GenericDao<UsuarioPessoa> genericDao = new GenericDao<UsuarioPessoa>();

        List<UsuarioPessoa> list = genericDao.getEntityManager().createNamedQuery("UsuarioPessoa.buscaPorNome")
                .setParameter("nome", "Emerson Araujo")
                .getResultList();

        for (UsuarioPessoa usuarioPessoa : list)
            System.out.println(usuarioPessoa);
    }


    @Test
    public void testeGravaTelefone() {
        GenericDao genericDao = new GenericDao();

        // Busca uma instância de UsuarioPessoa por ID
        UsuarioPessoa pessoa = (UsuarioPessoa) genericDao.pesquisar(7L, UsuarioPessoa.class);

        // Cria uma instância de TelefoneUser
        TelefoneUser telefoneUser = new TelefoneUser();
        telefoneUser.setTipo("Celular");
        telefoneUser.setNumero("759759759");

        // Configura a referência para UsuarioPessoa
        telefoneUser.setUsuarioPessoa(pessoa);

        // Salva a instância de TelefoneUser
        genericDao.salvar(telefoneUser);
    }

    @Test
    public void testeConsultaTelefones() {
        GenericDao genericDao = new GenericDao();

        UsuarioPessoa pessoa = (UsuarioPessoa) genericDao.pesquisar(7L, UsuarioPessoa.class);

        for (TelefoneUser fone : pessoa.getTelefoneUsers()){
            System.out.println(fone.getNumero());
            System.out.println(fone.getTipo());
            System.out.println(fone.getUsuarioPessoa().getNome());
            System.out.println("_________________________________________");
        }

    }
}
