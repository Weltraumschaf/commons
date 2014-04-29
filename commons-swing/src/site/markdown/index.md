# Commons Swing

This module provides helpers to minimize the boiler plate to build a Swing UI.

## SwingFrame Class

TBD

## Builders

This module privide builders to create UI components.

### MenuBarBuilder Class

The `MenuBarBuilder` provides a internal [DSL][dsl] to create a menu bar.

Example:

    final JMenuBar menubar = MenuBarBuilder.builder()
        .menu("File")
            .item("Open")
                .addListener(new Listener())
            .end()
            .separator()
            .item("Save")
                .addListener(new Listener())
            .end()
        .end()
        .menu("Edit")
            .item("foo")
            .end()
        .end()
        .menu("View")
            .item("bar")
            .end()
        .end()
        .menu("Window")
            .item("baz")
            .end()
        .end()
        .create();

### ToolBarBuilder Class

The `ToolBarBuilder` provides a internal [DSL][dsl] to create a button tool bar.

Example:

    final JToolBar toolbar = ToolBarBuilder.builder()
        .button("/de/weltraumschaf/swing/folder_16x16.gif")
            .toolTipText("Open an existing document.")
            .addListener(new Listener())
        .end()
        .button("/de/weltraumschaf/swing/disk_16x16.gif")
            .toolTipText("Save current document.")
            .addListener(new Listener())
        .end()
        .button("/de/weltraumschaf/swing/page_16x16.gif")
            .toolTipText("Create a new document.")
            .addListener(new Listener())
        .end()
        .create();
 
[template-method]:  https://en.wikipedia.org/wiki/Template_method_pattern
[builder]:          https://en.wikipedia.org/wiki/Template_method_pattern
[dsl]:              https://en.wikipedia.org/wiki/Domain-specific_language
