# Commons Swing

This module provides helpers to minimize the boiler plate to build a Swing UI.

## SwingFrame Class

This class provides boiler plate code to  have a main window with border layout.
It  is based  on  the [Template  Method][template-method]  pattern. It  provides
three hook  methods to initialize a  menu bar, a  tool buttons bar and  the main
panel.

Example:

    public final class MyFrame extends SwingFrame {
        
        public MyFrame(final String title) {
            super(title);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE); // optional
            setExitOnCloseWindow(true); // optional
        }
        
        @Override
        protected void initMenu() {
            final MenuBarBuilder builder = MenuBarBuilder.builder();
            // Build and set menu here.
            setJMenuBar(builder.create());
        }
        
        @Override
        protected void initToolBar() {
            final ToolBarBuilder builder = ToolBarBuilder.builder();
            // Build and set tool bar here.
            getContentPane().add(builder.create(), BorderLayout.NORTH);
        }
        
        @Override
        protected void initPanel() {
            // Initialize main panel here.
        }
    }

Use the frame that way:

    final MyFrame frame = new MyFrame("My Frame");
    frame.init();

## Builders

This module privide [builders][builder] to create UI components.

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
