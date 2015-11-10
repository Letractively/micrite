### In development ###

  * 增加系统自动邮件通知功能，基于Spring的`JavaMailSender`
  * 忘记密码时，通过邮件进行密码重置

### v0.11 ###
  * released Aug 27, 2009
  * 基于Spring Security的ACL 实现，可以对对象实例进行权限控制
  * 根据数据库字段动态生成查询条件，包括 and or 等组合方式
  * 增加HsqlDB数据库的支持，并作为默认数据库
  * 用户进行系统皮肤切换，不同重新登录，直接生效