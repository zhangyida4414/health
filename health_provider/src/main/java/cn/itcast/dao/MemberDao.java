package cn.itcast.dao;

import cn.itcast.pojo.Member;

import java.util.List;

public interface MemberDao {
    public Member findBytelephone(String telephone);

    public void add(Member memeber);


    public Integer findMemberCountBeforeDate(String date);

    public Integer findMemberCountByDate(String today);

    public Integer findMemberTotalCount();

    public Integer findMemberCountAfterDate(String thisWeekMonday);
}
