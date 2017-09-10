package com.github.shinharad.appliedakkapatternsexample.bulkheading

import akka.actor.{Actor, ActorRef}

// This example presents a set of PersonActors being managed by a PeopleActor.
// Each PersonActor acts as its own failure zone.

// The problem with bulkheading using just an actor hierarchy is that it is still limited to
// a single machine, and a single Java Virtual Machine (JVM)
object PeopleActor {
  case class CreatePerson(id: PersonId)
  case class FindQualifiedPeople(criteria: Set[Qualification])
}

class PeopleActor extends Actor {
  import PeopleActor._

  private var people = Map.empty[PersonId, ActorRef]

  protected def createPerson(personId: PersonId) =
    context.actorOf(PersonActor.props(personId))

  override def receive: Receive = {
    case CreatePerson(id) =>
     people = people + (id -> createPerson(id))
    case FindQualifiedPeople(criteria) =>
      people.values.foreach { p =>
        p.forward(PersonActor.IsQualified(criteria))
      }
  }

}
