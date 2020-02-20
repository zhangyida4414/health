package cn.itcast.service.impl;

import cn.itcast.dao.CheckItemDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //1:添加检查项
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //2:分页查询
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1:获取到前台传入的参数
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //2:分页助手设置分页条件
        PageHelper.startPage(currentPage,pageSize);

        //3:查询所有表中数据:
        Page<CheckItem> page = checkItemDao.findPage(queryString);
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();
        return new PageResult(total,result);
    }

   /*3:删除检查项目*/
    public void delete(Integer id) {
        //1:判断检查项是否关联检查组:
        Long count = checkItemDao.findCountByCheckItemId(id);
        if (count>0){
            new RuntimeException();
        }
        checkItemDao.delete(id);
    }

   /*4:编辑检查项*/
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /*5:根据ID回显检查项*/
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    /*6:查询所有*/
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
