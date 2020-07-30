package com.lzn.mybatisplus.codegenerator.utils.redis;

/**
 * String类型的Redis接口
 */
public interface IRedisStringService {

    /**
     * Redis SET 命令用于设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型。
     *
     * @return 在 Redis 2.6.12 以前版本， SET 命令总是返回 OK 。
     * 从 Redis 2.6.12 版本开始， SET 在设置操作成功完成时，才返回 OK 。
     */
    void set(String key, String value);

    /**
     * Redis Get 命令用于获取指定 key 的值。如果 key 不存在，返回 nil 。如果key 储存的值不是字符串类型，返回一个错误。
     *
     * @return 返回 key 的值，如果 key 不存在时，返回 nil。
     * 如果 key 不是字符串类型，那么返回一个错误。
     */
    String get(String key);

    /**
     * Redis Getset 命令用于设置指定 key 的值，并返回 key 旧的值。
     *
     * @return 返回给定 key 的旧值。
     * 当 key 没有旧值时，即 key 不存在时，返回 nil 。
     * 当 key 存在但不是字符串类型时，返回一个错误。
     */
    String getSet(String key, String value);

    /**
     * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     *
     * @return 设置成功，返回 1 。 设置失败，返回 0 。
     */
    Boolean setnx(String key, String value);

    /**
     * Redis Setex 命令为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
     *
     * @return 设置成功时返回 OK 。
     */
    void setex(String key, int seconds, String value);

    /**
     * Redis Append 命令用于为指定的 key 追加值。
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
     * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
     *
     * @return 追加指定值之后， key 中字符串的长度。
     */
    Integer append(String key, String value);

    /**
     * Redis Setbit 命令用于对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     *
     * @return 指定偏移量原来储存的位。
     */
    Boolean setbit(String key, long offset, boolean value);

    /**
     * Redis Getbit 命令用于对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
     *
     * @return 字符串值指定偏移量上的位(bit)。
     * 当偏移量 OFFSET 比字符串值的长度大，或者 key 不存在时，返回 0 。
     */
    Boolean getbit(String key, long offset);

    /**
     * 统计bit位数量
     *
     * @param key key
     * @return 数量
     */
    Long bitCount(String key);

    /**
     * Redis Setrange 命令用指定的字符串覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
     *
     * @return 被修改后的字符串长度。
     */
    void setrange(String key, long offset, String value);

    /**
     * Redis Getrange 命令用于获取存储在指定 key 中字符串的子字符串。
     * 字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     *
     * @return 截取得到的子字符串。
     */
    String getrange(String key, long startOffset, long endOffset);

    /**
     * Redis decrBy 命令将 key 所储存的值减去指定的减量值。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @return 减去指定减量值之后， key 的值。
     */
    Long decrBy(String key, long integer);

    /**
     * Redis Decr 命令将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @return 执行命令之后 key 的值。
     */
    Long decr(String key);

    /**
     * Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @return 加上指定的增量值之后， key 的值。
     */
    Long incrBy(String key, long integer);

    /**
     * Redis Incr 命令将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @return 执行 INCR 命令之后 key 的值。
     */
    Long incr(String key);

    /**
     * Redis Strlen 命令用于获取指定 key 所储存的字符串值的长度。
     * 当 key 储存的不是字符串值时，返回一个错误。
     *
     * @return 字符串值的长度。 当 key 不存在时，返回 0。
     */
    Long strlen(String key);
}
