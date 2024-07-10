package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId
import org.scalatest.freespec.AnyFreeSpec

class GroupChatSpec extends AnyFreeSpec with EitherValues {
  "GroupChat" - {
    "create" in {
      val id        = GroupChatId.generate()
      val name      = GroupChatName("name")
      val adminId   = UserAccountId.generate()
      val groupChat = GroupChat.create(id, name, Members.singleAdmin(adminId))
      assert(groupChat.id == id)
      assert(groupChat.name == name)
      assert(groupChat.members.containsByUserAccountId(adminId))
      assert(groupChat.members.isAdministrator(adminId))
    }
    "rename" in {
      val id               = GroupChatId.generate()
      val name             = GroupChatName("name")
      val adminId          = UserAccountId.generate()
      val groupChat        = GroupChat.create(id, name, Members.singleAdmin(adminId))
      val newName          = GroupChatName("newName")
      val renamedGroupChat = groupChat.rename(newName, adminId).getOrElse(fail("rename failed"))
      assert(renamedGroupChat.name == newName)
    }
    "addMember" in {
      val id            = GroupChatId.generate()
      val name          = GroupChatName("name")
      val adminId       = UserAccountId.generate()
      val groupChat     = GroupChat.create(id, name, Members.singleAdmin(adminId))
      val userAccountId = UserAccountId.generate()
      val addedGroupChat = groupChat
        .addMember(MemberId.generate(), userAccountId, MemberRole.Member, adminId).getOrElse(fail("addMember failed"))
      assert(addedGroupChat.members.containsByUserAccountId(userAccountId))
    }
    "removeMember" in {
      val id            = GroupChatId.generate()
      val name          = GroupChatName("name")
      val adminId       = UserAccountId.generate()
      val groupChat     = GroupChat.create(id, name, Members.singleAdmin(adminId))
      val userAccountId = UserAccountId.generate()
      val addedGroupChat = groupChat
        .addMember(MemberId.generate(), userAccountId, MemberRole.Member, adminId).getOrElse(fail("addMember failed"))
      val removedGroupChat = addedGroupChat.removeMember(userAccountId, adminId).getOrElse(fail("removeMember failed"))
      assert(!removedGroupChat.members.containsByUserAccountId(userAccountId))
    }
    // 課題1
    "postMessage" in {
      val id        = GroupChatId.generate()
      val name      = GroupChatName("name")
      val adminId   = UserAccountId.generate()
      val groupChat = GroupChat.create(id, name, Members.singleAdmin(adminId))
      val messageId = MessageId.generate()
      val postedGroupChat =
        groupChat.postMessage(messageId, MessageBody("message"), adminId).getOrElse(fail("postMessage failed"))
      assert(postedGroupChat.messages.containsByMessageId(messageId))
    }
    // 課題2
    "editMessage" in {
      val id = GroupChatId.generate()
      val name = GroupChatName("name")
      val adminId = UserAccountId.generate()
      val groupChat = GroupChat.create(id, name, Members.singleAdmin(adminId))
      val messageId = MessageId.generate()
      val postedGroupChat =
        groupChat.postMessage(messageId, MessageBody("message"), adminId).getOrElse(fail("postMessage failed"))
      assert(postedGroupChat.messages.containsByMessageId(messageId))

      val updatedBody = MessageBody("updated")

      postedGroupChat.editMessage(messageId, updatedBody, adminId)
      assert()
    }
    // 課題3
    "deleteMessage" in {
      // TODO: Delete message
    }
  }
}
