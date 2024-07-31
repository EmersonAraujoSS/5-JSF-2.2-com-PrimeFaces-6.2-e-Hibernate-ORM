JSF-2.2-PrimeFaces6.2-HibernateORM

Nesse projeto tenho uma classe de teste também(TesteHibernate): 
-- A anotação @Test é parte do framework JUnit e indica que o método é um caso de teste. 
-- Método testeQueryListParameter(): Este método é responsável por realizar o teste. 
    Ele usa o Hibernate para fazer uma consulta no banco de dados. 
-- Instanciando GenericDao: Cria uma instância da classe GenericDao parametrizada com UsuarioPessoa. 
    Isso indica que a consulta será feita na entidade UsuarioPessoa. 
-- Criando uma Consulta com Parâmetros: 
    genericDao.getEntityManager().createQuery("from UsuarioPessoa where nome = :nome"): 
    Isso cria uma consulta HQL (Hibernate Query Language) que seleciona entidades UsuarioPessoa 
    onde o campo nome é igual a um parâmetro nomeado :nome. 
    Essa consulta será usada para buscar os registros no banco de dados. 
-- Definindo Parâmetros: .setParameter("nome","Castelo"): 
    Isso define o valor do parâmetro :nome na consulta como "Castelo". 
    Isso é uma prática segura para evitar injeção de SQL e permite que você parametrize suas consultas. 
-- Obtendo Resultados: .getResultList(): 
    Executa a consulta no banco de dados e retorna uma lista de objetos UsuarioPessoa que atendem aos critérios da consulta. 
-- Iterando e Imprimindo Resultados: 
    for (UsuarioPessoa usuarioPessoa : list) { System.out.println(usuarioPessoa); }: 
    Itera sobre a lista de resultados e imprime cada objeto UsuarioPessoa. 
    O método toString() da classe UsuarioPessoa geralmente foi implementado para fornecer uma representação legível do objeto.
