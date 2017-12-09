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

    protected abstract void assemble();

    public void reload()
    {
        removeAll();
        assemble();
    }
}
