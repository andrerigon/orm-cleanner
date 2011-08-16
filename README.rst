orm-cleanner
============

Clean entities relationships.


Configure Environment
*********************

::

    $ mvn clean install
    $ mvn eclipse:eclipse


Deploy
******

::

    $ mvn clean install
    
Usage
*****

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


Project Information
*******************

:Author: ZUP IT INNOVATION
:License: New BSD License

