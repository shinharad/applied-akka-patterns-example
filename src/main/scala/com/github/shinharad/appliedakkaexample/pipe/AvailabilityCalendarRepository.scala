package com.github.shinharad.appliedakkaexample.pipe

import scala.concurrent.Future

trait AvailabilityCalendarRepository {
  def find(resourceId: Resource): Future[AvailabilityCalendar]
}
