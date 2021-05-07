package com.rozhaev.cache;

import java.util.concurrent.*;

public class FutureCache<K, V> {

    private final ConcurrentMap<K, Future<V>> cache = new ConcurrentHashMap<>();

    public V getValue(final K key, final Callable<V> callable) throws InterruptedException, ExecutionException {
        try {
            final Future<V> future = createFuture(key, callable);
            return future.get();
        } catch (Exception e) {
            cache.remove(key);
            throw e;
        }
    }

    public void setValue(final K key, final V value) {
        createFuture(key, () -> value);
    }

    private Future<V> createFuture(final K key, final Callable<V> callable) {
        Future<V> future = cache.get(key);
        if (future == null) {
            final FutureTask<V> futureTask = new FutureTask<>(callable);
            future = cache.putIfAbsent(key, futureTask);
            if (future == null) {
                future = futureTask;
                futureTask.run();
            }
        }
        return future;
    }
}
