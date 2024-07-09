package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

/** メンバー集合のファーストクラスコレクション。
  *
  * @param values
  *   メンバー集合
  */
final case class Members(private val values: Vector[Member]) {

  /** メンバーを追加する。
    *
    * @param member
    *   [[Member]]
    * @return
    *   [[Members]]
    */
  def add(member: Member): Members = Members(values :+ member)

  /** メンバーを削除する。
    *
    * @param userAccountId
    *   [[UserAccountId]]
    * @return
    *   [[Members]]
    */
  def removeByUserAccountId(userAccountId: UserAccountId): Members = Members(
    values.filterNot(_.userAccountId == userAccountId)
  )

  /** ユーザアカウントIDを指定してメンバーが含まれているかどうかを判定する。
    *
    * @param userAccountId
    *   [[UserAccountId]]
    * @return
    *   メンバーが含まれている場合はtrue
    */
  def containsByUserAccountId(userAccountId: UserAccountId): Boolean = values.exists(_.userAccountId == userAccountId)

  /** メンバー数を取得する。
    *
    * @return
    *   メンバー数
    */
  def size: Int = values.size

  /** 当該コレクションが空かどうかを判定する。
    *
    * @return 空の場合はtrue
    */
  def isEmpty: Boolean         = values.isEmpty

  /**
   * 当該コレクションが空でないかどうかを判定する。
   *
   * @return 空でない場合はtrue
   */
  def nonEmpty: Boolean        = values.nonEmpty

  /**
   * 当該コレクションをVectorに変換する。
   *
   * @return [[Vector]]
   */
  def toVector: Vector[Member] = values

  /**
   * 指定したユーザアカウントIDが管理者かどうかを判定する。
   *
   * @param userAccountId ユーザアカウントID
   * @return 管理者の場合はtrue
   */
  def isAdministrator(userAccountId: UserAccountId): Boolean =
    values.exists(e => e.userAccountId == userAccountId && e.role == MemberRole.Admin)

  /**
   * 管理者が含まれているかどうかを判定する。
   *
   * @return 管理者が含まれている場合はtrue
   */
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
