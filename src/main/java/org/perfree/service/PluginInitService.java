package org.perfree.service;

import com.perfree.plugin.BasePluginEvent;
import com.perfree.plugin.PluginEvent;
import org.perfree.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 插件事件
 * 启动,更新,卸载,安装示例
 */
@Service
public class PluginInitService implements BasePluginEvent {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onUpdate() {
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
