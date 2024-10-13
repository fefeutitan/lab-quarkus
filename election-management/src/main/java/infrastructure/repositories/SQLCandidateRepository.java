package infrastructure.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {
    private final EntityManager entityManager;

    public SQLCandidateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
        candidates.stream()
                  .map(infrastructure.repositories.entities.Candidate::fromDomain)
                  .forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(infrastructure.repositories.entities.Candidate.class);
        var root = cq.from(infrastructure.repositories.entities.Candidate.class);

        cq.select(root).where(conditions(query, cb, root));

        return entityManager.createQuery(cq)
                            .getResultStream()
                            .map(infrastructure.repositories.entities.Candidate::toDomain)
                            .toList();
    }

    private Predicate[] conditions(CandidateQuery query, CriteriaBuilder cb, Root<infrastructure.repositories.entities.Candidate> root) {
    	return Stream.of(
    			query.getIds().stream().map(id -> cb.in(root.get("id")).value(id)),

    			query.getName().stream().map(name -> 
    			cb.or(cb.like(cb.lower(root.get("familyName")), name.toLowerCase() + "%"),
    					cb.like(cb.lower(root.get("givenName")), name.toLowerCase() + "%"))
    					)
    			)
    			.flatMap(s -> s) 
    			.toArray(Predicate[]::new);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        var candidate = entityManager.find(infrastructure.repositories.entities.Candidate.class, id);
        if (candidate != null) {
            entityManager.remove(candidate);
            return true;
        }
        return false;
    }


    @Override
    public List<Candidate> findByQuery(CandidateQuery query) {
        return find(query);
    }

}
