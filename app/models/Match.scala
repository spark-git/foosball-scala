package models

case class Match(
      
   team1: Team, 
   team2: Team,
   var score: Score
      
  )
  
case class Score(
  
     var goals1: Integer,
     var goals2: Integer
)