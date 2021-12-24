package org.perfree.service;

import com.perfree.plugin.PluginEvent;
import org.perfree.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 插件事件
 * 启动,更新,卸载,安装示例
 */
@Service
public class PluginInitService implements PluginEvent {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void onStart() {
        System.out.println("onStart");
    }

    @Override
    public void onUpdate() {
        System.out.println("onUpdate");
    }

    @Override
    public void onInstall() {
        articleMapper.initTable();
    }

    @Override
    public void onUnInstall() {
        articleMapper.removeTable();
    }
}
