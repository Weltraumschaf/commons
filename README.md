# Common Utility Classes for Java

In this library I collect classes I used in several projects
to minimize code duplication oevr the projects.

## Word of the Author

This is my first artefact I try to deploy to Maven Central Repository
along with this [guide][1].

## Goal

Goal of this class library is to share common functionality to reduce duplicated
code. Further goal is to have no external dependencies. Only external dependencies
are [JUnit][3] and [Mockito][4] for testing.

## Usage

Add this <code>&lt;dependency&gt;</code> to your [Maven][2] pom.xml:

    <dependencies>

        <!-- ... -->

        <dependency>
            <!-- http://weltraumschaf.github.com/commons/ -->
            <groupId>de.weltraumschaf</groupId>
            <artifactId>commons</artifactId>
            <version>0.1.4</version>
        </dependency>

        <!-- ... -->

    <dependencies>

## Versions

### Version 0.1.0

- <code>CapturingOutputStream</code>: Can be used to capture output print
  to <code>System.out</code> or <code>System.err</code>.
- <code>IOStreams</code>: Immutable object to aggregate I/O streams.

### Version 0.1.1

- Fix POM and add signatures.

### Version 0.1.2

- Add more documentation.
- Add <code>toString()</code>, <code>hashCode()</code>, <code>equals()</code>
  to <code>CapturingOutputStream()</code>.
- Add example code and documentation to <code>IOStreams</code>.

### Version 0.1.3

- Messing around with releasing to Sonatype Nexus in combination with GitHub.

### Version 0.1.4

- First successful promoted release.

### Version 0.2.0
- Version

### File Signatures

To receive the keys type:

    $ gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys BA265082

[1]: http://maven.apache.org/guides/mini/guide-central-repository-upload.html
[2]: http://maven.apache.org/
[3]: http://www.junit.org/
[4]: http://code.google.com/p/mockito/
