package io.github.maayur28.helper;

import com.google.gson.Gson;
import io.github.maayur28.model.DealsOfTheDayModel;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public boolean updateKeyWithOptimisticLocking(String key, long value) {
        try {
            RedisCommands<String, String> commands = redisConnection.sync();
            commands.watch(key); // Start watching the key
            String currentValue = commands.get(key);
            // Check if the value has been modified by another replica
            if (!String.valueOf(value).equals(currentValue)) {
                commands.unwatch(); // Release the watch
                return false; // Another replica has modified the key, so abort
            }
            // Start a transaction
            commands.multi();
            commands.set(key, String.valueOf(value + 1));
            return true; // Key updated successfully
        } catch (RedisCommandTimeoutException e) {
            logger.error("RedisCommandTimeoutException: {}", e.getMessage());
            return false; // Update failed due to timeout
        }
    }

    public void set(String key, String value) {
        RedisCommands<String, String> commands = redisConnection.sync();
        commands.set(key, value);
    }

    public void setWithExpiry(String key, String value, long ttl) {
        RedisCommands<String, String> commands = redisConnection.sync();
        commands.setex(key, ttl, value); // Set the key with a TTL
    }

    public String get(String key) {
        RedisCommands<String, String> commands = redisConnection.sync();
        return commands.get(key);
    }

    public void removeAllKeys() {
        RedisCommands<String, String> commands = redisConnection.sync();
        commands.flushdb();
    }

    public void updateDealsOfTheDay(DealsOfTheDayModel entity) {
        String serializedEntity = serializeEntity(entity);
        RedisCommands<String, String> commands = redisConnection.sync();
        commands.rpush(CONST_DEALS_OF_THE_DAY, serializedEntity);
    }
}
