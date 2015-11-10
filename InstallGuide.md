
# Introduction #
This document describes how to install the Gaixie Micrite software. <br>
It explains what you need to install first, how to download Micrite, how to configure Micrite and how to install it to your existing java application server and relation database.<br>
<br>
<br><br>
<h1>Ready?</h1>
First, let's make sure you have everything you need to install and run Micrite.<br>
<br>
Micrite is a database-driven Java web application. To run it you need Java, a Java Servlet container such as Tomcat and a database such as MySQL. More specifically, here's what you need to install and run Micrite:<br>
<ul><li>The Java development kit, specifically the Sun Java 2 SE 1.6 JDK. The computer on which you install Micrite should be configure to run with the Java SE 6.<br>
</li><li>A Java aplication server, or more specifically a Servlet container that supports the Servlet 2.4 API. Hereinafter, we'll just call this your server. The Micrite community tends to use and is best able to answer questions about Tomcat 5.5, Tomcat 6.0.<br>
</li><li>A relational database such as MySQL, Apache Derby or PostgreSQL. Micrite includes database creation scripts for Derby, MySQL, PostgreSQL and MsSQL.</li></ul>

<br><br>
<h1>Download and un-package Micrite</h1>
Download the Gaixie Micrite release file from <a href='http://code.google.com/p/micrite/downloads/list'>http://code.google.com/p/micrite/downloads/list</a> and use your favorite ZIP program to unzip the release into a directory on your computer's disk.<br>
<br>
<h2>Installation directory layout</h2>
Once you've unpackaged the files you'll find a directory structure like this:<br>
<pre><code>gaixie-micrite-x.x<br>
  |--README.txt<br>
  |--LICENSE.txt<br>
  |--src<br>
  |--dbscripts<br>
  |--webapp<br>
       |--micrite<br>
</code></pre>

<h2>The Micrite War</h2>
The Micrite application itself is in the directory webapp/micrite and is organized using the standard Java EE WAR directory structure.Generally speaking, you won't need to modify and files in side the director. You can deploy it as is, in directory form, or you can package it up as a .war if you prefer to deploy as a file.<br>
<br>
For example, here's how you'd package it up as a WAR:<br>
<pre><code>% cd gaixie-micrite/webapp/micrite<br>
% jar cvf ../micrite.war *<br>
</code></pre>

<br><br>
<h1>Prepare your database for Micrite</h1>
Before you can install Micrite you'll probably need to some work to prepare your database for Micrite.<br>
<br>
You need some place to put the Micrite tables. Some folks call this a table-space, but we refer to it as a database.<br>
You need to create a database for Micrite, or get your database administrator to do it for you. And second, you need to have a JDBC driver installed for you database of choice.<br>
<br>
<b>Note: If you want take a glance at micrite v0.11 or higher, you can use hsqldb in memory, just skip to <a href='http://code.google.com/p/micrite/wiki/InstallGuide#Deploy_Micrite'>Deploy Micrite</a></b>
<h2>Create a database for Micrite</h2>
If you're luck enough to have your own database administrator, ask them to setup a database for Micrite. When they are done, ask them to provide you with this information, you'll need it later:<br>
<ul><li>Username and password for connecting to database<br>
</li><li>JDBC connection URL for database<br>
</li><li>JDBC driver class name</li></ul>

If you don't have a database administrator then you'll have to refer to the documentation for your database and do it yourself. You need to create a database for Micrite, protected by username and password.<br>
<ul><li>For example, if you're using Derby you might do something like this:<br>
<ol><li>To control derby, from project root directory run:<br>
<pre><code>gaixie-micrite-x.x&gt; java -cp webapp/micrite/WEB-INF/lib/derbytools-10.4.2.0.jar:webapp/micrite/WEB-INF/lib/derby-10.4.2.0.jar -Dderby.ui.codeset=utf8 org.apache.derby.tools.ij<br>
<br>
# Windows users need replace “:” (colon) with “;” (semi-colon), like this:<br>
gaixie-micrite-x.x&gt; java -cp webapp/micrite/WEB-INF/lib/derbytools-10.4.2.0.jar;webapp/micrite/WEB-INF/lib/derby-10.4.2.0.jar -Dderby.ui.codeset=utf8 org.apache.derby.tools.ij<br>
</code></pre>
</li><li>Connect Derby ,create database and tables:<br>
<pre><code>ij&gt; connect 'jdbc:derby:micritedb;create=true;user=micrite;password=micrite';<br>
ij&gt; run 'dbscripts/derby/createdb.sql';<br>
ij&gt; exit;<br>
</code></pre>
</li><li>Now, A directory has been created , check directory structure like this:<br>
<pre><code>gaixie-micrite-x.x<br>
  |--README.txt<br>
  |--LICENSE.txt<br>
  |--src<br>
  |--dbscripts<br>
  |--micritedb<br>
  |--webapp<br>
       |--micrite<br>
