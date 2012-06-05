package models
import scala.collection.mutable.ListBuffer


  
object League {


  var LEAGUE = scala.collection.mutable.LinkedHashMap[Integer,ListBuffer[Match]]()
  
  
  def generateLeague(teamsSeq: Seq[Team]){
    
    if(teamsSeq.size > 0){
      
	    var teams : ListBuffer[Team] = Team.copyTeams()
	    
	    if( (teams.size % 2) == 1){// Add a virtual team FREE
	      teams += new Team(null, null, "FREE", 0)
	    }
	    
	    for(i <- 0 to teams.size -2){

	    	var list : ListBuffer[Match] = ListBuffer()
	    	for(j <- 0 until teams.size/2){
	    		
	    		list += new Match(teams(j),teams(teams.size-1-j),new Score(null, null))
		    }
	    	moveTeams(teams)
	    	LEAGUE(i) = list
	    	
	  	}
    }
    
  }
  
  def saveMatch(week: Integer, matchId: Integer, goals1: Integer, goals2: Integer)  {
    
    
    
    LEAGUE(week)(matchId).score = new Score(goals1, goals2);
    
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