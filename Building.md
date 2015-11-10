
# Introduction #
This document how to build the Gaixie Micrite project.

This guide is intended to cover how to build the latest svn/trunk,though other newer branches/tags should also follow similar instructions. <br>
Micrite trees that use the same basic build tooling include:<br>
<br>
<ul><li><a href='http://micrite.googlecode.com/svn/trunk'>svn/trunk</a>
</li><li><a href='http://micrite.googlecode.com/svn/tags'>svn/tags/v0.x</a></li></ul>

<br><br>
<h1>Prerequisites</h1>
<h2>Java Developer Kit (JDK)</h2>

You will need a <a href='http://java.sun.com/j2se/1.5.0/'>JDK 5.0+ (J2SE 1.5.0+)</a> or compatible JDK to build Micrite. <br>
It is recommended you use SUN's implementation, or something compatible like Apples implementation. <br>
Other JDK vendors implementations may work, but use at your own risk.<br>
<br>
<h2>Apache Maven 2</h2>

To execute the build process you need to have <a href='http://maven.apache.org/download.html'>Apache Maven version 2.0.9</a> (or newer) installed.<br>
To check if your installation is working and you have the required minimum version run:<br>
<br>
<pre><code>mvn -version<br>
</code></pre>

And it should produce something like:<br>
<br>
<pre><code>Maven version: 2.0.9<br>
</code></pre>

If you have an incompatible version the project build will probably fail with a message complaining<br>
<br>
<h2>Subversion</h2>

To fetch the source code for the server, you will need to have a <a href='http://subversion.tigris.org/'>Subversion</a> client version 1.5 (or newer, 1.5 is recommended) installed.<br>
<pre><code>Windows Tip<br>
<br>
Windows users are strongly encouraged to change the M2 local repository (the place where dependencies are downloaded) to a shorter path with no spaces, e.g. C:\.m2.<br>
Using a longer path may cause the build to behave very strangely when it hits the 260 char limit for filenames on Windows.<br>
In order to change the m2 local repository go to %USERPROFILE%\.m2 and edit or create settings.xml file to contain the following content:<br>
--------------------------------------------------------<br>
&lt;?xml version="1.0"?&gt;<br>
&lt;settings&gt;<br>
    &lt;localRepository&gt;C:\.m2&lt;/localRepository&gt;<br>
&lt;/settings&gt;<br>
</code></pre>

<br><br>
<h1>Checkout Micrite</h1>

<pre><code>svn checkout http://micrite.googlecode.com/svn/trunk/ micrite-read-only<br>
</code></pre>
See <a href='http://code.google.com/p/micrite/source/checkout'>http://code.google.com/p/micrite/source/checkout</a> for more detail.<br>
<br>
<br><br>
<h1>Preparing to Build for the First Time</h1>

Chances are you will need to increase the heap size for Maven.<br>
<br>
Add the MAVEN_OPTS environment variable to specify JVM properties, e.g. export MAVEN_OPTS="-Xms256m -Xmx512m"<br>
<pre><code>Windows Tip<br>
Add the MAVEN_OPTS environment variable by opening up the system properties (WinKey + Pause) --&gt; "Advanced" tab--&gt; "Environment Variables" button, <br>
then adding the MAVEN_OPTS variable in the user variables with the value -Xms256m -Xmx512m .<br>
</code></pre>
This environment variable can be used to supply extra options to Maven.<br>
<br>
See <a href='http://maven.apache.org/download.html'>http://maven.apache.org/download.html</a> for more detail.<br>
<br>
<br><br>
<h1>Building</h1>

To build all changes incrementally, from project root directory run:<br>
<pre><code>mvn install<br>
</code></pre>
The first time, this will lead to "table does not exist" error messages but they can be (and are) ignored.<br>
<br>
To perform clean builds, which are sometimes needed after some changes to the source tree:<br>
<pre><code>mvn clean install<br>
</code></pre>

<br><br>
<h1>Testing the Assembly</h1>

Once you have build micrite fully, which will produce a war archive from the assemblies modules,<br>
<br>
you can use jetty-maven-plugin to run it. From the assemblies directory run:<br>
<pre><code>mvn jetty:run<br>
</code></pre>
And to stop, just CTRL-C.<br>
<br>
By default, Micrite use embedded Java database from the <a href='http://db.apache.org/derby/'>Apache Derby project</a>, you can easily switch to another open source database such as MySQL, PostgreSQL. See <a href='InstallGuide.md'>InstallGuide</a> for more detail.