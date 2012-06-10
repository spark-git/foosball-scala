package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import models._
import play.api.libs.json.Json

object PlayerController extends Controller {
  
  /**
   * Sign Up Form definition.
   *
   * Once defined it handle automatically, ,
   * validation, submission, errors, redisplaying, ...
   */
   
  val playerForm: Form[User] = Form(
  
    // Define a mapping that will handle User values
    mapping(
      "username" -> text(minLength = 4),
      "email" -> email,
      
      // Create a tuple mapping for the password/confirm
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
        // Add an additional constraint: both passwords must match
        "Passwords don't match", passwords => passwords._1 == passwords._2
      ),
      
      "handicap" -> number(min = 1, max = 5),
      
      // Create a mapping that will handle UserProfile values
      "profile" -> mapping(
        "country" -> nonEmptyText,
        "age" -> optional(number(min = 18, max = 100))
      )
      // The mapping signature matches the UserProfile case class signature,
      // so we can use default apply/unapply functions here
      (UserProfile.apply)(UserProfile.unapply),
      
      "accept" -> checked("You must accept the conditions")
      
    )
    // The mapping signature doesn't match the User case class signature,
    // so we have to define custom binding/unbinding functions
    {
      // Binding: Create a User from the mapping result (ignore the second password and the accept field)
      (username, email, passwords, handicap, profile, _) => User(username, username, passwords._1, email, handicap, profile) 
    } 
    {
      // Unbinding: Create the mapping values from an existing User value
      user => Some(user.name, user.email, (user.password, ""), user.handicap, user.profile, false)
    }.verifying(
      // Add an additional constraint: The username must not be taken (you could do an SQL request here)
      "This username is not available",
      user => !Seq("admin", "guest").contains(user.name)
    )
  )
  
  /**
   * Display an empty form.
   */
  def form = Action {
    Ok(html.addplayer.form(playerForm));
  }
  
  /**
   * Display a form pre-filled with an existing User.
   */
  def editForm = Action {
    val existingUser = User("id",
      "Insert user", "secret", "Insert email", 5,
      UserProfile("France", Some(30))
    )
    Ok(html.addplayer.form(playerForm.fill(existingUser)))
  }
  
  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    playerForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(html.addplayer.form(errors)),
      
      // We got a valid User value, display the summary
      user => {
        User.addUser(user)
        Ok(html.addplayer.summary(user))  
      }
    )
  }
  
  /**
   * List all the players 
   */
  def list = Action {
      Ok(html.addplayer.players(User.listUsers())); 
  }
  
  /**
   * Get information for the given player
   */
  def get(id:String) = Action {
      Ok(html.addplayer.profile.apply(null))
  }
  
  /**
   * Create a new player
   */
  def create() = Action {
      Ok("Creating a new player");
  }
  
  def update(id:String) = Action {
     Ok("Updating the player with the id: " + id); 
  }
  
  def createTestPlayers() = Action {
      for (i <- 1.to(10)) {
           User.addUser(new User("player-" + i, "player-" + i, "pw", "e", 1%5, null))
      }
      Ok("Added Users")
  }
  
}