package za.co.carhire.domain.vehicle;

public enum CarCondition {
    EXCELLENT("Excellent condition - like new"),
    GOOD("Good condition - well maintained"),
    FAIR("Fair condition - minor wear and tear"),
    NEEDS_SERVICE("Needs service or repair"),
    POOR("Poor condition - requires significant work");

    private final String description;

    CarCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
