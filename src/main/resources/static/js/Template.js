// JavaScript Document

layui.use(['element', 'laypage', 'form', 'util', 'layer', 'flow','table','layedit'], function () {
    try {
        var util = layui.util, layer = layui.layer;
        $(document).ready(function () { //DOM树加载完毕执行，不必等到页面中图片或其他外部文件都加载完毕
            //页面加载完成后，速度太快会导到loading层闪烁，影响体验，所以在此加上500毫秒延迟
            setTimeout(function () { $("#loading").hide(); }, 500);
        });

        //初始化WOW.js
        new WOW().init();

        //导航点击效果
        $('header nav > ul > li a').click(function () {
            $('header nav > ul > li').removeClass("nav-select-this").find("a").removeClass("nav-a-click");
            $(this).addClass("nav-a-click").parent().addClass("nav-select-this");
        });

        //固定块
        util.fixbar({
            css: { right: 10, bottom: 40, },
            bar1: "&#xe602e;",
            click: function (type) {
                if (type === 'bar1') {
                    layer.tab({
                        area: ['300', '290px'],
                        resize: false, 
                        shadeClose: true,                  
                        scrollbar: false,
                        anim: 4,                 
                        tab: [{
                            title: '微信',
                            content: '<img src="" style="width:255px;" oncontextmenu="return false;" ondragstart="return false;" />',
                        },
                        {
                            title: '支付宝',
                            content: '<img src="" style="width:255px;" oncontextmenu="return false;" ondragstart="return false;" />',
                        }],
                        success: function (layero, index) {
                            $("#" + layero[0].id + " .layui-layer-content").css("overflow", "hidden");
                            $("#" + layero[0].id + " .layui-layer-title span").css("padding", "0px");
                            layer.tips('本站收获的所有打赏费用均只用于服务器日常维护以及本站开发用途，感谢您的支持！', "#" + layero[0].id, {
                                tips: [1, '#FFB800'],
                                time: 0, 
                            });
                        },
                        end: function () {
                            layer.closeAll('tips'); 
                        }
                    });
                }
            }
        });

        //使刷新页面后，此页面导航仍然选中高亮显示，自己根据你实际情况修改
        var pathnameArr = window.location.pathname.split("/");
        var pathname = pathnameArr[pathnameArr.length - 1];
            if (pathname.indexOf(".html") < 0)
                pathname += ".html";
        if (!!pathname) {
            $('header nav > ul > li').removeClass("nav-select-this").find("a").removeClass("nav-a-click");
            $('header nav > ul > li').each(function () {
                var hrefArr = $(this).find("a").attr('href').split("/");
                var href = hrefArr[hrefArr.length - 1];
                if (pathname.toLowerCase() === href.toLowerCase()) {
                    $(this).addClass("nav-select-this").find("a").addClass("nav-a-click");
                    return false;
                }
            });
        }

        iniParam();

        //登录图标
        var anim = setInterval(function () {
            if ($(".animated-circles").hasClass("animated")) {
                $(".animated-circles").removeClass("animated");
            } else {
                $(".animated-circles").addClass('animated');
            }
        }, 3000);
        var wait = setInterval(function () {
            $(".livechat-hint").removeClass("show_hint").addClass("hide_hint");
            clearInterval(wait);
        }, 4500);
        $(".livechat-girl").hover(function () {
            clearInterval(wait);
            $(".livechat-hint").removeClass("hide_hint").addClass("show_hint");
        }, function () {
            $(".livechat-hint").removeClass("show_hint").addClass("hide_hint");
        }).click(function () {
            Islogin();
        });

        //设置样式
        function setStyle(flag,user) {
            if (flag) {//登录
                if("1" == user.gender){//男
                    $('.girl').attr("src", "../images/nan.png").css("border-radius", "50px");
                }else {//女
                    $('.girl').attr("src", "../images/nv.png").css("border-radius", "50px");
                }
                $('.rd-notice-content').text('欢迎您，' + user.realName + "!");
                $('.livechat-girl').css({ right: "0px", bottom: "80px" });
            }else {//未登录
                $('.girl').attr("src", "../images/a.png").css("border-radius", "0px");
                $('.livechat-girl').css({ right: "-35px", bottom: "75px" }).removeClass("red-dot");
                $('.rd-notice-content').text('嘿，来试试登录吧！');
                clearInterval(anim);
                return;
            }

        }

        //是否登录
        function Islogin() {
            var userName = _getCookie('userName');
            if(null == userName){
                //登录
                loginPage();
                return;
            }
            var data = {
                "userName": userName
            };
            $.ajax({
                type: 'post',
                dataTtpe: 'json',
                contentType: 'application/json',
                url:'/IsLogin',
                data: JSON.stringify(data),
                success: function (response) {
                    console.log(response);
                    if ("1" == response.status && null != response.data) {
                        if (response.data === 'true') {
                            //window.location.href = "/toLogin?urls=" + window.location.href;
                            userInfo(userName);
                            //setStyle(false);
                        }else {
                            //登录
                            loginPage();
                        }
                    }else {
                        layer.msg('请求失败！');
                    }
                },
                error: function (response) {
                    layer.msg('请求失败！');
                }
            })
        }

        //登录页面
        function loginPage() {
            var content = '<input type="text" name="userName" style="width: 90%; margin: 5% 20px;" lay-verify="title" autocomplete="off" placeholder="请输入登录名" class="layui-input"><input type="password" name="password" style="width: 90%; margin: 5% 20px;" placeholder="请输入密码" autocomplete="off" class="layui-input">';
            layer.open({
                type: 1
                ,title: '登录'
                ,area: ['390px', '240px']
                ,shade: 0.8
                ,id: 'loginPage' //设定一个id，防止重复弹出
                ,btn: ['登录', '取消']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: content
                ,success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    //默认加载当前名称
                    $("input[name='userName']").val($('.userName').text());
                    btn.find('.layui-layer-btn0').click(function () {
                        var userName = $("input[name='userName']").val();
                        var password = $("input[name='password']").val();
                        if("" == userName || null == userName || undefined == userName){
                            layer.msg('登录名不能为空！');
                            return;
                        }
                        if("" == password || null == password || undefined == password){
                            layer.msg('密码不能为空！');
                            return;
                        }
                        login(userName,password);
                    });
                }
            });
        }

        //登录事件
       function login(userName,password) {
            var data = {
                "userName" : userName,
                "password" : password
            };
            $.ajax({
                type: 'post',
                dataTtpe: 'json',
                contentType: 'application/json',
                url:'/Login',
                data: JSON.stringify(data),
                success: function (response) {
                    console.log(response);
                    if ("1" == response.status && null != response.data) {
                        var user = JSON.parse(response.data);
                        //将用户存入cookie
                        _setCookie('userName',user.userName,1);
                        setStyle(true,user);
                    }else {
                        layer.msg('登录失败！');
                    }
                },
                error: function (response) {
                    layer.msg('请求失败！');
                }
            })
        }

        //登录事件
        function userInfo(userName) {
            $.ajax({
                type: 'get',
                dataTtpe: 'text',
                url:'/user/get/name?userName='+userName,
                success: function (response) {
                    console.log(response);
                    if ("1" == response.status && null != response.data) {
                        var user = JSON.parse(response.data);
                        _setCookie('userName',user.userName,1);
                        setStyle(true,user);
                    }else {
                        layer.msg('登录失败！');
                    }
                },
                error: function (response) {
                    layer.msg('请求失败！');
                }
            })
        }

        // 获取cookie
        function _getCookie(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for(var i=0;i < ca.length;i++) {
                var c = ca[i];
                while (c.charAt(0)==' ') c = c.substring(1,c.length);
                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
            }
            return null;
        }

        // 删除cookie
        function _deleteCookie(name) {
            var _this = this;
            _this._setCookie(name,"",-1);
        }

        //赋值cookie
        function _setCookie(name,value,days) {
            if (days) {
                var date = new Date();
                date.setTime(date.getTime()+(days*24*60*60*1000));
                var expires = "; expires="+date.toGMTString();
            }else{
                var expires = "";
            }
            document.cookie = name+"="+value+expires+"; path=/";
        }

        // 获取用户信息
        $(function () {
            var userName = _getCookie('userName');
            if(null == userName){
                return;
            }
            userInfo(userName);
        })
    } catch (e) {
        layui.hint().error(e);
    }         
});

