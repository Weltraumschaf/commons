# Common Utility Classes for Java

In this library I collect classes I'  using in several projects to minimize code
duplication over the projects.

## Word of the Author

This is  my first  artefact I try  to deploy to  Maven Central  Repository along
with this [guide][sonatype].

## Goal

Goal  of  this  class  library  is  to  share  common  functionality  to  reduce
duplicated  code.  Further  goal  is  to have  no  external  dependencies.  Only
external dependencies are [JUnit][junit] and [Mockito][mockito] for testing.

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

- `application`
- `concurrent`
- `experimental`
- `guava`
- `shell`
- `swing`
- `system`
- `validate`

## File Signatures

To receive the keys type:

    $> gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys 45585598

[sonatype]: http://maven.apache.org/guides/mini/guide-central-repository-upload.html
[maven]:    http://maven.apache.org/
[junit]:    http://www.junit.org/
[mockito]:  http://code.google.com/p/mockito/
