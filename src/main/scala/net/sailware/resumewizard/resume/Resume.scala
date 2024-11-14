package net.sailware.resumewizard.resume

case class Resume(
    val name: String = "",
    val personalDetails: PersonalDetails = new PersonalDetails("", "", ""),
    val contactDetails: ContactDetails = new ContactDetails("", "", ""),
    val socials: List[Social] = List.empty[Social],
    val experiences: List[Experience] = List.empty[Experience],
    val certifications: List[Certification] = List.empty[Certification]
):

  def hasPersonalDetails(): Boolean =
    personalDetails.name.nonEmpty
      || personalDetails.title.nonEmpty
      || personalDetails.summary.nonEmpty

  def hasContactDetails(): Boolean =
    contactDetails.phone.nonEmpty
      || contactDetails.email.nonEmpty
      || contactDetails.location.nonEmpty

  def hasSocials(): Boolean =
    socials.nonEmpty

  def hasExperiences(): Boolean =
    experiences.nonEmpty

  def hasCertifications(): Boolean =
    certifications.nonEmpty
