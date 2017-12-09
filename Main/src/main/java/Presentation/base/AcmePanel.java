package Presentation.base;

import javax.swing.*;
import java.awt.*;

public abstract class AcmePanel extends JPanel
{
    protected App app;

    protected AcmePanel(App app, LayoutManager layout)
    {
        super(layout);
        this.app = app;
        setPreferredSize(new Dimension(400, 500));
    }

    AcmePanel(App app)
    {
        super();
        this.app = app;
    }

    /**
     * used to create the different components and display them
     */
    protected abstract void assemble();

    /*
    resets the panel by calling assemble again
     */
    public void reload()
    {
        removeAll();
        assemble();
    }
}
