# Commons Concurrent

This module provides concurrent implementations of common data structures.

Provided data structures are:

- [Javadov of Stack][Stack]
- [Javadov of Queue][Queue]

Implementations may be obtained by the [factory][Concurrent]:

    final Stack stack = Concurrent.newStack();
    final Queue queue = Concurrent.newQueue();

[Stack]:        concurrent/apidocs/de/weltraumschaf/commons/application/Stack.html
[Queue]:        concurrent/apidocs/de/weltraumschaf/commons/application/Queue.html
[Concurrent]:   concurrent/apidocs/de/weltraumschaf/commons/application/Concurrent.html
