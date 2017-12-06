package services.login.interfaces;

import model.exceptions.NoPermissionException;
import services.login.classes.Permissions;

public interface IPermission
{
    boolean hasPermission(ILoginToken requester);

    default void check(ILoginToken requester)
    {
        if (!hasPermission(requester)) throw new NoPermissionException();
    }

    default IPermission and(IPermission permission)
    {
        return Permissions.and(this, permission);
    }

    default IPermission or(IPermission permission)
    {
        return Permissions.or(this, permission);
    }
}
