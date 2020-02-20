package cn.itcast.service;

import cn.itcast.pojo.Member;

import java.util.List;

public interface MemberService {
    public  Member findBytelephone(String telephone);
    public void add(Member member);

    public List<Integer> findMemberCountByMonths(List<String> months);
}
