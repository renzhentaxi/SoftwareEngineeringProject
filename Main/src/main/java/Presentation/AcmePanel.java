package Presentation;

import javax.swing.*;
import java.awt.*;

public abstract class AcmePanel extends JPanel
{
    protected App app;

    public AcmePanel(App app, LayoutManager layout)
    {
        super(layout);
        this.app = app;
        setPreferredSize(new Dimension(400,500));
    }

    public AcmePanel(App app)
    {
        super();
        this.app = app;
    }

    public abstract void assemble();

    public abstract void reset();
}
