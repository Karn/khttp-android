.. _install:

Installation
============

khttp is a new library, and it isn't on Maven Central, yet. Once it matures a bit, it will be looked into.

JitPack
-------

`JitPack <https://jitpack.io/>`_ is a great service that will build GitHub projects and offer them as a repository for
free. If you want to use khttp in a project, use JitPack.

Maven
-----

Add the JitPack repository

.. code-block: xml

	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>

Add the dependency

.. code-block: xml

	<dependency>
	    <groupId>com.github.jkcclemens</groupId>
	    <artifactId>khttp</artifactId>
	    <version>-SNAPSHOT</version>
	</dependency>

*As khttp has no official releases or tags at the moment, JitPack uses -SNAPSHOT to denote the latest commit.*
