

# Introduction #
This page describes the standards used for Micrite code (java, xml, whatever). Code is read by a human being more often than it is actually written by a human being, make the code a pleasure to read.

In addition, check the wiki for details on [DevelopingInEclipse](DevelopingInEclipse.md).

<br><br>
<h1>Indentation</h1>
<h2>Java</h2>
Lets follow Sun's coding standard rules which are pretty common in Java.<br>
<br>
<a href='http://java.sun.com/docs/codeconv/'>http://java.sun.com/docs/codeconv/</a>

<a href='http://java.sun.com/docs/codeconv/html/CodeConvTOC.doc.html'>http://java.sun.com/docs/codeconv/html/CodeConvTOC.doc.html</a>

<ul><li>4 characters indentation<br>
</li><li><b>No tabs</b> please!</li></ul>

Correct brace style:<br>
<pre><code>public class Foo {<br>
    public void foo(boolean a, int x, int y, int z) {<br>
        do {<br>
            try {<br>
                if (x &gt; 0) {<br>
                    int someVariable = a ?  x : y;<br>
                } else if (x &lt; 0) {<br>
                    int someVariable = (y + z);<br>
                    someVariable = x = x + y;<br>
                } else {<br>
                    for (int i = 0; i &lt; 5; i++) {<br>
                        doSomething(i);<br>
                    }<br>
                }<br>
<br>
                switch (a) {<br>
                    case 0:<br>
                        doCase0();<br>
                        break;<br>
                    default:<br>
                        doDefault();<br>
                }<br>
            } catch (Exception e) {<br>
                processException(e.getMessage(), x + y, z, a);<br>
            } finally {<br>
                processFinally();<br>
            }<br>
        } while (true);<br>
<br>
        if (2 &lt; 3) {<br>
            return;<br>
        }<br>
<br>
        if (3 &lt; 4) {<br>
            return;<br>
        }<br>
<br>
        do {<br>
            x++<br>
        } while (x &lt; 10000);<br>
<br>
        while (x &lt; 50000) {<br>
            x++;<br>
        }<br>
<br>
        for (int i = 0; i &lt; 5; i++) {<br>
            System.out.println(i);<br>
        }<br>
    }<br>
<br>
    private class InnerClass implements I1, I2 {<br>
        public void bar() throws E1, E2 {<br>
        }<br>
    }<br>
}<br>
</code></pre>

<h2>XML</h2>
<ul><li>Use 4 characters. This is to allow IDEs such as Eclipse to use a unified formatting convention<br>
</li><li><b>No tabs</b> please!</li></ul>

<h2>Javascripts</h2>
<ul><li>Use 4 characters.<br>
</li><li><b>No tabs</b> please!</li></ul>

<br><br>
<h1>Classes and Interfaces</h1>
<ul><li>Sun's naming guidelines states.<br>
</li></ul><blockquote>Class names should be nouns, in mixed case with the first letter of each internal word capitalized. Try to keep your class names simple and descriptive. Use whole words - avoid acronyms and abbreviations (unless the abbreviation is much more widely used than the long form, such as URL or HTML).<br>
Examples:<br>
<pre><code> class UserAction<br>
</code></pre>
</blockquote><ul><li>Interface names should be capitalized like class names.<br>
</li></ul><blockquote>For interface names, we follow the "I"-for-interface convention: all interface names are prefixed with an "I".<br>
Examples:<br>
<pre><code>  interface IUserDao<br>
</code></pre>
This convention aids code readability by making interface names more readily recognizable.</blockquote>

<br><br>
<h1>Exceptions</h1>
The names of exception classes (subclasses of Exception) should follow the common practice of ending in "Exception".<br>
<br>
<br><br>
<h1>Imports</h1>
<ul><li>Should be fully qualified e.g.<br>
<pre><code>import java.util.Vector<br>
//and not<br>
import java.util.*<br>
</code></pre>
</li><li>Should be sorted alphabetically, with java, then javax packages listed first, and then other packages sorted by package name.</li></ul>

<br><br>
<h1>JavaDoc</h1>
<a href='http://java.sun.com/j2se/javadoc/writingapispecs/index.html'>Sun's Requirements for Writing Java API Specifications</a> deals with required semantic content of documentation comments for API specifications for the Java platform. Micrite APIs should follow these conventions.<br>
<br>
<a href='http://java.sun.com/j2se/javadoc/writingdoccomments/index.html'>Sun's How to Write Doc Comments for Javadoc</a> contains style guide and tag conventions for documentation comments. These conventions lead to high-quality code and API documentation. All code written for the Micrite should follow these conventions except as noted below.<br>
<ul><li>@version Should <b>not be used</b> in source code at all.<br>
</li><li>@author Should <b>not be used</b> in source code at all.<br>
</li><li>All HTML tags appearing in Javadoc comments must be explicitly terminated, even the ones that are considered optional in older versions of HTML such as<br>
<pre><code> &lt;p&gt;...&lt;/p&gt;<br>
</code></pre>
</li></ul><blockquote>Various internal tools that post-process the extracted HTML documentation into other forms (e.g., Windows help file) need these tags.<br>
</blockquote><ul><li>Documenting interface method implementations<br>
</li></ul><blockquote>When a method declared in an interface gets implemented in some class, there's often not a lot more to say about the method that wasn't already said in the Javadoc for the interface. In such cases, the Javadoc for the method can be omitted entirely. If the interface and the class will be Javadoc'd together, the standard 1.2 doclet automatically copies the method's description and tags from the interface to the class; if Javadoc'd separately, the automatically generated Javadoc for the method in the class will at least link it to the method in the interface.</blockquote>

<blockquote>In the source code, the implementation method should be flagged with a non-doc comment like<br>
<pre><code> /*<br>
  * Implements a method in IPath.<br>
  */<br>
</code></pre>
to alert the reader that the contract for the method is described in the named interface. This reduces the amount of method contract duplication---a serious maintenance headache---without compromising readability of the code.</blockquote>

<blockquote><br><br>
<h1>Unit Test Cases</h1>
Use the naming scheme <code> *Test.java </code> for unit tests.</blockquote>

<br><br>
<h1>Logging</h1>
Use Log4j rather than others.<br>
<ul><li>The TRACE Level designates finer-grained informational events than the DEBUG<br>
</li><li>The DEBUG Level designates fine-grained informational events that are most useful to debug an application.<br>
</li><li>The INFO level designates informational messages that highlight the progress of the application at coarse-grained level.<br>
</li><li>The WARN level designates potentially harmful situations.<br>
</li><li>The ERROR level designates error events that might still allow the application to continue running.<br>
</li><li>The FATAL level designates very severe error events that will presumably lead the application to abort.