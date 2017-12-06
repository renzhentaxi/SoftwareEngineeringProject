package services.login.exceptions;


import services.login.interfaces.ILoginToken;
import services.login.permissions.IPermission;

public class NoPermissionException extends RuntimeException {

    public NoPermissionException()
    {}

    public NoPermissionException(IPermission permission, ILoginToken requester)
    {
        super("requires " + permission.toString() + "\nRequester is " + requester +"\n");
    }

}
