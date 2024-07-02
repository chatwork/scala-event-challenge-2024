package chatwork.mob.no1.domain.groupChat

import wvlet.airframe.ulid.ULID

/** メッセージID。
  *
  * @param value
  *   [[ULID]]
  */
final case class MessageId(private val value: ULID) {
  def asString: String = value.toString
  def toULID: ULID     = value
}

object MessageId {
  def generate(): MessageId = MessageId(ULID.newULID)
}
