package models
import scala.collection.mutable.ListBuffer

case class Match(
      
   team1: Team, 
   team2: Team,
   score: Score
      
  )
  
  case class Score(
  
      goals1: Integer,
      goals2: Integer
  )
  
object League {

  
  
  
  
  val LEAGUE = scala.collection.mutable.LinkedHashMap[Integer,ListBuffer[Match]]()
  
  
  def generateLeague(teamsSeq: Seq[Team]){
    
    if(teamsSeq.size > 0){
      
	    var teams : ListBuffer[Team] = Team.copyTeams()
	    
	    if( (teams.size % 2) == 1){// Add a virtual team FREE
	      teams += new Team(null, null, "FREE", 0)
	    }
	    
	    for(i <- 0 to teams.size -2){

	    	var list : ListBuffer[Match] = ListBuffer()
	    	for(j <- 0 until teams.size/2){
	    		
	    		list += new Match(teams(j),teams(teams.size-1-j),null)
		    }
	    	moveTeams(teams)
	    	LEAGUE(i) = list
	    	
	  	}
    }
    
  }
  
  def getWeek(week: Integer): Seq[Match] = {
      
      if(LEAGUE.isEmpty) Seq()
      else LEAGUE(week).toList
   }
  
  
  def moveTeams(teams: ListBuffer[Team]){
    
    val team = teams(teams.size-2)
    
    for(i <- teams.size -2 to (1,-1)){
      
      teams(i) = teams(i-1);
    }
    
    teams(0) = team

  }
  
  
  def printTeams(teams: ListBuffer[Team]){
    
    teams foreach {  i =>
      println(i.name+" ")
    }
  }
  
  
  
}