package net.sailware.resumewizard.resume

case class Resume(
  val name: String = "",
  val personalDetails: PersonalDetails = new PersonalDetails("", "", "")
)
