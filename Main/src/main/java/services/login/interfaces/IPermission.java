package services.login.interfaces;

import services.login.classes.Permissions;
import services.login.exceptions.NoPermissionException;

public interface IPermission
{
    boolean hasPermission(ILoginToken requester);

    default void check(ILoginToken requester)
    {
        if (!hasPermission(requester)) throw new NoPermissionException(this, requester);
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
