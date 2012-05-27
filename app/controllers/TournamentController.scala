package controllers

import play.api._
import play.api.mvc._

import views._
import models._

object TournamentController extends Controller {
  
  def tournament = Action {
    if(Team.listTeams().size == 0){
	Team.generateTeams(User.listUsers())
    }
    Ok(html.tournament.start(Team.listTeams()));
  }
  
}