package gateway.route;

import java.util.List;
import java.util.Random;

/**
 * @author ztz
 * @description TODO
 * @date 2020/11/3 22:31
 */
public class RandomRoute implements HttpEndpointRouter {
    private final static Random random = new Random();

    @Override
    public String route(List<String> endpoints) {
        if ( endpoints == null || endpoints.isEmpty()) {
            return "127.0.0.1";
        }
        int idx = random.nextInt(endpoints.size());
        return endpoints.get(idx);
    }
}
