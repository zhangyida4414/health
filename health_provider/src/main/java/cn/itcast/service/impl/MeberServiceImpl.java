package cn.itcast.service.impl;

import cn.itcast.dao.MemberDao;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    public Member findBytelephone(String telephone) {
        return memberDao.findBytelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month +".31";
            Integer conut = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(conut);
        }
        return memberCount;
    }
}
