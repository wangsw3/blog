// JavaScript Document

function iniParam() {
    var form = layui.form,laypage = layui.laypage,layedit = layui.layedit;
	
 	layer.photos({
		photos: '.content',
		anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
	});   
	
	
	//评论和留言的编辑器
	for(var i=1;i<9;i++){
		layedit.build('demo-'+i.toString(), {
			height: 150,
			tool: ['face', '|', 'link'],
		});
	}
	

	$(".btn-reply").click(function(){
		 if ($(this).text() == '回复') {
       		$(this).parent().next().removeClass("layui-hide");
        	$('.btn-reply').text('回复');
		    $(this).text('收起');
		}
		else {
			$(this).parent().next().addClass("layui-hide");
			$(this).text('回复');
		}
	});

    //时间格式化
    function formatDate(time) {
        var date=new Date(time);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return  y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d) + " " + hour + ":" + minutes + ":" + seconds;
    }

    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

    //查询文章详情
    getArticleById();
    function getArticleById() {
		var id= getUrlParam('id');
        $.ajax({
            type: 'get',
            dataType: 'json',
            url:'/article/get/id?id='+id,
            success: function (response) {
                console.log(response);
                if ("1" == response.status && null != response.data) {
                    var article = JSON.parse(response.data);
                    $('.title').text(article.title);
                    $('.className').text(article.className);
                    $('.createByName').text(article.createByName);
                    $('.createTime').text(formatDate(article.createTime));
                    $('.readNum').text(article.readNum);
                    $('.commentNum').text(article.commentNum);
                    $('.likeNum').text(article.likeNum);
                    $('.articleContent').html(article.content);
                }else {
                    layer.msg('请求失败！'+response);
                }
            },
            error: function (response) {
                layer.msg('请求失败！'+response);
            }
        })

        //上一篇
        $.ajax({
            type: 'get',
            dataType: 'json',
            url:'/article/get/id?id='+(parseInt(id)-1),
            success: function (response) {
                console.log(response);
                if ("1" == response.status && null != response.data) {
                    var article = JSON.parse(response.data);
                    var article = JSON.parse(response.data);
                    var txt = '上一篇：<a target="_blank" href="/front/ArticleDetails?id='
                        + article.id + '">'
                        + article.title + '</a>';
                    $('.prev').html(txt);
                }
            },
            error: function (response) {
                layer.msg('请求失败！'+response);
            }
        })

        //下一篇
        $.ajax({
            type: 'get',
            dataType: 'json',
            url:'/article/get/id?id='+(parseInt(id)+1),
            success: function (response) {
                console.log(response);
                if ("1" == response.status && null != response.data) {
                    var article = JSON.parse(response.data);
                    var txt = '下一篇：<a target="_blank" href="/front/ArticleDetails?id='
                        + article.id + '">'
                        + article.title + '</a>';
                    $('.next').html(txt);

                }
            },
            error: function (response) {
                layer.msg('请求失败！'+response);
            }
        })
    }

	//我用的百度编辑器，按照你们自己需求改
	CodeHighlighting(); //代码高亮
    function CodeHighlighting() {
        //添加code标签
        var allPre = document.getElementsByTagName("pre");
        for (i = 0; i < allPre.length; i++) {
            var onePre = document.getElementsByTagName("pre")[i];
            var myCode = document.getElementsByTagName("pre")[i].innerHTML;
            onePre.innerHTML = '<div class="pre-title">Code</div><code class="' + onePre.className.substring((onePre.className.indexOf(":") + 1), onePre.className.indexOf(";")) + '">' + myCode + '</code>';
        }
        //添加行号
        $("code").each(function () {
            $(this).html("<ol><li>" + $(this).html().replace(/\n/g, "\n</li><li>") + "\n</li></ol>");
        });
		hljs.initHighlighting(); //对页面上的所有块应用突出显示
        //hljs.initHighlightingOnLoad(); //页面加载时执行代码高亮
    }
}

