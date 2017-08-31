package com.github.shinharad.appliedakkaexample.pipe

import akka.actor.Actor

object AvailabilityCalendarWrapper {
  case class Find(resourceId: Resource)
  case class ModifiedCalendar(value: String)
}

class AvailabilityCalendarWrapper(
  calendarRepository: AvailabilityCalendarRepository
) extends Actor {
  import AvailabilityCalendarWrapper._
  import scala.concurrent.ExecutionContext.Implicits.global
  import akka.pattern.pipe

  def receive: Receive = {
    case Find(resourceId) =>
      calendarRepository.find(resourceId).pipeTo(self)(sender())
    case AvailabilityCalendar(value) =>
      sender() ! ModifiedCalendar(s"xxx: $value")
  }

}
