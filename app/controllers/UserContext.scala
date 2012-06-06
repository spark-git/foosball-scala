package controllers
import play.api.mvc.Session
import models.User
import play.api.mvc.Security

/*
 * Gets User details from the session
 * @see http://julien.richard-foy.fr/blog/2012/02/26/composite-user-interface-without-boilerplate-using-play-2/
 */
trait UserContext {
	
    implicit def user(implicit session: Session): Option[User] = {
    		for (id <- session.get(Security.username)) yield new User(id)		
    }
}