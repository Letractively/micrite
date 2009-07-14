Ext.ns('micrite.util');
micrite.util = function() {
	var success = function(r,o){
		var res = Ext.decode(r.responseText);
		if (res.message){
			showExpire();
		   showMsg('success',res.message);
		}
	};
	var failure = function(r,o){
		var res = Ext.decode(r.responseText);
		if (res.message){
			showExpire();
			showMsg('failure', res.message);
			Ext.Msg.alert('info','FALSE');
		}
	};
	
	var callback = function(el,s,r,o){
			showExpire();
	};
	
	var showExpire = function(){
		if (Ext.get('session-expired')){
			Ext.Msg.alert('info','FALSE');
			//window.location='j_spring_security_logout';
		}
	}
	 return {
		gridLoad : function () {
 			c ={listeners:{
		                loadexception:function(proxy, options, resp, error) {
 						var res = Ext.decode(resp.responseText);
			 				if (res.message){
			 				   showMsg('failure',res.message);
			 				}
 							showExpire();
		                }
		            }
 				}
	 		return c;
	    },
		autoLoad : function (c){
	 			c = Ext.apply(c,{
	 				callback: callback
	 				}
	 			);
	 		return c;
	 	},
	 	genWindow : function(c){
	 		 if (c.id && Ext.getCmp(c.id)) {
                Ext.getCmp(c.id).center();
                return;
            }
	 		if (c.autoLoad){
	 			c.autoLoad = Ext.apply(c.autoLoad,{
	 					callback: callback,
	 					nocache: true
	 				}
	 			);
	 		}
	 		var pbid = Ext.id();
	        var win = new Ext.Window(Ext.apply({
		            closable : true,
		            width    : 640,
		            height   : 520,
		            plain    : true,
		            maximizable: true,
		            html:    '<div id="'+ pbid +'" style="height:100%;top:40%;left:30%;position:absolute;"></div>',
		            layout   : 'fit',
	            },c));
	        win.show();
	        win.center();
            var pb = new Ext.ProgressBar({
                            width:Math.round(win.width*0.4,0),
                            renderTo:Ext.get(pbid)
                        });
            pb.wait({
                interval:100,
                text:'Loading...',
                increment:10
            });
	        return win;
	    },
	    ajaxRequest : function (c,scope){
	    	c.success = success.createSequence(c.success,scope);
	    	c.failure = failure.createSequence(c.failure,scope);
	        Ext.Ajax.request(Ext.apply({
	            scope:scope,
	            method:'post',
	            requestexception:function(conn,response,options){
	            	Ext.Message.alert('Serivce Down');
	            }
	        },c));
	    }
	 }
}();