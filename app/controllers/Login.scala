package controllers

import play.api._
import play.api.mvc._
import views._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.openid.OpenID
import play.api.libs.concurrent.Redeemed
import play.api.libs.concurrent.Thrown

object Login extends Controller {

    def login = Action {
        Ok(views.html.login())
    }

    def loginPost = Action { implicit request =>
        println("Posting")
        Form(single(
            "openid" -> nonEmptyText)).bindFromRequest.fold(
            error => {
                Logger.info("bad request " + error.toString)
                BadRequest(error.toString)
            },
            {
                case (openid) => AsyncResult(OpenID.redirectURL(openid, routes.Login.openIDCallback.absoluteURL())
                    .extend(_.value match {
                        case Redeemed(url) => Redirect(url)
                        case Thrown(t) => {
                            t.printStackTrace()
                            Redirect(routes.Login.login)
                        }
                    }))
            })
    }

    def loginGoogle = Action { implicit request => 
        val openidUri = "https://www.google.com/accounts/o8/id"
        AsyncResult(OpenID.redirectURL(openidUri, routes.Login.openIDCallback.absoluteURL())
            .extend(_.value match {
                case Redeemed(url) => Redirect(url)
                case Thrown(t) => {
                    t.printStackTrace()
                    Redirect(routes.Login.login)
                }
            }))
    }

    def openIDCallback = Action { implicit request =>
        AsyncResult(
            OpenID.verifiedId.extend(_.value match {
                case Redeemed(info) => Ok(info.id + "\n" + info.attributes)
                case Thrown(t) => {
                    // Here you should look at the error, and give feedback to the user
                    Redirect(routes.Login.login)
                }
            }))
    }

}