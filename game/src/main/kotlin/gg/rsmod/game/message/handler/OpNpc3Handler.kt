package gg.rsmod.game.message.handler

import gg.rsmod.game.action.PawnPathAction
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpNpc3Message
import gg.rsmod.game.model.attr.INTERACTING_NPC_ATTR
import gg.rsmod.game.model.attr.INTERACTING_OPT_ATTR
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.priv.Privilege
import java.lang.ref.WeakReference

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpNpc3Handler : MessageHandler<OpNpc3Message> {

    override fun handle(client: Client, message: OpNpc3Message) {
        val npc = client.world.npcs.get(message.index) ?: return

        if (!client.lock.canNpcInteract()) {
            return
        }

        log(client, "Npc option 3: index=%d, movement=%d, npc=%s", message.index, message.movementType, npc)

        if (message.movementType == 1 && client.world.privileges.isEligible(client.privilege, Privilege.ADMIN_POWER)) {
            client.teleport(client.world.findRandomTileAround(npc.tile, 1) ?: npc.tile)
        }

        client.closeInterfaceModal()
        client.interruptPlugins()
        client.resetInteractions()

        client.attr[INTERACTING_OPT_ATTR] = 3
        client.attr[INTERACTING_NPC_ATTR] = WeakReference(npc)
        client.executePlugin(PawnPathAction.walkPlugin)
    }
}