# Commons Concurrent

This module provides concurrent implementations of common data structures.

Provided data structures are:

- [Javadoc of Stack][Stack]
- [Javadoc of Queue][Queue]

Implementations may be obtained by the [factory][Concurrent]:

    final Stack stack = Concurrent.newStack();
    final Queue queue = Concurrent.newQueue();

[Stack]:        apidocs/de/weltraumschaf/commons/concurrent/Stack.html
[Queue]:        apidocs/de/weltraumschaf/commons/concurrent/Queue.html
[Concurrent]:   apidocs/de/weltraumschaf/commons/concurrent/Concurrent.html
