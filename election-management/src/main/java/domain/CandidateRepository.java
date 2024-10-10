package domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface para operações de repositório relacionadas a candidatos.
 */
public interface CandidateRepository {

    /**
     * Salva uma lista de candidatos.
     *
     * @param candidates A lista de candidatos a serem salvos.
     */
    void save(List<Candidate> candidates);

    /**
     * Salva um único candidato.
     *
     * @param candidate O candidato a ser salvo.
     */
    default void save(Candidate candidate) {
        save(List.of(candidate));
    }

    /**
     * Encontra candidatos com base nos critérios especificados na consulta.
     *
     * @param query A consulta com critérios de busca.
     * @return Uma lista de candidatos que correspondem aos critérios.
     */
    List<Candidate> find(CandidateQuery query);

    /**
     * Encontra todos os candidatos.
     *
     * @return Uma lista de todos os candidatos.
     */
    default List<Candidate> findAll() {
        return find(new CandidateQuery.Builder().build());
    }

    /**
     * Encontra um candidato pelo seu ID.
     *
     * @param id O ID do candidato a ser encontrado.
     * @return Um Optional contendo o candidato, se encontrado.
     */
    default Optional<Candidate> findById(String id) {
        CandidateQuery query = new CandidateQuery.Builder().ids(Set.of(id)).build();
        return find(query).stream().findFirst();
    }

    /**
     * Remove um candidato pelo seu ID.
     *
     * @param id O ID do candidato a ser removido.
     */
    void delete(String id);
}
