package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

/** グループチャットエラー　
  */
enum GroupChatError {

  /** グループチャットが削除済みエラー
    */
  case AlreadyDeletedError(id: GroupChatId)

  /** 実行者が管理者ではないエラー
    */
  case AlreadyExistsNameError(id: GroupChatId)

  /** 既に同じ名前のグループチャットが存在するエラー
    */
  case AlreadyExistsMemberError(id: GroupChatId)

  /** 実行者がメンバーではないエラー
    */
  case NotMemberError(id: GroupChatId, userAccountId: UserAccountId)

  /** 実行者が管理者ではないエラー
    */
  case NotAdministratorError(id: GroupChatId, userAccountId: UserAccountId)

  /** メンバーが存在しないエラー
    */
  case NotFoundMemberError(id: GroupChatId, userAccountId: UserAccountId)

  /** メッセージが存在しないエラー
    */
  case NotFoundMessageError(id: GroupChatId, messageId: MessageId)

  /** 権限エラー
    */
  case NotAuthorError(id: GroupChatId, messageId: MessageId, authorId: UserAccountId)
}
