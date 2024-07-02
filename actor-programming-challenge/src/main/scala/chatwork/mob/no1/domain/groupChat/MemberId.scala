package chatwork.mob.no1.domain.groupChat

import wvlet.airframe.ulid.ULID

/** メンバーID値オブジェクト。
  *
  * @param value
  *   [[ULID]]
  */
final case class MemberId(private val value: ULID) {
  def asString: String = value.toString
  def toULID: ULID     = value
}

object MemberId {
  def generate(): MemberId = MemberId(ULID.newULID)
}
