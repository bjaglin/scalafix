package scalafix.internal.loader;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/** A basic thread-safe cache with arbitrary eviction on GC pressure. */
public final class BlockingCache<K, V> {

    private final ConcurrentHashMap<K, SoftReference<V>> map = new ConcurrentHashMap<>();

    /**
     * Computes or updates the value for {@code key}.
     * <p>
     * For the same {@code key} the {@code updateFn} runs sequentially. The previous
     * value is supplied as {@link Optional#empty()} if either the key
     * is new or the internal {@link SoftReference} was cleared by the GC.
     * </p>
     */
    public V compute(K key, Function<Optional<V>, V> updateFn) {
        SoftReference<V> newRef = map.compute(
                key,
                (k, softRef) -> {
                    V prev = softRef == null ? null : softRef.get();
                    V next = updateFn.apply(Optional.ofNullable(prev));
                    return new SoftReference<>(next);
                });
        // it is extremely unlikely that the GC has cleared the reference
        return newRef.get();
    }
}