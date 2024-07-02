package chatwork.mob.no1.domain.groupChat

import wvlet.airframe.ulid.ULID

object GroupChatId {
  final val Prefix: String    = "GroupChat"
  def generate(): GroupChatId = GroupChatId(ULID.newULID)
}

/** グループチャットID。
  *
  * @param value
  *   [[ULID]]
  */
final case class GroupChatId(private val value: ULID) {
  def asString: String = s"${GroupChatId.Prefix}-${value.toString}"
  def toULID: ULID     = value
}
