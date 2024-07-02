package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

/** メンバー集合のファーストクラスコレクション。
  *
  * @param values
  *   メンバー集合
  */
final case class Members(private val values: Vector[Member]) {
  def add(member: Member): Members = Members(values :+ member)
  def removeByUserAccountId(userAccountId: UserAccountId): Members = Members(
    values.filterNot(_.userAccountId == userAccountId)
  )
  def containsByUserAccountId(userAccountId: UserAccountId): Boolean = values.exists(_.userAccountId == userAccountId)
  def size: Int                                                      = values.size
  def isEmpty: Boolean                                               = values.isEmpty
  def nonEmpty: Boolean                                              = values.nonEmpty
  def toVector: Vector[Member]                                       = values
  def isAdministrator(userAccountId: UserAccountId): Boolean =
    values.exists(e => e.userAccountId == userAccountId && e.role == MemberRole.Admin)
  def containsAdministrator: Boolean = values.exists(_.role == MemberRole.Admin)
}

object Members {
  def empty: Members = Members(Vector.empty)
  def singleMember(userAccountId: UserAccountId): Members = Members(
    Vector(Member(MemberId.generate(), userAccountId, MemberRole.Member))
  )
  def singleAdmin(userAccountId: UserAccountId): Members = Members(
    Vector(Member(MemberId.generate(), userAccountId, MemberRole.Admin))
  )
  def apply(members: Member*): Members = Members(members.toVector)
}
