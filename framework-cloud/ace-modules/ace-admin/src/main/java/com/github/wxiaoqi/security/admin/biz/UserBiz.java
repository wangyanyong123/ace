package com.github.wxiaoqi.security.admin.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.feign.AppUserService;
import com.github.wxiaoqi.security.admin.feign.LogService;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.admin.vo.ChangePasswordVo;
import com.github.wxiaoqi.security.admin.vo.user.UserInfoParam;
import com.github.wxiaoqi.security.auth.client.jwt.MD5;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.*;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper, User> {
    @Autowired
    private MergeCore mergeCore;

    @Autowired
    private DepartMapper departMapper;

    @Autowired
	private AppUserService appUserService;

    @Autowired
    private LogService logService;
    @Autowired
    private UserMapper userMapper;

    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();


    @Override
    public User selectById(Object id) {
        User user = super.selectById(id);
        if("woman".equals(user.getSex()) || "2".equals(user.getSex()) || "女".equals(user.getSex())){
            user.setSex("女");
        }else if("man".equals(user.getSex()) || "1".equals(user.getSex()) || "男".equals(user.getSex())){
            user.setSex("男");
        }else{
            user.setSex("未知");
        }
        try {
            mergeCore.mergeOne(User.class, user);
            return user;
        } catch (Exception e) {
            return super.selectById(id);
        }
    }

    public Boolean changePassword(String oldPass, String newPass) {
        User user = this.getUserByUsername(BaseContextHandler.getUsername());
        if(user!=null && StringUtils.isNotEmpty(user.getId())){
			if (encoder.matches(oldPass, user.getPassword())) {
				String password = encoder.encode(newPass);
				user.setPassword(password);
				this.updateSelectiveById(user);
				return true;
			}
		}else {
			ChangePasswordVo changePasswordVo = new ChangePasswordVo();
			changePasswordVo.setOldPassword(oldPass);
			changePasswordVo.setNewPassword(newPass);
			ObjectRestResponse response = appUserService.changePassword(changePasswordVo);
			if(200 == response.getStatus()){
				return true;
			}else {
				return false;
			}
		}
        return false;
    }

    @Override
    public void insertSelective(User entity) {
    	if(StringUtils.isMobile(entity.getUsername())){
    		throw new RuntimeException("账户不能为手机号！");
//    		return;
		}
        if (mapper.selectUserByUserName(entity.getUsername()) != null) {
            throw new RuntimeException("账户已添加");
        }
        String password = encoder.encode(MD5.MD5Encode(entity.getPassword()));
        String departId = entity.getDepartId();
        EntityUtils.setCreatAndUpdatInfo(entity);
        entity.setPassword(password);
        entity.setDepartId(departId);
        entity.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        entity.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        String userId = UUIDUtils.generateUuid();
        entity.setTenantId(BaseContextHandler.getTenantID());
        entity.setId(userId);
        entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        try {
            departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(), BaseContextHandler.getTenantID());
            super.insertSelective(entity);
        }catch (Exception e){
            e.printStackTrace();
            try {
                InetAddress address = InetAddress.getLocalHost();
                LogInfoVo logInfoVo = new LogInfoVo();
                logInfoVo.setLogType("2");
                logInfoVo.setLogName("添加用户");
                logInfoVo.setIp(address.getHostAddress());
                logInfoVo.setType("1");
                logInfoVo.setMessage(e.getMessage());
                logInfoVo.setCreateTime(new Date());
                logInfoVo.setCreateBy(BaseContextHandler.getUserID());
                logService.savelog(logInfoVo);
            }catch (Exception e1){
                e1.printStackTrace();
            }
            throw e;
        }
    }

    @Override
    public void updateSelectiveById(User entity) {
        EntityUtils.setUpdatedInfo(entity);
        User user = mapper.selectByPrimaryKey(entity.getId());
        if (StringUtils.isNotEmpty(user.getDepartId()) && !user.getDepartId().equals(entity.getDepartId())) {
            departMapper.deleteDepartUser(user.getDepartId(), entity.getId());
            departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(), BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setTenantId(BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        super.updateSelectiveById(entity);
    }

    @Override
    public void deleteById(Object id) {
        User user = mapper.selectByPrimaryKey(id);
        user.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
        this.updateSelectiveById(user);
    }

    @Override
    public List<User> selectByExample(Object obj) {
        Example example = (Example) obj;
        example.createCriteria().andEqualTo("isDeleted", BooleanUtil.BOOLEAN_FALSE);
        List<User> users = super.selectByExample(example);
        try {
            mergeCore.mergeResult(User.class, users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        return mapper.selectOne(user);
    }

    @Override
    public void query2criteria(Query query, Example example) {
        if (query.entrySet().size() > 0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                Example.Criteria criteria = example.createCriteria();
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                example.or(criteria);
            }
        }
    }

    public List<String> getUserDataDepartIds(String userId) {
        return mapper.selectUserDataDepartIds(userId);
    }

    public List<User> getUserList(String name, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<User> userList = mapper.getUserList(name,startIndex, limit);
        for(User user : userList){
            if("woman".equals(user.getSex()) || "2".equals(user.getSex()) || "女".equals(user.getSex())){
                user.setSex("女");
            }else if("man".equals(user.getSex()) || "1".equals(user.getSex()) || "男".equals(user.getSex())){
                user.setSex("男");
            }else{
                user.setSex("未知");
            }
        }
        return userList;
    }

    public int countUserList(String name) {
        return mapper.countUserList(name);
    }

    public ObjectRestResponse checkUserByVal(String val) {
        ObjectRestResponse response = new ObjectRestResponse();
        User user = mapper.selectUserByUserName(val);
        if(user != null){
            response.setStatus(101);
            response.setMessage("账户已添加");
            return response;
        }
        return response;
    }


    public ObjectRestResponse updateUserInfo(UserInfoParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(org.apache.commons.lang3.StringUtils.isEmpty(param.getName())){
            msg.setStatus(201);
            msg.setMessage("姓名不能为空");
            return msg;
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(param.getUsername())){
            msg.setStatus(201);
            msg.setMessage("账号不能为空");
            return msg;
        }
       User userInfo = userMapper.selectUserInfoById(param.getId());
        User user = new User();
        if(userInfo != null){
            BeanUtils.copyProperties(userInfo,user);
            user.setName(param.getName());
            user.setUsername(param.getUsername());
            user.setSex(param.getSex());
            user.setDescription(param.getDescription());
            user.setUpdTime(new Date());
            user.setUpdUserId(BaseContextHandler.getUserID());
            if(userMapper.updateByPrimaryKeySelective(user) <= 0){
                msg.setStatus(201);
                msg.setMessage("编辑失败");
                return msg;
            }
        }else{
            msg.setStatus(201);
            msg.setMessage("查无此详情");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


}
