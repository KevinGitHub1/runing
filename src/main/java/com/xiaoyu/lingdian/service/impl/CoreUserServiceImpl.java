package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.core.mybatis.mapper.SimpleMapMapper;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 */
@Service("coreUserService")
public class CoreUserServiceImpl implements CoreUserService {

	private static final SimpleMapMapper<String, CoreUser> USER_UUID_MAPPER = new SimpleMapMapper<String, CoreUser>() {
		@Override
		public String mapKey(CoreUser object, int rowNum) {
			return object.getCrusrUuid();
		}
	};

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreUser(CoreUser coreUser) {
        myBatisDAO.insert(coreUser);
        return true;
    }

    @Override
    public boolean updateCoreUser(CoreUser coreUser) {
        myBatisDAO.update(coreUser);
        return true;
    }

   	@Override
	public boolean updateCoreUserByIntegral(String crusrUuid, int crusrIntegral) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crusrUuid", crusrUuid);
		hashMap.put("crusrIntegral", crusrIntegral);
		myBatisDAO.update("updateCoreUserByIntegral", hashMap);
		return true;
	}

	@Override
	public boolean updateBatchCoreUserByIds(List<String> list) {
		if (list.size() <= 0) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.update("updateBatchCoreUserByIds",hashMap);
		return true;
	}

	@Override
	public boolean updateCoreUserByOpenid(CoreUser coreUser) {
		myBatisDAO.update("updateCoreUserByOpenid", coreUser);
		return true;
	}

    @Override
    public CoreUser getCoreUser(CoreUser coreUser) {
        return (CoreUser) myBatisDAO.findForObject(coreUser);
    }

   	@Override
	public CoreUser getCoreUserByOpenId(String crusrOpenid) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crusrOpenid", crusrOpenid);
		return (CoreUser) myBatisDAO.findForObject("getCoreUserByOpenid", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, CoreUser> findCoreUserMapByUuidList(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new HashMap<>();
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		return myBatisDAO.findForMap("findCoreUserForLists", hashMap, USER_UUID_MAPPER);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreUser> findCoreUserList(String crusrName, List<String> list) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crusrName", crusrName);
		hashMap.put("list", list);
		return myBatisDAO.findForList("findCoreUserForLists", hashMap);
	}

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreUser> findCoreUserPage(CoreUser coreUser, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreUser);
        return myBatisDAO.findForPage("findCoreUserForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}