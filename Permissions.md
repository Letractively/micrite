
# Introduction #
本文档作为 micrite 用户手册的一部分，主要描述如何设置权限，对有关关开发，实现方面的内容不做讨论。

micrite 希望借助 SS(spring security) 强大的安全控制，尽可能的解决大型项目中复杂多变的权限需求。

<br><br>
<h1>Elements</h1>
micrite 的权限逻辑由*用户 ( User )<b>、</b>角色 ( Role )<b>和*授权资源 ( Authority )</b> 之间的关系来体现。<br>
<br>
所有的授权都针对角色 (只有 ACL 可以对用户) ，这意味着，如果一个用户 <b>绑定 ( bind )</b> 到一个角色，哪他就拥有了角色上的所有授权资源 ( Authority )，<br>
同样，如果一个用户与角色 <b>绑除帮定 ( unbind )</b> ，哪他就失去了此角色所拥有的所有权限。<br>
<br>
User,Role,Authority三者之间的关系为多对多的关系，并且可以互操作，如下图：<br>
<br>
<pre><code># User &lt;----<br>
#           \ (bind)<br>
#   (unbind) \<br>
#             ----&gt; Role &lt;----<br>
#                             \ (bind)<br>
#                     (unbind) \<br>
#                               ----&gt; Authority<br>
</code></pre>
micrite 提供灵活的前台页面供用户进行 bind/unbind 操作：<br>
<table><thead><th> bind\unbind </th><th> <b>to many Users</b> </th><th> <b>to many Roles</b> </th><th> <b>to many Authorities</b> </th></thead><tbody>
<tr><td> <b>from one User</b> </td><td>N/A                   </td><td>1. 菜单进入 User List<br>2. 选定 User<br>3. 点击 Bind Role 按钮<br>4. 在弹出的窗口中选择要操作的 Role<br>5. 点击 Bind 或 Unbind 按钮</td><td>N/A                         </td></tr>
<tr><td> <b>from one Role</b> </td><td>1. 菜单进入 Role List<br>2. 选定 Role<br>3. 点击 Bind User 按钮<br>4. 在弹出的窗口中选择要操作的 User<br>5. 点击 Bind 或 Unbind 按钮</td><td>N/A                   </td><td>1. 菜单进入 Role List<br>2. 选定 Role<br>3. 点击 Bind Authority 按钮<br>3. 在弹出的窗口中选择要操作的 Authority <br>3. 点击 Bind 或 Unbind 按钮</td></tr>
<tr><td> <b>from one Authority</b> </td><td>N/A                   </td><td>1. 菜单进入 Authority List<br>2. 选定 Authority<br>3. 点击 Bind Role 按钮<br>4. 在弹出的窗口中选择要操作的 Role<br>5. 点击 Bind 或 Unbind 按钮</td><td>N/A                         </td></tr></tbody></table>


<br><br>
<h1>Type</h1>
micrite 提供４中权限控制途径：<br>
<ul><li>Menu : 控制入口模块。<br>
</li><li>URL Interceptor : 对指定的URL资源进行保护。<br>
</li><li>Method Interceptor : 对指定的方法进行保护。<br>
</li><li>Access Control List : 对特定的数据进行保护。</li></ul>

上面的4中授权方式的侧重点和用途各不相同，Menu 和 URL 可以通过界面被用户很容易的使用， Method 和 ACL 则需要了解 micrite 的代码和数据结构才能掌握。<br>
<br>
<br>
<h2>Menu</h2>
micrite 的系统菜单控制着用户可以访问的入口模块。它本质上也是一个 URL，数据存储在 authorities 表，类型 ( TYPE ) 为 URL。<br>
如果从数据库客户端执行下面 SQL 语句：<br>
<pre><code>select * from authorities where type='URL' and name like '/%'<br>
</code></pre>
会得到如下系统默认保护的 URL：<br>
<table><thead><th> <b>ID</b> </th><th> <b>NAME</b> </th><th> <b>TYPE</b> </th><th> <b>VALUE</b> </th></thead><tbody>
<tr><td> 2         </td><td> /Security Modules/User List </td><td> URL         </td><td> /security/userList.js<code>*</code> </td></tr>
<tr><td> 3         </td><td> /Security Modules/Authority List </td><td> URL         </td><td> /security/authorityList.js<code>*</code> </td></tr>
<tr><td> 4         </td><td> /Security Modules/Role List      </td><td> URL         </td><td> /security/roleList.js<code>*</code> </td></tr>
<tr><td> 5         </td><td> /CRM Modules/Customer List       </td><td> URL         </td><td> /crm/customerList.js<code>*</code> </td></tr></tbody></table>

