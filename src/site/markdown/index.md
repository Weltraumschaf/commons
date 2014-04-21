# Common Utility Classes for Java

In this  library I  collect classes  I'm using in  several projects  to minimize
code duplication over the projects.

## Goal

Goal  of  this  class  library  is  to  share  common  functionality  to  reduce
duplicated  code.  Further  goal  is  to have  no  external  dependencies.  Only
external dependencies are [JUnit][junit]  and [Mockito][mockito] for testing and
[Findbugs][findbugs] annotations to suppress some warnings.

## Usage

Add this <code>&lt;dependency&gt;</code> to your [Maven][maven] pom.xml:

    <dependencies>

        <!-- ... -->

        <dependency>
            <!-- http://weltraumschaf.github.com/commons/ -->
            <groupId>de.weltraumschaf.commons</groupId>
            <artifactId>MODULE-NAME</artifactId>
            <version>1.0.0</version>
        </dependency>

        <!-- ... -->

    <dependencies>
    
Available modules (artifact id) are:

- [application](application/index.html)
- [concurrent](concurrent/index.html)
- [experimental](experimental/index.html)
- [guava](guava/index.html)
- [shell](shell/index.html)
- [swing](swing/index.html)
- [system](system/index.html)
- [validate](validate/index.html)

## File Signatures

To receive the keys type:

    $> gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys 45585598

## Deployment

This  artefact  is  deployed  to   Maven  Central  Repository  along  with  this
[guide][sonatype].

[sonatype]: http://maven.apache.org/guides/mini/guide-central-repository-upload.html
[maven]:    http://maven.apache.org/
[junit]:    http://www.junit.org/
[mockito]:  http://code.google.com/p/mockito/
[findbugs]: http://findbugs.sourceforge.net/
