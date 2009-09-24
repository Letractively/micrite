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

package org.gaixie.micrite.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Micrite的用户令牌。
 */
@Entity
@Table(name = "tokens")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    private String key;
    private String type;
    private Date expiration_ts;
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    
    /**
     * No-arg constructor for JavaBean tools.
     */
    public Token() {

    }
    
    /**
     * Full constructor
     */
    public Token(String key, String type, Date expiration_ts, User user) {
        this.key = key;
        this.type = type;     
        this.expiration_ts = expiration_ts;  
        this.user = user; 
    }  
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public Date getExpiration_ts() {
        return expiration_ts;
    }

    public void setExpiration_ts(Date expiration_ts) {
        this.expiration_ts = expiration_ts;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Token)) return false;
        final Token token = (Token) obj;
        return getKey() == token.getKey();
    }
    
    public String toString() {
        return  "Token ('" + getId() + "'), " +
                "key: '" + getKey() + "'" +
                "type: '" + getType() + "'" +
                "expiration_ts: '" + getExpiration_ts() + "'";
    }
}
