/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.security.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 处理用户对角色操作的事件。
 * 
 */
public class RoleAction extends ActionSupport{
	private static final long serialVersionUID = 3072284877032259302L;

	private static final Logger logger = Logger.getLogger(RoleAction.class);
	
	@Autowired
	private IRoleService roleService;

    //输出到页面的数据
    private List<Role> roles;

    private String roleIds;
    
    //  用户
    private Role role;
    
    //  以下两个分页用
    //  起始索引
    private int start;
    //  限制数
    private int limit;
    
    //  记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;
    
    //  action处理结果（map对象）
    private Map<String,Object> resultMap = new HashMap<String,Object>();
    private Map<String,String> returnMsg = new HashMap<String,String>();    

    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
   
    /**
     * 查找所有角色
     * @return "success"
     */
    public String findAll(){
    	roles = roleService.findAll();
    	return SUCCESS;
    }

    /**
     * 根据角色名查询角色集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByNameVague() {
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = roleService.findByNameVagueTotal(role.getName());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        List<Role> searchRoles = roleService.findByNameVaguePerPage(role.getName(), start, limit);

        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", searchRoles);
        return SUCCESS;
    }

    public String delete() {
        String[] ids = StringUtils.split(roleIds, ",");
        try {
            roleService.delete(ids);
            resultMap.put("message", getText("delete.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.warn(getText(e.getMessage()));            
        }
        return SUCCESS;
    }
    
    /**
     * 新增角色。
     * 
     * @return "success"
     */
    public String add() {
        try {
        	roleService.add(role);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }
    
    /**
     * 修改角色。
     * 
     * @return "success"
     */
    public String update() {
        try {
            roleService.update(role);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
    
    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public Map<String, Object> getResultMap() {
        return resultMap;
    }  
    
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }    
}
