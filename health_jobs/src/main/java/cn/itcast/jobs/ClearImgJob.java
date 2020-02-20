package cn.itcast.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        /*根据Redis报错的两个set集合进行差值的计算,获得垃圾图片名称的集合*/
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null){
            for (String picName : set) {
                //删除七牛云服务器上的图片:
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片的名称:
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("定时清理:"+picName);
            }
        }
    }
}


