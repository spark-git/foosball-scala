package models

case class User(
  username: String, 
  password: String,
  email: String,
  handicap: Integer,
  profile: UserProfile
)

case class UserProfile(
  country: String,
  age: Option[Int]
)