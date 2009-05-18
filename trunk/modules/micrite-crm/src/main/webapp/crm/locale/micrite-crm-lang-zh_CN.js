function customerListLocale(){
    if(micrite.crm.customerList.SearchPanel) {
        Ext.override(micrite.crm.customerList.SearchPanel, {
            searchText:'查找',
            newCustomerLink:'<a href="crm/customerDetail.jsp" id="Customer Detail" class="inner-link">增加新客户</a>'
        });
    }	
    if(micrite.crm.customerList.SearchResultGrid) {
        Ext.override(micrite.crm.customerList.SearchResultGrid, {
            colModelId:'ID',
            colModelName:'姓名',
            colModelMobile:'手机号',
            colModelSource:'来源'
        });
    }
}
function customerDetailLocale(){
    if(micrite.crm.customerDetail.FormPanel) {
        Ext.override(micrite.crm.customerDetail.FormPanel, {
            customerDetailText:'客户基本信息',
            idText:'ID',
            nameText:'姓名',
            mobileText:'手机号',
            sourceText:'来源',
            submitText:'提交',
            cancelText:'取消',
            comboEmptyText:'选择客户来源...',
            waitingMsg:'提交数据...'
        });
    }    
}