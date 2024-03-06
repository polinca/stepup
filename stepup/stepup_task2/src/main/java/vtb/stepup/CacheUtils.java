package vtb.stepup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheUtils {
    public static <T> T cache(T object) {
        return (T) Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new CacheHandler<>(object)
        );
    }

    private static class CacheHandler<T> implements InvocationHandler{
        private final T target;
        private final Map<String, Object> cache = new HashMap<>();
        private final Set<String> mutatorMethods = new HashSet<>();
        private final Set<String> cacheMethods = new HashSet<>();

        public CacheHandler(T target) {
            this.target = target;
            for (Method method : target.getClass().getMethods()) {
                if (method.isAnnotationPresent(Mutator.class)) {
                    mutatorMethods.add(method.getName());
                }
                if (method.isAnnotationPresent(Cache.class)) {
                    cacheMethods.add(method.getName());
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            String methodName = method.getName();

            if (mutatorMethods.contains(methodName)) {
                cache.clear();
                return invokeThis(method, args);
            }
            if (!cacheMethods.contains(methodName)) {
                return invokeThis(method, args);
            }

            if (cache.containsKey(methodName)) {
                return cache.get(methodName);
            } else {
                Object result = invokeThis(method, args);
                cache.put(methodName, result);
                return result;
            }
        }

        private Object invokeThis(Method method, Object[] args) throws Throwable {
            try {
                return method.invoke(target, args);
            }
            catch (Exception e) {
                if (e.getCause() instanceof IllegalArgumentException) {
                    throw e.getCause();
                }
                else {
                    throw e;
                }
            }
        }
    }

}
