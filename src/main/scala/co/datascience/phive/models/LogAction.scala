package 

// Scalaz
import scalaz._
import Scalaz._

case class xxx(
  name: String,         // Name of this merchant's company
  active: Boolean       // Is this merchant currently active?
  ) extends Model { // TODO: add active: Boolean to merchant (so we can switch 'em off)

  // def this() = this(∅[String], ∅[Boolean]) // TODO: remove when 0.9.5 comes out
}
