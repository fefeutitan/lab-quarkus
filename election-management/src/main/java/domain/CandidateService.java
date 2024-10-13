package domain;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CandidateService {
    private final CandidateRepository repository;

    public CandidateService(CandidateRepository repository) {
        this.repository = repository;
    }

    public void save(Candidate candidate) {
        repository.save(candidate);
    }

    public List<Candidate> findAll(int pageNumber, int pageSize) {
        return repository.findAll();
    }

    public Candidate findById(String id) {
        return repository.findById(id).orElseThrow(() -> new CandidateNotFoundException("Candidato não encontrado com ID: " + id));
    }

    public List<Candidate> findByQuery(CandidateQuery query) {
    	// Certifique-se de que o repositório tenha esse método
        return repository.findByQuery(query); 
    }

    public boolean delete(String id) {
        boolean deleted = repository.delete(id);
        if (!deleted) {
            throw new CandidateNotFoundException("Candidato não encontrado com ID: " + id);
        }
        return deleted;
    }

}
