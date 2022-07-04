package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("aphaHibernate")
//@Repository
public class AphaDaoHibernatelmpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
