package cn.itcast.service.impl;

import cn.itcast.constant.RedisConstant;
import cn.itcast.dao.SetmealDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath;

    /*1:添加检查套餐*/
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealIdAndCheckgroupId(setmealId,checkgroupIds);
        //将图片的名称保存到Redis集合中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);

        generateMobileStaticHtml();

    }
    //创建当前方法所需要的静态页面
    private void generateMobileStaticHtml() {
        List<Setmeal> list = setmealDao.findAll();
        generateMobileSetmealListHtml(list);
        generateMobileSetmealDetailHtml(list);
    }

    //创建套餐列表方法:
    private void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map map = new HashMap();
        map.put("setmealList",list);
        generteHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    //创建生成套餐详情的方法:
    private void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal",setmealDao.findById(setmeal.getId()));
            generteHtml("mobile_setmeal_detail.ftl","setmeal_detail_" + setmeal.getId() + ".html",map);
        }
    }



    //定义通用的生成静态文件的方法:
    public void generteHtml(String templateName,String htmlPageName,Map map){
        //1:获取到配置对象:
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            //2:获取到模板对象
            Template template = configuration.getTemplate(templateName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream
                  (new File(outPutPath + "/" + htmlPageName)),
                    "UTF-8"));

            template.process(map,out);
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
        }


    }
    /*2;分页查询:*/
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /*3:查询所有套餐:*/
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /*4:通过套餐ID 查询详情包括检查组合检查项*/
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    /*添加关联*/
    public void setSetmealIdAndCheckgroupId(Integer setmealId,Integer[]checkgroupIds ){
        if (checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealIdAndCheckgroupId(map);
            }
        }

    }
}
