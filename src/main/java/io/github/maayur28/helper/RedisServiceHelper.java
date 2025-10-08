package io.github.maayur28.helper;

import com.google.gson.Gson;
import io.github.maayur28.model.DealsOfTheDayModel;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

import static io.github.maayur28.utils.Constants.CONST_DEALS_OF_THE_DAY;

@Service
public class RedisServiceHelper {
    private final Logger logger = LoggerFactory.getLogger(RedisServiceHelper.class);
    private final StatefulRedisConnection<String, String> redisConnection;

    public RedisServiceHelper(StatefulRedisConnection<String, String> redisConnection) {
        this.redisConnection = redisConnection;
    }

    public static String serializeEntity(DealsOfTheDayModel model) {
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    /**
     * Optimistic increment with WATCH/MULTI/EXEC.
     */
    public boolean updateKeyWithOptimisticLocking(String key, long expectedValue) {
        try {
            RedisCommands<String, String> cmd = redisConnection.sync();
            cmd.watch(key);
            String current = cmd.get(key);
            if ((current == null && expectedValue != 0L) ||
                    (current != null && !current.equals(String.valueOf(expectedValue)))) {
                cmd.unwatch();
                return false;
            }
            cmd.multi();
            cmd.set(key, String.valueOf(expectedValue + 1));
            var execRes = cmd.exec(); // null if aborted
            return execRes != null;
        } catch (RedisCommandTimeoutException e) {
            logger.error("RedisCommandTimeoutException: {}", e.getMessage());
            return false;
        }
    }

    /* ------------ Simple KV helpers ------------ */

    public void set(String key, String value) {
        redisConnection.sync().set(key, value);
    }

    public void setWithExpiry(String key, String value, long ttlSeconds) {
        redisConnection.sync().setex(key, ttlSeconds, value);
    }

    public String get(String key) {
        return redisConnection.sync().get(key);
    }

    public Long incr(String key) {
        return redisConnection.sync().incr(key);
    }

    public void del(String key) {
        redisConnection.sync().del(key);
    }

    /* ------------ Init guards (for free-list setup) ------------ */

    /** SETNX key=value → true if set, false if already exists */
    public boolean setnx(String key, String value) {
        return redisConnection.sync().setnx(key, value);
    }

    /** EXPIRE key seconds */
    public void expire(String key, long seconds) {
        redisConnection.sync().expire(key, seconds);
    }

    /* ------------ LIST ops for free-index pool ------------ */

    /** LPUSH key value */
    public void lpush(String key, String value) {
        redisConnection.sync().lpush(key, value);
    }

    /** LPOP key -> null when list is empty */
    public String lpop(String key) {
        return redisConnection.sync().lpop(key);
    }

    /** Remove a specific value from a Redis list */
    public Long lrem(String key, long count, String value) {
        return redisConnection.sync().lrem(key, count, value);
    }

    /** LLEN key */
    public Long llen(String key) {
        return redisConnection.sync().llen(key);
    }

    /**
     * BRPOP key timeoutSeconds → returns popped value (right pop) or null on timeout.
     * Use timeoutSeconds=0 to block indefinitely.
     */
    public String brpop(String key, long timeoutSeconds) {
        KeyValue<String, String> kv = redisConnection.sync().brpop(timeoutSeconds, key);
        if (kv == null) return null;
        return kv.hasValue() ? kv.getValue() : null;
    }

    /* ------------ SET ops (presence guard for free-list) ------------ */

    /** SADD key member(s) -> number of elements actually added */
    public Long sadd(String key, String... members) {
        return redisConnection.sync().sadd(key, members);
    }

    /** SREM key member(s) -> number of elements actually removed */
    public Long srem(String key, String... members) {
        return redisConnection.sync().srem(key, members);
    }

    /** SISMEMBER key member -> true if present */
    public boolean sismember(String key, String member) {
        return redisConnection.sync().sismember(key, member);
    }

    /** SMEMBERS key -> full set (debugging) */
    public Set<String> smembers(String key) {
        return redisConnection.sync().smembers(key);
    }

    /* ------------ Existing sample code ------------ */

    public void removeAllKeys() {
        redisConnection.sync().flushdb();
    }

    public void updateDealsOfTheDay(DealsOfTheDayModel entity) {
        String serializedEntity = serializeEntity(entity);
        redisConnection.sync().rpush(CONST_DEALS_OF_THE_DAY, serializedEntity);
    }
}
