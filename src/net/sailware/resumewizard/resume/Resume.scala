package net.sailware.resumewizard.resume

case class Resume(
  val name: String = "",
  val personalDetails: PersonalDetails = new PersonalDetails("", "", ""),
  val contactDetails: ContactDetails = new ContactDetails("", "", ""),
  val socials: List[Social] = List.empty[Social],
  val experiences: List[Experience] = List.empty[Experience]
)
