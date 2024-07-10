package chatwork.mob.no1.interfaceAdaptor.groupChat

import chatwork.mob.no1.domain.groupChat._
import chatwork.mob.no1.domain.userAccount.UserAccountId
import chatwork.mob.no1.interfaceAdaptor.groupChat.GroupChatProtocol._
import org.apache.pekko.actor.typed.ActorRef
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

/** グループチャットの集約
  *
  * 集約とは、複数のエンティティや値オブジェクトをまとめて、一つのオブジェクトとして扱うことです。永続化は集約単位で行われます。
  * また、集約は一貫性の境界を持ちます。集約内のエンティティや値オブジェクトは、集約のルートエンティティによって管理されます。 この実装では、GroupChatAggregateが集約のルートエンティティとなります。
  */
object GroupChatAggregate {

  def apply(id: GroupChatId): Behavior[GroupChatCommand] =
    Behaviors.setup { ctx =>
      ctx.log.debug("GroupChatAggregate started: {}", id)
      notCreated(id)
    }

  private def notCreated(id: GroupChatId): Behavior[GroupChatCommand] = {
    Behaviors.receiveMessage {
      case CreateGroupChat(id, name, ownerId, replyTo) =>
        val state = GroupChat.create(id, name, Members(Member(MemberId.generate(), ownerId, MemberRole.Admin)))
        replyTo ! CreateGroupChatSuccess
        created(state)
      case _ =>
        Behaviors.ignore
    }
  }

  private def created(state: GroupChat): Behavior[GroupChatCommand] = {
    Behaviors.receiveMessage {
      case DeleteGroupChat(id, replyTo) if id == state.id =>
        deleted(id)
      case RenameGroupChat(id, name, executorId, replyTo) if id == state.id =>
        state
          .rename(name, executorId).fold(
            error =>
              replyTo ! RenameGroupChatFailure(error)
              Behaviors.same
            ,
            newState =>
              replyTo ! RenameGroupChatSuccess
              created(newState)
          )
      case AddMember(id, userAccountId, role, executorId, replyTo) if id == state.id =>
        val memberId = MemberId.generate()
        state
          .addMember(memberId, userAccountId, role, executorId).fold(
            error =>
              replyTo ! AddMemberFailure(error)
              Behaviors.same
            ,
            newState =>
              replyTo ! AddMemberSuccess(memberId)
              created(newState)
          )
      case RemoveMember(id, userAccountId, executorId, replyTo) if id == state.id =>
        state
          .removeMember(userAccountId, executorId).fold(
            error =>
              replyTo ! RemoveMemberFailure(error)
              Behaviors.same
            ,
            newState =>
              replyTo ! RemoveMemberSuccess(userAccountId)
              created(newState)
          )
      // 課題1
      case PostMessage(id, messageBody, senderId, replyTo) if id == state.id =>
        val messageId = MessageId.generate()
        state
          .postMessage(messageId, messageBody, senderId).fold(
            error =>
              replyTo ! PostMessageFailure(error)
              Behaviors.same
            ,
            newState =>
              replyTo ! PostMessageSuccess(messageId)
              created(newState)
          )
      // 課題2
      case EditMessage(id, messageId, newMessageBody, editorId, replyTo) if id == state.id =>
        // TODO: Edit message
        Behaviors.same
      // 課題3
      case DeleteMessage(id, messageId, deleterId, replyTo) if id == state.id =>
        // TODO: Delete message
        Behaviors.same
      case _ =>
        Behaviors.ignore
    }
  }

  private def deleted(id: GroupChatId): Behavior[GroupChatCommand] = {
    Behaviors.receiveMessage { _ =>
      Behaviors.ignore
    }
  }

}
