package com.github.shinharad.appliedakkapatternsexample.workpulling

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.github.shinharad.appliedakkapatternsexample.workpulling.ProjectWorker.ScheduleProject

import scala.concurrent.duration.FiniteDuration

object WorkPullingExample extends App {

  implicit val system = ActorSystem()

  val repo = new ProjectRepository() {
    override def nextUnscheduledProject(): Option[Project] = ???
  }

  val master = system.actorOf(ProjectMaster.props(repo, FiniteDuration(1000, TimeUnit.MILLISECONDS)))
  val worker = system.actorOf(ProjectWorker.props(master))

  worker ! ScheduleProject(Project(ProjectId(123l), "hoge"))

  system.terminate()

}
