package com.lzn.mybatisplus.codegenerator.utils.redis;


import org.springframework.data.redis.connection.DataType;

import java.util.List;
import java.util.Set;


/**
 * Redis基本接口
 */
interface IRedisBaseService {

    /**
     * Redis DEL 命令用于删除已存在的键。不存在的 key 会被忽略。
     *
     * @return 被删除 key 的数量。
     */
    Boolean del(String key);

    /**
     * Redis DEL 命令用于删除已存在的键。不存在的 key 会被忽略。
     *
     * @return 被删除 key 的数量。
     */
    Long del(Set<String> keys);

    /**
     * Redis EXISTS 命令用于检查给定 key 是否存在。
     *
     * @return 若 key 存在返回 1 ，否则返回 0 。
     */
    Boolean exists(String key);

    /**
     * Redis Type 命令用于返回 key 所储存的值的类型。
     *
     * @return 返回 key 的数据类型，
     * 数据类型有： none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash (哈希表)
     */
    DataType type(String key);

    /**
     * Redis Expire 命令用于设置 key 的过期时间。key 过期后将不再可用。
     *
     * @return 设置成功返回 1 。
     * 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0 。
     */
    Boolean expire(String key, int seconds);

    /**
     * Redis Expireat 命令用于以 UNIX 时间戳(unix timestamp)格式设置 key 的过期时间。key 过期后将不再可用。
     *
     * @return 设置成功返回 1 。
     * 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0 。
     */
    Boolean expireAt(String key, long unixTime);

    /**
     * Redis TTL 命令以秒为单位返回 key 的剩余过期时间。
     *
     * @return 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     * 否则，以秒为单位，返回 key 的剩余生存时间。
     * 注意：在 Redis 2.8 以前，当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1 。
     */
    Long ttl(String key);

    /**
     * 增量地迭代当前数据库中的数据库键
     *
     * @param pattern 匹配表达式
     * @param count   数量
     * @return 匹配的Key值
     */
    List<String> scan(String pattern, Integer count);

}
