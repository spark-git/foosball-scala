package models
import models._
import scala.collection.mutable.ListBuffer
import scala.collection.immutable.List

import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.Format

case class Team(
  
  player1: User,
  player2: User,
  name: String
)

object Team {

    implicit object TeamFormat extends Format[Team] {
    
        def reads(json: JsValue): Team = null
    	def writes(team: Team): JsValue = JsObject(List("blah" -> JsString(team.name)))
    }
  
    val TEAMS = scala.collection.mutable.Map[String, Team]()
  
    def listTeams(): Seq[Team] = {
	println(TEAMS.values.toList)
        TEAMS.values.toList;
    }
    
    def addTeam(team:Team) = {
        TEAMS(team.name) = team
    }
    
    def generateTeams(players: Seq[User]) {
    
      println(" players.size:  "+players.size)
      for(i <-0 until players.size/2){
	println("i  "+i)
        Team.addTeam(new Team(players(i*2), players((i*2)+1),"team-"+i))
        
      }
    }
    
}