package services.login.classes;

import services.login.interfaces.ILoginToken;
import services.login.interfaces.IPermission;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnyPermission implements IPermission
{
    private List<IPermission> permissions;

    public AnyPermission(IPermission... permissions)
    {
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public boolean hasPermission(ILoginToken requester)
    {
        return permissions.stream().anyMatch(iPermission -> iPermission.hasPermission(requester));
    }

    @Override
    public String toString()
    {
        return permissions.stream().map(IPermission::toString).collect(Collectors.joining());
    }
}
