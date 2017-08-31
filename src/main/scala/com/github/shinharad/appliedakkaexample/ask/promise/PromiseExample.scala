package com.github.shinharad.appliedakkaexample.ask.promise

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{Await, Promise}
import scala.language.postfixOps
import scala.util.{Failure, Success}

case class ProcessData(data: String, response: Promise[String])

class Stage1() extends Actor {

  private val nextStage = context.actorOf(Props(new Stage2()))

  def processData(p: String): String = p

  def receive: Receive = {
    case ProcessData(data, response) =>
      implicit val timeout = Timeout(5 seconds)
      val processedData = processData(data)
      nextStage ! ProcessData(processedData, response)
  }

}

class Stage2() extends Actor {

  private val nextStage = context.actorOf(Props(new Stage3()))

  def processData(p: String): String = p

  def receive: Receive = {
    case ProcessData(data, response) =>
      implicit val timeout = Timeout(5 seconds)
      val processedData = processData(data)
      nextStage ! ProcessData(processedData, response)
  }

}

class Stage3() extends Actor {

  def processData(p: String): String = s"test"

  def receive: Receive = {
    case ProcessData(data, response) =>
      val processedData = processData(data)
      response.complete(Success(processedData))
  }

}

object PromiseExample extends App {

  implicit val system = ActorSystem("promise-example")
  import scala.concurrent.ExecutionContext.Implicits.global

  val stage1 = system.actorOf(Props(new Stage1()))

  implicit val time = Timeout(5 seconds)
  val promise = Promise[String]
  stage1 ! ProcessData("hoge", promise)

  val result = promise.future

  result.onComplete {
    case Success(v) => println(s"success: $v")
    case Failure(e) => println(s"error: $e")
  }

  Await.ready(result, Duration.Inf)
  system.terminate()
}