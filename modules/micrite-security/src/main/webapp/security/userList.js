<script type="text/javascript">
Ext.namespace('micrite.security.userList');

micrite.security.userList.SearchPanel = function() {
    //  查询条件名称数组
    this.conNames = [''];
    //  查询条件组件组数组
    this.conCmpGroups = [
        [this.userName, {xtype:'textfield', name:'user.fullname', width:120}]
    ];
    //  动作按钮上的菜单项
    this.actionButtonMenuItems = [{
        text:this.addUserButton,
        scope:this,
        handler:function() {
	    	var win;
	    	if(!(win = Ext.getCmp('addUserWindow'))){
		        win = new Ext.Window({
		        	id: 'addUserWindow',
		            title    : this.addUserButton,
		            closable : true,
		            autoLoad : {url: 'security/userDetail.js?'+(new Date).getTime(),scripts:true},
		            width    : 500,
		            height   : 360,
		            maximizable : true,
		            layout:'fit'
		        });
	    	}
	        win.show();
	        win.center();
    	}
    }];    
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findUsersVague.action'];
    //  查询结果数据按此格式读取
    this.resultDataFields = [
                                [
                                    {name: 'fullname'},
                                    {name: 'emailaddress'},
                                    {name: 'loginname'},
                                    {name: 'enabled', type: 'boolean'}
                                ]
                            ];
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    //  查询结果列数组
    this.resultColumns = [
                             [
                                 {header: this.fullName, width: 100, sortable: true, dataIndex: 'fullname'},
                                 {header: this.email, width: 180, sortable: true, dataIndex: 'emailaddress'},
                                 {header: this.userName, width: 90, sortable: true, dataIndex: 'loginname'},
                                 {header: this.enabled, width: 70, sortable: true, dataIndex: 'enabled'},
                                 this.resultRowSelectionModel
                             ]
                         ];
    //  查询结果处理按钮数组
    this.resultProcessButtons = [
    [
        {
            text:this.modifyRolesButton, 
            scope:this, 
            handler:function() {
                var userIds = this.resultGrid.selModel.selections.keys;
                if (userIds.length != 1) {
                    Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridRowSelectMsg);
                    return;
                }
                //  加载所有角色
                var allRoleListStore = new Ext.data.Store({
                    autoLoad:true,
                    proxy:new Ext.data.HttpProxy({url:'/' + document.location.href.split("/")[3] + '/findRolesAll.action'}),    
                    reader:new Ext.data.JsonReader({id:'id'}, [{name:'name'}, {name:'description'}]),
                    listeners:{
                        load:function() {
                            //  一旦加载完所有角色，取用户角色，将用户角色项设置为已选择
                            var userRoleListStore = new Ext.data.Store({
                                proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/findUserRoles.action'}),    
                                reader: new Ext.data.JsonReader({id: 'id'}, []),
                                listeners:{
                                    load:function(st, records, options) {
                                        var userRoleListGrid = Ext.getCmp('userRoleListGrid');
                                        var userRoleRecords = [records.length];
                                        for (var i = 0; i < records.length; i++) {
                                            var userRoleRecord = userRoleListGrid.store.getById(records[i].id);
                                            userRoleRecords[i] = userRoleRecord;
                                        }
                                        userRoleListGrid.selModel.selectRecords(userRoleRecords);
                                    }
                                }
                            });
                            userRoleListStore.load({params:{'user.id':userIds[0]}});
                        }
                    }
                });
                var roleListGridSelModel = new Ext.grid.CheckboxSelectionModel();
                //  查询结果列
                var roleListGridColumns = [
                    {header: this.roleName, sortable: true, dataIndex: 'name'},
                    {header: this.roleDescription, sortable: true, dataIndex: 'description'},
                    roleListGridSelModel
                ];
                var win = new Ext.Window({
                            title:"标题",
                            width:600 ,
                            height:400,
                            items:[{
                                id:'userRoleListGrid',
                                xtype:'grid',
                                autoHeight:true,
                                border:false,
                                loadMask:{
                                    msg:mbLocale.loadingMsg
                                },
                                stripeRows:true,
                                selModel:roleListGridSelModel,
                                viewConfig:{
                                    forceFit:true,
                                    enableRowBody:true
                                },
                                store:allRoleListStore,
                                colModel:new Ext.grid.ColumnModel(roleListGridColumns)
                            }],
                            buttons:[{text:mbLocale.submitButton, handler:function() {}},
                                     {text:mbLocale.closeButton, handler:function() {win.close()}}]
                });
                win.show();
            }
        },                                                          
        {
            text:mbLocale.deleteButton, 
            scope:this, 
            handler:function() {
                var userIds = this.resultGrid.selModel.selections.keys;
                if (userIds.length == 0) {
                    Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
                    return;
                }
                var deleteUsersFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/deleteUsers.action',
                            params:{'userIds':userIds},
                            scope:this,
                            callback:function(options, success, response) {
                                if (Ext.util.JSON.decode(response.responseText).success) {
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('success', obj.message);
                                } else {
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('failure', obj.message);
                                }
                            }
                        });
                    }
                };
                Ext.Msg.show({
                    title:mbLocale.infoMsg,
                    msg: mbLocale.delConfirmMsg,
                    buttons: Ext.Msg.YESNO,
                    scope: this,
                    fn: deleteUsersFun,
                    icon: Ext.MessageBox.QUESTION
                });
            }
        },
        {
            text:this.enableUsersButton, 
            scope:this, 
            handler:function() {
                var userIds = this.resultGrid.selModel.selections.keys;
                if (userIds.length == 0) {
                    Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
                    return;
                }
                var users = this.resultGrid.selModel.getSelections();
                for (var i = 0; i < users.length; i++) {
                    //  判断所选择的用户可用状态是否一致（通过下一个条数据和上一条数据的可用状态比较来判断）
                    if (i > 0 && (users[i].get('enabled') != users[i - 1].get('enabled'))) {
                        Ext.MessageBox.alert(mbLocale.infoMsg, this.statusAccordConfMsg);
                        return;
                    }
                }
                var enableUsersFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/enableUsers.action',
                            params:{'userIds':userIds},
                            scope:this,
                            callback:function(options, success, response) {
                                if (Ext.util.JSON.decode(response.responseText).success) {
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('success', obj.message);
                                } else {
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('failure', obj.message);
                                }
                            }
                        });
                    }
                };
                
                //  确认框提示信息
                var enableOrDisableUsersConfMsg = '';
                if (users[0].get('enabled')) {
                    enableOrDisableUsersConfMsg = this.disableUsersConfMsg;
                } else {
                    enableOrDisableUsersConfMsg = this.enableUsersConfMsg;
                }
                
                Ext.Msg.show({
                    title:mbLocale.infoMsg,
                    msg: enableOrDisableUsersConfMsg,
                    buttons: Ext.Msg.YESNO,
                    scope: this,
                    fn: enableUsersFun,
                    icon: Ext.MessageBox.QUESTION
                });
            }
        }
    ]
    ];
    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    
    micrite.security.userList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.userList.SearchPanel, micrite.panel.ComplexSearchPanel, {
    userName:'User Name',
    fullName:'Full Name',
    email:'Email',
    enabled:'Enabled',
    roleName:'Role Name',
    roleDescription:'Role Description',
    addUserButton:'Add User',
    modifyRolesButton:'Modify Roles',
    enableUsersButton:'Enable/Disable',
    statusAccordConfMsg:'Please make sure users selected are all enabled or disabled!',
    enableUsersConfMsg:'Are you sure want to enable the users?',
    disableUsersConfMsg:'Are you sure want to disable the users?'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {userListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.userList.SearchPanel();
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        new Ext.Viewport({
            layout:'fit',
            items:[formPanel]
        });
    }
});
</script>