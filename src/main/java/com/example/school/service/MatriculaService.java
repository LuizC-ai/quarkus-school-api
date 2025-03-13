package com.example.school.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/*
 * A anotação @ApplicationScoped é usada para especificar que a classe
 * será um bean CDI. Isto significa que, a classe será gerenciada pelo 
 * container CDI. O container CDI irá instanciar a classe e irá injetar
 * a instância onde quer que seja necessário.
 * O container CDI é uma parte do Jakarta EE que fornece injeção de dependência
 * e um ciclo de vida de componentes.
 */
@ApplicationScoped
public class MatriculaService {

    /*
     * A anotação @Inject é usada para injetar uma dependência.
     * Neste caso, estamos injetando uma dependência do tipo EntityManager. Mas o que é EntityManager?
     * EntityManager é uma interface que é usada para interagir com o banco de dados. COMO?
     * EntityManager é uma interface que fornece métodos para fazer operações de persistência
     * de dados. Isto significa que, podemos usar o EntityManager para fazer operações de persistência. Mas como ele se conecta ao banco de dados?
     * 
     * EntityManager é uma interface que é implementada por um provedor de persistência. O provedor de persistência é uma implementação
     * da especificação JPA. Isto significa em simples palavras que o provedor de persistência é uma implementação da especificação JPA.
     */
    @Inject
    EntityManager entityManager;

    /*
     * A anotação @Transactional é usada para especificar que o método será executado dentro de uma transação.
     * Isto significa que, se o método lançar uma exceção, a transação será revertida. Se o método for executado com sucesso, a transação será commitada.
     * Isto significa que, se o método for executado com sucesso, as alterações feitas no banco de dados serão persistidas. Se o método lançar uma exceção, as alterações feitas no banco de dados serão revertidas.
     * Isto significa que, se o método lançar uma exceção, as alterações feitas no banco de dados serão revertidas.
     * Agora em entityManager usamos o createNativeQuery para criar uma query nativa. Mas o que é uma query nativa?
     * Uma query nativa é uma query SQL que é executada diretamente no banco de dados. 
     * Isto significa que, a query é executada diretamente no banco de dados. Mas como?
     * Passamos um INSERT INTO aluno_materia (aluno_id, materia_id), isto significa que estamos criando campos de aluno_id e materia_id
     * Passando VALUES, mas porque usamos :aluno e :materia?
     */
    @Transactional
    public void associarAlunoMateria(Long alunoId, Long materiaId) {
        entityManager.createNativeQuery("INSERT INTO aluno_materia (aluno_id, materia_id) VALUES (:aluno, :materia)")
            .setParameter("aluno", alunoId)
            .setParameter("materia", materiaId)
            .executeUpdate();
    }

    /**
     * Remove a associação entre um aluno e uma matéria
     */


     /*
      * O uso de : significa que estamos passando um parâmetro para a query.
      * Isto significa que, estamos passando um parâmetro chamado aluno e um parâmetro chamado materia.
      * Mas como passamos os valores para os parâmetros aluno e materia? Usamos o método setParameter.
      * O método setParameter é usado para passar valores para os parâmetros da query.
      * Isto significa que, estamos passando o valor de alunoId para o parâmetro aluno e o valor de materiaId para o parâmetro materia.
      * por exemplo, 
      * query.setParameter("aluno", 5); // agora aluno_id = 5 
      * query.setParameter("materia", 10); // materia_id = 10
      */


      /*
       Exemplo Prático: Situação: Você deseja remover a associação do aluno com ID = 3 e matéria ID = 2.
       entityManager.createNativeQuery("DELETE FROM aluno_materia WHERE aluno_id = :aluno AND materia_id = :materia")
        .setParameter("aluno", alunoId)     // aqui vai o ID do aluno, ex.: 3
        .setParameter("materia", materiaId) // aqui vai o ID da matéria, ex: 10
        .executeUpdate();

        Se na sua aplicação tem alunoId = 3; materiaId = 10;
        Então: DELETE FROM aluno_materia WHERE aluno_id = 3 AND materia_id = 10;

        NÃO é: .setParameter(3, alunoId) ou .setParameter(10, materiaId).

        FORMA CORRETA:
        .setParameter("aluno", 3)
        .setParameter("materia", 10)


        Analogia simples para reforçar:
        "Caro(a) :nome, você está matriculado(a) na matéria :materia."

        Você preencheria usando algo assim:
        .setParameter("aluno", "Luiz")
        .setParameter("materia", "Inglês")

        Você não coloca diretamente o valor no nome do parâmetro. O nome é fixo, o valor é variável.

       */

    @Transactional
    public void desassociarAlunoMateria(Long alunoId, Long materiaId) {
        entityManager.createNativeQuery("DELETE FROM aluno_materia WHERE aluno_id = :aluno AND materia_id = :materia")
            .setParameter("aluno", alunoId)
            .setParameter("materia", materiaId)
            .executeUpdate();
    } 

    /**
     Por que usar COUNT?
     Quando queremos saber se algo existe ou não em uma tabela, podemos contar as linhas que satisfazem uma determinada condição:
     Se o número de linhas (COUNT) for maior que zero, significa que existe essa associação.
     Se for zero, não existe essa associação.

     Mas repare: seu exemplo não tem um "COUNT" explícito, apenas .getSingleResult(). No caso, o Hibernate ORM, ao executar:
     SELECT COUNT(*) FROM aluno_materia WHERE aluno_id = 3 AND materia_id = 10
        Vai retornar um número, que é o resultado do COUNT. Se for 0, 
        não existe a associação. Se for maior que 0, existe a associação.

    Entendendo essa query em SQL puro:
    SELECT COUNT(*) FROM aluno_materia
    WHERE aluno_id = :aluno AND materia_id = :materia;

    Essa query verifica quantas linhas existem na tabela que correspondem ao aluno e à matéria especificados.
    COUNT(*) conta o número de linhas encontradas.


    Por que count > 0?
    Se o count for maior que zero, significa que já existe um registro associando aquele aluno àquela matéria.
    Caso contrário (zero), significa que não há registro e o aluno ainda não está matriculado nessa matéria

    Por que o uso do tipo Long (ou Integer) e não String?
    No banco de dados:

    id SERIAL gera um número inteiro automaticamente. É uma boa prática usar números como identificadores porque,
    são mais eficientes e rápidos para o banco de dados consultar.

    Usar String como identificador tornaria buscas mais lentas e menos eficientes.

    O que significa .getSingleResult()? Este método diz para o Hibernate ORM: "execute a query e me dê um único resultado".
    Se a query retornar mais de um resultado, o método lançará uma exceção. Se a query não retornar nenhum resultado, o método lançará uma exceção.


    🔑 Resumo completo dos conceitos importantes (para fixar):
            COUNT(*) → conta quantos registros satisfazem uma condição.
            Parâmetros nomeados (:param) → valores substituídos em tempo de execução com segurança.
            .getSingleResult() → retorna exatamente um resultado único.
            return count > 0; → verifica se existe pelo menos um registro correspondente, indicando existência.
            Não é ideal usar String como ID (normalmente usamos números sequenciais como Long ou Integer).
     */
    
    public boolean isAlunoMatriculado(Long alunoId, Long materiaId) {
        Long count = (Long) entityManager.createNativeQuery(
            "SELECT COUNT(1) FROM aluno_materia WHERE aluno_id = :aluno AND materia_id = :materia")
            .setParameter("aluno", alunoId)
            .setParameter("materia", materiaId)
            .getSingleResult();

            return count > 0;
    }
}
