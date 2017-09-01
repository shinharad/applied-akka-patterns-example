package com.github.shinharad.appliedakkapatternsexample.workpulling

trait ProjectRepository {
  def nextUnscheduledProject(): Option[Project]
}
