// JavaScript Document

function iniParam() {
    var device = layui.device();

    //页面效果
    $(".bg-overlay").removeClass("layui-hide");
    $(".bottom-grids a").hover(function () {
        $(this).prev().addClass("icon-hover");
    }, function () {
        $(this).prev().removeClass("icon-hover");
    });

    if ($(window).scrollTop() <= 200) {
        if (!(device.ios || device.android))
            $('header').css("background-color", "transparent");
        $('#suspension').addClass("layui-hide");
        $('.layui-fixbar > li').addClass("layui-hide");
    } 

    $(window).scroll(function () {
        if ($(window).scrollTop() <= 200) {
            if (!(device.ios || device.android))
                $('header').css("background-color", "transparent");
            $('#suspension').addClass("layui-hide");
            $('.layui-fixbar > li').addClass("layui-hide");
        } else {
            $('header').removeAttr("style");
            $('#suspension').removeClass("layui-hide");
            $('.layui-fixbar > li').removeClass("layui-hide");
        }
    });

    //数字滚动
    $('#statistics h4').running();
    //初始化Swiper
    var swiper1 = new Swiper('#swiper1', {
        autoplay: { //可选选项，自动滑动
            disableOnInteraction: false, // 用户操作后，不停止自动切换
        },
        loop: true,//会在原本slide前后复制若干个slide(默认一个)并在合适的时候切换，让Swiper看起来是循环的。
        initialSlide: 0,//设定初始化时slide的索引
        grabCursor: true,//设置为true时，鼠标覆盖Swiper时指针会变成手掌形状，拖动时指针会变成抓手形状。
        slidesPerView: 3,//设置slider容器能够同时显示的slides数量(carousel模式)。
        centeredSlides: true,//设定为true时，active slide会居中，而不是默认状态下的居左。
        breakpoints: { //根据屏幕宽度设置某参数为不同的值
            //当宽度小于等于
            568: {
                slidesPerView: 1,
            },
            768: {
                slidesPerView: 2,
            },
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,//此参数设置为true时，点击分页器的指示点分页器会控制Swiper切换。
            //dynamicBullets: true,//动态分页器，当你的slide很多时，开启后，分页器小点的数量会部分隐藏。
        },
    });

    //最近访客初始化
    var swiper2 = new Swiper('#swiper2', {
        autoplay: { //可选选项，自动滑动
            disableOnInteraction: false, // 用户操作后，不停止自动切换
        },
        loop: true,//会在原本slide前后复制若干个slide(默认一个)并在合适的时候切换，让Swiper看起来是循环的。
        initialSlide: 0,//设定初始化时slide的索引
        grabCursor: true,//设置为true时，鼠标覆盖Swiper时指针会变成手掌形状，拖动时指针会变成抓手形状。
        slidesPerView: 15,//设置slider容器能够同时显示的slides数量(carousel模式)。
        centeredSlides: true,//设定为true时，active slide会居中，而不是默认状态下的居左。
        breakpoints: { //根据屏幕宽度设置某参数为不同的值
            //当宽度小于等于
            568: {
                slidesPerView: 3,
            },
            768: {
                slidesPerView: 5,
            },
            1023: {
                slidesPerView: 7,
            }
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,//此参数设置为true时，点击分页器的指示点分页器会控制Swiper切换。
            dynamicBullets: true,//动态分页器，当你的slide很多时，开启后，分页器小点的数量会部分隐藏。
        },
    });
}

