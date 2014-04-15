# Commons Guava

This is a massively stripped down version of [Google's Guava][guava] library.

## Why do that?

1. The original library from google is quite large. Some mega bytes.
2. If I include Guava in version x.x.x and you include my library but need 
   Guava in version y.y.y you ae screwed.
3. I only use a very small subset of Guava (`Lists`, `Maps`, `Sets`, `Objects`).

[guava]: http://code.google.com/p/guava-libraries/
