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

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.dao.IAuthorityDAO;
import org.gaixie.micrite.security.service.ISecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接口 <code>ISecurityInterceptor</code> 的实现类。
 * 
 */
public class SecurityInterceptorImpl implements ISecurityInterceptor {

    @Autowired
    private IAuthorityDAO authorityDAO;

    public List<Authority> loadAuthorities(String type) {
        
        List<Authority> authorities = authorityDAO.findByType(type);
        for (Authority authority : authorities) {
            for (Role role : authority.getRoles()) {
                role.getName();
            }
        }
        return authorities;
    }
}
