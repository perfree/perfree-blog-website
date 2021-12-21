package org.perfree;

import com.perfree.permission.AdminGroup;
import com.perfree.permission.AdminGroups;
import com.perfree.plugin.BasePlugin;
import org.pf4j.PluginWrapper;

@AdminGroups(groups = {
        @AdminGroup(name = "官网管理", groupId = "blogWebsite", icon = "fa-newspaper-o", seq = 3)
})
public class Plugin extends BasePlugin {
    public Plugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}
