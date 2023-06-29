//package com.weaver.fengx.ecmonitor.common.redis.utils;
//
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//import cn.hutool.json.JSONUtil;
//
///**
// * @author Fengx
// * redis工具类
// *
// **/
//public class RedisUtils {
//
//    public RedisTemplate redisTemplate;
//
//    public RedisUtils(Boolean isMaster) {
//        if (isMaster) {
//            redisTemplate = SpringUtil.getBean("redisTemplateMaster");
//            return;
//        }
//        redisTemplate = SpringUtil.getBean("redisTemplateSlave");
//    }
//
//    /**
//     * 缓存基本的对象，Integer、String、实体类等
//     *
//     * @param key   缓存的键值
//     * @param value 缓存的值
//     */
//    public void setObject(final String key, final Object value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    /**
//     * 缓存基本的对象，Integer、String、实体类等
//     *
//     * @param key      缓存的键值
//     * @param value    缓存的值
//     * @param timeout  时间
//     * @param timeUnit 时间颗粒度
//     */
//    public  void setObject(final String key, final Object value, final Long timeout, final TimeUnit timeUnit) {
//        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
//    }
//
//    /**
//     * 设置有效时间
//     *
//     * @param key     Redis键
//     * @param timeout 超时时间
//     * @return true=设置成功；false=设置失败
//     */
//    public boolean expire(final String key, final long timeout) {
//        return expire(key, timeout, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 设置有效时间
//     *
//     * @param key     Redis键
//     * @param timeout 超时时间
//     * @param unit    时间单位
//     * @return true=设置成功；false=设置失败
//     */
//    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
//        return redisTemplate.expire(key, timeout, unit);
//    }
//
//    /**
//     * 获得缓存的基本对象。
//     *
//     * @param key 缓存键值
//     * @return 缓存键值对应的数据
//     */
//    public Object getObject(final String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//    /**
//     * 获得缓存的基本对象。
//     *
//     * @param key 缓存键值
//     * @return 缓存键值对应的数据
//     */
//    public <T> T getObject(final String key,final Class<T> clazz) {
//        return JSONUtil.toBean(JSONUtil.toJsonStr(redisTemplate.opsForValue().get(key)),clazz);
//    }
//
//    /**
//     * 删除单个对象
//     *
//     * @param key
//     */
//    public boolean deleteObject(final String key) {
//        return redisTemplate.delete(key);
//    }
//
//    /**
//     * 删除集合对象
//     *
//     * @param collection 多个对象
//     * @return
//     */
//    public long deleteObject(final Collection collection) {
//        return redisTemplate.delete(collection);
//    }
//
//    /**
//     * 缓存List数据
//     *
//     * @param key      缓存的键值
//     * @param dataList 待缓存的List数据
//     * @return 缓存的对象
//     */
//    public long setList(final String key, final Collection dataList) {
//        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
//        return count == null ? 0 : count;
//    }
//
//    /**
//     * 获得缓存的list对象
//     *
//     * @param key 缓存的键值
//     * @return 缓存键值对应的数据
//     */
//    public List getList(final String key) {
//        return redisTemplate.opsForList().range(key, 0, -1);
//    }
//
//    /**
//     * 获得缓存的list对象
//     *
//     * @param key 缓存的键值
//     * @return 缓存键值对应的数据
//     */
//    public <T> List<T> getList(final String key,final Class<T> clazz) {
//        return JSONUtil.toList(JSONUtil.toJsonStr(redisTemplate.opsForList().range(key, 0, -1)),clazz);
//    }
//
//    /**
//     * 缓存Set
//     *
//     * @param key     缓存键值
//     * @param dataSet 缓存的数据
//     * @return 缓存数据的对象
//     */
//    public void setSet(final String key, final Set dataSet) {
//        redisTemplate.opsForSet().add(key, dataSet);
//    }
//
//    /**
//     * 获得缓存的set
//     *
//     * @param key
//     * @return
//     */
//    public Set getSet(final String key) {
//        return redisTemplate.opsForSet().members(key);
//    }
//
//    /**
//     * 缓存Map
//     *
//     * @param key
//     * @param dataMap
//     */
//    public void setMap(final String key, final Map<String, Object> dataMap) {
//        if (dataMap != null) {
//            redisTemplate.opsForHash().putAll(key, dataMap);
//        }
//    }
//
//    /**
//     * 获得缓存的Map
//     *
//     * @param key
//     * @return
//     */
//    public Map getMap(final String key) {
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * 往Hash中存入数据
//     *
//     * @param key   Redis键
//     * @param hKey  Hash键
//     * @param value 值
//     */
//    public void setMap(final String key, final String hKey, final Object value) {
//        redisTemplate.opsForHash().put(key, hKey, value);
//    }
//
//    /**
//     * 获取Hash中的数据
//     *
//     * @param key  Redis键
//     * @param hKey Hash键
//     * @return Hash中的对象
//     */
//    public Object getMap(final String key, final String hKey) {
//        return redisTemplate.opsForHash().get(key, hKey);
//    }
//
//    /**
//     * 获取多个Hash中的数据
//     *
//     * @param key   Redis键
//     * @param hKeys Hash键集合
//     * @return Hash对象集合
//     */
//    public List getMap(final String key, final Collection<Object> hKeys) {
//        return redisTemplate.opsForHash().multiGet(key, hKeys);
//    }
//
//    /**
//     * 获得缓存的基本对象列表
//     *
//     * @param pattern 字符串前缀
//     * @return 对象列表
//     */
//    public Collection<String> keys(final String pattern) {
//        return redisTemplate.keys(pattern);
//    }
//}
