package com.github.shinharad.appliedakkapatternsexample.workpulling

import akka.actor.{Actor, ActorRef, Props}

object ProjectWorker {

  case class ScheduleProject(project: Project)
  case class ProjectScheduled(project: Project)

  def props(projectMaster: ActorRef): Props =
    Props(new ProjectWorker(projectMaster))

}

class ProjectWorker(projectMaster: ActorRef) extends Actor {
  import ProjectWorker._

  projectMaster ! ProjectMaster.RegisterWorker(self)

  override def receive: Receive = {
    case ScheduleProject(project) =>
      scheduleProject(project)
      projectMaster ! ProjectScheduled(project)
  }

  private def scheduleProject(project: Project) = {
    // perform project scheduling tasks
  }

}

