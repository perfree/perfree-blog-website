let form, layer;
layui.config({
    base: '/static/public/libs/layuiComponents/'
}).extend({
    xmSelect: 'xm-select/xm-select'
})
layui.use(['layer', 'form', 'xmSelect', 'element'], function () {
    element = layui.element;
    form = layui.form;
    layer = layui.layer;
    layer.config({
        offset: '20%'
    });
    initEditor($("#editorMode").val(), $("#articleContent").val());
    initEvent();
});

/**
 * 初始化页面事件
 */
function initEvent() {
    // 表单验证
    form.verify({});
    // 表单提交
    form.on('submit(draftForm)', function (data) {
        data.field.status = 1;
        submitArticle(data.field);
        return false;
    });
    form.on('submit(publishForm)', function (data) {
        data.field.status = 0;
        submitArticle(data.field);
        return false;
    });
}

/**
 * 提交文章
 * @param data
 */
function submitArticle(data) {
    if ($("#editorMode").val() === "markdown") {
        data.content = markdownEditor.getMarkdown();
    } else {
        data.content = richEditor.getData();
    }
    data.contentModel = $("#editorMode").val();
    data.isComment = data.isComment === 'on' ? 1 : 0;
    data.payType = data.payType === 'on' ? 1 : 0;
    $.ajax({
        type: "POST",
        url: "/admin/website/article/update",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (d) {
            if (d.code === 200) {
                location.reload();
                parent.layer.msg("修改成功", {icon: 1})
                parent.toPage('/admin/website/plugin');
                parent.element.tabDelete('tabNav', 'website-plugin-update');
            } else {
                layer.msg(d.msg, {icon: 2});
            }
        },
        error: function (data) {
            layer.msg("修改失败", {icon: 2});
        }
    });
}

/**
 * 动态修改PageUrl
 */
function updatePageUrl(){
    let slug = $("#slug").val();
    if (slug !== null && slug !== undefined &&　slug　!== '') {
        $("#pageUrl").html(slug);
    } else {
        $("#pageUrl").html('[ID]');
    }
}