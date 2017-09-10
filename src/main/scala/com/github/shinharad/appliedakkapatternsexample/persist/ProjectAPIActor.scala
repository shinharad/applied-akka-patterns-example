package com.github.shinharad.appliedakkapatternsexample.persist

import akka.actor.{ActorPath, Props}
import akka.persistence.{AtLeastOnceDelivery, PersistentActor}

// Dealing with External Service Failures
object ProjectAPIActor {

  case class CreateProject(projectId: ProjectId)
  case class ProjectCreatedSent(projectId: ProjectId)

  def props(remotePath: ActorPath): Props =
    Props(new ProjectAPIActor(remotePath))

}

class ProjectAPIActor(remotePath: ActorPath)
  extends PersistentActor
  with AtLeastOnceDelivery {
  import ProjectAPIActor._

  override val persistenceId: String = self.path.name

  private val projectActor = createProjectActor()

  protected def createProjectActor() =
    context.actorSelection(remotePath)

  private def handleSend(msg: ProjectCreatedSent) = {
    // Here, you use the deliver method rather than our usual tell or ask.
    deliver(projectActor) { deliveryId =>
      ProjectActor.CreateProject(deliveryId, msg.projectId)
    }
  }

  private def handleConfirm(confirm: ProjectActor.ProjectCreated): Unit =
    confirmDelivery(confirm.deliveryId)

  override def receiveRecover: Receive = {
    case ProjectCreatedSent(projectId) =>
      handleSend(ProjectCreatedSent(projectId))
    case confirm: ProjectActor.ProjectCreated =>
      handleConfirm(confirm)
  }

  override def receiveCommand: Receive = {
    case CreateProject(projectId) =>
      persist(ProjectCreatedSent(projectId))(handleSend)
    case confirm: ProjectActor.ProjectCreated =>
      persist(confirm)(handleConfirm)
  }
}



