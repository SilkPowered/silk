package org.bukkit.unrealized.craftbukkit.help;

import org.bukkit.unrealized.command.MultipleCommandAlias;
import org.bukkit.unrealized.help.HelpTopic;
import org.bukkit.unrealized.help.HelpTopicFactory;

/**
 * This class creates {@link MultipleCommandAliasHelpTopic} help topics from {@link MultipleCommandAlias} commands.
 */
public class MultipleCommandAliasHelpTopicFactory implements HelpTopicFactory<MultipleCommandAlias> {

    @Override
    public HelpTopic createTopic(MultipleCommandAlias multipleCommandAlias) {
        return new MultipleCommandAliasHelpTopic(multipleCommandAlias);
    }
}