likeArticle();
//查询最近一周访问的用户
function likeArticle() {
    $.ajax({
        type: 'get',
        dataType: 'json',
        url:'/article/get/like',
        success: function (response) {
            console.log(response);
            if ("1" == response.status && null != response.data) {
                var list = JSON.parse(response.data);
                if(list.length>0){
                    var likeArticle = '';
                    for(var i=0; i<list.length; i++){
                        var id = list[i].id;
                        var imgUrl = list[i].imgUrl;
                        var createByName = list[i].createByName;
                        var createTime = list[i].createTime;
                        createTime = formatDate(createTime)
                        var title = list[i].title;
                        if(title.length>15){
                            title = title.substring(0,15)+"...";
                        }
                        var content = list[i].content;
                        if(content.length>65){
                            content = content.substring(0,65)+"...";
                        }
                        var articleInfo = '<div class="layui-col-md4 layui-col-sm6 layui-col-xs12">'
                            + '<article class="card">'
                            + '<a href="/front/ArticleDetail?id=' +id+ '">'
                            + '<img class="card-img-top" src="' +imgUrl+ '" alt=""></a>'
                            + '<div class="card-body">'
                            + '<div class="card-subtitle mb-2 text-muted">作者：' +createByName+ ' &nbsp;&nbsp;' +createTime+ '</div>'
                            + '<h4 class="card-title"><a href="/front/ArticleDetail?id=' +id+ '">' +title+ '</a></h4>'
                            + '<p class="card-text">' +content+ '</p>'
                            + '<div class="text-right">'
                            + '<a href="/front/ArticleDetail?id='+id+'" class="card-more">阅读更多&nbsp;<i class="layui-icon layui-icon-right"></i></a>'
                            + '</div>'
                            + '</div>'
                            + '</article>'
                            + '</div>';
                        likeArticle += articleInfo;
                    }
                    likeArticle += '<span class="border-line"></span>';
                    $('.blog-body').html(likeArticle);
                }
            }else {
                layer.msg('请求失败！'+response);
            }
        },
        error: function (response) {
            layer.msg('请求失败！'+response);
        }
    })
}

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

hotCommentUser();
//查询最近一周访问的用户
function hotCommentUser() {
    $.ajax({
        type: 'get',
        dataType: 'json',
        url:'/user/get/hot',
        success: function (response) {
            console.log(response);
            if ("1" == response.status && null != response.data) {
                var list = JSON.parse(response.data);
                if(list.length>0){
                    var hotReview = '';
                    for(var i=0; i<list.length; i++){
                        var userInfo = '<div class="layui-col-md4 layui-col-sm4">'
                            + '<svg class="icon" aria-hidden="true">'
                            + '<use xlink:href="#layui-extend-jiangbei-' +(i+1)+ '"></use>'
                            + '</svg>'
                            + '<a href="#" target="_blank" rel="nofollow">'
                            + '<img class="layui-circle" src="' +list[i].headshot+' " alt="">'
                            + '<h4 class="layui-elip">' +list[i].userName+ '</h4>'
                            + '</a>'
                            + '<p>'
                            + ' 本站评论数：<span class="layui-badge layui-bg-orange">' +list[i].count+ '</span>'
                            + '</p>'
                            + '</div>';
                        hotReview += userInfo;
                    }
                    hotReview += '<span class="border-line"></span>';
                    $('.hot-review').find('.layui-row').html(hotReview);
                }
            }else {
                layer.msg('请求失败！'+response);
            }
        },
        error: function (response) {
            layer.msg('请求失败！'+response);
        }
    })
}


recentUser();
//查询最近一周访问的用户
function recentUser() {
    $.ajax({
        type: 'get',
        dataType: 'json',
        url:'/user/get/recent?pageNo=1&pageSize=15',
        success: function (response) {
            console.log(response);
            if ("1" == response.status && null != response.data) {
                var size = response.data.size;
                if(size>0){
                    var list = response.data.list;
                    var swiperWrapper = '';
                    for(var i=0; i<size; i++){
                        var userInfo = '<div class="swiper-slide">'
                            + '<a href="#" target="_blank" rel="nofollow">'
                            + '<img class="layui-circle" src="' +list[i].headshot+ '" alt="">'
                            + '<h4 class="layui-elip">' +list[i].userName+ '</h4>'
                            + '</a>'
                            + '</div>';
                        swiperWrapper += userInfo;
                    }
                    $('#swiper2 .swiper-wrapper').html(swiperWrapper);
                }
            }else {
                layer.msg('请求失败！'+response);
            }
        },
        error: function (response) {
            layer.msg('请求失败！'+response);
        }
    })
}

