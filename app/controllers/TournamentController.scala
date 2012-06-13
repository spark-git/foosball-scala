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

  val WEEK_PARAM_NAME = "week"
  val SCORE1_PARAM_NAME = "score1_"
  val SCORE2_PARAM_NAME = "score2_"
  val MATCH_PARAM_NAME = "match_"
  
  /**
   * Start.
   *
   * @return the play.api.mvc. action
   */
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

  /**
   * View.
   *
   * @return the play.api.mvc. action
   */
  def view = Action {

    Ok(html.tournament.start(Team.listTeams(), 0, League.LEAGUE));
  }

  /**
   * View week.
   *
   * @param id the id
   * @return the play.api.mvc. action
   */
  def viewWeek(id: String) = Action {
    Ok(html.tournament.start(Team.listTeams(), id.toInt, League.LEAGUE));
  }

  /**
   * Matches.
   *
   * @return the play.api.mvc. action
   */
  def matches = Action {
    Ok(html.tournament.matches(League.LEAGUE));
  }


  /**
   * Save. REFACTOR!!
   *
   * @return the play.api.mvc. action
   */
  def save() = Action { request =>
    
  	val week = request.body.asFormUrlEncoded.get(WEEK_PARAM_NAME)(0).toInt;
    try{
	    var j: Int = 0
	    val listScore1: Seq[String] = request.body.asFormUrlEncoded.get(SCORE1_PARAM_NAME + week)
	    val listScore2: Seq[String] = request.body.asFormUrlEncoded.get(SCORE2_PARAM_NAME + week)
	    val matchId:  Seq[String] = request.body.asFormUrlEncoded.get(MATCH_PARAM_NAME + week)
	    
	    if(listScore1 != null)
		    matchId.foreach{m=>
		      if (listScore1(j) != null && listScore2(j) != null && !listScore1(j).equals("") && !listScore1(j).equals("")) {
		        League.saveMatch(week, m.toInt, listScore1(j).toInt, listScore2(j).toInt)
		      }
		      j+=1
		}
	    Ok(html.tournament.start(Team.listTeams(), week, League.LEAGUE));
    }
    catch{
      case e: Exception =>{
         println(e)
        Ok(html.tournament.start(Team.listTeams(), week, League.LEAGUE))
       }
    }
  }

}