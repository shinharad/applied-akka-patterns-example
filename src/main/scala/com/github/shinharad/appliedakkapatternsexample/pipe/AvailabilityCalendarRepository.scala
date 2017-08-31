package com.github.shinharad.appliedakkapatternsexample.pipe

import scala.concurrent.Future

trait AvailabilityCalendarRepository {
  def find(resourceId: Resource): Future[AvailabilityCalendar]
}
