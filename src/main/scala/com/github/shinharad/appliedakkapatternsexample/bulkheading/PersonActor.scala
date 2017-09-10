package com.github.shinharad.appliedakkapatternsexample.bulkheading

import akka.actor.{Actor, Props}

object PersonActor {
  case class IsQualified(criteria: Set[Qualification])
  case class Qualified(personId: PersonId)
  case class NotQualified(personId: PersonId)

  def props(personId: PersonId): Props =
    Props(new PersonActor(personId))
}

class PersonActor(personId: PersonId) extends Actor {
  import PersonActor._

  private def isQualified(criteria: Set[Qualification]): Boolean = ???

  override def receive: Receive = {
    case IsQualified(criteria) =>
      if (isQualified(criteria)) {
        sender ! Qualified(personId)
      } else {
        sender ! NotQualified(personId)
      }
  }

}
