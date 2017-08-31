package com.github.shinharad.appliedakkaexample.become

import akka.actor.Actor

object Person {

  // value object
  case class FirstName(value: String) extends AnyVal
  case class LastName(value: String) extends AnyVal
  case class Address(value: String) extends AnyVal
  case class PhoneNumber(value: String) extends AnyVal
  case class Role(value: String) extends AnyVal

  case class PersonalInformation(
    firstName: FirstName,
    lastName: LastName,
    address: Address,
    phoneNumber: PhoneNumber,
    roles: Set[Role] = Set.empty
  )

  case class Create(personalInformation: PersonalInformation)

}

class Person extends Actor {
  import Person._

  def receive: Receive = {
    case Create(personalInformation) =>
      context.become(created(personalInformation))
  }

  private def created(personalInformation: PersonalInformation): Receive = ???

}
