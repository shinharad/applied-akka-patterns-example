package com.github.shinharad.appliedakkapatternsexample.workpulling

case class Project(id: ProjectId, name: String)

case class ProjectId(id: Long) extends AnyVal

