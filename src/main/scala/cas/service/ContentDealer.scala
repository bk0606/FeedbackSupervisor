package cas.service

import cas.analysis.subject.Subject
import cas.utils.Utils.ErrorMsg
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

abstract class ContentDealer {
  def estimatedQueryFrequency: FiniteDuration
  def pullSubjectsChunk: Future[Either[String, List[Subject]]]
  def pushEstimation(estim: Estimation): Future[Either[String, Any]]
}