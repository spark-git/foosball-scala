package models
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.Format

case class User(
  id: String,
  name: String, 
  password: String,
  email: String,
  handicap: Integer,
  profile: UserProfile
)

case class UserProfile(
  country: String,
  age: Option[Int]
)


object User {
    
    /**
     * This implicit object allow the serialization of the user 
     * to JSON
     */
    implicit object UserFormat extends Format[User] {
    	def reads(json: JsValue): User = null
    	def writes(user: User): JsValue = JsObject(List("blah" -> JsString(user.name)))
    }
    
    val USERS = scala.collection.mutable.Map[String, User]()
    
    def listUsers(): Seq[User] = {
	println(USERS.values.toList)
        USERS.values.toList;
    }
    
    def addUser(user:User) = {
	println(user)
        USERS(user.id) = user
    }
}