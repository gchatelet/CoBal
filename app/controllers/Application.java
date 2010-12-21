package controllers;

import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Controller;

public class Application extends Controller {
    private static final String USER = "user";

    // @Before(unless = { "login", "authenticate" })
    // static void checkAuthenticated() {
    // if (!session.contains(USER)) {
    // login();
    // }
    // }
    public static void authenticate(String user) {
        if (OpenID.isAuthenticationResponse()) {
            UserInfo verifiedUser = OpenID.getVerifiedID();
            if (verifiedUser == null) {
                flash.error("Oops. Authentication has failed");
                login();
            }
            session.put(USER, verifiedUser.id);
            index();
        } else {
            if (!OpenID.id(user).verify()) { // will redirect the user
                flash.error("Cannot verify your OpenID");
                login();
            }
        }
    }

    public static void index() {
        render();
    }

    public static void login() {
        render();
    }
}