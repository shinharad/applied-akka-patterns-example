package com.github.shinharad.appliedakkapatternsexample.workpulling

import akka.actor.{Actor, ActorRef, Props}

import scala.concurrent.duration.FiniteDuration

object ProjectMaster {

  case class ProjectAdded(projectId: ProjectId)
  case class RegisterWorker(worker: ActorRef)
  private case class CheckForWork(worker: ActorRef)

  def props(
    projectRepository: ProjectRepository,
    pollingInterval: FiniteDuration
  ): Props =
    Props(new ProjectMaster(projectRepository, pollingInterval))

}

class ProjectMaster(
  projectRepository: ProjectRepository,
  pollingInterval: FiniteDuration
) extends Actor {
  import ProjectMaster._
  import context.dispatcher

  override def receive: Receive = {
    case RegisterWorker(worker) => scheduleNextProject(worker)
    case CheckForWork(worker) => scheduleNextProject(worker)
    case ProjectWorker.ProjectScheduled(_) =>
      scheduleNextProject(sender())
  }

  private def scheduleNextProject(worker: ActorRef) = {
    projectRepository.nextUnscheduledProject() match {
      case Some(project) =>
        worker ! ProjectWorker.ScheduleProject(project)
      case None =>
        context.system.scheduler.scheduleOnce(pollingInterval, self, CheckForWork(worker))
        self ! CheckForWork(worker)
    }
  }

}



