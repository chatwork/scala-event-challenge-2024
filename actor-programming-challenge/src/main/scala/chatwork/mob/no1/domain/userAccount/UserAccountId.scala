package chatwork.mob.no1.domain.userAccount

import wvlet.airframe.ulid.ULID

object UserAccountId {
  final val Prefix: String      = "UserAccount"
  def generate(): UserAccountId = UserAccountId(ULID.newULID)
}

/** ユーザーアカウントID
  *
  * @param value
  *   [[ULID]]
  */
final case class UserAccountId(private val value: ULID) {
  def asString: String = s"${UserAccountId.Prefix}-${value.toString}"
  def toULID: ULID     = value
}
