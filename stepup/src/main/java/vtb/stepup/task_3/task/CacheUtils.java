package vtb.stepup.task_3.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CacheUtils {
    private static final List<Object> cacheHandlers = new ArrayList<>();

    public static <T> T cache(T object) {
        CacheHandler<T> cacheHandler = new CacheHandler<>(object);

        cacheHandlers.add(cacheHandler);

        Object result =  (T) Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                cacheHandler
        );
        return (T) result;
    }



    public static void startCleaningThreads(){
        startCleaning(250L);
    }

    public static void startCleaningThreads(long cleaningInterval){
        startCleaning(cleaningInterval);
    }

    private static void startCleaning(long cleaningInterval){
        for (Object object : cacheHandlers) {
            if (object != null) {
                CacheHandler cacheHandler = (CacheHandler) object;
                if (cacheHandler.getCleanExecutor() != null)
                    cacheHandler.getCleanExecutor().scheduleAtFixedRate(cacheHandler::cleanExpiredCacheValues, 0, cleaningInterval, TimeUnit.MILLISECONDS);
            };
        }
    }

    public static void stopCleaningThreads(){

        for (Object object : cacheHandlers) {
            if (object != null) {
                CacheHandler cacheHandler = (CacheHandler) object;
                if (cacheHandler.getCleanExecutor() != null)
                    cacheHandler.getCleanExecutor().shutdown();
            };
        }

    }

    private static class CacheHandler<T> implements InvocationHandler{
        private final T target;
        private final ConcurrentHashMap<Method, ConcurrentHashMap<String, CacheResult>> cache = new ConcurrentHashMap<>();

        private final ConcurrentHashMap<Method, Long> liveTimes = new ConcurrentHashMap<>();

        @Getter
        private ScheduledExecutorService cleanExecutor;


        private void initCleaningThread(){
            cleanExecutor = Executors.newSingleThreadScheduledExecutor();
        }

        protected synchronized void cleanExpiredCacheValues(){

            System.out.println("Start cleaning...");
            for (Method method : cache.keySet()){
                long currentTime = System.currentTimeMillis();
                long liveTime = liveTimes.get(method);
                cache.get(method).entrySet().
                        removeIf(methodResult -> (currentTime - methodResult.getValue().getPutTime()) > liveTime);
            }
        }
        public CacheHandler(T target) {
            this.target = target;
            for (Method method : target.getClass().getMethods()) {
                if (method.isAnnotationPresent(Cache.class)) {
                    cache.put(method, new ConcurrentHashMap<>());
                    liveTimes.put(method, method.getAnnotation(Cache.class).value());
                }
            }

            initCleaningThread();

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            synchronized (proxy) {
                Method objMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

                if (!cache.containsKey(objMethod)) {
                    return invokeThis(method, args);
                }
                ConcurrentHashMap<String, CacheResult> methodResults = cache.get(objMethod);

                String stateKeyString = stateObjectAndMethodArgs(method, args);

                Object result;

                if (methodResults.containsKey(stateKeyString)) {
                    System.out.println("Cached result returns");

                    result = methodResults.get(stateKeyString).getResult();
                } else {
                     result = invokeThis(method, args);

                }
                methodResults.put(stateKeyString, new CacheResult(result, System.currentTimeMillis()));

                cache.put( objMethod, methodResults);

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



        private String stateObjectAndMethodArgs(Method method, Object[] args){
            StringBuilder result = new StringBuilder("fields value");
            for (Field f : target.getClass().getDeclaredFields()) {
                if (!f.isAnnotationPresent(NotInState.class)) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    Object fieldVal = null;
                    try {
                        fieldVal = f.get(target);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    result.append(' ').append(f.getName()).append(' ').append(fieldVal);
                }

                if (args != null && args.length != 0) {
                    result.append(' ').append("args");
                    for (Object arg : args) {
                        result.append(' ').append(arg);
                    }
                }
            }
            return result.toString();
        }
    }

}

@Getter
@AllArgsConstructor
@ToString
class CacheResult{
    private Object result;
    private long putTime;
}
