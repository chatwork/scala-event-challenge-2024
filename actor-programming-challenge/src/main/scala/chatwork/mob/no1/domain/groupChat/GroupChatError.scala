package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

/** グループチャットエラー　
  */
enum GroupChatError {
  case AlreadyDeletedError(id: GroupChatId)
  case AlreadyExistsNameError(id: GroupChatId)
  case AlreadyExistsMemberError(id: GroupChatId)
  case NotMemberError(id: GroupChatId, userAccountId: UserAccountId)
  case NotAdministratorError(id: GroupChatId, userAccountId: UserAccountId)
  case NotFoundMemberError(id: GroupChatId, userAccountId: UserAccountId)
  case NotFoundMessageError(id: GroupChatId, messageId: MessageId)
  case NotAuthorError(id: GroupChatId, messageId: MessageId, authorId: UserAccountId)
}