<ul><li>NAME：micrite 约定如果 name 字段以 <b>' / '</b> 开头，即是一个受保护的 URL ，又是一个菜单数据。最后一个 <b>' / '</b> 之后的字符串为菜单上最终显式的名称(叶子节点)， 两个 <b>' / '</b> 之间的字符串都是菜单的中间节点。<br>
</li><li>TYPE：所有的菜单都是一个受保护的 URL 。<br>
</li><li>VALUE：模块文件的路径，末尾的 <code>*</code> 用来解决一些带参数的URL加载，如：'/security/userSelect.jsp?roleId=1' 。</li></ul>

所以，如果要为菜单上新增一个模块，需要下面几个步骤：<br>
<ol><li>进入 Authority List 模块<br>
</li><li>Toolbar 上的 Action Menu 菜单，点击 Add Authority<br>
</li><li>填写 name (注意要以<b>' / '</b>开头) , value, 选择 Type 为 URL<br>
</li><li>保存<br>
</li><li>为新增的菜单 bind 到相应的 Role ，否则无法生效(见 Elements 小节)。<br>
</li><li>重新登录，就可以看到新的菜单</li></ol>

<br>
<h2>URL Interceptor</h2>
普通 URL 保护的目的是为了防止他人以非法的方式获取、甚至修改数据。<br>
例如 Authority 的第一条资源 (ID=1 ,Name='All', VALUE=<code>/**</code>) 表示所有的URL都被保护，系统默认将它被绑定到所有角色，这样非登录用户将不能访问 micrite 的任何 URL。<br>
<br>
URL可以保护包括目录、图片、action等任何来自客户端请求的地址。<br>
<br>
增加一个URL的操作方法和为菜单新增一个模块相同，注意不要在 Name 的第一个字符用 <b>' / '</b>。<br>
<br>
<br>
<h2>Method Interceptor</h2>
我们通过方法拦截来实现不同角色可以有不同的操作，也就是说一个方法只能有指定的角色才能调用。<br>
<br>
例如 micrite 初始设置：除了 <b>管理员组 ( ROLE_ADMIN )</b> 可以修改权限，<b>普通用户 ( ROLE_USER )</b> 只能浏览权限。 ROLE_ADMIN 可以通过系统界面随时修改当前权限。<br>
<br>
如果从数据库客户端执行下面 SQL 语句<br>
<pre><code>select * from AUTHORITIES where TYPE='METHOD'<br>
</code></pre>

会得到如下系统默认保护的方法：<br>
<table><thead><th> <b>ID</b> </th><th> <b>NAME</b> </th><th> <b>TYPE</b> </th><th> <b>VALUE</b> </th></thead><tbody>
<tr><td> 6         </td><td> Role List Search Method protect          </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.IRoleService.<code>*</code>PerPage(..) </td></tr>
<tr><td> 7         </td><td> Role Bind Method protect                 </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.I<code>*</code>Service.<code>*</code>bind<code>*</code>(..) </td></tr>
<tr><td> 8         </td><td> Role unBind Method protect               </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.I<code>*</code>Service.<code>*</code>unBind<code>*</code>(..) </td></tr>
<tr><td> 9         </td><td> add action of security module protect    </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.I<code>*</code>Service.add<code>*</code>(..) </td></tr>
<tr><td>10         </td><td> update action of security module protect </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.I<code>*</code>Service.update<code>*</code>(..) </td></tr>
<tr><td>11         </td><td> delete action of security module protect </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.I<code>*</code>Service.delete<code>*</code>(..) </td></tr>
<tr><td>12         </td><td> update me method protect                 </td><td> METHOD      </td><td> <code>*</code> org.gaixie.micrite.security.service.IUserService.updateMe(..) </td></tr></tbody></table>

基于方法的授权资源 Type 为 METHOD， value 需要满足 aspectj 表达式，简单说明它们的作用：<br>
<blockquote>ID=6 : 保护 IRoleService 接口类所有以 PerPage 结尾的方法，并忽略方法的参数。(被默认绑定到所有角色)。<br>
ID=7,8,9,10,11 : 保护所有 Security 包下数据更新的方法，并忽略方法的参数。(被默认绑定到 ROLE_ADMIN )。<br>
ID=12 : 保护 IRoleService 接口类的 updateMe 方法，并忽略方法的参数。(被默认绑定到所有角色)。<br></blockquote>

通过将上边保护的方法绑定到相应的角色，micrite 实现了前面提到的安全策略，ROLE_USER 下的用户除了可以修改自己的用户信息，不能作任何数据更新的操作。<br>
<br>
有关aspectj表达式语法，可参见：<a href='http://static.springsource.org/spring/docs/2.0.x/reference/aop.html'>Spring AOP的Reference</a>。<br>
<br>
micrite 使用 Spring AOP 拦截所有服务层方法 (所有 service包下以 Service结尾的类的任何方法)，同时按下面流程进行处理：<br>
<pre><code># 验证方法是否被保护----&gt;<br>
#       \             \ 否<br>
#        \             \<br>
#         \ 是            ----&gt; 放行该方法<br>
#          \<br>
#           ----&gt;，判断User是否有权限----&gt;<br>
#                      \                \是<br>
#                       \                \<br>
#                        \否                ----&gt; 放行该方法<br>
#                         \<br>
#                          ----&gt; 禁止执行该方法，给出无权限访问的提示信息<br>
</code></pre>

添加一个 Method 和 URL 的添加方法没有区别，注意 aspectj 表达式语法，不合法的表达式可能导致 Web Server*无法启动*的严重错误。<br>
<br>
<br>
<h2>Access Control List</h2>
作为一个示例，micrite 只对 Role 对象应用了 ACL ( Access Control List )，如果想保护更多的对象实例，需要对代码进行少量的修改，详见：。<br>
<br>
要了解 ACL 的功能，最简单的方法就是分别用系统初始的两个用户登录，查询 Role List，会发现：admin 用户可以看到所有的 Role，<br>
而 user 只能看到自己所在的 Role (即ROLE_USER)。<br>
<br>
当你新增一个 Role，例如：ROLE_SALES，那么除了 ROLE_SALES 角色下的用户，其它人都无法读取此角色（ROLE_ADMIN出外）。<br>
这样，micrite 就实现了每个用户可以浏览自己拥有的权限，管理员可以维护所有角色的权限。<br>
<br>
目前 micrite 中 ACL 服务的处理流程如下：<br>
<pre><code># 验证方法是否被保护----&gt;<br>
#       \             \ 否<br>
#        \             \<br>
#         \ 是            ----&gt; 放行该方法<br>
#          \<br>
#           ----&gt;，判断User是否有权限----&gt;<br>
#                      \               \否<br>
#                       \               \<br>
#                        \是               ----&gt; 禁止执行该方法，给出无权限访问的提示信息<br>
#                         \<br>
#                          ----&gt; 执行方法并获的对象实例集合----&gt;是否调用ACL服务----&gt;<br>
#                                                                 \           \否<br>
#                                                                  \           \<br>
#                                                                   \是           ----&gt;返回全部结果集<br>
#                                                                    \<br>
#                                                                     ----&gt;根据ACL配置，返回有访问权限的结果集<br>
</code></pre>

通过将 <b>afterInvocationManager</b> 加入到方法拦截后的处理链(见 applicationContext-security.xml 文件)，实现了方法调用后的ACL对象结果集过滤。<br>
注意 afterAclCollectionRead 需要一个名为 <b>AFTER_ACL_COLLECTION_READ</b> 的角色来配合它进行结果集过滤，角色 AFTER_ACL_COLLECTION_READ 需要<br>
绑定一个被保护的方法（ID=6 见Method小节），如果返回的是Role对象实例结果集，ACL服务会进行过滤处理。所以不要修改或者删除此角色。<br>

ACL 在项目中的应用，需要明确的权限划分，否则很有可能日后出现权限混乱、难以维护、效率下降的问题。<br>
<br>
<br><br>
<h1>Reference</h1>
<ul><li><a href='http://blogs.gaixie.org/tommy/?p=7'>Hibernate implementation of Spring Security ACL</a>
</li><li><a href='http://static.springsource.org/spring-security/site/docs/2.0.x/reference/springsecurity.html'>Spring Security Reference Documentation</a>
</li><li><a href='http://www.family168.com/tutorial/springsecurity/html/springsecurity.html'>Spring Security中文参考文档</a>
</li><li><a href='http://server.denksoft.com/wordpress/?page_id=5'>A Spring Security ACL tutorial for PostgreSQL</a><br>