var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
var browserName = "";
//判断是否Safari浏览器
if (userAgent.indexOf("Safari") > 0) {
    browserName = "Safari浏览器";
}
//判断是否Opera浏览器
if (userAgent.indexOf("Opera") > 0) {
    browserName = "Opera浏览器";
};
//判断是否Firefox浏览器
if (userAgent.indexOf("Firefox") > 0) {
    browserName = "Firefox浏览器";
}
//判断是否chorme浏览器
if (userAgent.indexOf("Chrome") > 0) {
    browserName = "Chrome浏览器";
}
//判断是否Edge浏览器
if (userAgent.indexOf("Trident") > 0) {
    browserName = "Edge浏览器";
}
//判断是否浏览器
if (userAgent.indexOf("qqbrowser") > 0) {
    browserName = "QQ浏览器";
}
//判断是否搜狗浏览器
if (userAgent.indexOf("se 2.x") > 0) {
    browserName = "搜狗浏览器";
}


//获取IP和地市
var cityIp = "";
var cityName = "";
$.ajax({
    url: 'http://api.map.baidu.com/location/ip?ak=ia6HfFL660Bvh43exmH9LrI6',
    type: 'POST',
    dataType: 'jsonp',
    success:function(data) {
        console.log(data);
        //获取城市
        cityIp = returnCitySN["cip"];
        cityName = data.content.address_detail.province + data.content.address_detail.city;
    }
});


