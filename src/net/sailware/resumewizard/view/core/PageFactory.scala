package net.sailware.resumewizard.view.core


import net.sailware.resumewizard.view.dashboard.DashboardController
import net.sailware.resumewizard.view.resume.create.CreateResumeController
import net.sailware.resumewizard.view.resume.create.service.CreateResumeService
import net.sailware.resumewizard.view.resume.create.service.CreateResumeServiceImpl
import net.sailware.resumewizard.view.resume.wizard.certification.CertificationsController
import net.sailware.resumewizard.view.resume.wizard.contact.ContactDetailsController
import net.sailware.resumewizard.view.resume.wizard.experience.ExperiencesController
import net.sailware.resumewizard.view.resume.wizard.personal.PersonalDetailsController
import net.sailware.resumewizard.view.resume.wizard.social.SocialsController
import scalafx.scene.Node

class PageFactory(val createResumeService: CreateResumeService):
  def createPage(pageType: PageType): List[Node] =
    List(createView(pageType))

  private def createView(pageType: PageType): Node =
    pageType match
      case PageType.Dashboard => new DashboardController().view()
      case PageType.CreateResume => new CreateResumeController(createResumeService).view()
      case PageType.PersonalDetails => new PersonalDetailsController().view()
      case PageType.ContactDetails => new ContactDetailsController().view()
      case PageType.Socials => new SocialsController().view()
      case PageType.Experiences => new ExperiencesController().view()
      case PageType.Certifications => new CertificationsController().view()
