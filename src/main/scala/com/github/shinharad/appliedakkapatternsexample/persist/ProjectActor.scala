package com.github.shinharad.appliedakkapatternsexample.persist

import akka.actor.Actor

// remote actor
object ProjectActor {
  case class CreateProject(deliveryId: Long, projectId: ProjectId)
  case class ProjectCreated(deliveryId: Long)
}

class ProjectActor extends Actor {
  import ProjectActor._

  def receive = {
    case CreateProject(deliveryId, projectId) =>
      sender ! ProjectCreated(deliveryId)
  }
}

