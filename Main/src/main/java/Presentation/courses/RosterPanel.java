package Presentation.courses;

import Presentation.base.App;
import Presentation.base.MainPanel;
import services.login.interfaces.ILoginToken;

public class RosterPanel extends MainPanel
{
    public RosterPanel(App app, ILoginToken token, MainPanel prevPanel)
    {
        super(app, token, prevPanel);
    }
}
