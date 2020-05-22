public enum PlanetType {
    LUNAR("lunar"),
    PLANET("planet"),
    COMET("comet"),
    ARTIFICIAL("artificial");

    private String name;
    PlanetType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
