package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import models._
import scala.collection.mutable.HashMap
import scala.collection.immutable.List



object TournamentController extends Controller {


  
  
  def start = Action {

    if (User.listUsers.size == 0) {
      for (i <- 1.to(10)) {
        User.addUser(new User("player-" + i, "player-" + i, "pw", "e", 1 % 5, null))
      }
    }
    if (Team.listTeams().size == 0) {
      Team.generateTeams(User.listUsers())
    }
    League.generateLeague(Team.listTeams());

    Ok(html.tournament.start(Team.listTeams(), 0, League.LEAGUE));
  }

  def view = Action {

    Ok(html.tournament.start(Team.listTeams(), 0, League.LEAGUE));
  }

  def viewWeek(id: String) = Action {
    Ok(html.tournament.start(Team.listTeams(), id.toInt, League.LEAGUE));
  }

  def matches = Action {
    Ok(html.tournament.matches(League.LEAGUE));
  }


    
   def sayHello(myName:String) = {
    
   }
  
  def save() = Action { request =>

   val week : Integer = request.body.asFormUrlEncoded.get("week")(0).toInt;
   val listScore1 : Seq[String] = request.body.asFormUrlEncoded.get("score1_"+week)
   val listScore2 : Seq[String] = request.body.asFormUrlEncoded.get("score2_"+week)

   for(i<- 0 until listScore1.size){
	   
     if(listScore1(i) != null && listScore2(i) != null && !listScore1(i).equals("") && !listScore1(i).equals("") ){
    	 League.saveMatch(week, i, listScore1(i).toInt, listScore2(i).toInt)
     }
   }
   Ok(html.tournament.start(Team.listTeams(), week, League.LEAGUE));
  }


}