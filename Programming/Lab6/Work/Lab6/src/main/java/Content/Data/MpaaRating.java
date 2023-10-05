package Content.Data;

public enum MpaaRating {
    G("general audiences"),
    PG("parental guidance suggested"),
    R("restricted, no children under 17"),
    NC_17("no one 17 and under admitted");
    public String label;

    MpaaRating(String label) {
        this.label = label;
    }
}
