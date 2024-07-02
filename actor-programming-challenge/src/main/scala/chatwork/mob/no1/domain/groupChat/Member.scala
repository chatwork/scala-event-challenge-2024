package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

/** グループチャットに参加するメンバー値オブジェクト。
  *
  * @param id
  *   メンバーID
  * @param userAccountId
  *   ユーザアカウントID
  * @param role
  *   [[MemberRole]] オブジェクト
  */
final case class Member(id: MemberId, userAccountId: UserAccountId, role: MemberRole)

object Member {

  given Ordering[Member] with {
    override def compare(x: Member, y: Member): Int = x.id.toULID.compareTo(y.id.toULID)
  }

}
