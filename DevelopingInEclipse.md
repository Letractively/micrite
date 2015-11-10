
# Introduction #
The following instructions will enable you to get Micrite into the Eclipse 3.4 workspace as an eclipse project. You will be able use eclipse to make code changes and run Unit tests. For purposes of this discussion the development OS is Windows. Please adjust these instructions for your environment.

<br><br>
<h1>Prerequisites</h1>

Install the software required for <a href='Building.md'>Building Gaixie Micrite</a> and follow the instructions to build Geronimo.<br>
<br>
<br><br>
<h1>Creating Eclipse Projects</h1>
You could import all subprojects in the workspace. But a more efficient way is to import only the modules that you want to work on.<br>
<br>
<h2>Working with a single module</h2>
If you are planning to modify a single micrite module, you can import only that module in the workspace by running the following commands:<br>
<br>
<pre><code>mvn install<br>
cd modules\a_module<br>
mvn -o eclipse:eclipse<br>
</code></pre>

<h2>Working with multiple modules</h2>
We have no the feature at this time.<br>
<br>
<br><br>
<h1>Firing up Eclipse</h1>

<ul><li>Import the project(s) to eclipse workspace using:<br>
<pre><code>File --&gt; Import --&gt; +General --&gt; Existing Projects into Workspace<br>
</code></pre>
</li></ul><blockquote>Select project or projects depending on how you created the project in the Creating Eclipse Projects step.</blockquote>

<ul><li>Set the Maven Classpath variable M2_REPO to tell Eclipse where the Maven repository is by clicking the "New" button in<br>
<pre><code>Window --&gt; Preferences --&gt; Java --&gt; Build Path --&gt; Classpath Variables<br>
</code></pre>
</li></ul><blockquote>Enter M2_REPO and set it to your maven2 repository directory. On linux this directory is usually located at<br>
<pre><code>~/.m2/repository<br>
and on windows its usually at<br>
"%USERPROFILE%\.m2\repository"<br>
</code></pre>
<h2>Code Formatting</h2>
</blockquote><ul><li>Configure eclipse to not use tabs by doing the following:<br>
<pre><code>Window --&gt; Preferences --&gt; Java --&gt; Code Style --&gt; Formatter --&gt; Java Conventions [built-in]<br>
</code></pre>
Click on "Edit". Under Indentation select Tab policy as "space only". Enter a new name for this profile and click "OK".</li></ul>

<ul><li>Create new java file(s) with comments using:<br>
<pre><code> Window --&gt; Preferences --&gt; Java --&gt; Code Style --&gt; Code Templates --&gt; Comments --&gt; Files<br>
</code></pre>
</li></ul><blockquote>Click on "Edit". Replace default content with following lines:<br>
<pre><code> /* ===========================================================<br>
 * $$Id$$<br>
 * This file is part of Micrite<br>
 * ===========================================================<br>
 *<br>
 * (C) Copyright 2009, by Gaixie.org and Contributors.<br>
 * <br>
 * Project Info:  http://micrite.gaixie.org/<br>
 *<br>
 * Micrite is free software: you can redistribute it and/or modify<br>
 * it under the terms of the GNU General Public License as published by<br>
 * the Free Software Foundation, either version 3 of the License, or<br>
 * (at your option) any later version.<br>
 *<br>
 * Micrite is distributed in the hope that it will be useful,<br>
 * but WITHOUT ANY WARRANTY; without even the implied warranty of<br>
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<br>
 * GNU General Public License for more details.<br>
 *<br>
 * You should have received a copy of the GNU General Public License<br>
 * along with Micrite.  If not, see &lt;http://www.gnu.org/licenses/&gt;.<br>
 *<br>
 */<br>
</code></pre></blockquote>

<br><br>
<h1>Other Useful Information</h1>
If you plan on contributing code or patches to the project please configure your subversion client :<br>
<br>
<ul><li><a href='http://docs.google.com/Doc?id=dggnm2v4_3d9zr5rcd'>Subversion Client Configuration</a>