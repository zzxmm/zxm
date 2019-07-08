package com.shouzan.back.mapper;

import com.shouzan.back.entity.UserWechat;
import com.shouzan.back.rpc.union.UserList;

import java.util.List;

public interface UserWechatMapper {


    int insertPageInfo(List<UserWechat> list);

    List<UserList> batchFindInfoOrder();

    int batchUpdateOrder(List<UserList> lists);

    List<UserList> batchFindRechargeOrder();

    int batchUpdateRechargeOrder(List<UserList> listss);

    List<UserList> batchFindInfoCard();

    int batchUpdateCard(List<UserList> lists);

    List<UserList> batchFindInfoCode();

    int batchUpdateCode(List<UserList> listss);

    List<UserList> batchFindRechargeCard();

    int batchUpdateRechargeCard(List<UserList> listsss);

    List<UserList> batchFindSecne();

    int batchUpdateSecne(List<UserList> listsss);

    int insertUucPageInfo(List<UserWechat> list);
}