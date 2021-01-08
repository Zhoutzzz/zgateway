package gateway.route;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ztz
 * @description TODO
 * @date 2020/11/4 20:40
 */
public class RouteFactory {
    private static final Map<RouteStrategy, HttpEndpointRouter> routes = new HashMap<>();

    static {
        routes.put(RouteStrategy.DEFAULT, new RandomRoute());
        routes.put(RouteStrategy.RANDOM, new RandomRoute());
    }

    public static HttpEndpointRouter getRoute(RouteStrategy strategy) {
        if (routes.containsKey(strategy)) {
            return routes.get(strategy);
        } else {
            return routes.get(RouteStrategy.DEFAULT);
        }
    }
}
