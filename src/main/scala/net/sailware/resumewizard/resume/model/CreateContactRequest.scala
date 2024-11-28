package net.sailware.resumewizard.resume.model

case class CreateContactRequest(
  val phone: String,
  val email: String,
  val location: String
)

object CreateContactRequest:

  def apply(phone: String, email: String, location: String): CreateContactRequest =
    new CreateContactRequest(phone, email, location)
