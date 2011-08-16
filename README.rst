orm-cleanner
============

Clean entities relationships.


Instalation
***********

::

   git clone git://github.com/ZupIT/orm-cleanner.git
   cd orm-cleanner
   mvn install
   
Usage
*****

- Add this to your pom.xml

::

   <plugin>
      <groupId>br.com.zup</groupId>
      <artifactId>orm-cleanner</artifactId>
      <configuration>
         <outputDirectory>/home/user/project-exemple</outputDirectory>
         <packageScan>org.organization.model.entities</packageScan>
      </configuration>
      <executions>
         <execution>
            <id>default-compile</id>
            <phase>compile</phase>
            <goals>
            <goal>compile</goal>
            </goals>
         </execution>
      </executions>
   </plugin>


Referencies
***********

* http://maven.apache.org/
* http://maven.apache.org/guides/plugin/guide-java-plugin-development.html
* http://books.sonatype.com/books/mvnref-book/reference/writing-plugins.html


Project Information
*******************

:Author: ZUP IT INNOVATION
:License: New BSD License

