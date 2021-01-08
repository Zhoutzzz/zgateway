package gateway.route;

public enum RouteStrategy {
    DEFAULT("default"), RANDOM("random");

    private String name;

    RouteStrategy(String name) {
        this.name = name;
    }

    public static RouteStrategy getStrategy(String strategyName) {
        for (RouteStrategy value : RouteStrategy.values()) {
            if (value.name.equalsIgnoreCase(strategyName)) {
                return value;
            }
        }

        return DEFAULT;
    }

//    public static void addStrategy(String name) {
//        new RouteStrategy(name);
//    }
}
