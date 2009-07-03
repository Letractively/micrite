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

package org.gaixie.micrite.crm.service.impl;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.dao.ICustomerDao;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 客户管理功能实现
 * @see org.gaixie.micrite.crm.service.ICustomerService
 */
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    public void add(Customer customer, int customerSourceId) {
        CustomerSource cs = customerDao.getCustomerSource(customerSourceId);
        customer.setCustomerSource(cs);
        customerDao.save(customer);
    }
    
    public void update(Customer c, int customerSourceId) {
        Customer customer = customerDao.getCustomer(c.getId());
        CustomerSource cs = customerDao.getCustomerSource(customerSourceId);
        
        customer.setCustomerSource(cs);
        customer.setName(c.getName());
        customer.setTelephone(c.getTelephone());
        customerDao.update(customer);
    }

    public List<CustomerSource> findALLCustomerSource() {
        List<CustomerSource> customerSource = customerDao.findAllCustomerSource();
        return customerSource;
    }

    
    public CategoryDataset getCustomerSourceBarDataset(String tel) {
        List list = customerDao.findCSGroupByTelVague(tel);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue(Integer.parseInt(obj[0].toString()),"",obj[1].toString());
            }
        } else {
            return null;
        }
        return dataset;
    }
    
    public PieDataset getCustomerSourcePieDataset(String telphone) {
        List list = customerDao.findCSGroupByTelVague(telphone);
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue( obj[1].toString(),Integer.parseInt(obj[0].toString()));
            }
        } else {
            return null;
        }
        return dataset;
        
    }

    public List<Customer> findByTelVaguePerPage(String telephone, int start,
            int limit) {
        List<Customer> list = customerDao.findByTelVaguePerPage(telephone,start,limit);
        return list;
    }

    public int findByTelVagueCount(String telephone) {
        return customerDao.findByTelVagueCount(telephone); 
    }

    public void delete(int[] customerIds) {
        for (int i = 0; i < customerIds.length; i++) {
            Customer customer = customerDao.getCustomer(customerIds[i]);
            customerDao.delete(customer);
        }
    }
}
