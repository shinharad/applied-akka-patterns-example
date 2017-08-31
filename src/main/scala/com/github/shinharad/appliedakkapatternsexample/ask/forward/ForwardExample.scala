package com.github.shinharad.appliedakkapatternsexample.ask.forward

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success}

case class ProcessData(data: String)
case class DataProcessed(data: String)

class Stage1() extends Actor {

  private val nextStage = context.actorOf(Props(new Stage2()))

  def processData(p: String): String = p

  def receive: Receive = {
    case ProcessData(data) =>
      val processedData = processData(data)
      nextStage.forward(ProcessData(processedData))
  }
}

class Stage2() extends Actor {

  private val nextStage = context.actorOf(Props(new Stage3()))

  def processData(p: String): String = p

  def receive: Receive = {
    case ProcessData(data) =>
      val processedData = processData(data)
      nextStage.forward(ProcessData(processedData))
  }
}


class Stage3() extends Actor {

  def processData(p: String): String = p

  def receive: Receive = {
    case ProcessData(data) =>
      val processedData = processData(data)
      sender ! DataProcessed(processedData)
  }

}

object ForwardExample extends App {

  implicit val system = ActorSystem("forward-example")

  val stage1 = system.actorOf(Props(new Stage1()))

  implicit val timeout = Timeout(5 seconds)
  val result = stage1 ? ProcessData("hoge")

  result.onComplete {
    case Success(v) => println(s"success: $v")
    case Failure(e) => println(s"error: $e")
  }

  Await.ready(result, Duration.Inf)
  system.terminate()

}
