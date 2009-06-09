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

package org.gaixie.micrite.crm.action;

import org.gaixie.micrite.crm.service.ICustomerService;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 用户来源图形报表
 */
public class CustomerChartAction extends ActionSupport {
    private static final long serialVersionUID = -8118104364113464883L;
    @Autowired
    private ICustomerService customerService;
    private JFreeChart chart;
    
    /**
     * 2D柱图
     * @return
     */
    public String getBarChart(){
        chart =  customerService.getCustomerSourceBarChart();
        return SUCCESS ;
    }
    
    /**
     * 2D饼图
     * @return
     */
    public String getPieChart(){
        chart =  customerService.getCustomerSourcePieChart();
        return SUCCESS;
    }
    public JFreeChart getChart() {
        return chart;
    }
}
