# SekC-Physics
A mod which will add various features simulating physics inside of mc.

Potentially will support addons so not all features have to be included and other modders can make features using available resources.

Ragdolls will be generated on startup based on the mods installed. So if you are a mod author and want your mobs to work with this mod feel free to contact me or go ahead and add the data yourself if you want to.

Run VM args `-Dfml.coreMods.load=com.sekwah.sekcphysics.asm.SekCore`

Names for compiled http://export.mcpbot.bspk.rs/

MCPBot http://mcpbot.bspk.rs/help

IRC https://esper.net/publicirc.php #mcpbot

Todo (A list for me really also shows plans)
--------
Rotation constraints (Just basic min angle between two points at least)

Different point sizes in the ragdoll data to better suit stuff.

Make tools as a plugin into the SekCAnims editor

Armour tracking (check LayerBipedArmor) Need to find a way to render the armour with extra constructs and the ability for the custom forge model armour

Update rotations based off model animations and not soley head rotation.

Update for two things to name locations or a transfer for names because of dev environments changing to like field_178720_f

Location of mappings
~/.gradle/caches/minecraft/de/oceanlabs/mcp/mcp_snapshot/20171003/1.12.2/srgs