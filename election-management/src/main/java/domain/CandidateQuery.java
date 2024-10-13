package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CandidateQuery {
    private final List<String> ids;
    private final Optional<String> name;

    private CandidateQuery(Builder builder) {
        this.ids = builder.ids;
        this.name = builder.name;
    }

    public List<String> getIds() {
        return ids;
    }

    public Optional<String> getName() {
        return name;
    }

    public static class Builder {
        private List<String> ids;
        private Optional<String> name = Optional.empty();

        public Builder ids(Set<String> set) {
            this.ids = new ArrayList<>(set); // Convert Set to List
            return this;
        }

        public Builder name(String name) {
            this.name = Optional.ofNullable(name);
            return this;
        }

        public CandidateQuery build() {
            return new CandidateQuery(this);
        }
    }
}
