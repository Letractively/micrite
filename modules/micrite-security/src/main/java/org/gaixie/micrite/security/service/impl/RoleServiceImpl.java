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

package org.gaixie.micrite.security.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.action.LoginAction;
import org.gaixie.micrite.security.dao.IAuthorityDao;
import org.gaixie.micrite.security.dao.IRoleDao;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 *
 */
public class RoleServiceImpl implements IRoleService {

    private static final Logger logger = Logger.getLogger(LoginAction.class); 
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IAuthorityDao authorityDao;
    
    public List<Role> findAll() {
        List<Role> roles = roleDao.findAll();
        return roles;
    }
    
    public List<Role> findByNameVaguePerPage(String name, int start, int limit) {
        return roleDao.findByNameVaguePerPage(name, start, limit);
    }
    
    public int findByNameVagueTotal(String name) {
        return roleDao.findByNameVagueTotal(name);
    }    
    
    public void delete(String[] roleIds) throws SecurityException {
        for (int i = 0; i < roleIds.length; i++) {
            int roleId = Integer.parseInt(roleIds[i]);
            if(userDao.findByRoleIdCount(roleId)>0) {
                throw new SecurityException("error.role.delete.userNotEmptyInRole");
            }   
            if(authorityDao.findByRoleIdCount(roleId)>0) {
                throw new SecurityException("error.role.delete.authNotEmptyInRole");
            }  
            roleDao.delete(roleId);
        }
    }
}
