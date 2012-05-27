package controllers

import play.api._
import play.api.mvc._

import views._
import models._

object TournamentController extends Controller {
  
  def start = Action {
      
    /* for (i <- 1.to(40)) {
           User.addUser(new User("player-" + i, "player-" + i, "pw", "e", 1%5, null))
      }*/
      if(Team.listTeams().size == 0){
	Team.generateTeams(User.listUsers())
      }
      
      Ok(html.tournament.start(Team.listTeams()));
  }
  
  def view = Action {
    Ok(html.tournament.start(Team.listTeams()));
  }
  
}