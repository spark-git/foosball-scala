package controllers

import play.api._
import play.api.mvc._

import views._
import models._

object TournamentController extends Controller {
  
  def start = Action {
      
      if(User.listUsers.size == 0){
	     for (i <- 1.to(10)) {
		   User.addUser(new User("player-" + i, "player-" + i, "pw", "e", 1%5, null))
	      }
      }
      if(Team.listTeams().size == 0){
	Team.generateTeams(User.listUsers())
      }
      League.generateLeague(Team.listTeams());
      
      Ok(html.tournament.start(Team.listTeams(),League.getWeek(0)));
  }
  
  def view = Action {
    Ok(html.tournament.start(Team.listTeams(),League.getWeek(0)));
  }
  
  def matches = Action {
    Ok(html.tournament.matches(League.LEAGUE));
  }
  
}