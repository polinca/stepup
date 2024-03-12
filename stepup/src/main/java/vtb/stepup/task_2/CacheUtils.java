package vtb.stepup.task_2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
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
        private final Map<Method, Object> cache = new HashMap<>();
        private final Set<Method> mutatorMethods = new HashSet<>();
        private final Set<Method> cacheMethods = new HashSet<>();


        //Method objMethod = Fraction.class.getDeclaredMethod(method.getName(), method.getParameterTypes());

        public CacheHandler(T target) {
            this.target = target;
            for (Method method : target.getClass().getMethods()) {
                if (method.isAnnotationPresent(Mutator.class)) {
                    mutatorMethods.add(method);
                }
                if (method.isAnnotationPresent(Cache.class)) {
                    cacheMethods.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method objMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

            if (mutatorMethods.contains(objMethod)) {
                cache.clear();
                return invokeThis(method, args);
            }
            if (!cacheMethods.contains(objMethod)) {
                return invokeThis(method, args);
            }

            if (cache.containsKey(objMethod)) {
                return cache.get(objMethod);
            } else {
                Object result = invokeThis(method, args);
                cache.put(objMethod, result);
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