</code></pre></li></ol></li></ul>

<ul><li>MySQL like this:<br>
<pre><code>$ mysql -u root -p<br>
password: *****<br>
mysql&gt; create database micritedb;<br>
mysql&gt; grant all on micritedb.* to micrite@'%' identified by 'micrite';<br>
mysql&gt; use micritedb;<br>
mysql&gt; source {project_home}/dbscripts/mysql/createdb.sql<br>
</code></pre></li></ul>

<ul><li>PostgreSQL like this:<br>
<pre><code>$createdb -h localhost -p 5432 --encoding=utf8 -U postgres micritedb<br>
# login database use postgresql admin account<br>
$ psql -h localhost -p 5432 -U postgres<br>
postgres&gt;CREATE USER micrite_user WITH PASSWORD 'yourpassowrd';<br>
postgres&gt;ALTER DATABASE micritedb OWNER TO micrite_user;<br>
postgres&gt;\q<br>
#relogin as micrite_user<br>
$ psql -h localhost -p 5432 -U micrite_user micritedb<br>
# execute script file<br>
micritedb&gt;\i {project_home}/dbscripts/postgresql/createdb.sql<br>
</code></pre></li></ul>

<h2>Check your JDBC driver setup</h2>
Make sure that you have the correct JDBC driver installed in your server. Usually, this is as simple as downloading the JDBC driver jar(s) and placing it (or them) into your server's classpath. For example, on Tomcat 5.5 you place them in common/lib, on Tomcat 6.0 in lib.<br>
<br>
<br><br>
<h1>Configure Micrite</h1>
Here is how to configure database connection for Micrite.<br>
<ol><li>Find your <i><b>WEB-INF/classes/databaseResource-hibernate.xml</b></i> file and open it in a text editor.<br>
</li><li>Search the beans with ids <b>dataSource</b> and change the value field of the following property.<br>
<pre><code>&lt;property name="driverClassName" value="org.apache.derby.jdbc.EmbeddedDriver"/&gt;<br>
&lt;property name="url" value="jdbc:derby:{ProjectHome}/micritedb" /&gt;<br>
&lt;property name="username" value="micrite" /&gt;<br>
&lt;property name="password" value="micrite" /&gt;<br>
</code></pre>
</li><li>Find your <i><b>WEB-INF/classes/hibernate.properties</b></i> file and open it in a text editor.<br>
</li><li>Search "Platforms" text and comment-out your database part,then comment default database. For example, if you're useing Derby  you might do it like this:<br>
<pre><code>## Derby<br>
hibernate.dialect org.hibernate.dialect.DerbyDialect<br>
<br>
## PostgreSQL<br>
#hibernate.dialect org.hibernate.dialect.PostgreSQLDialect<br>
</code></pre></li></ol>

<br><br>
<h1>Deploy Micrite</h1>

Use your server's administration console to deploy the Micrite web application.<br>
You can deploy as a directory, in which case you'll have to enter the path to your Micrite installation's webapp/micrite directory. Or you can deploy as a WAR file, in which case you will have to have created a WAR file as we described in Section <a href='InstallGuide#The_Micrite_War.md'>The Micrite War</a>.<br>
<br>
<h2>Deploying to Apache Tomcat 6.0</h2>
You can deploy to Tomcat as either a directory or a WAR file.<br>
<ul><li>Deploy directory (Windows)<br>
<pre><code>Context Path : /micrite<br>
WAR or Directory URL : c:\gaixie-micrite-0.9\webapp\micrite<br>
</code></pre>
</li><li>WAR file to deploy<br>
<pre><code>Just select micrite.war file to deploy.<br>
</code></pre>
Once Micrite is deployed, you'll see that it's listed among the other applications. There's a <i><b>/micrite</b></i> link to Micrite itself and there are options to start, stop, reload and undeploy.<br>
If Micrite doesn't start-up, look for the problem in the logs. You can find them in Tomcat's logs directory. The Tomcat log file is catalina.out.</li></ul>

<br><br>
<h1>Getting started with Micrite</h1>
Visit <a href='http://localhost:8080/micrite'>http://localhost:8080/micrite</a> with following default account.<br>
<table><thead><th> <b>Username</b> </th><th> <b>Password</b> </th><th> <b>Role</b> </th></thead><tbody>
<tr><td> admin           </td><td> 123456          </td><td> ROLE_ADMIN  </td></tr>
<tr><td> user            </td><td> 123456          </td><td> ROLE_USER   </td></tr>