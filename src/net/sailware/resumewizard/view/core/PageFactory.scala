package net.sailware.resumewizard.view.core

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.dashboard.DashboardController
import net.sailware.resumewizard.view.resume.create.CreateResumeController
import net.sailware.resumewizard.view.resume.create.service.CreateResumeService
import net.sailware.resumewizard.view.resume.wizard.certification.CertificationsController
import net.sailware.resumewizard.view.resume.wizard.contact.ContactDetailsController
import net.sailware.resumewizard.view.resume.wizard.experience.ExperiencesController
import net.sailware.resumewizard.view.resume.wizard.personal.PersonalDetailsController
import net.sailware.resumewizard.view.resume.wizard.social.SocialsController
import scalafx.scene.Node

class PageFactory(val resumeService: ResumeService):
  def createPage(pageType: PageType): List[Node] =
    List(createView(pageType))

  private def createView(pageType: PageType): Node =
    pageType match
      case PageType.Dashboard => new DashboardController().view()
      case PageType.CreateResume => new CreateResumeController(resumeService).view()
      case PageType.PersonalDetails => new PersonalDetailsController(resumeService).view()
      case PageType.ContactDetails => new ContactDetailsController(resumeService).view()
      case PageType.Socials => new SocialsController(resumeService).view()
      case PageType.Experiences => new ExperiencesController(resumeService).view()
      case PageType.Certifications => new CertificationsController(resumeService).view()
