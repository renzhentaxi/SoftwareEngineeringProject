package services.login.permissions;

import services.login.interfaces.ILoginToken;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AllPermission implements IPermission
{
    private List<IPermission> permissions;

    public AllPermission(IPermission... permissions)
    {
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public boolean hasPermission(ILoginToken requester)
    {
        return permissions.stream().allMatch(iPermission -> iPermission.hasPermission(requester));
    }

    @Override
    public String toString()
    {
        return permissions.stream().map(IPermission::toString).collect(Collectors.joining());
    }
}
